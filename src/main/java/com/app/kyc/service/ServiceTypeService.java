package com.app.kyc.service;

import java.util.List;
import java.util.Map;

import com.app.kyc.model.DashboardObjectInterface;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.app.kyc.entity.ServiceType;
import com.app.kyc.service.exception.InvalidDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@org.springframework.stereotype.Service
public interface ServiceTypeService
{

   public ServiceType getServiceTypeById(Long id);

   public Map<String, Object> getServiceTypeByIdWithAssociatedServices(Long id);

   public ServiceType getServiceTypeByName(String name);

   public Map<String, Object> getServiceTypesByIndustryId(Long industryId);

   public List<ServiceType> getAllServiceTypes();

   public Map<String, Object> getAllServiceTypesWithAssociatedServices(String params) throws JsonMappingException, JsonProcessingException;

   public void addServiceType(ServiceType serviceType, String email) throws InvalidDataException;

   public ServiceType updateServiceType(ServiceType serviceType) throws NotFoundException, InvalidDataException;

   public void deleteServiceType(Long id);

   int countServiceTypes();

   public List<ServiceType> getAllServiceTypesByIndustryId(Long id);


   List<DashboardObjectInterface> getAllServiceTypesByIndustryIdForDashboard(Long id);

   List<Long> getAllServiceTypesIdByIndustryId(Long industryId);

}
