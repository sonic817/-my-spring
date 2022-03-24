package com.example.demo.service;

import com.example.demo.mapper.TestMapper;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequestWrapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public JSONObject tripDiary(MultipartFile multipartFile, Integer userCodeNo) throws ParseException {

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
//        String fileRoot = "/var/www/metalive/user/6/";
        String fileRoot = "C:/testFolder/";
//        String fileRoot = "/static/summernote/2203/";
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
        // 랜덤 UUID + 확장자로 저장될 savedFileName
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

        return jSONOResponse;
    }
}
