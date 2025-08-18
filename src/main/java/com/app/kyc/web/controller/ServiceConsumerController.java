package com.app.kyc.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.kyc.entity.ConsumerService;
import com.app.kyc.service.ConsumerServiceService;
import com.app.kyc.service.ServiceService;
import com.app.kyc.service.UserService;
import com.app.kyc.web.security.SecurityHelper;

@RestController
@RequestMapping("/servicesConsumers")
public class ServiceConsumerController
{

   @Autowired
   ServiceService serviceService;

   @Autowired
   ConsumerServiceService consumerServiceService;

   @Autowired
   SecurityHelper securityHelper;

   @Autowired
   UserService userService;

   @PostMapping("/add")
   public ResponseEntity<?> addService(HttpServletRequest request, @RequestBody ConsumerService consumerServiceObj) throws SQLException
   {
      //log.info("ServiceConsumerController/addService");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("Compliance Admin");
         roles.add("SP Admin");
         if(securityHelper.hasRole(request, roles))
         {
            final String authorizationHeader = request.getHeader("Authorization");
            String userName = null;
            if(authorizationHeader != null && authorizationHeader.startsWith(("Bearer ")))
            {
               userName = securityHelper.getUserName(authorizationHeader.substring(7));
            }
            if(userName == null) return ResponseEntity.ok("Invalid user");
            consumerServiceService.addConsumerService(consumerServiceObj);
            return ResponseEntity.ok(consumerServiceService);
         }
         else
            return ResponseEntity.ok("Not authorized");
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
         return ResponseEntity.ok(e.getMessage());
      }
   }

}
