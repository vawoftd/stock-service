package com.vawo.foundation.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockLimitDay {
    private int limitUpCount;
    private int limitDownCount;
    private int highestLimit;
    private int continuousLimit;
}
