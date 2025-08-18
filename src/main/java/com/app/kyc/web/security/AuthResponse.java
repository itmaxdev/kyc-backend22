package com.app.kyc.web.security;

public class AuthResponse
{

   private String token;
   private String role;
   private Long userId;
   private String firstName;

   public AuthResponse(String token, String role, Long userId, String firstName)
   {
      this.token = token;
      this.role = role;
      this.userId = userId;
      this.firstName = firstName;
   }

   public String getToken()
   {
      return token;
   }

   public String getRole()
   {
      return role;
   }

   public Long getUserId()
   {
      return userId;
   }

   public void setUserId(Long userId)
   {
      this.userId = userId;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public void setToken(String token)
   {
      this.token = token;
   }

   public void setRole(String role)
   {
      this.role = role;
   }

}
