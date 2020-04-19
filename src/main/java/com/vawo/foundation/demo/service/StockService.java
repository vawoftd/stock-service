package com.vawo.foundation.demo.service;

import com.vawo.foundation.demo.entity.StockLimitDay;

import java.util.List;

public interface StockService {
    List<StockLimitDay> listStockLimitDay(String startDate, String endDate);
}
