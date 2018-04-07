package com.cgtin.admin.sherazipetshopkimo.Classes;

/**
 * Created by Admin on 16-01-2018.
 */

public class HomeClass {



    private String product_category_id;
    private String product_category_name;
    private String product_category_image;
    private String product_category_status;
    private String product_category_created_at;
    private String product_category_updated_at;
    private String product_sub_category_id;
    private String product_sub_category_product_category_id;
    private String product_sub_category_product_category_parent_id;
    private Object product_sub_category_created_at;
    private Object product_sub_category_updated_at;

    public HomeClass() {
    }

    public String getProduct_category_id() {
        return product_category_id;
    }

    public void setProduct_category_id(String product_category_id) {
        this.product_category_id = product_category_id;
    }

    public String getProduct_category_name() {
        return product_category_name;
    }

    public void setProduct_category_name(String product_category_name) {
        this.product_category_name = product_category_name;
    }

    public String getProduct_category_image() {
        return product_category_image;
    }

    public void setProduct_category_image(String product_category_image) {
        this.product_category_image = product_category_image;
    }

    public String getProduct_category_status() {
        return product_category_status;
    }

    public void setProduct_category_status(String product_category_status) {
        this.product_category_status = product_category_status;
    }

    public String getProduct_category_created_at() {
        return product_category_created_at;
    }

    public void setProduct_category_created_at(String product_category_created_at) {
        this.product_category_created_at = product_category_created_at;
    }

    public String getProduct_category_updated_at() {
        return product_category_updated_at;
    }

    public void setProduct_category_updated_at(String product_category_updated_at) {
        this.product_category_updated_at = product_category_updated_at;
    }

    public String getProduct_sub_category_id() {
        return product_sub_category_id;
    }

    public void setProduct_sub_category_id(String product_sub_category_id) {
        this.product_sub_category_id = product_sub_category_id;
    }

    public String getProduct_sub_category_product_category_id() {
        return product_sub_category_product_category_id;
    }

    public void setProduct_sub_category_product_category_id(String product_sub_category_product_category_id) {
        this.product_sub_category_product_category_id = product_sub_category_product_category_id;
    }

    public String getProduct_sub_category_product_category_parent_id(String product_sub_category_product_category_parent_id) {
        return this.product_sub_category_product_category_parent_id;
    }

    public void setProduct_sub_category_product_category_parent_id(String product_sub_category_product_category_parent_id) {
        this.product_sub_category_product_category_parent_id = product_sub_category_product_category_parent_id;
    }

    public Object getProduct_sub_category_created_at() {
        return product_sub_category_created_at;
    }

    public void setProduct_sub_category_created_at(Object product_sub_category_created_at) {
        this.product_sub_category_created_at = product_sub_category_created_at;
    }

    public Object getProduct_sub_category_updated_at() {
        return product_sub_category_updated_at;
    }

    public void setProduct_sub_category_updated_at(Object product_sub_category_updated_at) {
        this.product_sub_category_updated_at = product_sub_category_updated_at;
    }
}
