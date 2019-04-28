package com.vawo.foundation.demo;

import com.vawo.foundation.demo.dao.InfoStockMapper;
import com.vawo.foundation.demo.entity.InfoStock;
import com.vawo.foundation.demo.entity.StockExtent;
import com.vawo.foundation.demo.service.StockService;
import com.vawo.foundation.demo.utils.MailUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StockServiceApplication.class)//这里是启动类
public class TestStockService {
    @Autowired
    private StockService stockService;

    @Autowired
    private InfoStockMapper infoStockMapper;
    @Autowired
    private MailUtils mailUtils;

    //    @Test
    public void testAllStock() {
        stockService.allStock();
    }

    //    @Test
    public void testGetData() {
        List<StockExtent> lse = new ArrayList<>();
        List<InfoStock> stocks = infoStockMapper.selectAll();
        int num = 0;
        for (InfoStock is : stocks) {
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

    //    @Test
    public void testGetDataCall() {
        stockService.collectData("sz000999");
    }

    //    @Test
    public void testReadMail() {
        mailUtils.readMail();
    }
}
