package com.starchive.springapp.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.findify.s3mock.S3Mock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class S3MockConfig {

    private String testBucketName = "test-bucket-in-memory";

    private String testRegion = "na-east-1";

    @Bean
    public S3Mock s3Mock() {
        return new S3Mock.Builder()
                .withPort(8001)
                .withInMemoryBackend()
                .build();
    }

    @Bean(name = "MockS3Client")
    public AmazonS3 amazonS3(S3Mock s3Mock) {
        s3Mock.start();
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
                "http://localhost:8001", testRegion);

        AmazonS3 client = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();

        client.createBucket(testBucketName);

        return client;
    }

    public String getTestBucketName() {
        return testBucketName;
    }
}