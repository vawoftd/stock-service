package com.vawo.foundation.demo.entity;

import java.util.Date;

public class StockPrice {
    private String stockCode;
    private float open;
    private float close;
    private Date closingTime;
    private float high;
    private float low;
    private long volume;

    public StockPrice() {
    }

    public StockPrice(String stockCode, float open, float close, Date closingTime, float high, float low, long volume) {
        this.stockCode = stockCode;
        this.open = open;
        this.close = close;
        this.closingTime = closingTime;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}
