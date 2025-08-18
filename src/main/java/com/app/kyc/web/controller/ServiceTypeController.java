package com.app.kyc.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.kyc.entity.ServiceType;
import com.app.kyc.service.ServiceTypeService;
import com.app.kyc.service.UserService;
import com.app.kyc.web.security.SecurityHelper;

@RestController
@RequestMapping("/serviceTypes")
public class ServiceTypeController
{

   @Autowired
   ServiceTypeService serviceTypeService;

   @Autowired
   SecurityHelper securityHelper;

   @Autowired
   UserService userService;

   @GetMapping("/original/{id}")
   public ResponseEntity<?> getServiceTypeById(HttpServletRequest request, @PathVariable("id") Long id) throws SQLException
   {
      //log.info("ServiceTypeController/getServiceTypeById");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         if(securityHelper.hasRole(request, roles))
            return ResponseEntity.ok(serviceTypeService.getServiceTypeById(id));
         else
            return ResponseEntity.ok("Not authorized");
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
         return ResponseEntity.ok(e.getMessage());
      }
   }

   @GetMapping("/{id}")
   public ResponseEntity<?> getServiceTypeByIdWithAssociatedServices(HttpServletRequest request, @PathVariable("id") Long id) throws SQLException
   {
      //log.info("ServiceTypeController/getServiceTypeById");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         if(securityHelper.hasRole(request, roles))
            return ResponseEntity.ok(serviceTypeService.getServiceTypeByIdWithAssociatedServices(id));
         else
            return ResponseEntity.ok("Not authorized");
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
         return ResponseEntity.ok(e.getMessage());
      }
   }

   @GetMapping("/getByName/{name}")
   public ResponseEntity<?> getServiceTypeByName(HttpServletRequest request, @PathVariable("name") String name) throws SQLException
   {
      //log.info("ServiceTypeController/getServiceTypeByName");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         if(securityHelper.hasRole(request, roles))
            return ResponseEntity.ok(serviceTypeService.getServiceTypeByName(name));
         else
            return ResponseEntity.ok("Not authorized");
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
         return ResponseEntity.ok(e.getMessage());
      }
   }

   @GetMapping("/getAll/")
   public ResponseEntity<?> getAllServiceTypesWithAssociatedServices(HttpServletRequest request, @RequestParam(value = "params", required = false) String params)
      throws SQLException
   {
      //log.info("ServiceTypeController/getAllServiceTypes");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Map<String, Object> response = serviceTypeService.getAllServiceTypesWithAssociatedServices(params);
            return ResponseEntity.ok(response);
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

   @GetMapping("/getByIndustry/{industryId}")
   public ResponseEntity<?> getAllServiceTypesByIndustryId(HttpServletRequest request, @PathVariable("industryId") Long industryId) throws SQLException
   {
      //log.info("ServiceTypeController/getAllServiceTypes");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Map<String, Object> response = serviceTypeService.getServiceTypesByIndustryId(industryId);
            return ResponseEntity.ok(response);
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

   @PostMapping("/add")
   public ResponseEntity<?> addServiceType(HttpServletRequest request, @RequestBody ServiceType serviceType) throws SQLException
   {
      //log.info("ServiceTypeController/addServiceType");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         if(securityHelper.hasRole(request, roles))
         {
            final String authorizationHeader = request.getHeader("Authorization");
            String email = securityHelper.getUserName(authorizationHeader.substring(7));
            serviceTypeService.addServiceType(serviceType, email);
            return new ResponseEntity<>(HttpStatus.OK);
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

   @PutMapping("/update")
   public ResponseEntity<?> updateServiceType(HttpServletRequest request, @RequestBody ServiceType serviceType) throws SQLException
   {
      //log.info("ServiceTypeController/updateServiceType");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         if(securityHelper.hasRole(request, roles))
         {
            serviceTypeService.updateServiceType(serviceType);
            return ResponseEntity.ok("ServiceType updated successfully");
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

   @DeleteMapping("/deleteServiceType/{id}")
   public ResponseEntity<?> deleteServiceType(HttpServletRequest request, @PathVariable("id") long id) throws SQLException
   {
      //log.info("ServiceTypeController/changePassword");
      try
      {
         ServiceType serviceType = serviceTypeService.getServiceTypeById(id);
         if(serviceType.getId() == null) return ResponseEntity.ok("ServiceType not found");
         List<String> roles = new ArrayList<String>();
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         if(securityHelper.hasRole(request, roles))
         {
            serviceTypeService.deleteServiceType(id);
            return ResponseEntity.ok("ServiceType Deleted");
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
