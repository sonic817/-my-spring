package demo.service;

import demo.domain.SetPlace;
import demo.domain.Tag;
import demo.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.halowd.saveImg.SaveImg;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private final S3Client s3Client;

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

    //관광지 업로드
    public void uploadFiles(ByteArrayInputStream bis, String folderPath) throws IOException {
        String extension = ".jpg";

        //업로드
        String uploadName = UUID.randomUUID().toString().replace("_", "") + extension;
        PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(bucketName).key(folderPath + uploadName).build();

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(bi, "jpg", baos);
//        var tmp = new ByteArrayInputStream(baos.toByteArray());

        RequestBody rb = RequestBody.fromInputStream(bis, bis.available());
        s3Client.putObject(objectRequest, rb);
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
}