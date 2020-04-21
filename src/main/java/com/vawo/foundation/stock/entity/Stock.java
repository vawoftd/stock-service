package com.vawo.foundation.stock.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @JSONField(alternateNames = "trade_date")
    private String tradeDate;
    @JSONField(alternateNames = "ts_code")
    private String tsCode;
    @JSONField(alternateNames = "name")
    private String name;
    @JSONField(alternateNames = "close")
    private float close;
    @JSONField(alternateNames = "pct_chg")
    private float pctChg;
    @JSONField(alternateNames = "amp")
    private float amp;
    @JSONField(alternateNames = "fc_ratio")
    private float fcRatio;
    @JSONField(alternateNames = "fl_ratio")
    private float flRatio;
    @JSONField(alternateNames = "fd_amount")
    private double fdAmount;
    @JSONField(alternateNames = "first_time")
    private String firstTime;
    @JSONField(alternateNames = "last_time")
    private String lastTime;
    @JSONField(alternateNames = "open_times")
    private int openTimes;
    @JSONField(alternateNames = "strth")
    private float strth;
    @JSONField(alternateNames = "limit")
    private String limit;
    @JSONField(alternateNames = "symbol")
    private String symble;
    @JSONField(alternateNames = "area")
    private String area;
    @JSONField(alternateNames = "industry")
    private String industry;
    @JSONField(alternateNames = "list_date")
    private String listDate;
    @JSONField(alternateNames = "limit_num")
    private int limitNum;
    private List<String> concept;
}
