package com.example.workmanagerandfirebasetesting.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestNotificaton {

    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("notification")
    @Expose
    private SendNotificationModel notification;

    public RequestNotificaton(String to, SendNotificationModel notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public SendNotificationModel getNotification() {
        return notification;
    }

    public void setNotification(SendNotificationModel notification) {
        this.notification = notification;
    }
}