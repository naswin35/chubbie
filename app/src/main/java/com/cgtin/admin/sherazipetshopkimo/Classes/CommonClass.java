package com.cgtin.admin.sherazipetshopkimo.Classes;

import com.cgtin.admin.sherazipetshopkimo.CommonClasses.AddressClass;

import java.util.ArrayList;

/**
 * Created by Admin on 12-12-2017.
 */

public class CommonClass {

    public static boolean close=false;

    public static int  per_page=5;
    public static int  last_page_food;

    public static int  froms=1;
    public static ArrayList<AddressClass> AddressListArray=new ArrayList<>();
    public static String fromActivity;

    public static String DogID;
    public static String CatID;
    public static String CatTitle;
    public static String DogTitle;

    public static String Address_back_for_add="";

    public static String Address_Id_Show;

    public static String DeliveryCharge="";

    public static String VATpercentage="";

    public static boolean Address_Edit=false;
    public static boolean Address_edit_header=false;

    public static String sort="";

    public static String smsid="";

    public static boolean OutFound=false;




}
