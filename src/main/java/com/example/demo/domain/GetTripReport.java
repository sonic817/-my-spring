package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetTripReport {
    private Integer userCodeNo;
    private String nickname;
    private String userId;
    private Integer tripCourseNo;
    private String tripCourseCode;
    private Integer likeCount;
    private String tripCourseName;
    private String tripCourseGetPath;
    private String insertTs;
    private String updateTs;
    private List<GetFollowing> recommendMateList;
    private List<GetPlace> recommendTripPlaceList;
}