package com.vawo.foundation.demo;

import com.vawo.foundation.demo.entity.Stock;
import com.vawo.foundation.demo.entity.StockRecord;
import com.vawo.foundation.demo.service.StockService;
import com.vawo.foundation.demo.utils.MailUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StockServiceApplication.class)//这里是启动类
public class TestStockService {
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRecordMapper stockRecordMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private MailUtils mailUtils;

    //    @Test
    public void testAllStock() {
        stockService.allStock();
    }

    //    @Test
    public void testGetData() {
        List<StockExtent> lse = new ArrayList<>();
        List<Stock> stocks = stockMapper.selectAll();
        int num = 0;
        for (Stock is : stocks) {
            StockExtent extent = stockService.collectData(is.getStockCode());
            extent.setStockName(is.getStockName());
            lse.add(extent);
            try {
                double random = Math.random();
                long times = 500 + (long) (random * 1000L);
                Thread.sleep(times);
                if (num % 50 == 0) {
                    System.out.println(num);
                    Thread.sleep(180000 + (long) (random * 30000));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            num++;
        }
        Collections.sort(lse);
        for (StockExtent se : lse) {
            System.out.println(se.getStockName() + " " + se.getPercent() + " " + se.getPrice());
        }
    }

    //    @Test
    public void testCall() {
        stockService.calPercent("2019-03-28 00:00:00", 100, "desc");
    }

    //        @Test
    public void testGetDataCall() {
        stockService.collectData("sz300347");
    }

    //        @Test
    public void testReadMail() {
        mailUtils.readMail();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> cmap = new ConcurrentHashMap<>();
    }

    // @Test
    public void testStatisticsTurnover() {
        try {
            Date date = StockServiceConstant.DATE_FORMAT_YMDHMS.parse("2019-04-30 00:00:00");
            List<StockRecord> srs = stockRecordMapper.selectByDate("sh600000", date);
            stockService.statisticsTurnover(srs);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
