package com.app.kyc.service;

import java.util.Map;

import com.app.kyc.entity.Document;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface DocumentService
{

   public Document getDocumentById(Long id);

   public Document getDocumentByName(String name);

   public Map<String, Object> getAllDocuments(String params) throws JsonMappingException, JsonProcessingException;

   public void addDocument(Document document);

   public Document updateDocument(Document document);

}
