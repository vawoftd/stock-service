package com.vawo.foundation.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vawo.foundation.demo.StockServiceConstant;
import com.vawo.foundation.demo.dao.StockMapper;
import com.vawo.foundation.demo.dao.StockRecordMapper;
import com.vawo.foundation.demo.dao.TurnoverDayMapper;
import com.vawo.foundation.demo.entity.Stock;
import com.vawo.foundation.demo.entity.StockExtent;
import com.vawo.foundation.demo.entity.StockRecord;
import com.vawo.foundation.demo.entity.TurnoverDay;
import com.vawo.foundation.demo.service.StockService;
import com.vawo.foundation.demo.utils.HttpResult;
import com.vawo.foundation.demo.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
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
    private StockRecordMapper stockRecordMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private TurnoverDayMapper turnoverDayMapper;

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
                    List<StockRecord> srs = new ArrayList<>();
                    JSONArray jsonArray = JSON.parseArray(data);
                    StockRecord sp0 = null;
                    StockRecord spn = null;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        float open = jo.getFloat("open");
                        float close = jo.getFloat("close");
                        Date closingTime = null;
                        try {
                            closingTime = StockServiceConstant.DATE_FORMAT_YMDHMS.parse(jo.getString("day"));
                        } catch (ParseException px) {
                            px.printStackTrace();
                        }
                        float high = jo.getFloat("high");
                        float low = jo.getFloat("low");
                        long volume = jo.getLong("volume");
                        StockRecord sp = new StockRecord(stockCode, open, close, closingTime, high, low, volume);
                        srs.add(sp);
                        if (i == 0) {
                            sp0 = sp;
                        } else {
                            spn = sp;
                        }
                    }
                    Date lastCloseDate = stockRecordMapper.selectLast(stockCode);
                    if (lastCloseDate.before(sp0.getClosingTime())) {
                        stockRecordMapper.insertBatch(srs);
                        statisticsTurnover(srs);
                    }
                    return new StockExtent(null, stockCode, sp0.getClose(), spn.getClose() - sp0.getClose(), spn.getClosingTime());
                } catch (Exception je) {
                    logger.error("result error, url:{}, em:{}", url, je.getMessage());
                }
            }
        }
        return new StockExtent();
    }

    public void statisticsTurnover(List<StockRecord> srs) {
        StockRecord sr = null;
        long turnover = 0L;
        for (StockRecord stockRecord : srs) {
            turnover = turnover + stockRecord.getVolume();
            sr = stockRecord;
        }
        TurnoverDay turnoverDay = new TurnoverDay();
        turnoverDay.setStockCode(sr.getStockCode());
        turnoverDay.setTurnoverDay(turnover);
        turnoverDay.setPriceDay(sr.getClose());
        turnoverDay.setDay(sr.getClosingTime());
        turnoverDay.setCalculateDate(new Date());
        turnoverDayMapper.insert(turnoverDay);
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
        List<Stock> stocks = stockMapper.selectAll();
        List<StockExtent> lse = new ArrayList<>();
        for (Stock is : stocks) {
            try {
                List<StockRecord> prices = stockRecordMapper.selectByDate(is.getStockCode(), StockServiceConstant.DATE_FORMAT_YMDHMS.parse(startDate));
                StockRecord sp0 = null;
                StockRecord spn = null;
                for (int i = 0; i < prices.size(); i++) {
                    StockRecord sp = prices.get(i);
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

    public List<TurnoverDay> listStockTurnover(String stockCode, String startDateStr) {
        Date startDate = null;
        try {
            startDate = StockServiceConstant.DATE_FORMAT_YMD.parse(startDateStr);
            return turnoverDayMapper.selectByDate(stockCode, startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }
}
