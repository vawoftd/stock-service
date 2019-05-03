package com.vawo.foundation.demo.dao;

import com.vawo.foundation.demo.entity.StockRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface StockRecordMapper {
    int insertBatch(@Param("srs") List<StockRecord> srs);

    List<StockRecord> selectByDate(@Param("stockCode") String stockCode, @Param("startTime") Date startTime);

    Date selectLast(@Param("stockCode") String stockCode);
}