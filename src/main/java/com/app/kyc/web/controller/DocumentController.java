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

import com.app.kyc.entity.Document;
import com.app.kyc.service.DocumentService;
import com.app.kyc.web.security.SecurityHelper;

@RestController
@RequestMapping("/documents")
public class DocumentController
{

   @Autowired
   DocumentService documentService;

   @Autowired
   SecurityHelper securityHelper;

   @GetMapping("/{id}")
   public ResponseEntity<?> getDocumentById(HttpServletRequest request, @PathVariable("id") Long id) throws SQLException
   {
      //log.info("DocumentController/getDocumentById");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         if(securityHelper.hasRole(request, roles))
            return ResponseEntity.ok(documentService.getDocumentById(id));
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
   public ResponseEntity<?> getDocumentByName(HttpServletRequest request, @PathVariable("name") String name) throws SQLException
   {
      //log.info("DocumentController/getDocumentByName");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         if(securityHelper.hasRole(request, roles))
            return ResponseEntity.ok(documentService.getDocumentByName(name));
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
   public ResponseEntity<?> getAllDocuments(HttpServletRequest request, @RequestParam(value = "params", required = false) String params) throws SQLException
   {
      //log.info("DocumentController/getAllDocuments");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Map<String, Object> documents = documentService.getAllDocuments(params);
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
   public ResponseEntity<?> addDocument(HttpServletRequest request, @RequestBody Document document) throws SQLException
   {
      //log.info("DocumentController/addDocument");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         if(securityHelper.hasRole(request, roles))
         {
            if(document.getName() == null || document.getName() == "") return ResponseEntity.ok("Name is Required");
            if(documentService.getDocumentByName(document.getName()).getId() != null) return ResponseEntity.ok("Name already in use");
            documentService.addDocument(document);
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
   public ResponseEntity<?> updateDocument(HttpServletRequest request, @RequestBody Document document) throws SQLException
   {
      //log.info("DocumentController/updateDocument");
      try
      {
         List<String> roles = new ArrayList<String>();
         roles.add("KYC Admin");
         roles.add("SP Admin");
         if(securityHelper.hasRole(request, roles))
         {
            Long id = document.getId();
            if(id == null || id <= 0 || id.toString() == "") return ResponseEntity.ok("id is Required");
            if(document.getName() == null || document.getName() == "") return ResponseEntity.ok("Name is Required");
            if((documentService.getDocumentById(id).getId() == null)) return ResponseEntity.ok("Service Type not found");
            documentService.updateDocument(document);
            return ResponseEntity.ok("Document updated successfully");
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
