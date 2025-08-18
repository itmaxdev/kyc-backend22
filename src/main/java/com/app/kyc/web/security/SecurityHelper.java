package com.app.kyc.web.security;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;

@Service
public class SecurityHelper
{

   @Autowired
   JWTUtil jwtUtil;

   public boolean hasRole(HttpServletRequest request, List<String> roles)
   {
      final String authorizationHeader = request.getHeader("Authorization");
      List<String> rolesMap = new ArrayList<String>();
      if(authorizationHeader != null && authorizationHeader.startsWith(("Bearer ")))
      {
         Claims claims = jwtUtil.getAllClaimsFromToken(authorizationHeader.substring(7));
         Object role = claims.get("role");
         rolesMap.add(((LinkedHashMap<?, ?>) role).get("name").toString());
      }
      for(int i = 0; i < rolesMap.size(); i++)
      {
         return (roles.contains(rolesMap.get(i)));
      }
      return false;
   }

   public String encryptPassword(String username, String password)
   {
      org.springframework.security.core.userdetails.User user;
      user = (org.springframework.security.core.userdetails.User) org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder().username(username)
         .password(password).roles("User").build();
      return user.getPassword();
   }

   public boolean passwordIsValid(String password, String ePassowrd)
   {
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      return passwordEncoder.matches(password, ePassowrd);
   }

   public boolean isValidEmail(String email)
   {
      //log.info("SecurityHelper/isValidEmail");
      try
      {
         String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(email);
         return matcher.matches();
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
      }
      return false;
   }

   public String getUserName(String token)
   {
      //log.info("SecurityHelper/getUserName");
      try
      {
         return jwtUtil.getUsernameFromToken(token);
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
      }
      return "";
   }

}
