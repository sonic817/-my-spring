package com.example.demo.service;

import com.example.demo.mapper.TestMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
