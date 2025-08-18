package com.app.kyc.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.app.kyc.model.Pagination;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaginationUtil
{
   public static Pageable getPageable(String params) throws JsonMappingException, JsonProcessingException
   {
      if(params == null)
      {
         Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
         return pageable;
      }
      else
      {
         ObjectMapper mapper = new ObjectMapper();
         Pagination obj = mapper.readValue(params, Pagination.class);
         if(obj.getSort().getOrder().equals("ASC"))
         {
            Pageable pageable = PageRequest.of(obj.getPagination().getPage(), obj.getPagination().getPerPage(), Sort.by(obj.getSort().getField()).ascending());
            return pageable;
         }
         else
            if(obj.getSort().getOrder().equals("DESC"))
            {
               Pageable pageable = PageRequest.of(obj.getPagination().getPage(), obj.getPagination().getPerPage(), Sort.by(obj.getSort().getField()).descending());
               return pageable;
            }
            else
            {
               Pageable pageable = PageRequest.of(obj.getPagination().getPage(), obj.getPagination().getPerPage(), Sort.by(obj.getSort().getField()));
               return pageable;
            }
      }
   }

   public static  Pagination getFilterObject(String params) throws JsonMappingException, JsonProcessingException{

      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(params, Pagination.class);

   }

}
