package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class SetFeed {
    private Integer userCodeNo;
    private Integer feedNo;
    private String feedCode;
    private String feedText;
    private String feedLocName;
    private String feedLocLongitude;
    private String feedLocLatitude;
    private String feedGetPath;
    private String feedSetPath;
    private String insertTs;
    private String updateTs;
}
