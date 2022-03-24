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


    @RequestMapping(value="/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
    @ResponseBody
    public JSONObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request )  {
        JSONObject jsonObject = new JSONObject();

        /*
         * String fileRoot = "C:\\summernote_image\\"; // 외부경로로 저장을 희망할때.
         */

        // 내부경로로 저장
        String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
        String fileRoot = contextRoot + "resources/fileupload/";

        String originalFileName = multipartFile.getOriginalFilename();    //오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));    //파일 확장자
        String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);
        try {
            InputStream fileStream = multipartFile.getInputStream();
//            FileUtils.c/opyInputStreamToFile(fileStream, targetFile);	//파일 저장
            jsonObject.put("url", "/summernote/resources/fileupload/" + savedFileName); // contextroot + resources + 저장할 내부 폴더명
            jsonObject.put("responseCode", "success");

        } catch (IOException e) {
//            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            jsonObject.put("responseCode", "error");
            e.printStackTrace();
        }

        return jsonObject;
    }
}