package com.cgtin.admin.sherazipetshopkimo.Classes;

/**
 * Created by Admin on 06-12-2017.
 */

public class ProductsClass {
    public String id;
    boolean click;




    private String product_id;
    private String product_product_category_id;
    private String product_brand_id;
    private String product_name;

    public String getArabic_products_detail_description() {
        return arabic_products_detail_description;
    }

    public void setArabic_products_detail_description(String arabic_products_detail_description) {
        this.arabic_products_detail_description = arabic_products_detail_description;
    }

    private String arabic_products_detail_description;


    public String getArabic_products_detail_name() {
        return arabic_products_detail_name;
    }

    public void setArabic_products_detail_name(String arabic_products_detail_name) {
        this.arabic_products_detail_name = arabic_products_detail_name;
    }

    private String arabic_products_detail_name;
    private String product_description;
    private String product_price;
    private String product_size;
    private String product_image;
    private String product_tax;
    private String product_stock;
    private String product_status;
    private String product_created_at;
    private String product_updated_at;
    private String brand_id;
    private String brand_name;
    private String brand_logo;
    private String brand_status;
    private String brand_created_at;
    private String brand_updated_at;
    private String product_category_id;
    private String product_category_name;
    private String product_category_image;
    private String product_category_status;
    private String product_category_created_at;
    private String product_category_updated_at;

    public String getVatPercentage() {
        return VatPercentage;
    }

    public void setVatPercentage(String vatPercentage) {
        VatPercentage = vatPercentage;
    }

    private String VatPercentage;

    public String getProduct_nutrition_contents() {
        return product_nutrition_contents;
    }

    public void setProduct_nutrition_contents(String product_nutrition_contents) {
        this.product_nutrition_contents = product_nutrition_contents;
    }

    private String product_nutrition_contents;

    public String getArabic_products_detail_nutrition_contents() {
        return arabic_products_detail_nutrition_contents;
    }

    public void setArabic_products_detail_nutrition_contents(String arabic_products_detail_nutrition_contents) {
        this.arabic_products_detail_nutrition_contents = arabic_products_detail_nutrition_contents;
    }

    private String arabic_products_detail_nutrition_contents;



    int count=0;
    public ProductsClass() {
    }





    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_product_category_id() {
        return product_product_category_id;
    }

    public void setProduct_product_category_id(String product_product_category_id) {
        this.product_product_category_id = product_product_category_id;
    }

    public String getProduct_brand_id() {
        return product_brand_id;
    }

    public void setProduct_brand_id(String product_brand_id) {
        this.product_brand_id = product_brand_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_size() {
        return product_size;
    }

    public void setProduct_size(String product_size) {
        this.product_size = product_size;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_tax() {
        return product_tax;
    }

    public void setProduct_tax(String product_tax) {
        this.product_tax = product_tax;
    }

    public String getProduct_stock() {
        return product_stock;
    }

    public void setProduct_stock(String product_stock) {
        this.product_stock = product_stock;
    }

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }

    public String getProduct_created_at() {
        return product_created_at;
    }

    public void setProduct_created_at(String product_created_at) {
        this.product_created_at = product_created_at;
    }

    public String getProduct_updated_at() {
        return product_updated_at;
    }

    public void setProduct_updated_at(String product_updated_at) {
        this.product_updated_at = product_updated_at;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public String getBrand_status() {
        return brand_status;
    }

    public void setBrand_status(String brand_status) {
        this.brand_status = brand_status;
    }

    public String getBrand_created_at() {
        return brand_created_at;
    }

    public void setBrand_created_at(String brand_created_at) {
        this.brand_created_at = brand_created_at;
    }

    public String getBrand_updated_at() {
        return brand_updated_at;
    }

    public void setBrand_updated_at(String brand_updated_at) {
        this.brand_updated_at = brand_updated_at;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
