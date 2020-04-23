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
@Api(description = "Queen cycle")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "Queen cycle", notes = "Queen cycle", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class)
    @GetMapping(value = "/queen-cycle")
    public BaseResult<?> listStockLimitDay(
            @ApiParam(defaultValue = "20200301") @RequestParam String startDate,
            @ApiParam(defaultValue = "20200401") @RequestParam String endDate
    ) {
        return BaseResultUtils.buildBaseResult(stockService.listStockLimitDay(startDate, endDate));
    }

    @ApiOperation(value = "Trade day detail", notes = "Trade day detail", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public BaseResult<?> tradeDayDetail(@ApiParam(defaultValue = "20200301") @RequestParam String tradeDay
    ) {
        return BaseResultUtils.buildBaseResult(stockService.listStock(tradeDay));
    }

    @ApiOperation(value = "stock turnvoer", notes = "stock turnvoer", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = ResponseEntity.class)
    @RequestMapping(value = "/turnover", method = RequestMethod.GET)
    public BaseResult<?> listStockTurnover(@ApiParam(defaultValue = "sh600000") @RequestParam String stockCode,
                                           @ApiParam(defaultValue = "2019-04-20") @RequestParam String startDateStr) {
        return BaseResultUtils.buildEmptyBaseResult();
    }
}
