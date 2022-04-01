package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetCourseBookmark {
    private Integer userCodeNo;
    private Integer userCodeNoFirst;
    private String nickname;
    private Integer tripCourseNo;
    private String tripCourseName;
    private Float starRating;
//    private Integer reviewCount;
    private Integer likeCount;
    private Integer placeCount;
    private List<GetCategory> categoryList;
    private String tripCourseGetPath;
    private String insertTs;
}