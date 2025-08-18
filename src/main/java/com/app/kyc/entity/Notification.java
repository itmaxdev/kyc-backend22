package com.app.kyc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.app.kyc.model.NotificationType;

@Entity
@Table(name = "notifications")
public class Notification
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String message;

   private Long userId;

   private Long clickableId;

   private boolean markRead;

   private NotificationType notificationType;

   private Date createdOn;

   public Notification() {}

   public Notification(String message, Long userId, Long clickableId, boolean markRead, NotificationType notificationType, Date createdOn)
   {
      this.message = message;
      this.userId = userId;
      this.clickableId = clickableId;
      this.markRead = markRead;
      this.notificationType = notificationType;
      this.createdOn = createdOn;
   }

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getMessage()
   {
      return message;
   }

   public void setMessage(String message)
   {
      this.message = message;
   }

   public Long getUserId()
   {
      return userId;
   }

   public void setUserId(Long userId)
   {
      this.userId = userId;
   }

   public boolean isMarkRead()
   {
      return markRead;
   }

   public void setMarkRead(boolean markRead)
   {
      this.markRead = markRead;
   }

   public NotificationType getNotificationType()
   {
      return notificationType;
   }

   public void setNotificationType(NotificationType notificationType)
   {
      this.notificationType = notificationType;
   }

   public Long getClickableId()
   {
      return clickableId;
   }

   public void setClickableId(Long clickableId)
   {
      this.clickableId = clickableId;
   }

   public Date getCreatedOn()
   {
      return createdOn;
   }

   public void setCreatedOn(Date createdOn)
   {
      this.createdOn = createdOn;
   }

}
