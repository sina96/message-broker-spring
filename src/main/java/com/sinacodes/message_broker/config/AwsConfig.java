package com.sinacodes.message_broker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;


@Configuration
public class AwsConfig {

   @Value("${sqs.use-local}")
   private boolean useLocal;

   @Value("${sqs.local.endpoint}")
   private String localEndpoint;

   @Value("${sqs.local.region}")
   private String localRegion;

   @Value("${sqs.local.accessKey}")
   private String localAccessKey;

   @Value("${sqs.local.secretKey}")
   private String localSecretKey;

   @Value("${sqs.aws.region}")
   private String awsRegion;

   @Bean
   public SqsClient sqsClient() {
      return useLocal
            ? SqsClient.builder()
            .endpointOverride(URI.create(localEndpoint))
            .region(Region.of(localRegion))
            .credentialsProvider(StaticCredentialsProvider.create(
                  AwsBasicCredentials.create(localAccessKey, localSecretKey)))
            .build()
            : SqsClient.builder()
            .region(Region.of(awsRegion))
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();
   }
}

