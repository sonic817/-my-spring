package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetCoursePlace {
    private Integer userCodeNo;
    private String nickname;
    private Integer tripCourseNo;
    private String tripCourseCode;
    private String tripCourseName;
    private String tripCourseText;
    private Float starRating;
    private Integer likeCount;
    private List<GetCategory> categoryList;
    private String tripCourseGetPath;
    private String tripCourseSetPath;
    private String insertTs;
    private String updateTs;
    private List<GetPlace> placeList;
}
