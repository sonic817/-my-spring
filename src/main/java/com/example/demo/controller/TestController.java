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
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.UUID;

@Controller
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/")
    public String summernote(
            //@RequestParam("userCodeNo") Integer userCodeNo
    ) {
        return "/summernote";
    }


    @PostMapping("/p")
    @ResponseBody
    public void postQQQ(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userCodeNo") Integer userCodeNo,
            @RequestParam("htmlCode") String htmlCode
    ) throws ParseException {
        JSONObject jSONOResponse = testService.postQQQ(userCodeNo, htmlCode);
    }


    @GetMapping("/p")
    @ResponseBody
    public JSONObject getQQQ(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userCodeNo") Integer userCodeNo
    ) throws Exception {
        JSONObject jSONOResponse = new JSONObject();
        return jSONOResponse;
    }


    @GetMapping("/split")
    @ResponseBody
    public JSONObject split(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        JSONObject jSONOResponse = testService.split();
        return jSONOResponse;
    }


    @PostMapping("/trip-diary")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "mRequest", value = "다중파일객체", required = true, paramType = "MultipartHttpServletRequest"),
//			@ApiImplicitParam(name = "bodyData", value = "userCodeNo, placeName, placeText, placeLatitude, placeLongitude, placeCategory, placetag", required = true),
//	})
    @ResponseBody
    public JSONObject tripDiary(
            @RequestParam("file") MultipartFile multipartFile,
			@RequestParam("userCodeNo") Integer userCodeNo
//            HttpServletResponse response
    ) throws ParseException, IOException {
        JSONObject jSONOResponse = new JSONObject();
        jSONOResponse = testService.tripDiary(multipartFile, userCodeNo);
        return jSONOResponse;
    }
}