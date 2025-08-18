package com.app.kyc.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.app.kyc.model.DashboardObjectInterface;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.app.kyc.entity.Industry;
import com.app.kyc.service.exception.InvalidDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IndustryService
{

   public Industry getIndustryById(Long id);

   public Industry getIndustryByName(String name);

   public Map<String, Object> getAllIndustries(String params) throws JsonMappingException, JsonProcessingException;

   public List<DashboardObjectInterface> getAllIndustries();

   public void addIndustry(Industry industry) throws SQLException, InvalidDataException;

   public Industry updateIndustry(Industry industry) throws SQLException, NotFoundException;

   public void deleteIndustry(Long id) throws SQLException;

}
