package com.vawo.foundation.demo.dao;

import com.vawo.foundation.demo.entity.InfoStock;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface InfoStockMapper {
    int insert(InfoStock is);

    List<InfoStock> selectAll();

    InfoStock selectOne();
}
