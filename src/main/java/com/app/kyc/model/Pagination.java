package com.app.kyc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pagination
{
   Page pagination;
   PaginationSort sort;
   Filter filter;

   public PaginationSort getSort()
   {
      return sort;
   }

   public Page getPagination()
   {
      return pagination;
   }

   public void setPagination(Page pagination)
   {
      this.pagination = pagination;
   }

   public void setSort(PaginationSort sort)
   {
      this.sort = sort;
   }

   public Filter getFilter() {
      return filter;
   }

   public void setFilter(Filter filter) {
      this.filter = filter;
   }
}
