package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Adapters.ProductsAdapter;
import com.cgtin.admin.sherazipetshopkimo.BaseActivity;
import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.ProductsClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.Classes.buildCounterDrawable;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.cgtin.admin.sherazipetshopkimo.StartActivities.LoginActivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.cgtin.admin.sherazipetshopkimo.Classes.Arrays.PetFoodArray;

public class ProductsList extends BaseActivity {
    RecyclerView PetFoodReqRecycler;
    ProductsAdapter postedReq_adapter;
    DrawerLayout drawer;
    String brand_id;
    String VatPercentage;
    SessionManager session;
    HashMap<String,String> SortData;
    HashMap<String,String> user;
    String sortdta;
    Menu mMenu;
    public static ProductsList Plist;
    String nextpathurl;
    LinearLayout ViewmoreAction;

    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;
    LinearLayout No_Record;
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_products_list);
        getLayoutInflater().inflate(R.layout.activity_products_list,frameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        mTitle.setText(R.string.products);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        No_Record=findViewById(R.id.no_record);
        session=new SessionManager(getApplicationContext());
        SortData=session.getPopupDetails();
        user=session.getUserDetails();
        internet=(LinearLayout)findViewById(R.id.internet);
        Plist=this;
        Intent i=getIntent();
        brand_id=i.getStringExtra("id");
//        for(int i=0;i<10;i++){
//
//            ProductsClass products=new ProductsClass();
//            products.setId(String.valueOf(i));
//
//            PetFoodArray.add(products);
//
//        }
//



        ViewmoreAction=findViewById(R.id.view_more);
        ViewmoreAction.setVisibility(View.GONE);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

//        PetFoodArray.clear();
//        PetFoodReqRecycler= (RecyclerView) findViewById(R.id.PostReq_Recy);
//        postedReq_adapter = new ProductsAdapter(ProductsList.this, PetFoodArray);
//        PetFoodReqRecycler.setAdapter(postedReq_adapter);
//        PetFoodReqRecycler.setHasFixedSize(true);
//
//        PetFoodReqRecycler.setLayoutManager(new LinearLayoutManager(ProductsList.this, LinearLayoutManager.VERTICAL, false));
//        postedReq_adapter.notifyDataSetChanged();




//        if(internet_status){
//
//            new vat().execute();
//
//        }else{
//
//
//            internet.setVisibility(View.VISIBLE);
//
//        }



    }
    private class product_list extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String urls;

        ProgressHUD mProgressHUD;
        String Price;
        String Name;

        public product_list(String price, String name,String url) {
            Price=price;
            Name=name;
            urls=url;
        }

        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(ProductsList.this,null, true);


        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();

            try {
                jsonObject.put("product_category_id",Brands.category_id);
                jsonObject.put("brand_id",brand_id);
                jsonObject.put("name",Name);
                jsonObject.put("price",Price);

                if(Global.LANG.equals("en"))
                {
                    jsonObject.put("language_id", "0");

                }
                else
                {
                    jsonObject.put("language_id", "0");
                }

                JSONObject json = jsonParser.SendHttpPosts(urls,"POST",jsonObject,null,null);
                if (json != null) {

                    return  json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(JSONObject json) {

            if(json!=null) {


                String status=json.optString("status");
                String message=json.optString("message");


                if(status.equals("ok")){

                    JSONObject jsonObjects=json.optJSONObject("data");


                    JSONArray jsonArray_data=jsonObjects.optJSONArray("data");
                    PetFoodArray.clear();
                    PetFoodArray=new ArrayList<>();



                    if (jsonArray_data != null && jsonArray_data.length() > 0) {

                        No_Record.setVisibility(View.GONE);

                        for (int i = 0; i < jsonArray_data.length(); i++) {

                            JSONObject obj = jsonArray_data.optJSONObject(i);

                            String product_id = obj.optString("product_id");
                            String product_product_category_id = obj.optString("product_product_category_id");
                            String product_brand_id = obj.optString("product_brand_id");
                            String product_name = obj.optString("english_products_detail_name");
                            String arabic_products_detail_name = obj.optString("arabic_products_detail_name");
                            String product_description = obj.optString("english_products_detail_description");
                            String arabic_products_detail_description = obj.optString("arabic_products_detail_description");
                            String product_price = obj.optString("product_price");
                            String product_size = obj.optString("product_size");
                            String product_image = obj.optString("product_image");
                            String product_tax = obj.optString("product_tax");
                            String product_stock = obj.optString("product_stock");
                            String product_status = obj.optString("product_status");
                            String product_created_at = obj.optString("product_created_at");
                            String product_updated_at = obj.optString("product_updated_at");
                            String brand_id = obj.optString("brand_id");
                            String brand_name = obj.optString("brand_name");
                            String brand_logo = obj.optString("brand_logo");
                            String brand_status = obj.optString("brand_status");
                            String brand_created_at = obj.optString("brand_created_at");
                            String product_category_id = obj.optString("product_category_id");
                            String product_category_name = obj.optString("product_category_name");
                            String product_category_image = obj.optString("product_category_image");
                            String product_category_status = obj.optString("product_category_status");
                            String product_category_created_at = obj.optString("product_category_created_at");
                            String product_category_updated_at = obj.optString("product_category_updated_at");
                            String product_nutrition_contents = obj.optString("english_products_detail_nutrition_contents");
                            String arabic_products_detail_nutrition_contents = obj.optString("arabic_products_detail_nutrition_contents");

                            ProductsClass homeclass = new ProductsClass();

                            homeclass.setProduct_id(product_id);
                            homeclass.setId(product_id);
                            homeclass.setProduct_brand_id(product_brand_id);
                            homeclass.setProduct_name(product_name);
                            homeclass.setArabic_products_detail_name(arabic_products_detail_name);
                            homeclass.setProduct_description(product_description);
                            homeclass.setArabic_products_detail_description(arabic_products_detail_description);
                            homeclass.setProduct_price(product_price);
                            homeclass.setProduct_size(product_size);
                            homeclass.setProduct_image(product_image);
                            homeclass.setProduct_tax(product_tax);
                            homeclass.setProduct_stock(product_stock);
                            homeclass.setProduct_status(product_status);
                            homeclass.setProduct_created_at(product_created_at);
                            homeclass.setProduct_updated_at(product_updated_at);
                            homeclass.setBrand_name(brand_name);

                            homeclass.setBrand_logo(brand_logo);
                            homeclass.setBrand_status(brand_status);
                            homeclass.setBrand_created_at(brand_created_at);
                            homeclass.setProduct_category_id(product_category_id);
                            homeclass.setProduct_category_name(product_category_name);
                            homeclass.setProduct_category_image(product_category_image);
                            homeclass.setProduct_category_status(product_category_status);
                            homeclass.setProduct_category_created_at(product_category_created_at);
                            homeclass.setProduct_category_updated_at(product_category_updated_at);
                            homeclass.setProduct_nutrition_contents(product_nutrition_contents);
                            homeclass.setArabic_products_detail_nutrition_contents(arabic_products_detail_nutrition_contents);
                            homeclass.setVatPercentage(VatPercentage);

                            PetFoodArray.add(homeclass);


                        }

                        nextpathurl = jsonObjects.optString("next_page_url");

                        if (nextpathurl.equals("null")) {

                            ViewmoreAction.setVisibility(View.GONE);
                        } else {

                            ViewmoreAction.setVisibility(View.VISIBLE);
                        }


                        if (postedReq_adapter != null && postedReq_adapter.getItemCount() > 0) {

                            assert postedReq_adapter != null;
                            postedReq_adapter.notifyDataSetChanged();

//                        PetFoodReqRecycler= (RecyclerView) findViewById(R.id.PostReq_Recy);
//                        postedReq_adapter = new ProductsAdapter(ProductsList.this, PetFoodArray);
//                        PetFoodReqRecycler.setAdapter(postedReq_adapter);
//                        PetFoodReqRecycler.setHasFixedSize(true);
//
//                        PetFoodReqRecycler.setLayoutManager(new LinearLayoutManager(ProductsList.this, LinearLayoutManager.VERTICAL, false));
//                        postedReq_adapter.notifyDataSetChanged();


                        } else {


                            PetFoodReqRecycler = (RecyclerView) findViewById(R.id.PostReq_Recy);
                            postedReq_adapter = new ProductsAdapter(ProductsList.this, PetFoodArray);
                            PetFoodReqRecycler.setAdapter(postedReq_adapter);
                            PetFoodReqRecycler.setHasFixedSize(true);

                            PetFoodReqRecycler.setLayoutManager(new LinearLayoutManager(ProductsList.this, LinearLayoutManager.VERTICAL, false));
                            postedReq_adapter.notifyDataSetChanged();


                        }


                        ViewmoreAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //new product_list(nextpathurl).execute();

                                if (SortData.get(SessionManager.KEY_SORT_DATA) != null) {

                                    String data = SortData.get(SessionManager.KEY_SORT_DATA);

                                    if (data.equals(getString(R.string.price_high_to_low)))

                                    {

                                        if (internet_status) {

                                            new product_list("desc", "all", nextpathurl).execute();

                                        } else {


                                            internet.setVisibility(View.VISIBLE);

                                        }

                                    } else if (data.equals(getString(R.string.price_low_to_high))) {

                                        if (internet_status) {

                                            new product_list("asc", "all", nextpathurl).execute();

                                        } else {

                                            internet.setVisibility(View.VISIBLE);

                                        }
                                    } else if (data.equals(getString(R.string.name_ascenting))) {

                                        if (internet_status) {

                                            new product_list("all", "asc", nextpathurl).execute();

                                        } else {


                                            internet.setVisibility(View.VISIBLE);

                                        }

                                    } else if (data.equals(getString(R.string.name_descending))) {

                                        if (internet_status) {

                                            new product_list("all", "desc", nextpathurl).execute();

                                        } else {


                                            internet.setVisibility(View.VISIBLE);

                                        }


                                    }


                                } else {


                                    if (internet_status) {

                                        new product_list("all", "all", nextpathurl).execute();

                                    } else {


                                        internet.setVisibility(View.VISIBLE);

                                    }


                                }


                            }
                        });

                    }
                    else
                    {
                        No_Record.setVisibility(View.GONE);
                    }

                }else{


                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }

            }

            mProgressHUD.dismiss();
        }


    }

    private class vat extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.orderSettings;

        ProgressHUD mProgressHUD;
        String Token;
        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(ProductsList.this,null, true);
            Token=user.get(SessionManager.KEY_CUSTOMER_TOKEN);

        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();

            try {


                JSONObject json = jsonParser.SendHttpPosts(url,"GET",null,null,Token);
                if (json != null) {
                    return  json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(JSONObject json) {

            if(json!=null) {


                if (json.has("error")) {

                    session.logoutUser();
                    Intent intent = new Intent(ProductsList.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                }

                {
                    String status = json.optString("status");
                    String message = json.optString("message");


                    if (status.equals("ok")) {

                        JSONObject jsonObject = json.optJSONObject("data");
                        JSONObject order_settings = jsonObject.optJSONObject("order_settings");
                        String vatPercentage = order_settings.optString("vat_percentage");
                        String shipping_amount = order_settings.optString("shipping_amount");
                        CommonClass.DeliveryCharge = shipping_amount;
                        CommonClass.VATpercentage = vatPercentage;
                        VatPercentage = vatPercentage;
                        String price = null;
                        String name;

                        if (SortData.get(SessionManager.KEY_SORT_DATA) != null) {

                            String data = SortData.get(SessionManager.KEY_SORT_DATA);

                            if (data.equals(getString(R.string.price_high_to_low))) {


                                if(internet_status){

                                    new product_list("desc", "all", ApiLinks.products).execute();

                                }else{


                                    internet.setVisibility(View.VISIBLE);

                                }



                            } else if (data.equals(getString(R.string.price_low_to_high))) {


                                if(internet_status){

                                    new product_list("asc", "all", ApiLinks.products).execute();

                                }else{


                                    internet.setVisibility(View.VISIBLE);

                                }



                            } else if (data.equals(getString(R.string.name_ascenting))) {


                                if(internet_status){

                                    new product_list("all", "asc", ApiLinks.products).execute();

                                }else{


                                    internet.setVisibility(View.VISIBLE);

                                }




                            } else if (data.equals(getString(R.string.name_descending))) {

                                if(internet_status){

                                    new product_list("all", "desc", ApiLinks.products).execute();

                                }else{


                                    internet.setVisibility(View.VISIBLE);

                                }



                            }
                            else
                            {
                                if(internet_status){

                                    new product_list("all", "all", ApiLinks.products).execute();

                                }else{


                                    internet.setVisibility(View.VISIBLE);

                                }



                            }


                        } else {

                            if(internet_status){

                                new product_list("all", "all", ApiLinks.products).execute();

                            }else{


                                internet.setVisibility(View.VISIBLE);

                            }



                        }


                    } else {


                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                }

            }

            mProgressHUD.dismiss();
        }


    }

    public void sortfnctn()
    {



        final Dialog alertDialog = new Dialog(this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        session=new SessionManager(getApplicationContext());
        SortData=session.getPopupDetails();

        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");

        final Typeface face_bold = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Bold.otf");

        final RadioButton Price_High_To_Low;
        final RadioButton Price_Low_To_High;
        final RadioButton NameAscending;
        final RadioButton NameDescending;

        TextView SortHead;
        TextView Cance;
        TextView Appl;


        // loginDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_DeviceDefault_Light_Dialog));
        LayoutInflater factory = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View Sort_layout = factory.inflate(R.layout.sorting_popup, null);

        Price_High_To_Low=(RadioButton)Sort_layout.findViewById(R.id.radio2);
        Price_Low_To_High=(RadioButton)Sort_layout.findViewById(R.id.radio3);
        NameAscending=(RadioButton)Sort_layout.findViewById(R.id.radio4);
        NameDescending=(RadioButton)Sort_layout.findViewById(R.id.radio5);

        SortHead=(TextView) Sort_layout.findViewById(R.id.sortby);
        Cance=(TextView) Sort_layout.findViewById(R.id.cance);
        Appl=(TextView) Sort_layout.findViewById(R.id.appl);

        Price_High_To_Low.setTypeface(face);
        Price_Low_To_High.setTypeface(face);
        NameAscending.setTypeface(face);
        NameDescending.setTypeface(face);

        SortHead.setTypeface(face_bold);
        Cance.setTypeface(face_bold);
        Appl.setTypeface(face_bold);



        if(SortData.get(SessionManager.KEY_SORT_DATA)!=null)
        {

            String datas=SortData.get(SessionManager.KEY_SORT_DATA);

            if (datas.equals(getString(R.string.price_high_to_low)))
            {
                Price_High_To_Low.setChecked(true);
                sortdta= (String) Price_High_To_Low.getText();

            }
            else if (datas.equals(getString(R.string.price_low_to_high)))
            {
                Price_Low_To_High.setChecked(true);
                sortdta= (String) Price_Low_To_High.getText();

            }
            else if (datas.equals(getString(R.string.name_ascenting)))
            {
                NameAscending.setChecked(true);
                sortdta= (String) NameAscending.getText();
            }
            else if (datas.equals(getString(R.string.name_descending)))
            {
                NameDescending.setChecked(true);
                sortdta= (String) NameDescending.getText();

            }



        }


        else {

            RadioGroup rg = (RadioGroup) Sort_layout.findViewById(R.id.Sort_group);
            RadioButton rb = (RadioButton)Sort_layout. findViewById(rg.getCheckedRadioButtonId());

            sortdta = (String) rb.getText().toString();


            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // checkedId is the RadioButton selected
                    RadioButton rb = (RadioButton)Sort_layout. findViewById(checkedId);
                    sortdta = (String) rb.getText().toString();


                }
            });
        }


        Price_High_To_Low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sortdta= (String) Price_High_To_Low.getText();


            }
        });

        Price_Low_To_High.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sortdta= (String) Price_Low_To_High.getText();
            }
        });

        NameAscending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sortdta= (String) NameAscending.getText();


            }
        });



        NameDescending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sortdta= (String) NameDescending.getText();


            }
        });

        LinearLayout cancel = (LinearLayout) Sort_layout.findViewById(R.id.CancelPopUp);
        LinearLayout apply = (LinearLayout) Sort_layout.findViewById(R.id.ApplyPopUP);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                //CommonStrings.NeedResume="false";



            }
        });


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  CommonClass.sort = sortdta;
                  session.CreatePopupSession( CommonClass.sort);


                if (sortdta.equals(getString(R.string.price_high_to_low)))
                {
                    if(internet_status){

                        new product_list("desc","all",ApiLinks.products).execute();

                    }else{


                        internet.setVisibility(View.VISIBLE);

                    }





                }
                else if (sortdta.equals(getString(R.string.price_low_to_high)))
                {

                    if(internet_status){

                        new product_list("asc","all",ApiLinks.products).execute();

                    }else{


                        internet.setVisibility(View.VISIBLE);

                    }




                }
                else if (sortdta.equals(getString(R.string.name_ascenting)))
                {

                    if(internet_status){

                        new product_list("all","asc",ApiLinks.products).execute();

                    }else{


                        internet.setVisibility(View.VISIBLE);

                    }




                }
                else if (sortdta.equals(getString(R.string.name_descending)))
                {


                    if(internet_status){

                        new product_list("all","desc",ApiLinks.products).execute();

                    }else{


                        internet.setVisibility(View.VISIBLE);

                    }



                }
                alertDialog.dismiss();




            }
        });



        alertDialog.setContentView(Sort_layout);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }

     public void sort_common_function()
     {

         session=new SessionManager(getApplicationContext());
         SortData=session.getPopupDetails();

         if(SortData.get(SessionManager.KEY_SORT_DATA)!=null)
         {

             String data=SortData.get(SessionManager.KEY_SORT_DATA);

             if (data.equals(getString(R.string.price_high_to_low)))

             {

                 if(internet_status){

                     new product_list("desc","all",ApiLinks.products).execute();

                 }else{


                     internet.setVisibility(View.VISIBLE);

                 }



             }
             else if (data.equals(getString(R.string.price_low_to_high)))
             {
                 if(internet_status){

                     new product_list("asc","all",ApiLinks.products).execute();

                 }else{


                     internet.setVisibility(View.VISIBLE);

                 }




             }
             else if (data.equals(getString(R.string.name_ascenting)))
             {

                 if(internet_status){

                     new product_list("all","asc",ApiLinks.products).execute();

                 }else{


                     internet.setVisibility(View.VISIBLE);

                 }



             }
             else if (data.equals(getString(R.string.name_descending)))

             {

                 if(internet_status){

                     new product_list("all","desc",ApiLinks.products).execute();

                 }else{


                     internet.setVisibility(View.VISIBLE);

                 }





             }


         }
         else
         {

             if(internet_status){

                 new product_list("all","all",ApiLinks.products).execute();

             }else{


                 internet.setVisibility(View.VISIBLE);

             }




         }




     }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        buildCounterDrawable drawable=new buildCounterDrawable();
        getMenuInflater().inflate(R.menu.menu_new, menu);
         this.mMenu=menu;

        MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setIcon(drawable.buildCounterDrawables(Arrays.CartArray.size(),  R.drawable.ic_cart,this));


        MenuItem sort=menu.findItem(R.id.action_sort);
        sort.setVisible(true);

        return true;
    }
    private static long back_pressed;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()){


            case android.R.id.home:

                onBackPressed();
                return true;
            case R.id.action_cart:
                if(Arrays.CartArray.size()>0)
                {
                    Intent i= new Intent(ProductsList.this,CartActivity.class) ;
                    startActivity(i);
                }
                else

                {

                    Toast.makeText(getApplicationContext(), R.string.Cart_is_empty, Toast.LENGTH_LONG).show();

                }

                break;
            case R.id.action_notification:


                Intent i= new Intent(ProductsList.this,MyOrders.class) ;
                startActivity(i);

                break;
            case R.id.action_sort:


                sortfnctn();




                break;
        }

        return super.onOptionsItemSelected(item);


    }
    @Override
    public void  onBackPressed(){

        finish();

    }
     public static ProductsList getInstance()
     {

        return Plist;

     }


    private void safelyUnsubscribe(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        PetFoodReqRecycler= (RecyclerView) findViewById(R.id.PostReq_Recy);
        postedReq_adapter = new ProductsAdapter(ProductsList.this, PetFoodArray);
        PetFoodReqRecycler.setAdapter(postedReq_adapter);
        PetFoodReqRecycler.setHasFixedSize(true);

        PetFoodReqRecycler.setLayoutManager(new LinearLayoutManager(ProductsList.this, LinearLayoutManager.VERTICAL, false));
        postedReq_adapter.notifyDataSetChanged();
        invalidateOptionsMenu();
        sort_common_function();
        initTypeface();




        internetConnectivitySubscription = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override public void call(Boolean isConnectedToInternet) {


                        session=new SessionManager(getApplicationContext());
                        user=session.getUserDetails();
                        internet_status=isConnectedToInternet;

                        if(isConnectedToInternet){
                            //snackbar1.dismiss();
                            internet.setVisibility(View.GONE);
                            new vat().execute();
                        }
                        else{


                            internet.setVisibility(View.VISIBLE);

                        }


                    }
                });




    }
    @Override
    protected void onPause() {

        super.onPause();
        safelyUnsubscribe(internetConnectivitySubscription);
    }



}
