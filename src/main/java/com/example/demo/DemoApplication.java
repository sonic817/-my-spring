package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Base64;
import java.util.UUID;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);


//		String text = "<p>이강원</p><p><br></p><p>한재호</p>";
//		String textWithoutTag = text.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
//		System.out.println(textWithoutTag);
//
//		System.out.println("-------------------------------");
//
//		String fileName = "asdhtuysabda.png";
//		String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
//		UUID uuid = UUID.randomUUID();
//		String newFileName = uuid.toString() + extension;
//		System.out.println(newFileName);

//		String tmp = "<p>sans-serif 기본</p><p><span style=\"font-size: 24px;\">sans-serif size 24</span></p><p><span style=\"font-weight: bolder; font-size: 14px;\">sans-seirf 굵기</span></p><p><i>sans-serif 기울기</i></p><p><u>sans-serif 밑줄</u></p><p><font color=\"#ff0000\">sans-serif 빨간색</font></p><p><span style=\"font-size: 24px;\"><b><i><u><font color=\"#ff0000\">sans-serif size24, 굵기, 기울기, 밑줄, 빨간색</font></u></i></b></span></p><p><span style=\"font-size: 24px;\"><b><i><u><font color=\"#ff0000\" face=\"Arial Black\">Arial Black size24, 굵기, 기울기, 밑줄, 빨간색</font></u></i></b></span></p><p>중간<span style=\"font-size: 24px;\">사이즈24</span>중간 중간<b>굵기</b>중간 중간<i>기울기</i>중간 중간<u>밑줄</u>중간 중간<font color=\"#ff0000\">빨간색</font>중간</p>";
//		String encodedString = Base64.getEncoder().encodeToString(tmp.getBytes());
//		Integer awd = 2;
	}
}
