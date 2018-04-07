package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Adapters.BrandAdapter;
import com.cgtin.admin.sherazipetshopkimo.Adapters.HomeAdapter;
import com.cgtin.admin.sherazipetshopkimo.BaseActivity;
import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.HomeClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.OverlapDecoration;
import com.cgtin.admin.sherazipetshopkimo.Classes.ProductDetailClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.Classes.buildCounterDrawable;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.cgtin.admin.sherazipetshopkimo.StartActivities.LoginActivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends BaseActivity {

    DrawerLayout drawer;
    ArrayList<HomeClass> pets;
    RecyclerView PetsRecycler;
    private static long back_pressed;
    private static final boolean DOBLETAP =true ;
    SessionManager session;
    HashMap<String,String>UserDetails;
    HashMap<String,String>CartDetails;
    public static HomeActivity homeActivity;
    private CoordinatorLayout coordinatorLayout;
    private RelativeLayout relative;
    HomeAdapter home_adapter;
    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;
    Snackbar snackbar1;
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home,frameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        internet=(LinearLayout)findViewById(R.id.internet);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coardinate);
        relative = (RelativeLayout) findViewById(R.id.relative);
        homeActivity=this;

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_paw_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.START);
            }
        });
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        session=new SessionManager(getApplicationContext());
        UserDetails=session.getUserDetails();
        CartDetails=session.getCartDetails();



//        if(internet_status){
//
//            new Home().execute();
//
//        }else{
////            snackbar1 = Snackbar.make(relative, "Please check your internet connection", Snackbar.LENGTH_INDEFINITE);
////            snackbar1.getView().setBackgroundColor(Color.parseColor("#F44336"));
////            snackbar1 .setDuration(5000).show();
//
//            //Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
//
//            internet.setVisibility(View.VISIBLE);
//
//        }


        initTypeface();


        if(CartDetails.get(SessionManager.KEY_CART_DATA)!=null){

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ProductDetailClass>>() { }.getType();
            ArrayList<ProductDetailClass> cartList = gson.fromJson(CartDetails.get(SessionManager.KEY_CART_DATA), type);


            Arrays.CartArray.clear();
            Arrays.ProductItemsArray.clear();
            Arrays.CartArray.addAll(cartList);
            Arrays.ProductItemsArray.addAll(cartList);

        }


    }

    private class Home extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.Home;


        ProgressHUD mProgressHUD;

        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(HomeActivity.this,null, true);



        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();
            JSONObject jsonObject_tocken=new JSONObject();
            HashMap<String,String> params=new HashMap<>();

            try {
                String lang;

                if(Global.LANG.equals("en"))
                {
                    jsonObject.put("language_id", "1");

                }
                else
                {
                    jsonObject.put("language_id", "2");
                }

                JSONObject json = jsonParser.SendHttpPosts(url,"POST",jsonObject,null,null);
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



                    String status = json.optString("status");
                    String message = json.optString("message");


                    if (status.equals("ok")) {

                        JSONObject jsonObject = json.optJSONObject("data");
                        JSONObject jsonObject_categories = jsonObject.optJSONObject("product_categories");
                        String current_page = jsonObject.optString("current_page");
                        String from = jsonObject.optString("from");
                        String last_page = jsonObject.optString("last_page");
                        String next_page_url = jsonObject.optString("next_page_url");
                        String path = jsonObject.optString("path");
                        String per_page = jsonObject.optString("per_page");
                        String prev_page_url = jsonObject.optString("prev_page_url");
                        String to = jsonObject.optString("to");
                        String total = jsonObject.optString("total");

                        JSONArray jsonArray_data = jsonObject_categories.optJSONArray("data");


                        pets = new ArrayList<>();
                        for (int i = 0; i < jsonArray_data.length(); i++) {

                            JSONObject obj = jsonArray_data.optJSONObject(i);

                            String product_category_id = obj.optString("product_category_id");
                            String product_category_name = obj.optString("product_categories_detail_name");
                            String product_category_image = obj.optString("product_category_image");
                            String product_category_status = obj.optString("product_category_status");
                            String product_category_created_at = obj.optString("product_category_created_at");
                            String product_category_updated_at = obj.optString("product_category_updated_at");
                            String product_sub_category_id = obj.optString("product_sub_category_id");
                            String product_sub_category_product_category_id = obj.optString("product_sub_category_product_category_id");
                            String product_sub_category_product_category_parent_id = obj.optString("product_sub_category_product_category_parent_id");
                            String product_sub_category_created_at = obj.optString("product_sub_category_created_at");
                            String product_sub_category_updated_at = obj.optString("product_sub_category_updated_at");

                            HomeClass homeclass = new HomeClass();

                            homeclass.setProduct_category_id(product_category_id);
                            homeclass.setProduct_category_name(product_category_name);
                            homeclass.setProduct_category_image(product_category_image);
                            homeclass.setProduct_category_status(product_category_status);
                            homeclass.setProduct_category_created_at(product_category_created_at);
                            homeclass.setProduct_category_updated_at(product_category_updated_at);
                            homeclass.setProduct_sub_category_id(product_sub_category_id);
                            homeclass.setProduct_sub_category_product_category_id(product_sub_category_product_category_id);
                            homeclass.setProduct_sub_category_product_category_parent_id(product_sub_category_product_category_parent_id);
                            homeclass.setProduct_sub_category_created_at(product_sub_category_created_at);
                            homeclass.setProduct_sub_category_updated_at(product_sub_category_updated_at);

                            pets.add(homeclass);


                        }
                        HomeClass petsIDDog;
                        HomeClass petsIDCat;
                        if(pets.size()!=0)
                        {

                            for(HomeClass petsname:pets)
                            {
                              if(petsname.getProduct_category_name().equals("Cat")||petsname.getProduct_category_name().equals("قط"))
                              {
                                  CommonClass.CatID = petsname.getProduct_category_id();
                                  CommonClass.CatTitle = petsname.getProduct_category_name();

                              }
                              else if(petsname.getProduct_category_name().equals("Dog")||petsname.getProduct_category_name().equals("الكلب"))
                              {
                                  CommonClass.DogID = petsname.getProduct_category_id();
                                  CommonClass.DogTitle = petsname.getProduct_category_name();
                              }


                            }

//                             petsIDDog = pets.get(0);
//                             petsIDCat = pets.get(1);
//                             CommonClass.DogID = petsIDDog.getProduct_category_id();
//                             CommonClass.CatID = petsIDCat.getProduct_category_id();
                        }

                        if(home_adapter!=null)
                        {
                            home_adapter.notifyDataSetChanged();
                        }
                        else {

                            PetsRecycler = (RecyclerView) findViewById(R.id.PostReq_Recy);
                            home_adapter = new HomeAdapter(HomeActivity.this, pets);
                            PetsRecycler.setAdapter(home_adapter);
                            PetsRecycler.setHasFixedSize(true);
                            PetsRecycler.addItemDecoration(new OverlapDecoration());
                            PetsRecycler.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false));
                            home_adapter.notifyDataSetChanged();
                        }




                    } else {


                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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

            mProgressHUD = ProgressHUD.show(HomeActivity.this,null, true);
            Token=UserDetails.get(SessionManager.KEY_CUSTOMER_TOKEN);

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
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
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


                        if(internet_status){

                            new Home().execute();

                        }else{


                            internet.setVisibility(View.VISIBLE);

                        }

                    } else {


                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                }

            }

            mProgressHUD.dismiss();
        }


    }











    public static HomeActivity getInstance()
    {

     return homeActivity;

    }





    private void safelyUnsubscribe(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        buildCounterDrawable drawable=new buildCounterDrawable();
        getMenuInflater().inflate(R.menu.menu_new, menu);


        MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setIcon(drawable.buildCounterDrawables(Arrays.CartArray.size(),  R.drawable.ic_cart,this));
        return true;
    }

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
                    Intent i= new Intent(HomeActivity.this,CartActivity.class) ;
                    startActivity(i);
                }
                else

                {

                    Toast.makeText(getApplicationContext(), R.string.Cart_is_empty, Toast.LENGTH_LONG).show();

                }

                break;
            case R.id.action_notification:


                Intent i= new Intent(HomeActivity.this,MyOrders.class) ;
                startActivity(i);

                break;
        }

        return super.onOptionsItemSelected(item);


    }
    @Override
    public void  onBackPressed(){


        if(DOBLETAP==true){
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);





                Arrays.ProductItemsArray.clear();
                Arrays.CartArray.clear();
                finish();
                System.exit(0);

            } else
                Toast.makeText(getBaseContext(), R.string.Press_once_again_to_exit, Toast.LENGTH_SHORT).show();

            back_pressed = System.currentTimeMillis();
        }

        else{
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


        invalidateOptionsMenu();
        initTypeface();

        internetConnectivitySubscription = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override public void call(Boolean isConnectedToInternet) {


                        session=new SessionManager(getApplicationContext());
                        UserDetails=session.getUserDetails();
                        internet_status=isConnectedToInternet;

                        if(isConnectedToInternet){
                            //snackbar1.dismiss();
                            internet.setVisibility(View.GONE);

                            new vat().execute();


                        }
                        else
                        {
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
