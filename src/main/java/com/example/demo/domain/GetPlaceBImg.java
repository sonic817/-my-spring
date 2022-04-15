package com.example.demo.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetPlaceBImg {
    private Integer tripPlaceNo;
    private Integer tripPlaceBImgNo;
    private String tripPlaceBImgGetPath;
    private String insertTs;
    private String updateTs;
}