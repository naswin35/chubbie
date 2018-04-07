package com.cgtin.admin.sherazipetshopkimo.CommonClasses;

import android.content.Context;

/**
 * Created by Admin on 14-10-2017.
 */

public class ApiLinks {


    public static Context AddressFrom;


    public static String CommonPath="http://cgtindia.in/sherazi-api/public/api/v1/";

    public static String CommonImagePath_products_category="http://cgtindia.in/sherazi-api/public/images/product-category/";

    public static String Product_image="http://cgtindia.in/sherazi-api/public/images/products/";

    public static String Brand_Image="http://cgtindia.in/sherazi-api/public/images/brands/";

    public static String Login=CommonPath+"login";

    public static String SignUp=CommonPath+"register";

    public static String SignUp_otp=CommonPath+"generateOtpNewRegister";

    public static String Home=CommonPath+"productSubCategoriesWithLanguageID/0";

    public static String SubCategory=CommonPath+"productSubCategoriesWithLanguageID/";

    public static String products=CommonPath+"fetchProductsWithBrandAndCategory";

    public static String store=CommonPath+"orderStoreAndUpdate/0";

    public static String payment_mode=CommonPath+"paymentModeSelectListWithLanguageID/";

    public static String address_get=CommonPath+"customerAddressShow/";

    public static String customer_address_add=CommonPath+"customerAddressStoreAndUpdate/";

    public static String address_delete=CommonPath+"customerAddressDestroy/";

    public static String changePassword=CommonPath+"changePassword";

    public static String updateEmail=CommonPath+"updateEmail";

    public static String otpGenerateForNewMobileNo=CommonPath+"otpGenerateForNewMobileNo";

    public static String updateMobile=CommonPath+"updateMobile";

    public static String specificCustomerOrderItemList=CommonPath+"specificCustomerOrderItemList/";

    public static String specificCustomerOrderItemView=CommonPath+"specificCustomerOrderItemView/";

    public static String updatePassword=CommonPath+"updatePassword";

    public static String forgotPassword=CommonPath+"forgotPassword";

    public static String productShow=CommonPath+"productShow/";

    public static String productImages=CommonPath+"productImages/";

    public static String orderSettings=CommonPath+"orderSettings";

    public static String updateCustomerProfile=CommonPath+"updateCustomerProfile/";

    public static String NotificationApi=CommonPath+"notificationList/";

    public static String notificationUpdate=CommonPath+"notificationUpdate/";

    public static String setAddressToDefault=CommonPath+"setAddressToDefault";
}
