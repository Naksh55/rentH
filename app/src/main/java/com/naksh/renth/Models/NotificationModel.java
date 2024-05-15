package com.naksh.renth.Models;

public class NotificationModel {
    String notificationId,notificationMessage,id,userId,propertyId,ownerId;
    int totalprice;

    public NotificationModel(String notificationId,String notificationMessage,String id) {
        this.notificationMessage = notificationMessage;
        this.id=id;
        this.notificationId=notificationId;
    }

    public NotificationModel(String notificationMessage, String id, String userId, String propertyId,String ownerId) {
        this.notificationMessage = notificationMessage;
        this.id = id;
        this.userId = userId;
        this.propertyId = propertyId;
        this.ownerId=ownerId;
    }

    public NotificationModel(String notificationId, String notificationMessage, String id, String userId, String propertyId, String ownerId, int totalprice) {
        this.notificationId = notificationId;
        this.notificationMessage = notificationMessage;
        this.id = id;
        this.userId = userId;
        this.propertyId = propertyId;
        this.ownerId = ownerId;
        this.totalprice = totalprice;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
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

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }
}
