package com.sinacodes.message_broker.config;

import com.sinacodes.message_broker.service.MessageBrokerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;


@Configuration
@Profile("dev") // Seeder only runs in 'dev' profile
public class MessageSeeder {

   @Value("${message.seeder.broker}")
   private String brokerKey;

   @Bean
   CommandLineRunner seedMessages(ApplicationContext context) {
      return args -> {
         Map<String, MessageBrokerService> brokerMap = Map.of(
               "sqs", context.getBean("sqsService", MessageBrokerService.class),
               "elasticmq", context.getBean("sqsService", MessageBrokerService.class),
               "activemq", context.getBean("activemqService", MessageBrokerService.class)
         );

         MessageBrokerService broker = brokerMap.get(brokerKey.toLowerCase());

         if (broker == null) {
            System.err.println("❌ Invalid broker configured for seeding: " + brokerKey);
            return;
         }

         broker.send("🚀 Seeded Message 1 from MessageSeeder");
         broker.send("📦 Seeded Message 2 from MessageSeeder");
         broker.send("🔁 Seeded Message 3 from MessageSeeder");

         System.out.println("✅ Messages seeded to broker: " + brokerKey);
      };
   }
}
