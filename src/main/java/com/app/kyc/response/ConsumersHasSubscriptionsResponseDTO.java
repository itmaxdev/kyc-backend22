package com.app.kyc.response;

import com.app.kyc.model.ConsumerDto;

public class ConsumersHasSubscriptionsResponseDTO
{

   ConsumerDto consumer;
   boolean hasSubscription;

   public ConsumersHasSubscriptionsResponseDTO(ConsumerDto consumer, boolean hasSubscription)
   {
      this.consumer = consumer;
      this.hasSubscription = hasSubscription;
   }

   public ConsumerDto getConsumer()
   {
      return consumer;
   }

   public void setConsumer(ConsumerDto consumer)
   {
      this.consumer = consumer;
   }

   public boolean isHasSubscription()
   {
      return hasSubscription;
   }

   public void setHasSubscription(boolean hasSubscription)
   {
      this.hasSubscription = hasSubscription;
   }

}
