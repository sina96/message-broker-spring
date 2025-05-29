package com.sinacodes.message_broker.service;

import java.util.List;


public interface MessageBrokerService {
   void send(String message);
   List<String> receive();
}
