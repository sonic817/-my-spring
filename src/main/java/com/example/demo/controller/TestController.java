package com.example.demo.controller;

import com.example.demo.service.TestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
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
    public String summernote() {
        return "/summernote";
    }

    //region
    @PostMapping("/p")
    @ResponseBody
    public JSONObject postQQQ(
            @ApiIgnore HttpSession session,
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userCodeNo") Integer userCodeNo
    ) throws Exception {
        JSONObject jSONOResponse = testService.postQQQ(userCodeNo);
        return jSONOResponse;
    }

    //region
    @GetMapping("/qqq")
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