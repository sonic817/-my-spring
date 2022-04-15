package com.example.demo.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class GetAlarm {
    private Integer alarmHistoryNo;
    private Integer alarmHistorySender;
    private Integer alarmHistoryReceiver;
    private Integer alarmKindsNo;
    private String alarmHistoryText;
    private Integer pageKindsNo;
    private Integer alarmHistoryState;
    private String insertTs;
    private String recvTs;
    private String readTs;
}

