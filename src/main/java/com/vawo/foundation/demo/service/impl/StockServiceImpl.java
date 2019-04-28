package com.vawo.foundation.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vawo.foundation.demo.StockServiceConstant;
import com.vawo.foundation.demo.dao.InfoStockMapper;
import com.vawo.foundation.demo.dao.StockPriceMapper;
import com.vawo.foundation.demo.entity.InfoStock;
import com.vawo.foundation.demo.entity.StockExtent;
import com.vawo.foundation.demo.entity.StockPrice;
import com.vawo.foundation.demo.service.StockService;
import com.vawo.foundation.demo.utils.HttpResult;
import com.vawo.foundation.demo.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StockServiceImpl implements StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private StockPriceMapper stockPriceMapper;
    @Autowired
    private InfoStockMapper infoStockMapper;

    /**
     * @param stockCode
     * @return
     */
    @Override
    public StockExtent collectData(String stockCode) {
        String url = String.format(StockServiceConstant.DATA_COLLECT_URL, stockCode);
        HttpResult result = HttpUtil.doGet(url);
        if (null != result) {
            String data = result.getData();
            if (StringUtils.isNotBlank(data)) {
                try {
                    List<StockPrice> sps = new ArrayList<>();
                    JSONArray jsonArray = JSON.parseArray(data);
                    StockPrice sp0 = null;
                    StockPrice spn = null;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        float open = jo.getFloat("open");
                        float close = jo.getFloat("close");
                        Date closingTime = null;
                        try {
                            closingTime = StockServiceConstant.DATE_FORMAT.parse(jo.getString("day"));
                        } catch (ParseException px) {
                            px.printStackTrace();
                        }
                        float high = jo.getFloat("high");
                        float low = jo.getFloat("low");
                        long volume = jo.getLong("volume");
                        StockPrice sp = new StockPrice(stockCode, open, close, closingTime, high, low, volume);
                        sps.add(sp);
                        if (i == 0) {
                            sp0 = sp;
                        } else {
                            spn = sp;
                        }
                    }
                    Date lastCloseDate = stockPriceMapper.selectLast(stockCode);
                    if (lastCloseDate.before(sp0.getClosingTime())) {
                        stockPriceMapper.insertBatch(sps);
                    }
                    return new StockExtent(null, stockCode, sp0.getClose(), spn.getClose() - sp0.getClose(), spn.getClosingTime());
                } catch (Exception je) {
                    logger.error("result error, url:{}, em:{}", url, je.getMessage());
                }
            }
        }
        return new StockExtent();
    }

    public void allStock() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("D:\\stock.txt"));
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            for (String sPre : StockServiceConstant.STOCK_PREFIX) {
                for (int i = 0; i < 1000; i++) {
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setGroupingUsed(false);
                    //设置最大整数位数
                    nf.setMaximumIntegerDigits(3);
                    //设置最小整数位数
                    nf.setMinimumIntegerDigits(3);
                    String socCode = sPre + nf.format(i);
                    String url = String.format(StockServiceConstant.STOCK_URL, sPre + nf.format(i));
                    HttpResult result = HttpUtil.doGet(url);
                    if (null != result) {
                        String data = result.getData();
                        if (StringUtils.isNotBlank(data)) {
                            String p = "\"([^\\,]*)\\,";
                            Pattern P = Pattern.compile(p);
                            Matcher matcher = P.matcher(data);
                            if (matcher.find()) {
                                String arr = matcher.group(0).replaceAll(p, "$1");
                                bw.write(socCode + "," + arr + "\t\n");
                            }
                        }
                    }
                }
            }
            bw.close();
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<StockExtent> calPercent(String startDate, int top, String sort) {
        List<InfoStock> stocks = infoStockMapper.selectAll();
        List<StockExtent> lse = new ArrayList<>();
        for (InfoStock is : stocks) {
            try {
                List<StockPrice> prices = stockPriceMapper.selectByDate(is.getStockCode(), StockServiceConstant.DATE_FORMAT.parse(startDate));
                StockPrice sp0 = null;
                StockPrice spn = null;
                for (int i = 0; i < prices.size(); i++) {
                    StockPrice sp = prices.get(i);
                    if (i == 0) {
                        sp0 = sp;
                    } else {
                        spn = sp;
                    }
                }
                if (sp0 != null && spn != null) {
                    StockExtent extent = new StockExtent(is.getStockName(), is.getStockCode(), sp0.getClose(), spn.getClose(), spn.getClosingTime());
                    lse.add(extent);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.equals("desc", sort)) {
            Collections.sort(lse);
        } else {
            Collections.sort(lse, new Comparator<StockExtent>() {
                @Override
                public int compare(StockExtent o1, StockExtent o2) {
                    if (o1.getPct() < o2.getPct()) {
                        return 1;
                    } else if (o1.getPct() > o2.getPct()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }
        return lse.subList(0, top);
    }

    @Scheduled(cron = "0 5 15 * * ?")
    public void collectStockData() {
        logger.info("collect stock data ...");
        List<InfoStock> stocks = infoStockMapper.selectAll();
        int num = 1;
        for (InfoStock is : stocks) {
            collectData(is.getStockCode());
            try {
                double random = Math.random();
                long times = 500 + (long) (random * 1000L);
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
