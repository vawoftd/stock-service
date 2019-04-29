package com.vawo.foundation.demo.dao;

import com.vawo.foundation.demo.entity.TurnoverDay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface TurnoverDayMapper {
    int insertBatch(@Param("tds") List<TurnoverDay> tds);

    int insert(TurnoverDay td);

    List<TurnoverDay> selectByDate(@Param("stockCode") String stockCode, @Param("startTime") Date startTime);
}