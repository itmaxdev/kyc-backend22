package com.app.kyc.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.kyc.entity.Role;
import com.app.kyc.entity.User;
import com.app.kyc.service.RoleService;
import com.app.kyc.service.UserService;
import com.app.kyc.web.security.SecurityHelper;

@RestController
@RequestMapping("/roles")
public class RoleController
{

   @Autowired
   RoleService roleService;

   @Autowired
   SecurityHelper securityHelper;
   
   @Autowired
   UserService userService;

   @GetMapping("/{id}")
   public ResponseEntity<?> getRoleById(HttpServletRequest request, @PathVariable("id") Long id) throws SQLException
   {
      //log.info("RoleController/getRoleById");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         if(securityHelper.hasRole(request, roles))
            return ResponseEntity.ok(roleService.getRoleById(id));
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
   public ResponseEntity<?> getRoleByName(HttpServletRequest request, @PathVariable("name") String name) throws SQLException
   {
      //log.info("RoleController/getRoleByName");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         if(securityHelper.hasRole(request, roles))
            return ResponseEntity.ok(roleService.getRoleByName(name));
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
   public ResponseEntity<?> getAllRoles(HttpServletRequest request) throws SQLException
   {
      //log.info("RoleController/getAllRoles");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         if(securityHelper.hasRole(request, roles))
         {
            String authTokenHeader = request.getHeader("Authorization");
            String jwt = "";
            if(authTokenHeader != null && authTokenHeader.startsWith(("Bearer ")))
            {
               jwt = authTokenHeader.substring(7);
            }
            User user = userService.getUserByEmail(securityHelper.getUserName(jwt));
            Map<String, Object> rolesWithCount = roleService.getRoles(user);
            return ResponseEntity.ok(rolesWithCount);
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
   public ResponseEntity<?> addRole(HttpServletRequest request, @RequestBody Role role) throws SQLException
   {
      //log.info("RoleController/addRole");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         if(securityHelper.hasRole(request, roles))
         {
            if(role.getName() == null || role.getName() == "") return ResponseEntity.ok("Name is Required");
            if(roleService.getRoleByName(role.getName()).getId() != null) return ResponseEntity.ok("Name already in use");
            roleService.addRole(role);
            return ResponseEntity.ok("Role added successfully");
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
   public ResponseEntity<?> updateRole(HttpServletRequest request, @RequestBody Role role) throws SQLException
   {
      //log.info("RoleController/updateRole");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Long id = role.getId();
            if(id == null || id <= 0 || id.toString() == "") return ResponseEntity.ok("id is Required");
            if(role.getName() == null || role.getName() == "") return ResponseEntity.ok("Name is Required");
            if((roleService.getRoleById(id).getId() == null)) return ResponseEntity.ok("Service Type not found");
            roleService.updateRole(role);
            return ResponseEntity.ok("Role updated successfully");
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
