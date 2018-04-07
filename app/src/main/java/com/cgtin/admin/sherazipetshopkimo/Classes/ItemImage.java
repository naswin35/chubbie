package com.cgtin.admin.sherazipetshopkimo.Classes;

import java.io.Serializable;

/**
 * Created by Admin on 30-01-2017.
 */
public class ItemImage implements Serializable {

    String id;
    String image;

    public ItemImage(){

    }

    public ItemImage(String ids, String image) {
        this.image = image;
        this.id=ids;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
