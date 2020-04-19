package com.vawo.foundation.demo.service.impl;

import com.google.common.collect.Lists;
import com.vawo.foundation.demo.entity.StockLimitDay;
import com.vawo.foundation.demo.service.StockService;
import com.vawo.foundation.demo.utils.rest.BaseResultRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private BaseResultRestClient restClient;

    @Override
    public List<StockLimitDay> listStockLimitDay(String startDate, String endDate) {
        ArrayList<StockLimitDay> list = Lists.newArrayList();
        return list;
    }

    public void getCal(){
//        String url = baseUrl + "/composite-search/capture/select/query";
//        BaseResult<CaptureSelectQueryResp<Capture>> baseResult;
//        CaptureSelectQueryResp<Capture> result = null;
//        try {
//            result = restClient.baseResultRequest(url, new TypeReference<CaptureSelectQueryResp<Capture>>() {
//            })
//                    .body(param)
//                    .param(user)
//                    .build()
//                    .post();
//            result.getCaptureGroups().forEach(cg -> cg.getCaptures().forEach(c -> mapCaptureTime(c, false)));
//            baseResult = BaseResultUtils.buildBaseResult(result);
//        } catch (BaseResultRestClientException e) {
//            baseResult = e.getBaseResult();
//        }
    }
}
