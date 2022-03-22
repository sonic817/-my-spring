package com.example.demo.service;

import com.example.demo.mapper.TestMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
