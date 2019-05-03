package com.vawo.foundation.demo.entity;


import java.util.Date;

public class TurnoverDay {
    private long turnoverDayId;
    private String stockCode;
    private long turnoverDay;
    private float priceDay;
    private String extentDay;
    private Date day;
    private Date calculateDate;

    public TurnoverDay() {
    }

    public TurnoverDay(long turnoverDayId, String stockCode, long turnoverDay, float priceDay, String extentDay, Date day, Date calculateDate) {
        this.turnoverDayId = turnoverDayId;
        this.stockCode = stockCode;
        this.turnoverDay = turnoverDay;
        this.priceDay = priceDay;
        this.extentDay = extentDay;
        this.day = day;
        this.calculateDate = calculateDate;
    }

    public long getTurnoverDayId() {
        return turnoverDayId;
    }

    public void setTurnoverDayId(long turnoverDayId) {
        this.turnoverDayId = turnoverDayId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public long getTurnoverDay() {
        return turnoverDay;
    }

    public void setTurnoverDay(long turnoverDay) {
        this.turnoverDay = turnoverDay;
    }

    public float getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(float priceDay) {
        this.priceDay = priceDay;
    }

    public String getExtentDay() {
        return extentDay;
    }

    public void setExtentDay(String extentDay) {
        this.extentDay = extentDay;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getCalculateDate() {
        return calculateDate;
    }

    public void setCalculateDate(Date calculateDate) {
        this.calculateDate = calculateDate;
    }
}
