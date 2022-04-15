package com.example.demo.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetDiary {
    private Integer tripDiaryNo;
    private Integer userCodeNo;
    private String tripDiaryName;
    private String tripDiaryHtmlCode;
    private String tripDiaryGetPath;
    private String tripDiarySetPath;
    private Integer likeCount;
    private Integer viewCount;
    private String insertTs;
    private String updateTs;
    private List<GetPlace> placeList;
}

