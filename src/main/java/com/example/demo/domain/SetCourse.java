package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class SetCourse {
    private Integer userCodeNo;
    private Integer tripCourseNo;
    private String tripCourseCode;
    private String tripCourseName;
    private String tripCourseText;
    private Float starRating;
    private Integer reviewCount;
    private String tripCourseGetPath;
    private String tripCourseSetPath;
    private String insertTs;
    private String updateTs;
}
