package com.sinacodes.message_broker.controller;

import com.sinacodes.message_broker.DTO.MessageRequest;
import com.sinacodes.message_broker.service.MessageBrokerService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

   private final Map<String, MessageBrokerService> brokerMap;

   public MessageController(ApplicationContext context) {
      this.brokerMap = Map.of(
            "sqs", context.getBean("sqsService", MessageBrokerService.class),
            "elasticmq", context.getBean("sqsService", MessageBrokerService.class),
            "activemq", context.getBean("activemqService", MessageBrokerService.class)
      );
   }

   @PostMapping("/send")
   public String sendMessage(@RequestBody MessageRequest request) {
      MessageBrokerService broker = resolveBroker(request.getBroker());
      broker.send(request.getBody());
      return "Message sent via " + request.getBroker();
   }

   @GetMapping("/receive")
   public List<String> receiveMessages(@RequestParam String broker) {
      MessageBrokerService service = resolveBroker(broker);
      return service.receive();
   }

   private MessageBrokerService resolveBroker(String brokerKey) {
      if (!brokerMap.containsKey(brokerKey)) {
         throw new IllegalArgumentException("Invalid broker: " + brokerKey);
      }
      return brokerMap.get(brokerKey);
   }
}

