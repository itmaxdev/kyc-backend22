package com.app.kyc.response;

import java.util.List;

import com.app.kyc.entity.ConsumerService;
import com.app.kyc.model.ConsumerDto;

public class ConsumersDetailsResponseDTO
{
   ConsumerDto consumer;

   List<ConsumerService> consumerServices;

   public ConsumersDetailsResponseDTO(ConsumerDto consumer, List<com.app.kyc.entity.ConsumerService> consumerServices)
   {
      this.consumer = consumer;
      this.consumerServices = consumerServices;
   }

   public ConsumerDto getConsumer()
   {
      return consumer;
   }

   public void setConsumer(ConsumerDto consumer)
   {
      this.consumer = consumer;
   }

   public List<ConsumerService> getConsumerServices()
   {
      return consumerServices;
   }

   public void setConsumerServices(List<ConsumerService> consumerServices)
   {
      this.consumerServices = consumerServices;
   }

}
