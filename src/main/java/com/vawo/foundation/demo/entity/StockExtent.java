package com.vawo.foundation.demo.entity;

import java.util.Date;

public class StockExtent implements Comparable<StockExtent> {
    private String stockCode;
    private String stockName;
    private float price;
    private float extent;
    private float pct;
    private Date closingTime;

    public StockExtent(String stockName,String stockCode, float price, float extent, Date closingTime) {
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.price = price;
        this.extent = extent;
        this.closingTime = closingTime;
        this.pct = extent / price;
    }

    public StockExtent() {
    }

    public float getPrice() {
        return price;
    }

    public String getPercent() {
        int pctt = (int) (pct * 100);
        return pctt + "%";
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    @Override
    public int compareTo(StockExtent o) {
        if (this.pct > o.pct) {
            return 1;
        } else if (this.pct < o.pct) {
            return -1;
        } else {
            return 0;
        }
    }
}
