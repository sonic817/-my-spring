package com.example.demo.service;


import com.example.demo.domain.FcmMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.TopicManagementResponse;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/metalive-339505/messages:send";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessageTo(String targetToken, String title, String body, String alarmHistorySender, String alarmHistoryReceiver, String alarmKindsNo, String pageKindsNo) throws IOException {
        String message = makeMessage(targetToken, title, body, alarmHistorySender, alarmHistoryReceiver, alarmKindsNo, pageKindsNo);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .build();

        Response response = client.newCall(request)
                .execute();

        System.out.println(response.body().string());
    }

    private String makeMessage(String targetToken, String title, String body, String alarmHistorySender, String alarmHistoryReceiver, String alarmKindsNo, String pageKindsNo) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .data(
                                FcmMessage.Data.builder()
                                        .alarmHistorySender(alarmHistorySender)
                                        .alarmHistoryReceiver(alarmHistoryReceiver)
                                        .alarmKindsNo(alarmKindsNo)
                                        .pageKindsNo(pageKindsNo)
                                        .build())
                        .build()
                )
                .validate_only(false)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}