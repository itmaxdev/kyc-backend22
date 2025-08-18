package com.app.kyc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public interface DashboardObjectInterface
{
   @JsonInclude(JsonInclude.Include.NON_NULL)
   String getName();

   @JsonInclude(JsonInclude.Include.NON_NULL)
   Integer getValue();

   @JsonInclude(JsonInclude.Include.NON_NULL)
   Long getServiceproviderid();

   @JsonInclude(JsonInclude.Include.NON_NULL)
   List<DashboardObject> getValues();
}
