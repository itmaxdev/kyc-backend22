package com.app.kyc.response;

import java.util.List;

import com.app.kyc.entity.Industry;
import com.app.kyc.model.DashboardObject;

public class DahsboardHeaderListsResponse {
    List<Industry> listIndustry;
    List<DashboardObject> listServiceProvider;
    List<DashboardObject> listServiceType;

    public List<Industry> getListIndustry()
    {
       return listIndustry;
    }
 
    public void setListIndustry(List<Industry> industries)
    {
       this.listIndustry = industries;
    }
 
    public List<DashboardObject> getListServiceProvider()
    {
       return listServiceProvider;
    }
 
    public void setListServiceProvider(List<DashboardObject> listServiceProvider)
    {
       this.listServiceProvider = listServiceProvider;
    }
 
    public List<DashboardObject> getListServiceType()
    {
       return listServiceType;
    }
 
    public void setListServiceType(List<DashboardObject> listServiceType)
    {
       this.listServiceType = listServiceType;
    }
}
