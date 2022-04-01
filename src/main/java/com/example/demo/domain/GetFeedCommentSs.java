package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetFeedCommentSs {
    private Integer userCodeNo;
    private String userId;
    private String nickname;
    private Integer feedCommentNo;
    private Integer feedNo;
    private String feedCommentText;
    private Integer feedParent;
    private Integer feedDepth;
    private String insertTs;
}
