package com.app.kyc.request;

public class ResetPasswordRequestDTO
{
   String email;

   String newPassword;

   Long code;

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

   public String getNewPassword()
   {
      return newPassword;
   }

   public void setNewPassword(String newPassword)
   {
      this.newPassword = newPassword;
   }

   public Long getCode()
   {
      return code;
   }

   public void setCode(Long code)
   {
      this.code = code;
   }

}
