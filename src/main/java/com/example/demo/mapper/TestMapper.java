package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
    Integer asd(Integer userCodeNo, String htmlCode);
}
