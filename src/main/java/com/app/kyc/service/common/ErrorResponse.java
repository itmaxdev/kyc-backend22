package com.app.kyc.service.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse
{

   private int errorCode;
   private String errorMessage;
   private List<String> errors = new ArrayList<>();

   @JsonInclude(JsonInclude.Include.NON_EMPTY)
   private List<FieldError> objectErrors = new ArrayList<>();
   @JsonInclude(JsonInclude.Include.NON_EMPTY)
   private List<FieldError> fieldErrors = new ArrayList<>();

   public ErrorResponse(ErrorCode errorCode)
   {
      this.errorCode = errorCode.getCode();
      this.errorMessage = errorCode.getMessage();
   }

   public ErrorResponse(ErrorCode errorCode, String errorMessage)
   {
      this.errorCode = errorCode.getCode();
      this.errorMessage = errorMessage;
   }

   public String getErrorMessage()
   {
      return errorMessage;
   }

   public void setErrorMessage(String errorMessage)
   {
      this.errorMessage = errorMessage;
   }

   public List<String> getErrors()
   {
      return errors;
   }

   public void setErrors(List<String> errors)
   {
      this.errors = errors;
   }

   public List<FieldError> getObjectErrors()
   {
      return objectErrors;
   }

   public void setObjectErrors(List<FieldError> objectErrors)
   {
      this.objectErrors = objectErrors;
   }

   public List<FieldError> getFieldErrors()
   {
      return fieldErrors;
   }

   public void setFieldErrors(List<FieldError> fieldErrors)
   {
      this.fieldErrors = fieldErrors;
   }

   public int getErrorCode()
   {
      return errorCode;
   }

   public void setErrorCode(ErrorCode errorCode)
   {
      this.errorCode = errorCode.getCode();
   }

   @Override
   public String toString()
   {
      return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
