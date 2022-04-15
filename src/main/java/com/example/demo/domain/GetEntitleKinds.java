package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetEntitleKinds {
    private Integer entitleConditionKindsNo;
    private String entitleConditionKindsEn;
    private String entitleConditionKindsKr;
    private String insertTs;
    private String readTs;
}