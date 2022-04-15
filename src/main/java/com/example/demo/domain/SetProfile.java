package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class SetProfile {
    private Integer userCodeNo;
    private Integer profileNo;
    private String profileCode;
    private Integer profileImgNo;
    private Integer profileBImgNo;
    private String insertTs;
    private String updateTs;
}
