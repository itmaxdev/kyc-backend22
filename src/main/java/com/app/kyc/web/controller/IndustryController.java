package com.app.kyc.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.app.kyc.entity.Industry;
import com.app.kyc.service.IndustryService;
import com.app.kyc.web.security.SecurityHelper;

@RestController
@RequestMapping("/industries")
public class IndustryController
{

   @Autowired
   IndustryService industryService;

   @Autowired
   SecurityHelper securityHelper;

   @GetMapping("/{id}")
   public ResponseEntity<?> getIndustryById(HttpServletRequest request, @PathVariable("id") Long id) throws SQLException
   {
      //log.info("IndustryController/getIndustryById");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
            return ResponseEntity.ok(industryService.getIndustryById(id));
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
   public ResponseEntity<?> getIndustryByName(HttpServletRequest request, @PathVariable("name") String name) throws SQLException
   {
      //log.info("IndustryController/getIndustryByName");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
            return ResponseEntity.ok(industryService.getIndustryByName(name));
         else
            return ResponseEntity.ok("Not authorized");
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
         return ResponseEntity.ok(e.getMessage());
      }
   }

   @GetMapping("/getAll")
   public ResponseEntity<?> getAllIndustries(HttpServletRequest request, @RequestParam(value = "params", required = false) String params) throws SQLException
   {
      //log.info("IndustryController/getAllIndustries");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         roles.add("Compliance Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Map<String, Object> industries = industryService.getAllIndustries(params);
            return ResponseEntity.ok(industries);
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
   public ResponseEntity<?> addIndustry(HttpServletRequest request, @RequestBody Industry industry) throws SQLException
   {
      //log.info("IndustryController/addIndustry");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
         {
            industryService.addIndustry(industry);
            return ResponseEntity.ok(industry);
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
   public ResponseEntity<?> updateIndustry(HttpServletRequest request, @RequestBody Industry industry) throws SQLException
   {
      //log.info("IndustryController/updateIndustry");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
         {
            industryService.updateIndustry(industry);
            return ResponseEntity.ok("Industry updated successfully");
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

   @DeleteMapping("/deleteIndustry/{id}")
   public ResponseEntity<?> deleteIndustry(HttpServletRequest request, @PathVariable("id") long id) throws SQLException
   {
      //log.info("IndustryController/changePassword");
      try
      {
         Industry industry = industryService.getIndustryById(id);
         if(industry.getId() == null) return ResponseEntity.ok("Industry not found");
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles)) industryService.deleteIndustry(id);
         return ResponseEntity.ok("Industry Deleted");
      }
      catch(Exception e)
      {
         //log.info(e.getMessage());
         return ResponseEntity.ok(e.getMessage());
      }
   }

}
