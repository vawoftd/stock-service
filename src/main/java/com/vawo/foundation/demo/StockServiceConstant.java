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

    public static final String[] STOCK_PREFIX = {"sz300", "sh600", "sh601", "sh603", "sz002", "sz000"};
    public static final String STOCK_URL = "http://hq.sinajs.cn/list=%s";
    /**
     * 个股交易数据采集url，（参数：编号、分钟间隔（5、15、30、60）、均值（5、10、15、20、25）、查询个数点（最大值242））
     */
    public static final String DATA_COLLECT_URL = "http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=%s&scale=60&ma=5&datalen=4";
}
