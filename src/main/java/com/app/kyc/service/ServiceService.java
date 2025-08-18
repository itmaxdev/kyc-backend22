package com.app.kyc.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.app.kyc.entity.Service;
import com.app.kyc.request.ApproveServiceRequest;
import com.app.kyc.response.ServiceByIdResponseDTO;
import com.app.kyc.service.exception.InvalidDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ServiceService
{

   public ServiceByIdResponseDTO getServiceByIdResponse(Long id);

   public Service getServiceById(Long id);

   public Service getServiceByName(String name);

   public List<Service> getAllServices();

   public Map<String, Object> getAllServicesWithUserInfo(String params) throws JsonMappingException, JsonProcessingException;

   public void addService(Service service);

   public Service updateService(Service service) throws NotFoundException;

   Service deactivateService(Long id);

   Service activateService(Long id, Long approvedBy);

   void approveService(ApproveServiceRequest approveServiceRequest) throws InvalidDataException;

   List<Service> getAllServicesByServiceTypeId(Long id);

   Map<String, Object> getAllServicesByServiceProviderId(Long id);

   List<Service> getListServicesByServiceProviderId(Long id);

   Map<String, Object> getAllServicesMapByServiceTypeId(Long id);

}
