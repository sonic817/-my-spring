package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetFeedComments {
    private Integer userCodeNo;
    private String userId;
    private String nickname;
    private Integer feedCommentNo;
    private Integer feedNo;
    private String feedCommentText;
    private Integer feedParent;
    private Integer feedDepth;
    private String insertTs;
    private List<GetFeedCommentSs> getFeedCommentSs;
}
