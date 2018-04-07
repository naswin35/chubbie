package com.cgtin.admin.sherazipetshopkimo.Classes;

/**
 * Created by Admin on 22-01-2018.
 */

public class OrderListModel {



    private String customer_order_id;
    private String customer_order_total_price;
    private String customer_order_shipping_amount;
    private String customer_order_created_at;
    private String productImage;
    private String payment_mode_name;

    public OrderListModel() {
    }


    public String getCustomer_order_id() {
        return customer_order_id;
    }

    public void setCustomer_order_id(String customer_order_id) {
        this.customer_order_id = customer_order_id;
    }

    public String getCustomer_order_total_price() {
        return customer_order_total_price;
    }

    public void setCustomer_order_total_price(String customer_order_total_price) {
        this.customer_order_total_price = customer_order_total_price;
    }

    public String getCustomer_order_shipping_amount() {
        return customer_order_shipping_amount;
    }

    public void setCustomer_order_shipping_amount(String customer_order_shipping_amount) {
        this.customer_order_shipping_amount = customer_order_shipping_amount;
    }

    public String getCustomer_order_created_at() {
        return customer_order_created_at;
    }

    public void setCustomer_order_created_at(String customer_order_created_at) {
        this.customer_order_created_at = customer_order_created_at;
    }

    public String getPayment_mode_name() {
        return payment_mode_name;
    }

    public void setPayment_mode_name(String payment_mode_name) {
        this.payment_mode_name = payment_mode_name;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}