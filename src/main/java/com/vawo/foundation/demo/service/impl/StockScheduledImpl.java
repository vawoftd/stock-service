package com.vawo.foundation.demo.service.impl;

import com.vawo.foundation.demo.dao.StockMapper;
import com.vawo.foundation.demo.entity.Stock;
import com.vawo.foundation.demo.service.StockScheduled;
import com.vawo.foundation.demo.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockScheduledImpl implements StockScheduled {

    private static final Logger logger = LoggerFactory.getLogger(StockScheduledImpl.class);

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private StockService stockService;

    @Async
    @Scheduled(cron = "0 30 0 * * ?")
    public void collectStockData() {
        logger.info("collect stock data ...");
        List<Stock> stocks = stockMapper.selectAll();
        int num = 1;
        for (Stock is : stocks) {
            stockService.collectData(is.getStockCode());
            try {
                double random = Math.random();
                long times = 1000 + (long) (random * 1000L);
                logger.info("collect stock data: {}, sleep: {}", is.getStockCode(), times);
                Thread.sleep(times);
                if (num % 50 == 0) {
                    Thread.sleep(180000 + (long) (random * 30000));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            num++;
        }
    }
}
