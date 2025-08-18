package com.app.kyc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;

import com.app.kyc.entity.Service;
import com.app.kyc.model.ServiceStatus;
import com.app.kyc.repository.ServiceRepository;
import com.app.kyc.request.ApproveServiceRequest;
import com.app.kyc.response.ServiceByIdResponseDTO;
import com.app.kyc.response.ServiceUserResponseDTO;
import com.app.kyc.service.common.ErrorCode;
import com.app.kyc.service.exception.InvalidDataException;
import com.app.kyc.util.EmailUtil;
import com.app.kyc.util.PaginationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService
{

   @Autowired
   private ServiceRepository serviceRepository;

   @Autowired
   private UserService userService;

   @Autowired
   private ConsumerServiceService consumerServiceService;

   @Override
   public ServiceByIdResponseDTO getServiceByIdResponse(Long id)
   {
      Service service = serviceRepository.findById(id).get();
      if(service != null)
      {
         ServiceByIdResponseDTO serviceResponse =
            new ServiceByIdResponseDTO(service, userService.getUserById(service.getApprovedBy()), userService.getUserById(service.getCreatedBy()),
               userService.getUserById(service.getServiceProvider().getCreatedBy()), userService.getUserById(service.getServiceType().getCreatedBy()));
         return serviceResponse;
      }
      return null;
   }

   @Override
   public Service getServiceById(Long id)
   {
      return serviceRepository.findById(id).get();
   }

   @Override
   public Service getServiceByName(String name)
   {
      return serviceRepository.findByName(name);
   }

   @Override
   public List<Service> getAllServices()
   {
      return serviceRepository.findAll();
   }

   @Override
   public Map<String, Object> getAllServicesWithUserInfo(String params) throws JsonMappingException, JsonProcessingException
   {
      Page<Service> pageServices = serviceRepository.findAll(PaginationUtil.getPageable(params));
      List<Service> services = pageServices.toList();
      int consumersByService = 0;
      List<ServiceUserResponseDTO> response = new ArrayList<ServiceUserResponseDTO>();
      for(Service s : services)
      {
         consumersByService = consumerServiceService.countConsumersByServiceId(s.getId());
         ServiceUserResponseDTO obj = new ServiceUserResponseDTO(s.getId(), s.getName(), s.getServiceProvider().getName(), s.getCreatedOn(), s.getServiceType(),
            (s.getCreatedBy() == null ? null : userService.getUserById(s.getCreatedBy())), (s.getApprovedBy() == null ? null : userService.getUserById(s.getApprovedBy())),
            consumersByService > 0 ? true : false);
         response.add(obj);
      }
      Map<String, Object> servicesWithCount = new HashMap<String, Object>();
      servicesWithCount.put("data", response);
      servicesWithCount.put("count", pageServices.getTotalElements());
      return servicesWithCount;
   }

   @Override
   public void addService(Service service)
   {
      serviceRepository.save(service);
   }

   @Override
   public Service updateService(Service service) throws NotFoundException
   {
      Service originalService = getServiceById(service.getId());
      if(originalService == null) throw new NotFoundException();
      originalService.setName(service.getName());
      originalService.setServiceProvider(service.getServiceProvider());
      originalService.setServiceType(service.getServiceType());
      return serviceRepository.save(originalService);
   }

   @Override
   public List<Service> getAllServicesByServiceTypeId(Long id)
   {
      return serviceRepository.findByServiceType(id);
   }

   @Override
   public Map<String, Object> getAllServicesMapByServiceTypeId(Long id)
   {
      Map<String, Object> servicesWithCount = new HashMap<String, Object>();
      List<Service> services = serviceRepository.findByServiceType(id);
      servicesWithCount.put("data", serviceRepository.findByServiceType(id));
      servicesWithCount.put("count", services.size());
      return servicesWithCount;
   }

   @Override
   public Map<String, Object> getAllServicesByServiceProviderId(Long id)
   {
      List<Service> services = serviceRepository.findByServiceProvider(id);
      int consumersByService = 0;
      List<ServiceUserResponseDTO> response = new ArrayList<ServiceUserResponseDTO>();
      for(Service s : services)
      {
         consumersByService = consumerServiceService.countConsumersByServiceId(s.getId());
         ServiceUserResponseDTO obj = new ServiceUserResponseDTO(s.getId(), s.getName(), s.getServiceProvider().getName(), s.getCreatedOn(), s.getServiceType(),
            (s.getCreatedBy() == null ? null : userService.getUserById(s.getCreatedBy())), (s.getApprovedBy() == null ? null : userService.getUserById(s.getApprovedBy())),
            consumersByService > 0 ? true : false);
         response.add(obj);
      }
      Map<String, Object> servicesWithCount = new HashMap<String, Object>();
      servicesWithCount.put("data", response);
      servicesWithCount.put("count", response.size());
      return servicesWithCount;
   }

   @Override
   public Service deactivateService(Long id)
   {
      Service service = getServiceById(id);
      service.setStatus(ServiceStatus.Inactive);
      //      service.setApprovedBy(null);
      return serviceRepository.save(service);
   }

   @Override
   public Service activateService(Long id, Long approvedBy)
   {
      Service service = getServiceById(id);
      service.setStatus(ServiceStatus.Active);
      //      service.setApprovedBy(approvedBy);
      return serviceRepository.save(service);
   }

   @Override
   public void approveService(ApproveServiceRequest approveServiceRequest) throws InvalidDataException
   {
      Service service = getServiceById(approveServiceRequest.getServiceId());
      if(service.getApprovedBy() == null)
      {
         service.setStatus(approveServiceRequest.getStatus());
         if(approveServiceRequest.getStatus() == ServiceStatus.Inactive && approveServiceRequest.getRejectionReason() != null)
         {
            service.setRejectionReason(approveServiceRequest.getRejectionReason());
            //send email to SP admin who created the service
            String message = "Hello, \r\nPlease note that your request to create " + service.getName() + " was rejected for the following reason: " + approveServiceRequest
               .getRejectionReason() + "\r\n\r\nBest,\r\n" + "National KYC";
            EmailUtil.sendEmail(userService.getUserById(service.getCreatedBy()).getEmail(), "Service Creation Denied - National KYC Platform", message);
         }
         else
            if(approveServiceRequest
               .getStatus() == ServiceStatus.Active && (approveServiceRequest.getRejectionReason() == null || approveServiceRequest.getRejectionReason().equals("")))
            {
               service.setApprovedBy(approveServiceRequest.getUserId());
            }
            else
            {
               if(approveServiceRequest.getStatus() == ServiceStatus.Inactive && approveServiceRequest.getRejectionReason() == null)
               {
                  throw new InvalidDataException(ErrorCode.REASON_IS_REQUIRED);
               }
            }
         serviceRepository.save(service);
      }
      else
      {
         throw new InvalidDataException(ErrorCode.SERVICE_ALREADY_PROCESSED);
      }
   }

   @Override
   public List<Service> getListServicesByServiceProviderId(Long id)
   {
      List<Service> services = serviceRepository.findByServiceProvider(id);
      List<ServiceUserResponseDTO> response = new ArrayList<ServiceUserResponseDTO>();
      int consumersByService = 0;
      for(Service s : services)
      {
         consumersByService = consumerServiceService.countConsumersByServiceId(s.getId());
         ServiceUserResponseDTO obj = new ServiceUserResponseDTO(s.getId(), s.getName(), s.getServiceProvider().getName(), s.getCreatedOn(), s.getServiceType(),
            (s.getCreatedBy() == null ? null : userService.getUserById(s.getCreatedBy())), (s.getApprovedBy() == null ? null : userService.getUserById(s.getApprovedBy())),
            consumersByService > 0 ? true : false);
         response.add(obj);
      }
      return services;
   }

}
