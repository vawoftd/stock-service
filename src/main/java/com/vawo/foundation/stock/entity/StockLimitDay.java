package com.vawo.foundation.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockLimitDay {
    private String tradeDate;
    private int limitUpCount;
    private int limitDownCount;
    private int highestLimit;
    private int continuousLimit;
}
