package com.app.kyc.web.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.app.kyc.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil
{

   @Value("${jwt.secret}")
   private String secret;

   @Value("${jwt.expiration}")
   private String expirationTime;

   private Key key;

   @PostConstruct
   public void init()
   {
      this.key = Keys.hmacShaKeyFor(secret.getBytes());
   }

   public Claims getAllClaimsFromToken(String token)
   {
      try
      {
         return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
      }
      catch(Exception e)
      {
         //            //log.info(e.getMessage());
         return null;
      }
   }

   public String getUsernameFromToken(String token)
   {
      try
      {
         return getAllClaimsFromToken(token).getSubject();
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
         return "";
      }
   }

   public Date getExpirationDateFromToken(String token)
   {
      try
      {
         return getAllClaimsFromToken(token).getExpiration();
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
         return null;
      }
   }

   private Boolean isTokenExpired(String token)
   {
      try
      {
         final Date expiration = getExpirationDateFromToken(token);
         return expiration.before(new Date());
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
         return false;
      }
   }

   public String generateToken(String username, Role role)
   {
      try
      {
         Map<String, Object> claims = new HashMap<>();
         claims.put("role", role);
         return doGenerateToken(claims, username);
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
         return "";
      }
   }

   private String doGenerateToken(Map<String, Object> claims, String username)
   {
      try
      {
         Long expirationTimeLong = Long.parseLong(expirationTime); //in second
         final Date createdDate = new Date();
         final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

         return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(createdDate).setExpiration(expirationDate).signWith(key).compact();
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
         return "";
      }
   }

   public Boolean validateToken(String token, UserDetails userDetails)
   {
      try
      {
         String username = getUsernameFromToken(token);
         return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
         return false;
      }
   }

}
