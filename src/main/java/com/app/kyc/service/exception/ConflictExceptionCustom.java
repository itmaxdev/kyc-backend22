package com.app.kyc.service.exception;

import com.app.kyc.service.common.ErrorCode;

public class ConflictExceptionCustom extends Exception
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private ErrorCode errorCode;

   /**
    * Constructs an instance of {@link ConflictExceptionCustom} with the specified detail message.
    *
    * @param message the detail message. The detail message is saved for
    *                later retrieval by the {@link #getMessage()} method.
    */
   public ConflictExceptionCustom(String message)
   {
      super(message);
   }

   public ConflictExceptionCustom(String message, ErrorCode errorCode)
   {
      super(message);
      this.errorCode = errorCode;
   }

   public ConflictExceptionCustom(ErrorCode errorCode)
   {
      super(errorCode.getMessage());
      this.errorCode = errorCode;
   }

   public ErrorCode getErrorCode()
   {
      return errorCode;
   }

}
