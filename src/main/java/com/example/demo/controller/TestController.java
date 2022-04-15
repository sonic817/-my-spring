package com.example.demo.controller;

import com.example.demo.service.AwsS3Service;
import com.example.demo.service.TestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class TestController {

    @Autowired
    TestService testService;
    @Autowired
    AwsS3Service awsS3Service;

    //region
    @GetMapping("/")
    @ResponseBody
    public JSONObject get(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        JSONObject jSONOResponse = new JSONObject();
        jSONOResponse.put("message", "정상작동중입니다.");
        return jSONOResponse;
    }
    //endregion


    //region
    @PostMapping("/trip/place-upload")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mRequest", value = "다중파일객체", required = true, paramType = "MultipartHttpServletRequest"),
            @ApiImplicitParam(name = "bodyData", value = "userCodeNo, placeName, placeText, placeLatitude, placeLongitude, placeCategory, placetag", required = true),
    })
    @ResponseBody
    public JSONObject postPlaceMineUpload(
            @ApiIgnore MultipartHttpServletRequest mRequest,
            @RequestParam("bodyData") String bodyData
    ) throws ParseException, IOException {
        JSONObject jSONOResponse = testService.upload(mRequest, bodyData);
        return jSONOResponse;
    }
    //endregion


    //region
    @GetMapping("/alarm/send")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "bodyData", value = "alarmSender : 보낸 userCodeNo PK, alarmReceiver 받는 userCodeNo PK, alarmKindsNo 알람종류 PK, pageKindsNo 화면종류 PK", required = true),
//    })
    @ResponseBody
    public JSONObject sendAlarm(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response
//            @RequestBody String bodyData
    ) throws Exception {
        JSONObject jSONOResponse = testService.sendAlarm();
        return jSONOResponse;
    }
    //endregion


    //region
    @GetMapping("/trip/report")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCodeNo", value = "사용자 PK", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "tripCourseNo", value = "코스 PK", required = true, dataType = "int", paramType = "query"),
    })
    @ResponseBody
    public JSONObject getTripReport(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userCodeNo") Integer userCodeNo,
            @RequestParam("tripCourseNo") Integer tripCourseNo
    ) throws Exception {
        JSONObject jSONOResponse = testService.getTripReport(userCodeNo, tripCourseNo);
        return jSONOResponse;
    }
    //endregion
}