package com.app.kyc.web.security;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.kyc.service.AppUserDetailsService;


@Component
public class JWTRequestFilter extends OncePerRequestFilter
{

   @Autowired
   AppUserDetailsService appUserDetailsService;

   @Autowired
   JWTUtil jwtUtil;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
   {
      try
      {
         final String authorizationHeader = request.getHeader("Authorization");
         String usernameOrEmail = null;
         String jwt = null;

         if(authorizationHeader != null && authorizationHeader.startsWith(("Bearer ")))
         {
            jwt = authorizationHeader.substring(7);
            usernameOrEmail = jwtUtil.getUsernameFromToken(jwt);
         }

         if(usernameOrEmail != null && usernameOrEmail != "" && SecurityContextHolder.getContext().getAuthentication() == null)
         {
            UserDetails userDetails = appUserDetailsService.loadUserByUsername(usernameOrEmail);
            if(jwtUtil.validateToken(jwt, userDetails))
            {
               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
         }
         chain.doFilter(request, response);
      }
      catch(Exception e)
      {
         //            log.info(e.getMessage());
      }
   }

}
