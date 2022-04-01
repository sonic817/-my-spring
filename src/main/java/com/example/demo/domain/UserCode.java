package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class UserCode {
    private Integer userCodeNo;
    private Integer deviceCodeNo;
    private String birthday;
    private String userHp;
    private BigInteger userSeq;
    private String apiId;
    private String userId;
    private String userEmail;
    private String nickname;
    private Integer entitleNo;
    private Float experience;
    private Integer feedCount;
    private Integer feedFollower;
    private Integer feedFollowing;
    private Integer point;
    private Integer purple;
    private String loginId;
    private String password;
    private String roleType;
    private String cidi;
    private String userName;
    private String insertTs;
    private String updateTs;
}
