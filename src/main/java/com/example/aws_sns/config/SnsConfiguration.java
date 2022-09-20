package com.example.aws_sns.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SnsConfiguration {

    @Bean
    @Primary
    public AmazonSNSClient amazonSNSClient(){

        BasicAWSCredentials credentials=new BasicAWSCredentials("accesskey","secretkey" );
        return (AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion("region")
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }
}
