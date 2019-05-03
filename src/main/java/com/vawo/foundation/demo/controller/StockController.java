package com.vawo.foundation.demo.controller;

import com.alibaba.fastjson.JSON;
import com.vawo.foundation.demo.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("stock")
@Api(description = "服务接口")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "获取数据", notes = "获取数据", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class)
    @RequestMapping(value = "/collect", method = RequestMethod.GET)
    public ResponseEntity<?> collect(@RequestParam String stockId) {
        return new ResponseEntity<>(stockService.collectData(stockId), HttpStatus.OK);
    }

    @ApiOperation(value = "stock extent", notes = "stock extent", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class)
    @RequestMapping(value = "/extent", method = RequestMethod.GET)
    public ResponseEntity<?> calPercent(@ApiParam(defaultValue = "2019-04-20") @RequestParam String startDate,
                                        @ApiParam(defaultValue = "20") @RequestParam Integer top,
                                        @ApiParam(defaultValue = "asc", allowableValues="asc, desc") @RequestParam String sort) {
        startDate = startDate + " 00:00:00";
        return new ResponseEntity<>(JSON.toJSONString(stockService.calPercent(startDate, top, sort)), HttpStatus.OK);
    }

    @ApiOperation(value = "stock turnvoer", notes = "stock turnvoer", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class)
    @RequestMapping(value = "/turnover", method = RequestMethod.GET)
    public ResponseEntity<?> listStockTurnover(@ApiParam(defaultValue = "sh600000") @RequestParam String stockCode,
                                               @ApiParam(defaultValue = "2019-04-20") @RequestParam String startDateStr) {
        return new ResponseEntity<>(JSON.toJSONString(stockService.listStockTurnover(stockCode, startDateStr)), HttpStatus.OK);
    }
}
