package com.example.demo.controller;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.SysexMessage;

@Controller
public class TestController {

    @GetMapping("/")
    @ResponseBody
    public JSONObject home(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        JSONObject jSONOResponse = new JSONObject();
        jSONOResponse.put("data", "hello world");
        return jSONOResponse;
    }

    @GetMapping("/html")
    public String tripDiary() {
        System.out.println(11);
        return "a";
    }

    @GetMapping("/test")
    @ResponseBody
    public JSONObject asd(
            @ApiIgnore HttpSession session, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        JSONObject jSONOResponse = new JSONObject();
        jSONOResponse.put("test", "test");
        jSONOResponse.put("test", "est");
        return jSONOResponse;
    }
}
