package com.app.kyc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.app.kyc.model.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User
{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String email;
   private String password;
   private String firstName;
   private String lastName;
   private String phone;
   private Long code;
   private Date codeExpiry;
   private boolean deleted;
   private UserStatus status;
   @JsonIgnore
   private String governmentId;
   private Date lastLogin;
   private Date createdOn;
   private Long createdBy;
   private String department;
   @ManyToOne
   private Role role;
   @ManyToOne
   private Industry industry;
   @ManyToOne
   private ServiceProvider serviceProvider;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String password)
   {
      this.password = password;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }

   public String getPhone()
   {
      return phone;
   }

   public void setPhone(String phone)
   {
      this.phone = phone;
   }

   public Long getCode()
   {
      return code;
   }

   public void setCode(Long code)
   {
      this.code = code;
   }

   public Date getCodeExpiry()
   {
      return codeExpiry;
   }

   public void setCodeExpiry(Date codeExpiry)
   {
      this.codeExpiry = codeExpiry;
   }

   public boolean isDeleted()
   {
      return deleted;
   }

   public void setDeleted(boolean deleted)
   {
      this.deleted = deleted;
   }

   public UserStatus getStatus()
   {
      return status;
   }

   public void setStatus(UserStatus status)
   {
      this.status = status;
   }

   public String getGovernmentId()
   {
      return governmentId;
   }

   public void setGovernmentId(String governmentId)
   {
      this.governmentId = governmentId;
   }

   public Date getLastLogin()
   {
      return lastLogin;
   }

   public void setLastLogin(Date lastLogin)
   {
      this.lastLogin = lastLogin;
   }

   public Date getCreatedOn()
   {
      return createdOn;
   }

   public void setCreatedOn(Date createdOn)
   {
      this.createdOn = createdOn;
   }

   public Role getRole()
   {
      return role;
   }

   public void setRole(Role role)
   {
      this.role = role;
   }

   public String getDepartment()
   {
      return department;
   }

   public void setDepartment(String department)
   {
      this.department = department;
   }

   public Industry getIndustry()
   {
      return industry;
   }

   public void setIndustry(Industry industry)
   {
      this.industry = industry;
   }

   public Long getCreatedBy()
   {
      return createdBy;
   }

   public void setCreatedBy(Long createdBy)
   {
      this.createdBy = createdBy;
   }

   public ServiceProvider getServiceProvider()
   {
      return serviceProvider;
   }

   public void setServiceProvider(ServiceProvider serviceProvider)
   {
      this.serviceProvider = serviceProvider;
   }

   @Override
   public String toString()
   {
      return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }

}
