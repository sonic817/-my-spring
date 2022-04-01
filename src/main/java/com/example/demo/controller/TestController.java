package com.example.demo.controller;

import com.example.demo.service.TestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    TestService testService;

    //region
    @GetMapping("/summernote")
    public String summernote(
            @RequestParam("userCodeNo") Integer userCodeNo
    ) {
        return "/summernote";
    }
    //endregion


//    @PostMapping("/p")
//    @ResponseBody
//    public void postQQQ(
//            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
//            @RequestParam("userCodeNo") Integer userCodeNo,
//            @RequestParam("htmlCode") String htmlCode
//    ) throws ParseException {
//        JSONObject jSONOResponse = testService.postQQQ(userCodeNo, htmlCode);
//    }
//
//
//    @GetMapping("/p")
//    @ResponseBody
//    public JSONObject getQQQ(
//            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
//            @RequestParam("userCodeNo") Integer userCodeNo
//    ) throws Exception {
//        JSONObject jSONOResponse = new JSONObject();
//        return jSONOResponse;
//    }
//
//
//    @GetMapping("/split")
//    @ResponseBody
//    public JSONObject split(
//            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response
//    ) throws Exception {
//        JSONObject jSONOResponse = testService.split();
//        return jSONOResponse;
//    }


    //region
    @GetMapping("/trip-diary")
//	@ApiOperation(value="여행기 가져오기", notes="", response = RegisterResponse.class)
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "userCodeNo", value = "사용자 PK", required = true, dataType = "int", paramType = "query"),
//	})
    @ResponseBody
    public JSONObject getTripDiary(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("tripPlaceNo") Integer tripPlaceNo
    ) throws Exception {
        JSONObject jSONOResponse = testService.getTripDiary(tripPlaceNo);
        return jSONOResponse;
    }
    //endregion


    //region
    @PostMapping("/trip-diary")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "mRequest", value = "다중파일객체", required = true, paramType = "MultipartHttpServletRequest"),
//			@ApiImplicitParam(name = "bodyData", value = "userCodeNo, placeName, placeText, placeLatitude, placeLongitude, placeCategory, placetag", required = true),
//	})
    @ResponseBody
    public String postTripDiary(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("userCodeNo") Integer userCodeNo,
            HttpServletRequest request, HttpServletResponse response
    ) throws ParseException {
        String a = testService.tripDiary(multipartFile, userCodeNo);
        return "a";
    }
    //endregion


    //region
    @PostMapping("/postAgeByName")
//	@ApiOperation(value = "여행기 업로드", notes = "", response = RegisterResponse.class)
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "mRequest", value = "다중파일객체", required = true, paramType = "MultipartHttpServletRequest"),
//			@ApiImplicitParam(name = "bodyData", value = "userCodeNo, placeName, placeText, placeLatitude, placeLongitude, placeCategory, placetag", required = true),
//	})
    @ResponseBody
    public JSONObject postPurchaseRecharge(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestPart(value = "file", required = false) Map<String, Object> file
            ) throws Exception {

        JSONObject jSONOResponse = new JSONObject();
        return jSONOResponse;
    }
    //endregion


    //region
    @GetMapping("/area/test")
    @ResponseBody
    public JSONObject getCountryAreaRegion(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        JSONObject jSONOResponse = testService.getCountryAreaRegion();
        return jSONOResponse;
    }
    //endregion


    //region
    @PostMapping("/asd")
    @ResponseBody
    public JSONObject postTripDiaryLike(
            HttpSession session,
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userCodeNo") Integer userCodeNo,
            @RequestParam("tripCourseDiaryNo") Integer tripCourseDiaryNo,
            @RequestParam("insertOrDelete") Integer insertOrDelete
    ) throws Exception {
        JSONObject jSONOResponse = testService.setTripDiaryLike(userCodeNo, tripCourseDiaryNo, insertOrDelete);
        return jSONOResponse;
    }
    //endregion


    @GetMapping("/trip/course/hot")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "starRating", value = "별점", required = true, dataType = "float", paramType = "query"),
    })
    @ResponseBody
    public JSONObject getCourseHot(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("starRating") Float starRating
    ) throws Exception {
        JSONObject jSONOResponse = testService.getCourseHot(starRating);
        return jSONOResponse;
    }
    //endregion


    //region
    @GetMapping("/trip/report")
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

    //region
    @GetMapping("/")
    @ResponseBody
    public JSONObject home(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        JSONObject jSONOResponse = new JSONObject();
        return jSONOResponse;
    }
    //endregion

    //region
    @GetMapping("/trip/search/place/more")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "페이지 번호, 0번 부터 시작", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "range", value = "불러올 개수", required = true, dataType = "int", paramType = "query"),
    })
    @ResponseBody
    public JSONObject getPlaceMoreBySearch(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("inputText") String inputText,
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("range") Integer range
    ) throws Exception {
        JSONObject jSONOResponse = testService.getPlaceMoreBySearch(inputText, pageNo, range);
        return jSONOResponse;
    }
    //endregion


    //region
    @GetMapping("/user/info")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCodeNo", value = "사용자 PK", required = true, dataType = "int", paramType = "query"),
    })
    @ResponseBody
    public JSONObject getUserInfo(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userCodeNo") Integer userCodeNo
    ) throws Exception {
        JSONObject jSONOResponse = testService.getUserInfo(userCodeNo);
        return jSONOResponse;
    }
    //endregion


    //region
    @GetMapping("/feed/mine")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCodeNo", value = "사용자 PK", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "페이지 번호, 0번 부터 시작", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "range", value = "불러올 개수", required = true, dataType = "int", paramType = "query"),
    })
    @ResponseBody
    public JSONObject getFeedMine(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userCodeNo") Integer userCodeNo,
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("range") Integer range
    ) throws Exception {
        JSONObject jSONOResponse = testService.getFeedMine(userCodeNo, pageNo, range);
        return jSONOResponse;
    }
    //endregion


    //region
    @GetMapping("/trip/search/course/more")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "페이지 번호, 0번 부터 시작", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "range", value = "불러올 개수", required = true, dataType = "int", paramType = "query"),
    })
    @ResponseBody
    public JSONObject getCourseMoreBySearch(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("inputText") String inputText,
            @RequestParam("pageNo") Integer pageNo,
            @RequestParam("range") Integer range
    ) throws Exception {
        JSONObject jSONOResponse = testService.getCourseMoreBySearch(inputText, pageNo, range);
        return jSONOResponse;
    }
    //endregion


    //region
    @GetMapping("/trip/course/start")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jsonData", value = "userCodeNo, tripCourseName, tripCourseText, tripCourseParent, tripCourseDepth, tripPlaceNo", required = true),
    })
    @ResponseBody
    public JSONObject postTripCourseStart(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        JSONObject jSONOResponse = testService.setTripCourseStart();
        return null;
    }
    //endregion


//    //region
//    @GetMapping("/sendAlarm")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "title", value = "알람 종류", required = true),
//            @ApiImplicitParam(name = "body", value = "알람 내용", required = true),
//    })
//    @ResponseBody
//    public void sendAlarm(
//            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response
////            @RequestParam("title") String title,
////            @RequestParam("body") String body
//    ) throws Exception {
//        testService.sendAlarm();
//    }
//    //endregion


    //region
    @GetMapping("/sendAlarm")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "알람 종류", required = true),
            @ApiImplicitParam(name = "body", value = "알람 내용", required = true),
    })
    @ResponseBody
    public void sendAlarm(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        testService.sendAlarm();
    }
    //endregion


    //region
    @PostMapping("/insertToken")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCodeNo", value = "유저코드", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userFcmToken", value = "유저토큰", required = true),
    })
    @ResponseBody
    public JSONObject insertToken(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userCodeNo") Integer userCodeNo,
            @RequestParam("userFcmToken") String userFcmToken
    ) throws Exception {
        JSONObject jSONOResponse = testService.insertToken(userCodeNo, userFcmToken);
        return jSONOResponse;
    }
    //endregion


    //region
    @GetMapping("/encodeURIComponent")
    @ResponseBody
    public JSONObject encodeURIComponent(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        JSONObject jSONOResponse = new JSONObject();

        String url = "https://unikys.tistory.com/195";
        //암호화
        String encodedUrl = Base64.getUrlEncoder().encodeToString(url.getBytes());

        //복호화
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedUrl);
        String decodedUrl = new String(decodedBytes);

        jSONOResponse.put("암호", encodedUrl);
        jSONOResponse.put("복호", decodedUrl);

        return jSONOResponse;
    }
    //endregion


    //region
    @GetMapping("/trip/course/bookmark")
    @ResponseBody
    public JSONObject getCourseListBookmark(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userCodeNo") Integer userCodeNo
    ) throws Exception {

        JSONObject jSONOResponse = testService.getCourseListBookmark(userCodeNo);
        return jSONOResponse;

    }
    //endregion
}