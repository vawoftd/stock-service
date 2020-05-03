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
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StockServiceImpl implements StockService {
    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

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
            if (!CollectionUtils.isEmpty(limitUp)) {
                limitUp.forEach(u -> {
                    Stock stock = JSON.parseObject(u.toString(), Stock.class);
                    if (sld.getHighestLimit() < stock.getLimitNum()) {
                        sld.setHighestLimit(stock.getLimitNum());
                        sld.setHighestTsCode(stock.getTsCode());
                        sld.setHighestTsName(stock.getName());
                    }
                    if (stock.getLimitNum() > 1) {
                        sld.setContinuousLimit(sld.getContinuousLimit() + 1);
                    }
                });
                sld.setLimitUpCount(limitUp.size());
                List<Object> limitDown = redisTemplate.opsForHash().values(String.format(StockServiceConstant.STOCK_LIMIT_DOWN, d));
                sld.setLimitDownCount(limitDown.size());
                list.add(sld);
            }
        });
        return list;
    }

    @Override
    public List<Stock> listStock(String tradeDay, int type) {
        String key;
        if(type > 0){
            key = String.format(StockServiceConstant.STOCK_LIMIT_UP, tradeDay);
        }else{
            key = String.format(StockServiceConstant.STOCK_LIMIT_DOWN, tradeDay);
        }
        ArrayList<Stock> list = Lists.newArrayList();
        List<String> values = redisTemplate.<String, String>opsForHash().values(key);
        for (String u : values) {
            Stock stock = JSON.parseObject(u, Stock.class);
            if (type == 0) {
                list.add(stock);
            } else if (type == 1) {
                //今日涨停
                list.add(stock);
            } else{
                //今日连版
                if (stock.getLimitNum() > 1) {
                    list.add(stock);
                }
            }
        }
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
}
