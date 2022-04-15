package com.example.demo.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validate_only;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Notification notification;
        private String token;
        private Data data;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Data {
        private String alarmHistorySender;
        private String alarmHistoryReceiver;
        private String alarmKindsNo;
        private String pageKindsNo;
    }
}

//@Builder
//@AllArgsConstructor
//@Getter
//public class FcmMessage {
////    private boolean validate_only;
//    private Message message;
//
//    @Builder
//    @AllArgsConstructor
//    @Getter
//    public static class Message {
//        private Notification notification;
////        private Data data;
//        private RegistrationIds registration_ids;
//        private Topic topic;
////        private String token;
//    }
//
//    @Builder
//    @AllArgsConstructor
//    @Getter
//    public static class Notification {
//        private String title;
//        private String body;
//        private String image;
//    }
//
//    //    @Builder
////    @AllArgsConstructor
////    @Getter
////    public static class Data {
////        private String alarmHistorySender;
////        private String alarmHistoryReceiver;
////        private String alarmKindsNo;
////        private String pageKindsNo;
////    }
////
//    @Builder
//    @AllArgsConstructor
//    @Getter
//    public static class RegistrationIds {
//        private String operation;
//        private String notification_key_name;
//        private String[] registration_ids;
//    }
//
//    @Builder
//    @AllArgsConstructor
//    @Getter
//    public static class Topic {
//        private String topic;
//    }
//}