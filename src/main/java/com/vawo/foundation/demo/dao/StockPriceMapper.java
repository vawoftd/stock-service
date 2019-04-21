package com.vawo.foundation.demo.dao;

import com.vawo.foundation.demo.entity.StockPrice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface StockPriceMapper {
    int insertBatch(@Param("sps") List<StockPrice> sps);

    List<StockPrice> selectByDate(@Param("stockCode") String stockCode, @Param("startTime") Date startTime);

    Date selectLast(@Param("stockCode") String stockCode);
}