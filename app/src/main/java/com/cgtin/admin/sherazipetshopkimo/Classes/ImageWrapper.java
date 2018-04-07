package com.cgtin.admin.sherazipetshopkimo.Classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Admin on 15-02-2017.
 */


public class ImageWrapper implements Serializable {

    private ArrayList<ItemImage> parliaments;

    public ImageWrapper(ArrayList<ItemImage> data) {
        this.parliaments = data;
    }

    public ArrayList<ItemImage> getImageWrapper() {
        return this.parliaments;
    }

}
