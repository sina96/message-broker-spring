package com.sinacodes.message_broker.service;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("activemqService")
public class ActiveMqService implements MessageBrokerService {

   private final JmsTemplate jmsTemplate;
   private final String queueName;

   public ActiveMqService(JmsTemplate jmsTemplate, @Value("${queue.name}") String queueName) {
      this.jmsTemplate = jmsTemplate;
      this.queueName = queueName;
   }

   @Override
   public void send(String message) {
      jmsTemplate.convertAndSend(queueName, message);
   }

   @Override
   public List<String> receive() {
      List<String> result = new ArrayList<>();
      Message message;

      // With receiveTimeout set, this will return null if no message after 1 second
      while ((message = jmsTemplate.receive(queueName)) != null) {
         try {
            if (message instanceof TextMessage textMessage) {
               result.add(textMessage.getText());
            }
         } catch (JMSException e) {
            e.printStackTrace(); // Optionally use a logger
         }
      }

      return result;
   }
}
