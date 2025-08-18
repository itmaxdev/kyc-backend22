package com.app.kyc.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.kyc.entity.Service;
import com.app.kyc.entity.User;
import com.app.kyc.model.ServiceStatus;
import com.app.kyc.request.ApproveServiceRequest;
import com.app.kyc.service.ConsumerServiceService;
import com.app.kyc.service.ServiceService;
import com.app.kyc.service.UserService;
import com.app.kyc.web.security.SecurityHelper;

@RestController
@RequestMapping("/services")
public class ServiceController
{

   @Autowired
   ServiceService serviceService;

   @Autowired
   ConsumerServiceService consumerServiceService;

   @Autowired
   SecurityHelper securityHelper;

   @Autowired
   UserService userService;

   @GetMapping("/{id}")
   public ResponseEntity<?> getServiceById(HttpServletRequest request, @PathVariable("id") Long id) throws SQLException
   {
      //log.info("ServiceController/getServiceById");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("SP Admin");
         roles.add("SP User");
         roles.add("Compliance Admin");
         if(securityHelper.hasRole(request, roles))
            return ResponseEntity.ok(serviceService.getServiceByIdResponse(id));
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
   public ResponseEntity<?> getServiceByName(HttpServletRequest request, @PathVariable("name") String name) throws SQLException
   {
      //log.info("ServiceController/getServiceByName");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         if(securityHelper.hasRole(request, roles))
            return ResponseEntity.ok(serviceService.getServiceByName(name));
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
   public ResponseEntity<?> getAllServices(HttpServletRequest request, @RequestParam(value = "params", required = false) String params) throws SQLException
   {
      //log.info("ServiceController/getAllServices");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Map<String, Object> services = serviceService.getAllServicesWithUserInfo(params);
            return ResponseEntity.ok(services);
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

   @GetMapping("/getAllByServiceProvider/{id}")
   public ResponseEntity<?> getAllServicesByServiceProvider(HttpServletRequest request, @PathVariable("id") Long id) throws SQLException
   {
      //log.info("ServiceController/getAllServices");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("SP Admin");
         roles.add("SP User");
         roles.add("Compliance Admin");
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Map<String, Object> services = serviceService.getAllServicesByServiceProviderId(id);
            return ResponseEntity.ok(services);
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

   @GetMapping("/getAllByServiceType/{id}")
   public ResponseEntity<?> getAllServicesByServiceType(HttpServletRequest request, @PathVariable("id") Long id) throws SQLException
   {
      //log.info("ServiceController/getAllServices");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Map<String, Object> services = serviceService.getAllServicesMapByServiceTypeId(id);
            return ResponseEntity.ok(services);
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

   @GetMapping("/getAllConsumerServices/{consumerId}")
   public ResponseEntity<?> getAllConsumerServicesByConsumerId(HttpServletRequest request, @PathVariable("consumerId") Long consumerId) throws SQLException
   {
      //log.info("ServiceController/getAllServices");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Map<String, Object> Services = consumerServiceService.getAllConsumerServices(consumerId);
            return ResponseEntity.ok(Services);
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
   public ResponseEntity<?> addService(HttpServletRequest request, @RequestBody Service service) throws SQLException
   {
      //log.info("ServiceController/addService");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("Compliance Admin");
         roles.add("SP Admin");
         if(securityHelper.hasRole(request, roles))
         {
            if(serviceService.getServiceByName(service.getName()) != null) return ResponseEntity.ok("Name already in use");
            final String authorizationHeader = request.getHeader("Authorization");
            String userName = null;
            if(authorizationHeader != null && authorizationHeader.startsWith(("Bearer ")))
            {
               userName = securityHelper.getUserName(authorizationHeader.substring(7));
            }
            if(userName == null) return ResponseEntity.ok("Invalid user");
            service.setCreatedBy(userService.getUserByEmail(userName).getId());
            service.setStatus(ServiceStatus.Inactive);
            service.setCreatedOn(new Date());
            serviceService.addService(service);
            return ResponseEntity.ok(service);
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
   public ResponseEntity<?> updateService(HttpServletRequest request, @RequestBody Service Service) throws SQLException
   {
      //log.info("ServiceController/updateService");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("Compliance Admin");
         roles.add("SP Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Long id = Service.getId();
            if(id == null || id <= 0 || id.toString() == "") return ResponseEntity.ok("id is Required");
            if(Service.getName() == null || Service.getName() == "") return ResponseEntity.ok("Name is Required");
            if((serviceService.getServiceById(id).getId() == null)) return ResponseEntity.ok("Service Provider not found");
            serviceService.updateService(Service);
            return ResponseEntity.ok("Service updated successfully");
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

   @PatchMapping("/approve")
   public ResponseEntity<?> approveService(HttpServletRequest request, @RequestBody ApproveServiceRequest approveServiceRequest) throws SQLException
   {
      //log.info("ServiceController/activateService");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("Compliance Admin");
         if(securityHelper.hasRole(request, roles))
         {
            String authTokenHeader = request.getHeader("Authorization");
            String jwt = "";
            if(authTokenHeader != null && authTokenHeader.startsWith(("Bearer ")))
            {
               jwt = authTokenHeader.substring(7);
            }
            User user = userService.getUserByEmail(securityHelper.getUserName(jwt));
            approveServiceRequest.setUserId(user.getId());
            serviceService.approveService(approveServiceRequest);
            return ResponseEntity.ok("Request processed successfully");
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

   @PatchMapping("/activate/{id}")
   public ResponseEntity<?> activateService(HttpServletRequest request, @PathVariable("id") Long id) throws SQLException
   {
      //log.info("ServiceController/activateService");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         if(securityHelper.hasRole(request, roles))
         {
            String authTokenHeader = request.getHeader("Authorization");
            String jwt = "";
            if(authTokenHeader != null && authTokenHeader.startsWith(("Bearer ")))
            {
               jwt = authTokenHeader.substring(7);
            }
            User user = userService.getUserByEmail(securityHelper.getUserName(jwt));
            if(id == null || id <= 0 || id.toString() == "") return ResponseEntity.ok("id is Required");
            if(user.getId() == 0) return ResponseEntity.ok("Invalid user");
            serviceService.activateService(id, user.getId());
            return ResponseEntity.ok("Service activated successfully");
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

   @PatchMapping("/deactivate/{id}")
   public ResponseEntity<?> deactivateService(HttpServletRequest request, @PathVariable("id") Long id) throws SQLException
   {
      //log.info("ServiceController/deactivateService");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         if(securityHelper.hasRole(request, roles))
         {
            String authTokenHeader = request.getHeader("Authorization");
            String jwt = "";
            if(authTokenHeader != null && authTokenHeader.startsWith(("Bearer ")))
            {
               jwt = authTokenHeader.substring(7);
            }
            User user = userService.getUserByEmail(securityHelper.getUserName(jwt));
            if(id == null || id <= 0 || id.toString() == "") return ResponseEntity.ok("id is Required");
            if(user.getId() == 0) return ResponseEntity.ok("Invalid user");
            serviceService.deactivateService(id);
            return ResponseEntity.ok("Service deactivated successfully");
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
