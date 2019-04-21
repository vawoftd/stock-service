package com.vawo.foundation.demo.entity;

import java.util.Date;

public class StockExtent implements Comparable<StockExtent> {
    private String stockCode;
    private String stockName;
    private float price;
    private float current;
    private float pct;
    private Date closingTime;

    public StockExtent(String stockName, String stockCode, float price, float current, Date closingTime) {
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.price = price;
        this.current = current;
        this.closingTime = closingTime;
        this.pct = (current - price) / price;
    }

    public StockExtent() {
    }


    public String getPercent() {
        int pctt = (int) (pct * 100);
        return pctt + "%";
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public float getPct() {
        return pct;
    }

    public void setPct(float pct) {
        this.pct = pct;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
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
