package com.vawo.foundation.stock.service;


import com.vawo.foundation.stock.entity.Stock;
import com.vawo.foundation.stock.entity.StockLimitDay;

import java.util.List;

public interface StockService {
    List<StockLimitDay> listStockLimitDay(String startDate, String endDate);

    List<Stock> listStock(String tradeDay);
}
