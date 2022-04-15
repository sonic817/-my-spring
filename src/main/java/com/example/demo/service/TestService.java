package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.mapper.TestMapper;
import com.example.demo.utils.DistanceUtil;
import com.google.firebase.messaging.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TestService {

    @Autowired
    AwsS3Service awsS3Service;
    @Autowired
    TestMapper testMapper;


    public JSONObject upload(MultipartHttpServletRequest mRequest, String jsonData) throws ParseException, IOException {
        JSONObject jSONOResponse = new JSONObject();

        JSONParser parser = new JSONParser();
        Object obj = null;
        Integer userCodeNo = 0;
        String placeName = "";
        String placeAdd0 = "";
        String placeAdd1 = "";
        String placeAdd2 = "";
        String placeAdd3 = "";
        String placeText = "";
        String placeLatitude = "";
        String placeLongitude = "";
        List<Integer> placeCategory = null;
        List<String> placetag = null;

        try {
            obj = parser.parse(jsonData);
            JSONObject paramJson = (JSONObject) obj;

            userCodeNo = Integer.parseInt(paramJson.get("userCodeNo").toString());
            placeName = paramJson.get("placeName").toString();
            placeAdd0 = paramJson.get("placeAdd0").toString();
            placeAdd1 = paramJson.get("placeAdd1").toString();
            placeAdd2 = paramJson.get("placeAdd2").toString();
            placeAdd3 = paramJson.get("placeAdd3").toString();
            placeText = paramJson.get("placeText").toString();
            placeLatitude = paramJson.get("placeLatitude").toString();
            placeLongitude = paramJson.get("placeLongitude").toString();
            placeCategory = (List<Integer>) paramJson.get("placeCategory");
            placetag = (List<String>) paramJson.get("placeTag");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //yyyyMMdd
        String yyyyMMdd = yyyyMMdd(userCodeNo);
        //s3FolderPath
        String n3Path = "user/" + userCodeNo + "/" + "place/place_n/" + yyyyMMdd + " / ";
        String b3Path = "user/" + userCodeNo + "/" + "place/place_b/" + yyyyMMdd + " / ";
        //s3에 파일 업로드
        List<MultipartFile> nRequest = filtering(mRequest, "_n");
        List<MultipartFile> bRequest = filtering(mRequest, "_b");

        List<String> nfileList = awsS3Service.uploadFiles(nRequest, n3Path);
        List<String> bfileList = awsS3Service.uploadFiles(bRequest, b3Path);
        //db insert
        insertDB(nfileList, bfileList, userCodeNo, placeName, placeAdd0, placeAdd1, placeAdd2, placeAdd3,
                placeText, placeLatitude, placeLongitude, yyyyMMdd, n3Path, b3Path);

        return jSONOResponse;
    }

    private List<MultipartFile> filtering(MultipartHttpServletRequest mRequest, String includeChr) {
        List<MultipartFile> files = mRequest.getFiles("file");
        List<MultipartFile> tmp = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getOriginalFilename().contains(includeChr)) {
                tmp.add(files.get(i));
            }
        }
        return tmp;
    }

    private String yyyyMMdd(Integer userCodeNo) {
        String code = "";
        String getCode = "";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String today = sdf.format(date);
        today = today.substring(2);

        getCode = testMapper.getNewCode(userCodeNo, today);

        if (getCode == null || getCode.isEmpty()) {
            code = today + "_" + "0001";
        } else {
            String[] newCodeSplit = getCode.split("_");
            Integer seq = Integer.parseInt(newCodeSplit[1]) + 1;
            code = getCode.replace(newCodeSplit[1], String.format("%04d", seq));
        }

        return code;
    }

    private void insertDB(List<String> nfileList, List<String> bfileList, Integer userCodeNo, String placeName, String placeAdd0, String placeAdd1, String placeAdd2, String placeAdd3,
                          String placeText, String placeLatitude, String placeLongitude, String yyyyMMdd, String n3Path, String b3Path) {
        //저장
        SetPlace setPlace = new SetPlace();
        setPlace.setUserCodeNo(userCodeNo);
        setPlace.setTripPlaceCode(yyyyMMdd);
        setPlace.setTripPlaceName(placeName);
        setPlace.setTripPlaceAdd0(placeAdd0);
        setPlace.setTripPlaceAdd1(placeAdd1);
        setPlace.setTripPlaceAdd2(placeAdd2);
        setPlace.setTripPlaceAdd3(placeAdd3);
        setPlace.setTripPlaceText(placeText);
        setPlace.setTripPlaceLatitude(placeLatitude);
        setPlace.setTripPlaceLongitude(placeLongitude);

        //profile db insert
        Integer tripPlaceNo = testMapper.insertTripPlace(setPlace);

        //insert trip_place_b_img
        for (int i = 0; i < nfileList.size(); i++) {
            Integer resultCount = testMapper.insertPlaceImgN(setPlace.getTripPlaceNo(), awsS3Service.add + n3Path + nfileList.get(i));
        }
        //insert trip_place_img
        for (int i = 0; i < bfileList.size(); i++) {
            Integer resultCount = testMapper.insertPlaceImgB(setPlace.getTripPlaceNo(), awsS3Service.add + b3Path + bfileList.get(i));
        }
    }

    public JSONObject sendAlarm() throws IOException, FirebaseMessagingException {
        JSONObject jSONOResponse = new JSONObject();

//        JSONParser parser = new JSONParser();
//        Object obj = null;
//        Integer alarmHistorySender = 0;
//        Integer alarmHistoryReceiver = 0;
//        Integer alarmKindsNo = 0;
//        Integer pageKindsNo = 0;
//        String response = "";
//
//        try {
//            obj = parser.parse(bodyData);
//            JSONObject paramJson = (JSONObject) obj;
//            alarmHistorySender = Integer.parseInt(paramJson.get("alarmHistorySender").toString());
//            alarmHistoryReceiver = Integer.parseInt(paramJson.get("alarmHistoryReceiver").toString());
//            alarmKindsNo = Integer.parseInt(paramJson.get("alarmKindsNo").toString());
//            pageKindsNo = Integer.parseInt(paramJson.get("pageKindsNo").toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        //기록 저장
//        Integer insertCount = testMapper.insertAlarmUserToUser(alarmHistorySender, alarmHistoryReceiver, alarmKindsNo, pageKindsNo);
//        GetAlarmKinds getAlarmKinds = testMapper.getAlarmKinds(alarmKindsNo);
//        String getUserNickname = testMapper.getUserNickname(alarmHistorySender);
//        String token = testMapper.getToken(alarmHistoryReceiver);
//        String title = getAlarmKinds.getAlarmKindsTitle();
//        String body = String.format(getAlarmKinds.getAlarmKindsText(), getUserNickname);

//        List<String> tokens = Arrays.asList(
//                "flXBsKD_Tom4ZZOtraI09w:APA91bHj6kaGjXGEGCDe08mrLGtn1SRFNrUGD7QZgH_bQ38EJeHl7K4WIQFJCRsE7ERA3QAmuo_-gBeeeBEZELm1JccrgHgaxnH4jwxZfkzFNrT8aGGDpKUKJWIrJb-ojVLpwJs4SHM_",
//                "flXBsKD_Tom4ZZOtraI09w:APA91bHj6kaGjXGEGCDe08mrLGtn1SRFNrUGD7QZgH_bQ38EJeHl7K4WIQFJCRsE7ERA3QAmuo_-gBeeeBEZELm1JccrgHgaxnH4jwxZfkzFNrT8aGGDpKUKJWIrJb-ojVLpwJs4SHM_",
//                "flXBsKD_Tom4ZZOtraI09w:APA91bHj6kaGjXGEGCDe08mrLGtn1SRFNrUGD7QZgH_bQ38EJeHl7K4WIQFJCRsE7ERA3QAmuo_-gBeeeBEZELm1JccrgHgaxnH4jwxZfkzFNrT8aGGDpKUKJWIrJb-ojVLpwJs4SHM_"
//        );

//        String[] tokens = {
//                "flXBsKD_Tom4ZZOtraI09w:APA91bHj6kaGjXGEGCDe08mrLGtn1SRFNrUGD7QZgH_bQ38EJeHl7K4WIQFJCRsE7ERA3QAmuo_-gBeeeBEZELm1JccrgHgaxnH4jwxZfkzFNrT8aGGDpKUKJWIrJb-ojVLpwJs4SHM_"
//        };
//
//
//        FirebaseCloudMessageService fcm = new FirebaseCloudMessageService();
//            fcm.sendMessageTo(
//                    "cWkpVFNrQ8axM5AZ3L-ASr:APA91bFvtkiascAknVnfPYq90ACYhidhHD-3VHw5GeozVIvEpaNOA2bNGOguWBqDT1SnM9ZfPo01tympuddSmT_nXhl61sZbTUYqmdtdwzb88k6mh3pBZAPoF9IsPoPFY5MvJ_NnfIEs",
//                    "title",
//                    "body",
//                    alarmHistorySender.toString(),
//                    alarmHistoryReceiver.toString(),
//                    alarmKindsNo.toString(),
//                    pageKindsNo.toString()
//            );

        //구독
        String topic = "test";

        return jSONOResponse;
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
                if (distanceMeter < 3.0) {
                    place3List.add(tripPlaceNo);
                }
            }
        }

        if (place3List.size() > 0) {
            List<GetPlace> getPlaces = testMapper.getPlacesRecommand(place3List);
            getTripReport.setRecommendTripPlaceList(getPlaces);

            for (int i = 0; i < getPlaces.size(); i++) {
                getPlaces.get(i).setCategoryList(testMapper.getPlaceCategory(getPlaces.get(i).getTripPlaceNo()));
                getPlaces.get(i).setPlaceImgList(testMapper.getPlaceImgList(getPlaces.get(i).getTripPlaceNo()));
                getPlaces.get(i).setPlaceBImgList(testMapper.getPlaceBImgList(getPlaces.get(i).getTripPlaceNo()));
            }
        }

        List<Integer> recommendMateList = new ArrayList<>();
        List<Integer> likeUserCodeNoList = testMapper.getLikeMate(tripCourseNo);
        List<Integer> mateList = testMapper.getMateOfMate(userCodeNo);
        for (int i = 0; i < likeUserCodeNoList.size(); i++) {
            Integer likeUserCodeNo = likeUserCodeNoList.get(i);

            for (int j = 0; j < mateList.size(); j++) {
                Integer mate = mateList.get(j);
                if (likeUserCodeNo == mate) {
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
}