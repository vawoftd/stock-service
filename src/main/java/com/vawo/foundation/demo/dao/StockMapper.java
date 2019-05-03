package com.vawo.foundation.demo.dao;

import com.vawo.foundation.demo.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StockMapper {
    int insert(Stock is);

    int update(@Param("stockCode") String stockCode, @Param("stockName") String stockName);

    List<Stock> selectAll();

    Stock selectOne();
}
