package com.app.kyc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.app.kyc.model.DashboardObjectInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;

import com.app.kyc.entity.Service;
import com.app.kyc.entity.ServiceType;
import com.app.kyc.repository.ServiceTypeRepository;
import com.app.kyc.response.ServiceTypesServicesResponseDTO;
import com.app.kyc.service.common.ErrorCode;
import com.app.kyc.service.exception.InvalidDataException;
import com.app.kyc.util.PaginationUtil;
import com.app.kyc.web.security.SecurityHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@org.springframework.stereotype.Service
public class ServiceTypeServiceImpl implements ServiceTypeService
{

   @Autowired
   private ServiceTypeRepository serviceTypeRepository;

   @Autowired
   ServiceService serviceService;

   @Autowired
   SecurityHelper securityHelper;

   @Autowired
   UserService userService;

   @Autowired
   private EntityManager entityManager;

   public ServiceType getServiceTypeById(Long id)
   {
      return serviceTypeRepository.findById(id).get();
   }

   @SuppressWarnings("unchecked")
   public Map<String, Object> getServiceTypeByIdWithAssociatedServices(Long id)
   {
      List<Service> services = serviceService.getAllServicesByServiceTypeId(id);
      ServiceType st = serviceTypeRepository.findById(id).get();
      ServiceTypesServicesResponseDTO response = new ServiceTypesServicesResponseDTO(st, serviceService.getAllServicesByServiceTypeId(id), services.isEmpty() ? false : true,
         userService.getUserById(st.getCreatedBy()).getFirstName() + " " + userService.getUserById(st.getCreatedBy()).getLastName());
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> map = mapper.convertValue(response.getServiceType(), Map.class);
      map.put("hasLinkedServices", response.isHasLinkedServices());
      return map;
   }

   public ServiceType getServiceTypeByName(String name)
   {
      return serviceTypeRepository.findByName(name);
   }

   public List<ServiceType> getAllServiceTypes()
   {
      return serviceTypeRepository.findAll();
   }

   public List<ServiceType> getAllServiceTypesByIndustryId(Long industryId)
   {
      return serviceTypeRepository.findAllByIndustryId(industryId);
   }

   @Override
   public List<DashboardObjectInterface> getAllServiceTypesByIndustryIdForDashboard(Long id) {
      return serviceTypeRepository.findAllByIndustryIdForDashboard(id);
   }

   @Override
   public List<Long> getAllServiceTypesIdByIndustryId(Long industryId) {
      return serviceTypeRepository.findServiceTypesIdByIndustryId(industryId);
   }

   public Map<String, Object> getAllServiceTypesWithAssociatedServices(String params) throws JsonMappingException, JsonProcessingException
   {
      HashMap<Long, ServiceTypesServicesResponseDTO> map = new HashMap<>();
      List<Service> services = serviceService.getAllServices();
      Page<ServiceType> pageServiceTypes = serviceTypeRepository.findAll(PaginationUtil.getPageable(params));
      List<ServiceType> serviceTypes = pageServiceTypes.toList();
      for(ServiceType st : serviceTypes)
      {
         if(!map.containsKey(st.getId()))
         {
            List<Service> addService = new ArrayList<Service>();
            map.put(st.getId(), new ServiceTypesServicesResponseDTO(st, addService, false,
               userService.getUserById(st.getCreatedBy()).getFirstName() + " " + userService.getUserById(st.getCreatedBy()).getLastName()));
         }
      }
      for(Service s : services)
      {
         s.setServiceProvider(null);
         if(map.containsKey(s.getServiceType().getId()))
         {
            map.get(s.getServiceType().getId()).getAssociatedServices().add(s);
            map.get(s.getServiceType().getId()).setHasLinkedServices(true);
         }
      }
      Map<String, Object> serviceTypesWithCount = new HashMap<String, Object>();
      serviceTypesWithCount.put("data", map);
      serviceTypesWithCount.put("count", pageServiceTypes.getTotalElements());
      return serviceTypesWithCount;
   }

   public void addServiceType(ServiceType serviceType, String email) throws InvalidDataException
   {
      if(getServiceTypeByName(serviceType.getName()) != null) throw new InvalidDataException(ErrorCode.ALREADY_EXIST);
      if(email != null)
      {
         serviceType.setCreatedBy(userService.getUserByEmail(email).getId());
         serviceType.setCreatedOn(new Date());
         serviceTypeRepository.save(serviceType);
      }
      else
      {
         throw new InvalidDataException(ErrorCode.EMAIL_NOT_FOUND);
      }
   }

   public ServiceType updateServiceType(ServiceType serviceType) throws NotFoundException, InvalidDataException
   {
      ServiceType originalServiceType = getServiceTypeById(serviceType.getId());
      if(originalServiceType == null)
      {
         throw new NotFoundException();
      }
      List<Service> services = serviceService.getAllServicesByServiceTypeId(serviceType.getId());
      if(!services.isEmpty())
      {
         if(originalServiceType.getIndustry().getId() != serviceType.getIndustry().getId())
         {
            throw new InvalidDataException(ErrorCode.INVALID_CHANGE_REQUEST);
         }
      }
      else
      {
         if(originalServiceType.getIndustry().getId() != serviceType.getIndustry().getId())
         {
            originalServiceType.setIndustry(serviceType.getIndustry());
         }
      }
      originalServiceType.setName(serviceType.getName());
      return serviceTypeRepository.save(originalServiceType);
   }

   public void deleteServiceType(Long id)
   {
      serviceTypeRepository.deleteById(id);
   }

   @Override
   public Map<String, Object> getServiceTypesByIndustryId(Long industryId)
   {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<ServiceType> query = cb.createQuery(ServiceType.class);

      List<Predicate> predicates = new ArrayList<>();

      Root<ServiceType> root = query.from(ServiceType.class);

      Predicate consumerIdPredicate = cb.equal(root.get("industry"), industryId);
      predicates.add(consumerIdPredicate);
      query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
      TypedQuery<ServiceType> typedQuery = entityManager.createQuery(query);

      List<ServiceType> response = typedQuery.getResultList();
      entityManager.close();
      Map<String, Object> serviceTypesWithCount = new HashMap<String, Object>();
      serviceTypesWithCount.put("data", response);
      serviceTypesWithCount.put("count", response.size());
      return serviceTypesWithCount;
   }

   @Override
   public int countServiceTypes()
   {
      return (int) serviceTypeRepository.count();
   }

}
