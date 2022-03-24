package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/templates/", "classpath:/static/")
                .setCachePeriod(20);

        //web root가 아닌 외부 경로에 있는 리소스를 url로 불러올 수 있도록 설정
        //현재 localhost:8090/summernoteImage/1234.jpg
        //로 접속하면 C:/summernote_image/1234.jpg 파일을 불러온다.
        registry.addResourceHandler("/summernoteImage/**")
                .addResourceLocations("file:///C:/summernote_image/");
    }
}
