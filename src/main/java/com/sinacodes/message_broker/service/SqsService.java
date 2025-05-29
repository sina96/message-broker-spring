package com.sinacodes.message_broker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.ArrayList;
import java.util.List;


@Service("sqsService")
public class SqsService implements MessageBrokerService
{

   private final SqsClient sqsClient;
   private final String queueName;

   public SqsService(SqsClient sqsClient, @Value("${queue.name}") String queueName) {
      this.sqsClient = sqsClient;
      this.queueName = queueName;
   }

   @Override
   public void send(String message) {
      String queueUrl = getQueueUrl();
      sqsClient.sendMessage(SendMessageRequest.builder()
            .queueUrl(queueUrl).messageBody(message).build());
   }

   @Override
   public List<String> receive() {
      String queueUrl = getQueueUrl();
      var messages = sqsClient.receiveMessage(
            ReceiveMessageRequest.builder().queueUrl(queueUrl).maxNumberOfMessages(5).build()
      ).messages();

      List<String> result = new ArrayList<>();
      for (var msg : messages) {
         result.add(msg.body());
         sqsClient.deleteMessage(DeleteMessageRequest.builder()
               .queueUrl(queueUrl).receiptHandle(msg.receiptHandle()).build());
      }
      return result;
   }

   private String getQueueUrl() {
      return sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build()).queueUrl();
   }
}
