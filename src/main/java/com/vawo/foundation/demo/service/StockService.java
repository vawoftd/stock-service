package com.vawo.foundation.demo.service;

import com.vawo.foundation.demo.entity.StockExtent;
import com.vawo.foundation.demo.entity.StockRecord;
import com.vawo.foundation.demo.entity.TurnoverDay;

import java.util.List;

public interface StockService {
    /**
     * @param stockCode
     * @return
     */
    StockExtent collectData(String stockCode);

    void statisticsTurnover(List<StockRecord> srs);

    void allStock();

    List<StockExtent> calPercent(String startDate, int top, String sort);

    List<TurnoverDay> listStockTurnover(String stockCode, String startDateStr);
}
