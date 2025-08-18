package com.app.kyc.service.exception;

import com.app.kyc.service.common.ErrorCode;

public class InvalidDataException extends Exception
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private ErrorCode errorCode;

   /**
    * Constructs an instance of {@link InvalidDataException} with the specified detail message.
    *
    * @param message the detail message. The detail message is saved for
    *                later retrieval by the {@link #getMessage()} method.
    */
   public InvalidDataException(String message)
   {
      super(message);
   }

   public InvalidDataException(String message, ErrorCode errorCode)
   {
      super(message);
      this.errorCode = errorCode;
   }

   public InvalidDataException(ErrorCode errorCode)
   {
      super(errorCode.getMessage());
      this.errorCode = errorCode;
   }

   public ErrorCode getErrorCode()
   {
      return errorCode;
   }

}
