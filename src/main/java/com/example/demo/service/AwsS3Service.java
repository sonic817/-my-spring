package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final S3Client s3Client;
    private final List<String> EXTENSIONS_IMAGE = Arrays.asList("jpg", "gif", "png", "jpeg", "bmp", "tif");

    // Upload 하고자 하는 버킷의 이름
    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    //버킷아래 사용할 폴더명 지정 디폴트 summernote
    @Value("${cloud.aws.s3.folderName}")
    private String folderName;

    // Upload 하고자 하는 버킷의 이름
    @Value("${cloud.aws.s3.add}")
    public String add;

    // 폴더지정
    public String setFolderName(String folderName) throws IOException {
        return this.folderName = folderName;
    }

    //썸머노트업로드
    public String upload(MultipartFile multipartFile, String filename) throws IOException {
        return this.putS3(multipartFile, folderName + "/" + filename);
    }

    private String putS3(MultipartFile file, String key) throws IOException {
        PutObjectRequest objectRequest = getPutObjectRequest(key);
        RequestBody rb = getFileRequestBody(file);
        s3Client.putObject(objectRequest, rb);
        return findUploadKeyUrl(key);
    }

    private String findUploadKeyUrl(String key) {
        S3Utilities s3Utilities = s3Client.utilities();
        GetUrlRequest request = GetUrlRequest.builder().bucket(bucketName).key(key).build();
        URL url = s3Utilities.getUrl(request);
        return url.toString();
    }

    private PutObjectRequest getPutObjectRequest(String key) {
        return PutObjectRequest.builder().bucket(bucketName).key(key).build();
    }

    private RequestBody getFileRequestBody(MultipartFile file) throws IOException {
        return RequestBody.fromInputStream(file.getInputStream(), file.getSize());
    }

    //멀티업로드
    public List<String> uploadFiles(MultipartHttpServletRequest mRequest, String folderPath) throws IOException {
        List<MultipartFile> files = mRequest.getFiles("file");

        String extension = "";
        List<String> fileList = new ArrayList<>();

        //경로 없으면 폴더 생성
        boolean isExists = checkFolder(folderPath);
        if (isExists == false) {
            createFolder(folderPath);
        }

        for (int i = 0; i < files.size(); i++) {
            //확장자
            String fileName = files.get(i).getOriginalFilename();
            extension = "";
            if (fileName.contains(".")) {
                extension = fileName.substring(fileName.lastIndexOf("."));
            }

            //업로드
            String uploadName = UUID.randomUUID().toString().replace("_", "") + extension;
            PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(bucketName).key(folderPath + uploadName).build();
            RequestBody rb = RequestBody.fromInputStream(files.get(i).getInputStream(), files.get(i).getSize());
            s3Client.putObject(objectRequest, rb);
            fileList.add(uploadName);

//			//각 파일의 위치 반환
//			S3Utilities s3Utilities = s3Client.utilities();
//			GetUrlRequest request = GetUrlRequest.builder().bucket(bucketName).key(folderPath + uploadName).build();
//			URL url = s3Utilities.getUrl(request);
//			linkList.add(url.toString());
        }

        return fileList;
    }

    //여행용 멀티업로드
    public List<String> uploadFiles(List<MultipartFile> files, String folderPath) throws IOException {
        String extension = "";
        List<String> fileList = new ArrayList<>();

        //경로 없으면 폴더 생성
        boolean isExists = checkFolder(folderPath);
        if (isExists == false) {
            createFolder(folderPath);
        }

        for (int i = 0; i < files.size(); i++) {
            //확장자
            String fileName = files.get(i).getOriginalFilename();
            extension = "";
            if (fileName.contains(".")) {
                extension = fileName.substring(fileName.lastIndexOf("."));
            }

            //업로드
            String uploadName = UUID.randomUUID().toString().replace("_", "") + extension;
            PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(bucketName).key(folderPath + uploadName).build();
            RequestBody rb = RequestBody.fromInputStream(files.get(i).getInputStream(), files.get(i).getSize());
            s3Client.putObject(objectRequest, rb);
            fileList.add(uploadName);

//			//각 파일의 위치 반환
//			S3Utilities s3Utilities = s3Client.utilities();
//			GetUrlRequest request = GetUrlRequest.builder().bucket(bucketName).key(folderPath + uploadName).build();
//			URL url = s3Utilities.getUrl(request);
//			linkList.add(url.toString());
        }

        return fileList;
    }

    //폴더만들기
    public boolean createFolder(String folderName) throws IOException {
        boolean isSuccess = false;
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(folderName)
                    .build();
            s3Client.putObject(request, RequestBody.empty());
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
        }

        return isSuccess;
    }

    //폴더 유무 확인
    public boolean checkFolder(String folderName) throws IOException {
        ListObjectsV2Response listObjects =
                s3Client.listObjectsV2(ListObjectsV2Request.builder()
                        .bucket(bucketName)
                        .prefix(folderName)
                        .build());
        boolean isExists = !listObjects.contents().isEmpty();

        return isExists;
    }

    public void awuidnasd() {
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).prefix("user/10202/feed/feed_220410_0006/").build();
        ListObjectsV2Iterable response = s3Client.listObjectsV2Paginator(request);

        for (ListObjectsV2Response page : response) {
            page.contents().forEach((S3Object object) -> {
                System.out.println(object.key());
            });
        }
    }

    public List<String> getOriginFiles(MultipartHttpServletRequest mRequest) throws IOException {
        List<MultipartFile> files = mRequest.getFiles("file");
        List<String> originFileList = new ArrayList<>();

        if (files.size() > 0) {
            for (int i = 0; i < files.size(); i++) {
                originFileList.add(files.get(i).getOriginalFilename());
            }
        }

        return originFileList;
    }
}
