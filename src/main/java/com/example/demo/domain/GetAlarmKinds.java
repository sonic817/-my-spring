package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetAlarmKinds {
    private Integer alarmKindsNo;
    private String alarmKindsNameEn;
    private String alarmKindsNameKr;
    private String alarmKindsTitle;
    private String alarmKindsText;
    private Integer serverKindsNo;
    private String serverKinds;
    private String insertTs;
    private String updateTs;
}