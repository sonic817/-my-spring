package com.example.demo.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetArea {
    private Integer countryNo;
    private Integer areaNo;
    private String areaNameKr;
    private String areaNameEn;
    private Integer areaUseflag;
    private String insertTs;
    private String updateTs;
    private List<GetRegion> regionList;
}

