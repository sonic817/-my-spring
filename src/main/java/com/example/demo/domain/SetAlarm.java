package com.example.demo.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter // getter를 자동 생성
@Setter // setter를 자동 생성
@ToString // toString을 자동 생성
public class SetAlarm {
    private List<Integer> alarmHistoryNoList;
    private Integer alarmHistoryReceiver;
    private Integer alarmHistoryState;
}

