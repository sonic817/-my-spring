package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetCategory {
    private Integer categoryNo;
    private String categoryKindsNo;
    private String categoryName;
    private Integer categoryUseflag;
    private String insertTs;
    private String updateTs;
}
