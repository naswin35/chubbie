package com.cgtin.admin.sherazipetshopkimo.Classes;

/**
 * Created by Admin on 08-12-2017.
 */

public class ProductDetailClass {


    String name;

    public String getArabic_products_detail_name() {
        return arabic_products_detail_name;
    }

    public void setArabic_products_detail_name(String arabic_products_detail_name) {
        this.arabic_products_detail_name = arabic_products_detail_name;
    }

    String arabic_products_detail_name;
    String price;

    String product_id;
    int quantity;
    String image;

    String AddressID;
    double TotalPrice;

    String vat_amount;
    String vat_percentage;
    String stock_size;
    String nutrition;
    String single_product_price;


    public String getVat_amount() {
        return vat_amount;
    }

    public void setVat_amount(String vat_amount) {
        this.vat_amount = vat_amount;
    }

    public String getVat_percentage() {
        return vat_percentage;
    }

    public void setVat_percentage(String vat_percentage) {
        this.vat_percentage = vat_percentage;
    }

    public String getShipping_amount() {
        return shipping_amount;
    }

    public void setShipping_amount(String shipping_amount) {
        this.shipping_amount = shipping_amount;
    }

    String shipping_amount;

    public String getAddressID() {
        return AddressID;
    }

    public void setAddressID(String addressID) {
        AddressID = addressID;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        GrandTotal = grandTotal;
    }

    String GrandTotal;



    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    String size;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }


    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getStock_size() {
        return stock_size;
    }

    public void setStock_size(String stock_size) {
        this.stock_size = stock_size;
    }


    public ProductDetailClass() {



    }

    public String getSingle_product_price() {
        return single_product_price;
    }

    public void setSingle_product_price(String single_product_price) {
        this.single_product_price = single_product_price;
    }
}
