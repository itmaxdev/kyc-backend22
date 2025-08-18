package com.app.kyc.service.exception;

import com.app.kyc.service.common.ErrorCode;

public class NotFoundExceptionCustom extends InvalidDataException
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   public NotFoundExceptionCustom(String message)
   {
      super(message);
   }

   public NotFoundExceptionCustom(ErrorCode message)
   {
      super(message);
   }
}
