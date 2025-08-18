package com.app.kyc.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.app.kyc.entity.Document;
import com.app.kyc.repository.DocumentRepository;
import com.app.kyc.util.PaginationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class DocumentServiceImpl implements DocumentService
{

   @Autowired
   private DocumentRepository documentRepository;

   public Document getDocumentById(Long id)
   {
      return documentRepository.findById(id).get();
   }

   public Document getDocumentByName(String name)
   {
      return documentRepository.findByName(name);
   }

   public Map<String, Object> getAllDocuments(String params) throws JsonMappingException, JsonProcessingException
   {
      Page<Document> pageDocuments = documentRepository.findAll(PaginationUtil.getPageable(params));
      Map<String, Object> documentsWithCount = new HashMap<String, Object>();
      documentsWithCount.put("data", pageDocuments.toList());
      documentsWithCount.put("count", pageDocuments.getTotalElements());
      return documentsWithCount;
   }

   public void addDocument(Document document)
   {
      documentRepository.save(document);
   }

   public Document updateDocument(Document document)
   {
      return documentRepository.save(document);
   }

}
