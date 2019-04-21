package com.vawo.foundation.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class StockServiceConstant {

    public static final int SUCCESS = 1;
    public static final int ERROR = 0;
    public static final String ACCESSTOKEN = "accessToken";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 发送告警短信
    public static final String ALARM_SMS_SEND = "SenseTime:SenseFace:Alarm:Send:%s";
    public static final String SMS_TEMPLATE = "【人像布控告警】姓名：%s；身份证号码：%s；时间：%s；地点：%s；相似度：%s；库类型：%s";

    public static final String[] STOCK_PREFIX = {"sz300", "sh600", "sh601", "sh603", "sz002", "sz000"};
    public static final String STOCK_URL = "http://hq.sinajs.cn/list=%s";
}
