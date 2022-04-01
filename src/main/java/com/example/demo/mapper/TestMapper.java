package com.example.demo.mapper;

import com.example.demo.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface TestMapper {
    Integer asd(Integer userCodeNo, String htmlCode);

    List<GetCountry> getCountryList();
    List<GetArea> getAreaList(Integer countryNo);
    List<GetRegion> getRegionList(Integer areaNo);

    Integer insertTripCourseDiaryLike(Integer userCodeNo, Integer tripCourseDiaryNo);
    Integer deleteTripCourseDiaryLike(Integer userCodeNo, Integer tripCourseDiaryNo);

    List<GetCourse> getHotCourse(Float starRating);
    List<GetCategory> getCourseCategory(Integer tripNo);

    List<GetCategory> getPlaceCategory(Integer tripNo);

    GetTripReport getTripReport(Integer userCodeNo, Integer tripCourseNo);
    List<HashMap<String, Object>> getPlacePosInTripCourseNo(Integer tripCourseNo);
    List<HashMap<String, Object>> getPlacePosInTripPlaceData();
    List<GetPlace> getPlacesRecommand(List<Integer> place3List);

    List<Integer> getLikeMate(Integer tripCourseNo);
    List<Integer> getMateOfMate(Integer userCodeNo);
    List<GetFollowing> getMateRecommand(List<Integer> recommendMateList);

    List<GetPlace> getPlaceMoreBySearch(String inputText, Integer limitStart, Integer limitEnd);

    UserCode getUserInfo(Integer userCodeNo);
    HashMap<String, Object> getFeedFollower(Integer userCodeNo);
    HashMap<String, Object> getFeedFollowing(Integer userCodeNo);

    List<GetFeedMine> getFeedMine(Integer userCodeNo, Integer limitStart, Integer limitEnd);
    List<GetFeedLike> getFeedLike(Integer feedNo);
    List<GetFeedComments> getFeedComments(Integer feedNo);
    List<GetFeedCommentSs> getFeedCommentSs(Integer feedNo, Integer feedCommentNo);

    List<GetCourseSearch> getCourseMoreBySearch(String inputText, Integer limitStart, Integer limitEnd);

    Integer setCourse(SetCourse setCourse);
    Integer setCoursePlace(List<SetCoursePlace> setCoursePlaceList);

    void insertToken(Integer userCodeNo, String userFcmToken);

    List<GetCourseBookmark> getCourseBookmark(Integer userCodeNo);
}
