package com.naksh.renth.Models;

public class NotificationModel {
    String notificationMessage,id,userId,propertyId,ownerId;

    public NotificationModel(String notificationMessage,String id) {
        this.notificationMessage = notificationMessage;
        this.id=id;
    }

    public NotificationModel(String notificationMessage, String id, String userId, String propertyId,String ownerId) {
        this.notificationMessage = notificationMessage;
        this.id = id;
        this.userId = userId;
        this.propertyId = propertyId;
        this.ownerId=ownerId;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
