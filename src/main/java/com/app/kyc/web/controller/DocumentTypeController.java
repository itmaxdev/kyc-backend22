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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.kyc.entity.DocumentType;
import com.app.kyc.service.DocumentTypeService;
import com.app.kyc.web.security.SecurityHelper;

@RestController
@RequestMapping("/documentTypes")
public class DocumentTypeController
{

   @Autowired
   DocumentTypeService documentTypeService;

   @Autowired
   SecurityHelper securityHelper;

   @GetMapping("/{id}")
   public ResponseEntity<?> getDocumentTypeById(HttpServletRequest request, @PathVariable("id") Long id) throws SQLException
   {
      //log.info("DocumentTypeController/getDocumentTypeById");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
            return ResponseEntity.ok(documentTypeService.getDocumentTypeById(id));
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
   public ResponseEntity<?> getAllDocumentTypes(HttpServletRequest request, @RequestParam(value = "params", required = false) String params) throws SQLException
   {
      //log.info("DocumentTypeController/getAllDocumentTypes");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Map<String, Object> documents = documentTypeService.getAllDocumentTypes(params);
            return ResponseEntity.ok(documents);
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
   public ResponseEntity<?> addDocumentType(HttpServletRequest request, @RequestBody DocumentType document) throws SQLException
   {
      //log.info("DocumentTypeController/addDocumentType");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
         {
            documentTypeService.addDocumentType(document);
            return ResponseEntity.ok(document);
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
   public ResponseEntity<?> updateDocumentType(HttpServletRequest request, @RequestBody DocumentType document) throws SQLException
   {
      //log.info("DocumentTypeController/updateDocumentType");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Long id = document.getId();
            if(id == null || id <= 0 || id.toString() == "") return ResponseEntity.ok("id is Required");
            if(document.getName() == null || document.getName() == "") return ResponseEntity.ok("Name is Required");
            if((documentTypeService.getDocumentTypeById(id).getId() == null)) return ResponseEntity.ok("Service Type not found");
            documentTypeService.updateDocumentType(document);
            return ResponseEntity.ok("DocumentType updated successfully");
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
