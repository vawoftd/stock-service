package com.vawo.foundation.stock.controller;

import com.vawo.foundation.stock.service.StockService;
import com.vawo.foundation.stock.utils.result.BaseResult;
import com.vawo.foundation.stock.utils.result.BaseResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stock")
@Api(description = "服务接口")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "获取数据", notes = "获取数据", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class)
    @GetMapping(value = "/limit")
    public BaseResult<?> listStockLimitDay(
            @ApiParam(defaultValue = "20200301") @RequestParam String startDate,
            @ApiParam(defaultValue = "20200401") @RequestParam String endDate
    ) {
        return BaseResultUtils.buildBaseResult(stockService.listStockLimitDay(startDate, endDate));
    }

    @ApiOperation(value = "stock extent", notes = "stock extent", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class)
    @RequestMapping(value = "/extent", method = RequestMethod.GET)
    public BaseResult<?> calPercent(@ApiParam(defaultValue = "2019-04-20") @RequestParam String startDate,
                                    @ApiParam(defaultValue = "20") @RequestParam Integer top,
                                    @ApiParam(defaultValue = "asc", allowableValues = "asc, desc") @RequestParam String sort) {
        startDate = startDate + " 00:00:00";
        return BaseResultUtils.buildEmptyBaseResult();
    }

    @ApiOperation(value = "stock turnvoer", notes = "stock turnvoer", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class)
    @RequestMapping(value = "/turnover", method = RequestMethod.GET)
    public BaseResult<?> listStockTurnover(@ApiParam(defaultValue = "sh600000") @RequestParam String stockCode,
                                           @ApiParam(defaultValue = "2019-04-20") @RequestParam String startDateStr) {
        return BaseResultUtils.buildEmptyBaseResult();
    }
}
