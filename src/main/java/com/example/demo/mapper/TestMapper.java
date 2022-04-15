package com.example.demo.mapper;

import com.example.demo.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TestMapper {
    //여행지 업로드
    String getNewCode(Integer userCodeNo, String today);
    Integer insertTripPlace(SetPlace setPlace);
    Integer insertPlaceImgN(Integer tripPlaceNo, String s3Add);
    Integer insertPlaceImgB(Integer tripPlaceNo, String s3Add);

    //알람
    Integer insertAlarmUserToUser(Integer alarmHistorySender, Integer alarmHistoryReceiver, Integer alarmKindsNo, Integer pageKindsNo);
    GetAlarmKinds getAlarmKinds(Integer alarmKindsNo);
    String getUserNickname(Integer alarmReceiver);
    String getToken(Integer alarmReceiver);

    //여행기
    GetTripReport getTripReport(Integer userCodeNo, Integer tripCourseNo);
    List<HashMap<String, Object>> getPlacePosInTripCourseNo(Integer tripCourseNo);
    List<HashMap<String, Object>> getPlacePosInTripPlaceData();
    List<GetPlace> getPlacesRecommand(List<Integer> place3List);
    List<Integer> getLikeMate(Integer tripCourseNo);
    List<Integer> getMateOfMate(Integer userCodeNo);
    List<GetFollowing> getMateRecommand(List<Integer> recommendMateList);
    List<GetCategory> getPlaceCategory(Integer tripNo);
    List<GetPlaceImg> getPlaceImgList(Integer tripCourseNo);
    List<GetPlaceBImg> getPlaceBImgList(Integer tripCourseNo);
}
