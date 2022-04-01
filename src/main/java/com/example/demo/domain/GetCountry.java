package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetCountry {
    private Integer countryNo;
    private String countryCode;
    private String countryNameKr;
    private String countryNameEn;
    private String insertTs;
    private String updateTs;
    private List<GetArea> areaList;
}

