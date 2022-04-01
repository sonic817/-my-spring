package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetFeedMine {
    private Integer userCodeNo;
    private String userId;
    private String nickname;
    private Integer feedNo;
    private String feedText;
    private String feedLocName;
    private String feedLocLongitude;
    private String feedLocLatitude;
    private String feedGetPath;
    private String feedSetPath;
    private List<GetFeedComments> feedComments;
    private List<GetFeedLike> feedLike;
    private Integer commentCount;
    private Integer feedLikeCount;
    private Integer feedLikeState;
    private String insertTs;
    private String updateTs;
}