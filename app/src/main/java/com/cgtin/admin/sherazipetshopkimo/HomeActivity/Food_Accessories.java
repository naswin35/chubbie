package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
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

import com.cgtin.admin.sherazipetshopkimo.Adapters.FoodAccessoriesAdapter;
import com.cgtin.admin.sherazipetshopkimo.Adapters.HomeAdapter;
import com.cgtin.admin.sherazipetshopkimo.BaseActivity;
import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.FoodAccessories_Class;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
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

public class Food_Accessories extends BaseActivity {


    DrawerLayout drawer;
    String Title;
    ArrayList<FoodAccessories_Class> food_acc;
    RecyclerView PetsRecycler;
    String id;
    public static Food_Accessories foodAccessories;
    FoodAccessoriesAdapter home_adapter;
    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;
    SessionManager session;
    HashMap<String,String>UserDetails;
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_food__accessories);
        getLayoutInflater().inflate(R.layout.activity_food__accessories,frameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        internet=(LinearLayout)findViewById(R.id.internet);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i=getIntent();
        Title=i.getStringExtra("Title");
        id=i.getStringExtra("id");

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        mTitle.setText(Title);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);
        foodAccessories=this;
        internet=(LinearLayout)findViewById(R.id.internet);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        session=new SessionManager(getApplicationContext());
        UserDetails=session.getUserDetails();









    }


    private class FoodAccessories extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.SubCategory+id;


        ProgressHUD mProgressHUD;

        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(Food_Accessories.this,null, true);



        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();
            JSONObject jsonObject_tocken=new JSONObject();
            HashMap<String,String> params=new HashMap<>();

            try {
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


                String status=json.optString("status");
                String message=json.optString("message");


                if(status.equals("ok")){

                    JSONObject jsonObject=json.optJSONObject("data");
                    JSONObject jsonObject_categories=jsonObject.optJSONObject("product_categories");
                    String current_page=jsonObject.optString("current_page");
                    String from=jsonObject.optString("from");
                    String last_page=jsonObject.optString("last_page");
                    String next_page_url=jsonObject.optString("next_page_url");
                    String path=jsonObject.optString("path");
                    String per_page=jsonObject.optString("per_page");
                    String prev_page_url=jsonObject.optString("prev_page_url");
                    String to=jsonObject.optString("to");
                    String total=jsonObject.optString("total");

                    JSONArray jsonArray_data=jsonObject_categories.optJSONArray("data");


                    food_acc=new ArrayList<>();
                    for(int i=0;i<jsonArray_data.length();i++)
                    {

                        JSONObject obj=jsonArray_data.optJSONObject(i);

                        String product_category_id=obj.optString("product_category_id");
                        String product_category_name=obj.optString("product_categories_detail_name");
                        String product_category_image=obj.optString("product_category_image");
                        String product_category_status=obj.optString("product_category_status");
                        String product_category_created_at=obj.optString("product_category_created_at");
                        String product_category_updated_at=obj.optString("product_category_updated_at");
                        String product_sub_category_id=obj.optString("product_sub_category_id");
                        String product_sub_category_product_category_id=obj.optString("product_sub_category_product_category_id");
                        String product_sub_category_product_category_parent_id=obj.optString("product_sub_category_product_category_parent_id");
                        String product_sub_category_created_at=obj.optString("product_sub_category_created_at");
                        String product_sub_category_updated_at=obj.optString("product_sub_category_updated_at");

                        FoodAccessories_Class food_class=new FoodAccessories_Class();

                        food_class.setProduct_category_id(product_category_id);
                        food_class.setProduct_category_name(product_category_name);
                        food_class.setProduct_category_image(product_category_image);
                        food_class.setProduct_category_status(product_category_status);
                        food_class.setProduct_category_created_at(product_category_created_at);
                        food_class.setProduct_category_updated_at(product_category_updated_at);
                        food_class.setProduct_sub_category_id(product_sub_category_id);
                        food_class.setProduct_sub_category_product_category_id(product_sub_category_product_category_id);
                        food_class.setProduct_sub_category_product_category_parent_id(product_sub_category_product_category_parent_id);
                        food_class.setProduct_sub_category_created_at(product_sub_category_created_at);
                        food_class.setProduct_sub_category_updated_at(product_sub_category_updated_at);

                        food_acc.add(food_class);



                    }


                    if(home_adapter!=null)
                    {
                        home_adapter.notifyDataSetChanged();
                    }
                    else {

                        PetsRecycler= (RecyclerView) findViewById(R.id.PostReq_Recy);
                        home_adapter = new FoodAccessoriesAdapter(Food_Accessories.this, food_acc);
                        PetsRecycler.setAdapter(home_adapter);
                        PetsRecycler.setHasFixedSize(true);
                        PetsRecycler.addItemDecoration(new OverlapDecoration());
                        PetsRecycler.setLayoutManager(new LinearLayoutManager(Food_Accessories.this, LinearLayoutManager.VERTICAL, false));
                        home_adapter.notifyDataSetChanged();
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


    public static Food_Accessories getInstance()
    {


        return foodAccessories;
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
                if(Arrays.CartArray.size()>0)
                {
                    Intent i= new Intent(Food_Accessories.this,CartActivity.class) ;
                    startActivity(i);
                }
                else

                {

                    Toast.makeText(getApplicationContext(), R.string.Cart_is_empty, Toast.LENGTH_LONG).show();

                }

                break;
            case R.id.action_notification:


                Intent i= new Intent(Food_Accessories.this,MyOrders.class) ;
                startActivity(i);

                break;

        }

        return super.onOptionsItemSelected(item);


    }
    @Override
    public void  onBackPressed(){

        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {



                Intent i= new Intent(Food_Accessories.this,HomeActivity.class) ;
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                finish();



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
                            new FoodAccessories().execute();
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
