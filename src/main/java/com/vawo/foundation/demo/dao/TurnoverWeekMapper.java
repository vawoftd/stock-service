package com.vawo.foundation.demo.dao;

import com.vawo.foundation.demo.entity.TurnoverWeek;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface TurnoverWeekMapper {
    int insertBatch(@Param("tds") List<TurnoverWeek> tds);

    int insert(TurnoverWeek td);

    List<TurnoverWeek> selectByDate(@Param("stockCode") String stockCode, @Param("startTime") Date startTime);
}