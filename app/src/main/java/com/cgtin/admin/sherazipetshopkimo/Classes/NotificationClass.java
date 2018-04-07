package com.cgtin.admin.sherazipetshopkimo.Classes;

/**
 * Created by Admin on 10-02-2018.
 */

public class NotificationClass {




    private String notification_id;
    private String notification_status;
    private String notification_created_at;
    private String notifications_detail_title;
    private String notifications_detail_description;
    private String notification_type;
    private String customer_order_id;

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getNotification_status() {
        return notification_status;
    }

    public void setNotification_status(String notification_status) {
        this.notification_status = notification_status;
    }

    public String getNotification_created_at() {
        return notification_created_at;
    }

    public void setNotification_created_at(String notification_created_at) {
        this.notification_created_at = notification_created_at;
    }

    public String getNotifications_detail_title() {
        return notifications_detail_title;
    }

    public void setNotifications_detail_title(String notifications_detail_title) {
        this.notifications_detail_title = notifications_detail_title;
    }

    public String getNotifications_detail_description() {
        return notifications_detail_description;
    }

    public void setNotifications_detail_description(String notifications_detail_description) {
        this.notifications_detail_description = notifications_detail_description;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public String getCustomer_order_id() {
        return customer_order_id;
    }

    public void setCustomer_order_id(String customer_order_id) {
        this.customer_order_id = customer_order_id;
    }
}
