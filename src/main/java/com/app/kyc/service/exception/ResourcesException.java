package com.app.kyc.service.exception;

import com.app.kyc.service.common.ErrorCode;

/**
 * Define a Resource exception while calling another resource endpoint 
 * @author cme
 *
 */
public class ResourcesException extends Exception
{

   private ErrorCode errorCode;
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of {@link ResourcesException} with the specified detail message.
    *
    * @param message the detail message. The detail message is saved for
    *                later retrieval by the {@link #getMessage()} method.
    */
   public ResourcesException(String message)
   {
      super(message);
   }

   public ResourcesException(String message, ErrorCode errorCode)
   {
      super(message);
      this.errorCode = errorCode;
   }

   public ResourcesException(ErrorCode errorCode)
   {
      super(errorCode.getMessage());
      this.errorCode = errorCode;
   }

   public ErrorCode getErrorCode()
   {
      return errorCode;
   }

}
