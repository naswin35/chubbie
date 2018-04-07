package com.cgtin.admin.sherazipetshopkimo.Classes;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Admin on 14-03-2018.
 */

public class Cart_show {

    ArrayList<ProductDetailClass> CartArray;
    String CartString;
    SessionManager session;

    public Cart_show(ArrayList<ProductDetailClass> cartArray, Context context) {
        CartArray = cartArray;

        Gson gson = new Gson();
        CartString = gson.toJson(CartArray);

        session=new SessionManager(context);
        session.CartSession(CartString);
    }




}
