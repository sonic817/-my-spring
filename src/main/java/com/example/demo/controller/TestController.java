package com.example.demo.controller;

import com.example.demo.service.TestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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


    //region
    @PostMapping("/p")
    @ResponseBody
    public void postQQQ(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userCodeNo") Integer userCodeNo,
            @RequestParam("htmlCode") String htmlCode
    ) throws ParseException {
        JSONObject jSONOResponse = testService.postQQQ(userCodeNo, htmlCode);
    }


    //region
    @GetMapping("/p")
    @ResponseBody
    public JSONObject getQQQ(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userCodeNo") Integer userCodeNo
    ) throws Exception {
        JSONObject jSONOResponse = new JSONObject();
        return jSONOResponse;
    }
    //endregion
}