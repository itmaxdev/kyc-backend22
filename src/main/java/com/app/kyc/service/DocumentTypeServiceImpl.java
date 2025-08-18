package com.app.kyc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.app.kyc.entity.DocumentType;
import com.app.kyc.repository.DocumentTypeRepository;
import com.app.kyc.util.PaginationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService
{

   @Autowired
   private DocumentTypeRepository documentTypeRepository;

   public void addDocumentType(DocumentType documentType)
   {
      documentType.setCreatedOn(new Date());
      documentTypeRepository.save(documentType);
   }

   public DocumentType getDocumentTypeById(Long id)
   {
      return documentTypeRepository.findById(id).get();
   }

   public Map<String, Object> getAllDocumentTypes(String params) throws JsonMappingException, JsonProcessingException
   {
      Page<DocumentType> pageDocumentType = documentTypeRepository.findAll(PaginationUtil.getPageable(params));
      Map<String, Object> documentsWithCount = new HashMap<String, Object>();
      documentsWithCount.put("data", pageDocumentType.toList());
      documentsWithCount.put("count", pageDocumentType.getTotalElements());
      return documentsWithCount;
   }

   public DocumentType updateDocumentType(DocumentType document) throws NotFoundException
   {
      DocumentType originalDocumentType = getDocumentTypeById(document.getId());
      if(originalDocumentType == null)
      {
         throw new NotFoundException();
      }
      originalDocumentType.setDescription(document.getDescription());
      originalDocumentType.setName(document.getName());
      return documentTypeRepository.save(originalDocumentType);
   }

}
