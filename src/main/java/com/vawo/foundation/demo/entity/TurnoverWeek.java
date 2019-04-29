package com.vawo.foundation.demo.entity;

import java.util.Date;

public class TurnoverWeek {
    private long turnoverWeekId;
    private String stockCode;
    private long turnoverWeek;
    private float priceWeek;
    private String extentWeek;
    private Date calculateDate;

    public TurnoverWeek() {
    }

    public long getTurnoverWeekId() {
        return turnoverWeekId;
    }

    public void setTurnoverWeekId(long turnoverWeekId) {
        this.turnoverWeekId = turnoverWeekId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public long getTurnoverWeek() {
        return turnoverWeek;
    }

    public void setTurnoverWeek(long turnoverWeek) {
        this.turnoverWeek = turnoverWeek;
    }

    public float getPriceWeek() {
        return priceWeek;
    }

    public void setPriceWeek(float priceWeek) {
        this.priceWeek = priceWeek;
    }

    public String getExtentWeek() {
        return extentWeek;
    }

    public void setExtentWeek(String extentWeek) {
        this.extentWeek = extentWeek;
    }

    public Date getCalculateDate() {
        return calculateDate;
    }

    public void setCalculateDate(Date calculateDate) {
        this.calculateDate = calculateDate;
    }
}
