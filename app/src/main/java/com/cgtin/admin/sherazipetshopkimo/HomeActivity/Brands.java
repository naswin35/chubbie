package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Adapters.BrandAdapter;
import com.cgtin.admin.sherazipetshopkimo.BaseActivity;
import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.BrandClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.OverlapDecoration;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.Classes.buildCounterDrawable;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.R;
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

import static com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass.froms;

public class Brands extends BaseActivity {


    DrawerLayout drawer;
    public static String category_id;
    public static Brands Brands;
    BrandAdapter postedReq_adapter;
    RecyclerView PetFoodReqRecycler;
    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;
    SessionManager session;
    HashMap<String,String>UserDetails;
    LinearLayout No_Record;
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_brands);
        getLayoutInflater().inflate(R.layout.activity_brands,frameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        mTitle.setText(R.string.brand);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        session=new SessionManager(getApplicationContext());
        UserDetails=session.getUserDetails();
        mTitle.setTypeface(face);
        CommonClass.fromActivity="Brands";
        Intent i=getIntent();
        category_id=i.getStringExtra("id");
        internet=(LinearLayout)findViewById(R.id.internet);

        No_Record=findViewById(R.id.no_record);


//        if(internet_status){
//
//            new brands().execute();
//
//        }else{
//
//
//            internet.setVisibility(View.VISIBLE);
//
//        }
        Brands=this;

    }


    public void brand_async()
    {

        new brands().execute();

    }

    public static Brands getIntance()
    {
        return Brands;
    }


    private class brands extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.products+"?"+"page="+froms;


        ProgressHUD mProgressHUD;

        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(Brands.this,null, true);



        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();
            JSONObject jsonObject_tocken=new JSONObject();
            HashMap<String,String> params=new HashMap<>();


            try {
                jsonObject.put("product_category_id",category_id);
                jsonObject.put("brand_id","0");
                jsonObject.put("name","all");
                jsonObject.put("price","all");


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


                String status=json.optString("status");
                String message=json.optString("message");



                if(status.equals("ok")){

                    JSONObject jsonObject=json.optJSONObject("data");


                    JSONArray jsonArray_data=jsonObject.optJSONArray("data");
                    CommonClass.last_page_food=jsonObject.optInt("last_page");

                    if (jsonArray_data != null && jsonArray_data.length() > 0) {
                        ArrayList<BrandClass> BrandArray = new ArrayList<>();
                        No_Record.setVisibility(View.GONE);

                        for (int i = 0; i < jsonArray_data.length(); i++) {

                            JSONObject obj = jsonArray_data.optJSONObject(i);

                            String team_group_id = obj.optString("team_group_id");
                            String product_product_category_id = obj.optString("product_product_category_id");
                            String product_brand_id = obj.optString("product_brand_id");
                            String product_name = obj.optString("product_name");
                            String product_description = obj.optString("product_description");
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

                            BrandClass homeclass = new BrandClass();

                            homeclass.setTeam_group_id(team_group_id);
                            homeclass.setProduct_product_category_id(product_product_category_id);
                            homeclass.setProduct_brand_id(product_brand_id);
                            homeclass.setProduct_name(product_name);
                            homeclass.setProduct_description(product_description);
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

                            BrandArray.add(homeclass);


                        }

                        if (postedReq_adapter != null) {
                            postedReq_adapter.notifyDataSetChanged();
                        } else {

                            PetFoodReqRecycler = (RecyclerView) findViewById(R.id.PostReq_Recy);
                            postedReq_adapter = new BrandAdapter(Brands.this, BrandArray);
                            PetFoodReqRecycler.setAdapter(postedReq_adapter);
                            PetFoodReqRecycler.setHasFixedSize(true);
                            PetFoodReqRecycler.addItemDecoration(new OverlapDecoration());
                            PetFoodReqRecycler.setLayoutManager(new LinearLayoutManager(Brands.this, LinearLayoutManager.VERTICAL, false));
                            postedReq_adapter.notifyDataSetChanged();
                        }


                    }
                    else
                    {
                        No_Record.setVisibility(View.VISIBLE);
                    }

                }else{


                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }

            }

            mProgressHUD.dismiss();
        }


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
                if( Arrays.CartArray.size()>0)
                {
                    Intent i= new Intent(Brands.this,CartActivity.class) ;
                    startActivity(i);
                }
                else

                {

                    Toast.makeText(getApplicationContext(), R.string.Cart_is_empty, Toast.LENGTH_LONG).show();

                }

                break;
            case R.id.action_notification:

                Intent i= new Intent(Brands.this,MyOrders.class) ;
                startActivity(i);

                break;

        }

        return super.onOptionsItemSelected(item);


    }
    @Override
    public void  onBackPressed(){

      finish();

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
                            new brands().execute();
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
