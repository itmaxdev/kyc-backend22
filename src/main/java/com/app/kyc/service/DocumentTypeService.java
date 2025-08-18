package com.app.kyc.service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.app.kyc.entity.DocumentType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface DocumentTypeService
{

   public void addDocumentType(DocumentType documentType) throws SQLException;

   public DocumentType getDocumentTypeById(Long id);

   public Map<String, Object> getAllDocumentTypes(String params) throws JsonMappingException, JsonProcessingException;

   public DocumentType updateDocumentType(DocumentType document) throws NotFoundException;

}
