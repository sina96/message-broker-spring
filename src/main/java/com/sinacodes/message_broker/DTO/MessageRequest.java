package com.sinacodes.message_broker.DTO;

public class MessageRequest {
   private String body;
   private String broker; // NEW FIELD

   public String getBody() {
      return body;
   }

   public void setBody(String body) {
      this.body = body;
   }

   public String getBroker() {
      return broker;
   }

   public void setBroker(String broker) {
      this.broker = broker;
   }
}

