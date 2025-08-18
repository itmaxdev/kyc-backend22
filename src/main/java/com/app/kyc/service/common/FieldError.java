package com.app.kyc.service.common;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FieldError
{
   private String path;

   private String error;

   public FieldError()
   {
   }

   public FieldError(String path, String error)
   {
      this.path = path;
      this.error = error;
   }

   public String getPath()
   {
      return path;
   }

   public void setPath(String path)
   {
      this.path = path;
   }

   public String getError()
   {
      return error;
   }

   public void setError(String error)
   {
      this.error = error;
   }

   @Override
   public String toString()
   {
      return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }
}
