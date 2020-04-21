package com.vawo.foundation.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.vawo.foundation.stock.StockServiceConstant;
import com.vawo.foundation.stock.entity.Stock;
import com.vawo.foundation.stock.entity.StockLimitDay;
import com.vawo.foundation.stock.service.StockService;
import com.vawo.foundation.stock.utils.rest.BaseResultRestClient;
import com.vawo.foundation.stock.vo.TushareParam;
import com.vawo.foundation.stock.vo.TushareResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StockServiceImpl implements StockService {
    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private BaseResultRestClient restClient;

    protected RestTemplate restTemplate = new RestTemplate();

    private static final String token = "724d638ffc9ca97a8b631f10e607d5d37c7f6d1699158ffa4e75579c";

    @Override
    public List<StockLimitDay> listStockLimitDay(String startDate, String endDate) {
        List<String> days = getCal(startDate, endDate);
        ArrayList<StockLimitDay> list = Lists.newArrayList();
        days.forEach(d -> {
            StockLimitDay sld = new StockLimitDay();
            sld.setTradeDate(d);
            List<Object> limitUp = redisTemplate.opsForHash().values(String.format(StockServiceConstant.STOCK_LIMIT_UP, d));
            limitUp.forEach(u -> {
                Stock stock = JSON.parseObject(u.toString(), Stock.class);
                if (sld.getHighestLimit() < stock.getLimitNum()) {
                    sld.setHighestLimit(stock.getLimitNum());
                }
                if (stock.getLimitNum() > 1) {
                    sld.setContinuousLimit(sld.getContinuousLimit() + 1);
                }
            });
            sld.setLimitUpCount(limitUp.size());
            List<Object> limitDown = redisTemplate.opsForHash().values(String.format(StockServiceConstant.STOCK_LIMIT_DOWN, d));
            sld.setLimitDownCount(limitDown.size());
            list.add(sld);
        });
        return list;
    }

    public List<String> getCal(String startDate, String endDate) {
        String url = "http://api.waditu.com";
        TushareParam param = new TushareParam("trade_cal", token);
        param.addParam("exchange", "SSE");
        param.addParam("start_date", startDate);
        param.addParam("end_date", endDate);
        param.addParam("is_open", "1");
        param.setFields("cal_date");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<TushareParam> entity = new HttpEntity<>(param, headers);
        ResponseEntity<TushareResponse> result;
        List<String> list = Lists.newArrayList();
        try {
            result = restTemplate.exchange(url, HttpMethod.POST, entity, TushareResponse.class);
            List<List<Object>> items = Objects.requireNonNull(result.getBody()).getData().getItems();
            items.forEach(i -> {
                Object o = i.get(0);
                list.add(o.toString());
            });
        } catch (Exception e) {
            logger.error("[Error] >>> error msg: {}", e.getMessage());
        }
        return list;
    }

    private List<String> getDays(String startTime, String endTime) {
        // 返回的日期集合
        List<String> days = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }
}
