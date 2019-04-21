package com.vawo.foundation.demo.service;

import com.vawo.foundation.demo.entity.StockExtent;

import java.util.List;

public interface StockService {
    /**
     * @param stockCode
     * @return
     */
    StockExtent getData(String stockCode);

    void allStock();

    List<StockExtent> calPercent(String startDate, int top);

    void colectStockData();

}
