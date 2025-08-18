package com.app.kyc.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.kyc.model.DashboardObjectInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.app.kyc.entity.Industry;
import com.app.kyc.repository.IndustryRepository;
import com.app.kyc.service.common.ErrorCode;
import com.app.kyc.service.exception.InvalidDataException;
import com.app.kyc.util.PaginationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class IndustryServiceImpl implements IndustryService
{

   @Autowired
   private IndustryRepository industryRepository;

   public Industry getIndustryById(Long id)
   {
      return industryRepository.findById(id).get();
   }

   public Industry getIndustryByName(String name)
   {
      return industryRepository.findByName(name);
   }

   public Map<String, Object> getAllIndustries(String params) throws JsonMappingException, JsonProcessingException
   {
      Page<Industry> pageIndustry = industryRepository.findAll(PaginationUtil.getPageable(params));
      Map<String, Object> industriesWithCount = new HashMap<String, Object>();
      industriesWithCount.put("data", pageIndustry.toList());
      industriesWithCount.put("count", pageIndustry.getTotalElements());
      return industriesWithCount;
   }

   @Override
   public List<DashboardObjectInterface> getAllIndustries()
   {
      return industryRepository.findAllForDashboard();
   }

   public void addIndustry(Industry industry) throws SQLException, InvalidDataException
   {
      if(getIndustryByName(industry.getName()) != null) throw new InvalidDataException(ErrorCode.ALREADY_EXIST);
      industry.setCreatedOn(new Date());
      industryRepository.save(industry);
   }

   public Industry updateIndustry(Industry industry) throws SQLException, NotFoundException
   {
      Industry originalIndustry = getIndustryById(industry.getId());
      if(originalIndustry == null) throw new NotFoundException();
      originalIndustry.setName(industry.getName());
      return industryRepository.save(originalIndustry);
   }

   public void deleteIndustry(Long id) throws SQLException
   {
      industryRepository.deleteById(id);
   }

}
