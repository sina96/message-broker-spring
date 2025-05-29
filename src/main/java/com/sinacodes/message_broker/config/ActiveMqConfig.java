package com.sinacodes.message_broker.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import jakarta.jms.ConnectionFactory;

@Configuration
@EnableJms
public class ActiveMqConfig {

   @Value("${spring.activemq.broker-url}")
   private String brokerUrl;

   @Value("${spring.activemq.user}")
   private String username;

   @Value("${spring.activemq.password}")
   private String password;

   /**
    * Define the ActiveMQ connection factory.
    */
   @Bean
   public ConnectionFactory connectionFactory() {
      ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
      factory.setBrokerURL(brokerUrl);
      factory.setUserName(username);
      factory.setPassword(password);
      return factory;
   }

   /**
    * Define the JmsTemplate with a receive timeout.
    */
   @Bean
   public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
      JmsTemplate template = new JmsTemplate(connectionFactory);
      template.setReceiveTimeout(1000); // 1 second timeout
      return template;
   }
}
