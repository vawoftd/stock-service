package com.vawo.foundation.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class StockServiceConstant {
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateFormat DATE_FORMAT_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * yyyy-MM-dd
     */
    public static final DateFormat DATE_FORMAT_YMD = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 每日涨停，redis hash，date->{stock:value}
     */

    public static final String STOCK_LIMIT_UP = "Stock:Limit:Up:%s";
    /**
     * 每日跌停，redis hash，date->{stock:value}
     */

    public static final String STOCK_LIMIT_DOWN = "Stock:Limit:Down:%s";
    /**
     * 个股概念，redis list，stock->list
     */
    public static final String STOCK_CONCEPT_DETAIL = "Stock:Concept:Detail:%s";
}
