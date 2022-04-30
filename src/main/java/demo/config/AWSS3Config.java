package demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AWSS3Config {

    private String accessKey;
    private String secretKey;
    private String region;

    // @Value 어노테이션의 값들은 s3관련된 계정IAM의 accessKey, secretKey와 S3의 리전을 넣어주시면됩니다.
    public AWSS3Config(@Value("${cloud.aws.s3.accessKey}") String accessKey,
                       @Value("${cloud.aws.s3.secretKey}") String secretKey,
                       @Value("${cloud.aws.s3.region}") String region) {

        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
    }

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }
}