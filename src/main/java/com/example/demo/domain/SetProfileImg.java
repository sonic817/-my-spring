package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class SetProfileImg {
    private Integer profileImgNo;
    private String profileImgPath;
    private String insertTs;
    private String updateTs;
}
