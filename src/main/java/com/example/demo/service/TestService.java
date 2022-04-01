package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.mapper.TestMapper;
import com.example.demo.utils.DistanceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class TestService {

    @Autowired
    TestMapper testMapper;

    public JSONObject postQQQ(Integer userCodNo, String htmlCode) throws ParseException {
        JSONObject jSONOResponse = new JSONObject();

        Integer resultCount = testMapper.asd(userCodNo, htmlCode);

        jSONOResponse.put("result", "true");
        jSONOResponse.put("message", "데이터를 성공적으로 가져왔습니다.");
        jSONOResponse.put("data", resultCount);

        return jSONOResponse;
    }

    public JSONObject split() throws ParseException {
        JSONObject jSONOResponse = new JSONObject();

        String tmp = "<p>호<img src=\"data:image/jpeg;base64,/9j/4AAQS/2Q==\" style=\"width: 25%;\" data-filename=\"dog.jpg\">ㅎ<img src=\"data:image/jpeg;base64,/9j\" style=\"width: 25%;\" data-filename=\"dog.jpg\"></p>";
        List<Integer> imgList = findIndexes("<img", tmp);

        Integer tmpInt = 0;
        for (int i = 0; i < imgList.size(); i++) {
            Integer imgStartPoint = imgList.get(i);
            Integer standard = imgStartPoint - tmpInt;

            Integer imgEndPoint = tmp.indexOf(">", standard);
            String cutStr = tmp.substring(standard + "<img".length(), imgEndPoint);
            tmpInt = cutStr.length() - ("_" + Integer.toString(i)).length();
            tmp = tmp.replace(cutStr, ("_" + Integer.toString(i)));
        }

        jSONOResponse.put("data", tmp);
        return jSONOResponse;
    }

    public List<Integer> findIndexes(String word, String document) {
        List<Integer> indexList = new ArrayList<Integer>();
        int index = document.indexOf(word);

        while(index != -1) {
            indexList.add(index);
            index = document.indexOf(word, index+word.length());
        }

        return indexList;
    }

    public String tripDiary(MultipartFile multipartFile, Integer userCodeNo) throws ParseException {

//        //새로운 주소 만들기
//        String newCode = "";
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
//        String today = sdf.format(date);
//        today = today.substring(2);
//
//        String getCode = tripMapper.getNewCode(userCodeNo, today);
//
//        if (getCode == null || getCode.isEmpty()) {
//            newCode = "diary" + today + "_" + "0001";
//        } else {
//            String[] newCodeSplit = getCode.split("_");
//            Integer seq = Integer.parseInt(newCodeSplit[2]) + 1;
//            newCode = getCode.replace(newCodeSplit[2], String.format("%04d", seq));
//        }
//
//        String setPath = "/var/www/metalive/user/" + userCodeNo + "/diary/" + newCode + "/";
//        String getPath = "https://media.awesomeserver.kr/user/" + setPath.split("user/")[1];
//
//
//
        JSONObject jSONOResponse = new JSONObject();

        // 내부경로로 저장
        String fileRoot = "/var/www/metalive/user/6/";
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
        // 랜덤 UUID+확장자로 저장될 savedFileName
        String savedFileName = UUID.randomUUID() + extension;
        File targetFile = new File(fileRoot + savedFileName);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile); //파일 저장
            jSONOResponse.put("url", fileRoot + savedFileName);
            jSONOResponse.put("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile); // 실패시 저장된 파일 삭제
            jSONOResponse.put("responseCode", "error");
            e.printStackTrace();
        }

        return jSONOResponse.toString();
    }

    public JSONObject getTripDiary(Integer tripPlaceNo) throws ParseException {
        JSONObject jSONOResponse = new JSONObject();

//        List<GetPlaceDiary> getPlaceDiaries = tripMapper.getTripDiary(tripPlaceNo);
//
//        jSONOResponse.put("result", "true");
//        jSONOResponse.put("message", "데이터를 성공적으로 가져왔습니다.");
//        jSONOResponse.put("data", getPlaceDiaries);

        return jSONOResponse;
    }

    public JSONObject getCountryAreaRegion() {
        JSONObject jSONOResponse = new JSONObject();

        List<GetCountry> getCountryList = testMapper.getCountryList();
        for (int i = 0; i < getCountryList.size(); i++) {
            Integer countryNo = getCountryList.get(i).getCountryNo();
            getCountryList.get(i).setAreaList(testMapper.getAreaList(countryNo));

            List<GetArea> getAreaList = testMapper.getAreaList(countryNo);
            for (int j = 0; j < getAreaList.size(); j++) {
                Integer areaNo = getAreaList.get(j).getAreaNo();
                getAreaList.get(j).setRegionList(testMapper.getRegionList(areaNo));
            }
            getCountryList.get(i).setAreaList(getAreaList);
        }

        jSONOResponse.put("result", "true");
        jSONOResponse.put("message", "데이터를 성공적으로 가져왔습니다.");
        jSONOResponse.put("data", getCountryList);
        return jSONOResponse;
    }

    public JSONObject setTripDiaryLike(Integer userCodeNo, Integer tripCourseDiaryNo, Integer likeState) throws ParseException {
        JSONObject jSONOResponse = new JSONObject();

        //0이면 삭제, 1이면 생성
        if (likeState == 0) {
            Integer deleteCount = testMapper.deleteTripCourseDiaryLike(userCodeNo, tripCourseDiaryNo);
            jSONOResponse.put("data", deleteCount + " 개 삭제");
        } else {
            Integer insertCount = testMapper.insertTripCourseDiaryLike(userCodeNo, tripCourseDiaryNo);
            jSONOResponse.put("data", insertCount + " 개 추가");
        }

        jSONOResponse.put("result", "true");
        jSONOResponse.put("message", "데이터를 성공적으로 가져왔습니다.");

        return jSONOResponse;
    }

    public JSONObject getCourseHot(Float starRating) throws ParseException {

        JSONObject parentsJ = new JSONObject();
        List<GetCourse> getCourses = testMapper.getHotCourse(starRating);
        for (int i = 0; i < getCourses.size(); i++) {
            getCourses.get(i).setCategoryList(testMapper.getCourseCategory(getCourses.get(i).getTripCourseNo()));
        }

        parentsJ.put("result", "true");
        parentsJ.put("message", "데이터를 성공적으로 가져왔습니다.");
        parentsJ.put("data", getCourses);

        return parentsJ;
    }


    public JSONObject getTripReport(Integer userCodeNo, Integer tripCourseNo) throws ParseException {
        JSONObject jSONOResponse = new JSONObject();

        GetTripReport getTripReport = testMapper.getTripReport(userCodeNo, tripCourseNo);
        List<HashMap<String, Object>> placePosInTripCourseNo = testMapper.getPlacePosInTripCourseNo(tripCourseNo);
        List<HashMap<String, Object>> placePosInTripPlaceData = testMapper.getPlacePosInTripPlaceData();
        List<Integer> place3List = new ArrayList<>();
        for (int i = 0; i < placePosInTripCourseNo.size(); i++) {
            String myPlaceLat = (String) placePosInTripCourseNo.get(i).get("trip_place_latitude");
            String myPlaceLong = (String) placePosInTripCourseNo.get(i).get("trip_place_longitude");
            if (myPlaceLat.equals("") || myPlaceLong.equals("")) {
                continue;
            }

            for (int j = 0; j < placePosInTripPlaceData.size(); j++) {
                String placeLat = (String) placePosInTripPlaceData.get(j).get("trip_place_latitude");
                String placeLong = (String) placePosInTripPlaceData.get(j).get("trip_place_longitude");
                if (placeLat.equals("") || placeLong.equals("")) {
                    continue;
                }
                Integer tripPlaceNo = Integer.parseInt(String.valueOf(placePosInTripPlaceData.get(j).get("trip_place_no")));

                double distanceMeter = DistanceUtil.distance(Double.parseDouble(myPlaceLat), Double.parseDouble(myPlaceLong), Double.parseDouble(placeLat), Double.parseDouble(placeLong), "meter");
                if (distanceMeter < 3.0){
                    place3List.add(tripPlaceNo);
                }
            }
        }

        if (place3List.size() > 0) {
            List<GetPlace> getPlaceList = testMapper.getPlacesRecommand(place3List);
            getTripReport.setRecommendTripPlaceList(getPlaceList);

            for (int i = 0; i < getPlaceList.size(); i++) {
                getPlaceList.get(i).setCategoryList(testMapper.getPlaceCategory(getPlaceList.get(i).getTripPlaceNo()));
            }
        }

        List<Integer> recommendMateList = new ArrayList<>();
        List<Integer> likeUserCodeNoList = testMapper.getLikeMate(tripCourseNo);
        List<Integer> mateList = testMapper.getMateOfMate(userCodeNo);
        for (int i = 0; i < likeUserCodeNoList.size(); i++) {
            Integer likeUserCodeNo = likeUserCodeNoList.get(i);

            for (int j = 0; j < mateList.size(); j++) {
                Integer mate = mateList.get(j);
                if (likeUserCodeNo == mate){
                    recommendMateList.add(mate);
                    continue;
                }
            }
        }
        //제거
        for (int i = 0; i < recommendMateList.size(); i++) {
            likeUserCodeNoList.remove(Integer.valueOf(recommendMateList.get(i)));
            mateList.remove(Integer.valueOf(recommendMateList.get(i)));
        }
        //좋아요 누른 사람들 랜덤으로 추가
        if (recommendMateList.size() < 3) {
            while (likeUserCodeNoList.size() > 0) {
                Collections.shuffle(likeUserCodeNoList);
                recommendMateList.add(likeUserCodeNoList.get(0));
                likeUserCodeNoList.remove(0);
                if (recommendMateList.size() == 3) {
                    break;
                }
            }
        }
        //메이트의 메이트 추가
        if (recommendMateList.size() < 3) {
            while (mateList.size() > 0) {
                Collections.shuffle(mateList);
                recommendMateList.add(mateList.get(0));
                mateList.remove(0);
                if (recommendMateList.size() == 3) {
                    break;
                }
            }
        }

        List<GetFollowing> getFollowingList = testMapper.getMateRecommand(recommendMateList);
        getTripReport.setRecommendMateList(getFollowingList);

        jSONOResponse.put("result", "true");
        jSONOResponse.put("message", "데이터를 성공적으로 가져왔습니다.");
        jSONOResponse.put("data", getTripReport);

        return jSONOResponse;
    }

    public JSONObject getPlaceMoreBySearch(String inputText, Integer pageNo, Integer range) throws ParseException {
        JSONObject parentsJ = new JSONObject();

        Integer limitStart = 0 +  range * pageNo;
        Integer limitEnd = range;

        List<GetPlace> getPlaces = testMapper.getPlaceMoreBySearch(inputText, limitStart, limitEnd);
        for (int i = 0; i < getPlaces.size(); i++) {
            getPlaces.get(i).setCategoryList(testMapper.getPlaceCategory(getPlaces.get(i).getTripPlaceNo()));
        }

        parentsJ.put("result", "true");
        parentsJ.put("message", "데이터를 성공적으로 가져왔습니다.");
        parentsJ.put("data", getPlaces);

        return parentsJ;
    }

    public JSONObject getUserInfo(Integer userCodeNo) {
        JSONObject jSONOResponse = new JSONObject();
        UserCode userCode = testMapper.getUserInfo(userCodeNo);
        HashMap<String, Object> feedFollower = testMapper.getFeedFollower(userCodeNo);
        HashMap<String, Object> feedFollowing = testMapper.getFeedFollowing(userCodeNo);
        userCode.setFeedFollower(Integer.parseInt(String.valueOf(feedFollower.get("feed_follower"))));
        userCode.setFeedFollowing(Integer.parseInt(String.valueOf(feedFollowing.get("feed_following"))));

        if (userCode != null){
            jSONOResponse.put("result", "true");
            jSONOResponse.put("message", "데이터를 성공적으로 가져왔습니다.");
            jSONOResponse.put("data", userCode);
        }else {
            jSONOResponse.put("result", "false");
            jSONOResponse.put("message", "데이터가 없습니다.");
            jSONOResponse.put("data", userCode);
        }

        return jSONOResponse;
    }

    public JSONObject getFeedMine(Integer userCodeNo, Integer pageNo, Integer range) throws ParseException {
        JSONObject jSONOResponse = new JSONObject();

        Integer limitStart = 0 +  range * pageNo;
        Integer limitEnd = range;

        List<GetFeedMine> getFeedMineList = testMapper.getFeedMine(userCodeNo, limitStart, limitEnd);
        for (int i = 0; i < getFeedMineList.size(); i++) {
            Integer feedNo = getFeedMineList.get(i).getFeedNo();
            getFeedMineList.get(i).setFeedLike(testMapper.getFeedLike(feedNo));

            List<GetFeedComments> getFeedComments = testMapper.getFeedComments(feedNo);
            for (int j = 0; j < getFeedComments.size(); j++) {
                Integer feedCommentNo = getFeedComments.get(j).getFeedCommentNo();
                List<GetFeedCommentSs> getFeedCommentSs = testMapper.getFeedCommentSs(feedNo, feedCommentNo);
                getFeedComments.get(j).setGetFeedCommentSs(getFeedCommentSs);
            }
            getFeedMineList.get(i).setFeedComments(getFeedComments);
        }

        if (getFeedMineList == null || getFeedMineList.size() == 0) {
            jSONOResponse.put("result", "false");
            jSONOResponse.put("message", "데이터가 존재하지 않습니다");
        } else {
            jSONOResponse.put("result", "true");
            jSONOResponse.put("message", "데이터를 성공적으로 가져왔습니다.");
        }

        jSONOResponse.put("data", getFeedMineList);

        return jSONOResponse;
    }

    public JSONObject getCourseMoreBySearch(String inputText, Integer pageNo, Integer range) throws ParseException {
        JSONObject parentsJ = new JSONObject();

        Integer limitStart = 0 +  range * pageNo;
        Integer limitEnd = range;

        List<GetCourseSearch> getPlaces = testMapper.getCourseMoreBySearch(inputText, limitStart, limitEnd);

        parentsJ.put("result", "true");
        parentsJ.put("message", "데이터를 성공적으로 가져왔습니다.");
        parentsJ.put("data", getPlaces);

        return parentsJ;
    }

    public JSONObject setTripCourseStart() throws ParseException {
        JSONObject jSONOResponse = new JSONObject();

        JSONParser parser = new JSONParser();
        Object obj = null;
        Integer userCodeNo = 54;
        String tripCourseName = "as";
        String tripCourseText = "asas";
        List<Integer> tripPlaceNoList = new ArrayList<Integer>(Arrays.asList(1,2,3));//생성시 값추가

        SetCourse setCourse = new SetCourse();
        setCourse.setUserCodeNo(userCodeNo);
        setCourse.setTripCourseName(tripCourseName);
        setCourse.setTripCourseText(tripCourseText);
        Integer setTripCourseResult = testMapper.setCourse(setCourse);

        if (setTripCourseResult > 0){
            List<SetCoursePlace> setCoursePlaceList = new ArrayList<>();
            for (int i = 0; i < tripPlaceNoList.size(); i++) {
                SetCoursePlace setCoursePlace = new SetCoursePlace();
                setCoursePlace.setTripCourseNo(setCourse.getTripCourseNo());
                setCoursePlace.setTripPlaceNo(tripPlaceNoList.get(i));
                setCoursePlaceList.add(setCoursePlace);
            }

            Integer setTripCoursePlaceResult = testMapper.setCoursePlace(setCoursePlaceList);

            jSONOResponse.put("result", "true");
            jSONOResponse.put("message", "데이터를 성공적으로 가져왔습니다.");
            jSONOResponse.put("data", "trip_course에 " + setTripCourseResult + "개 추가했고\r\ntrip_course_place에 " + setTripCoursePlaceResult + "개 추가했습니다.");
        }
        else{
            jSONOResponse.put("result", "true");
            jSONOResponse.put("message", "데이터를 성공적으로 가져왔습니다.");
            jSONOResponse.put("data", "trip_course에 " + setTripCourseResult + "개 추가했습니다.");
        }

        return jSONOResponse;
    }

    public void sendAlarm() throws IOException {
        FirebaseCloudMessageService fcm = new FirebaseCloudMessageService();
        try {
            fcm.sendMessageTo("f5qwyJPpSe-tvd7_9X-ocF:APA91bHuUYFzujW1NagH0fXeaCaGhmco9E6dQu8Gj3aOyTqWcI7W0fOyZg3cY-jfzne3I6ynjB_PhkytprVaEm1Fa2_kmpcuqju_2KragDyiA7obSzUNT-PzRlBIdPutsryxekk2Gbac",
                    "ttttttttttttttttt",
                    "bbbbbbbbbbbbbbbbbbbbbbb");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject insertToken(Integer userCodeNo, String userFcmToken) throws FirebaseMessagingException {
        JSONObject jSONOResponse = new JSONObject();

        testMapper.insertToken(userCodeNo, userFcmToken);

        return jSONOResponse;
    }

    public JSONObject getCourseListBookmark(Integer userCodeNo) throws ParseException {
        JSONObject parentsJ = new JSONObject();

        List<GetCourseBookmark> getCourseBookmarks = testMapper.getCourseBookmark(userCodeNo);
        for (int i = 0; i < getCourseBookmarks.size(); i++) {
            getCourseBookmarks.get(i).setCategoryList(testMapper.getCourseCategory(getCourseBookmarks.get(i).getTripCourseNo()));
        }

        parentsJ.put("result", "true");
        parentsJ.put("message", "데이터를 성공적으로 가져왔습니다.");
        parentsJ.put("data", getCourseBookmarks);

        return parentsJ;
    }
}