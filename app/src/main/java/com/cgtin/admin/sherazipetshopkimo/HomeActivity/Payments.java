package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.cgtin.admin.sherazipetshopkimo.Adapters.PaymentAdapter;
import com.cgtin.admin.sherazipetshopkimo.Classes.AfterPay;
import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.OverlapDecoration;
import com.cgtin.admin.sherazipetshopkimo.Classes.Payment_Class;
import com.cgtin.admin.sherazipetshopkimo.Classes.ProductDetailClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.Classes.buildCounterDrawable;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.AddressClass;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.cgtin.admin.sherazipetshopkimo.StartActivities.LoginActivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Payments extends AppCompatActivity {


    ArrayList<Payment_Class> PaymentArray;
    RecyclerView PetFoodReqRecycler;
    public PaymentAdapter PaymentAdapter;
    public static Payments Payments;
    HashMap<String,String> user;
    HashMap<String,String> CartDetails;
    SessionManager session;

    public static boolean internet_status;
    private Subscription internetConnectivitySubscription;
    public static LinearLayout internet;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        mTitle.setText(R.string.payments);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        internet=(LinearLayout)findViewById(R.id.internet);
        Payments=this;


        session=new SessionManager(getApplicationContext());
        user=session.getUserDetails();


    }


    public  void Upload_Asy_Class(int id)
    {

        if(internet_status){

            new Upload(id).execute();

        }else{


            internet.setVisibility(View.VISIBLE);

        }


    }


    private class Upload extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.store;
        String AddID;
        ProgressHUD mProgressHUD;
        String Token;
        String StudentsAttendance;
        public Upload(int id) {
            AddID= String.valueOf(id);
        }

        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(Payments.this,null, true);

            Token=user.get(SessionManager.KEY_CUSTOMER_TOKEN);

        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();

            HashMap<String,String> params=new HashMap<>();


            try {




                Gson gson = new Gson();
                StudentsAttendance = gson.toJson(Arrays.CartArray);
                double Total = 0;
                for (ProductDetailClass items : Arrays.CartArray) {

                    String price = items.getPrice();
                    Double tprice=Double.parseDouble(price);
                    Total += tprice;

                }



                jsonObject.put("payment_mode_id",AddID);
                jsonObject.put("payment_status","pending");
                jsonObject.put("customer_address_id",CommonClass.Address_Id_Show);
                jsonObject.put("user_id",user.get(SessionManager.KEY_CUSTOMER_ID));
                Double VatAmount=Total*Double.valueOf(CommonClass.VATpercentage)/100;
                jsonObject.put("total_price",Total+Double.valueOf(CommonClass.DeliveryCharge)+VatAmount);


                JSONArray array=new JSONArray(StudentsAttendance);

                jsonObject.put("order_items",array);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {


                JSONObject json = jsonParser.SendHttpPosts(url,"POST",jsonObject,null,Token);
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

                   // session.logoutUser();
                    Intent intent = new Intent(Payments.this, LoginActivity.class);
                    intent.putExtra("from","Payments");
                    startActivity(intent);


                }
                else
                {

                   // Toast.makeText(getApplicationContext(),StudentsAttendance.toString(),Toast.LENGTH_LONG);
                    String status=json.optString("status");
                    String message=json.optString("message");
                    JSONArray Data=json.optJSONArray("data");
                    Arrays.AfterPayArray.clear();

                    if(status.equals("ok")){


                        session.CartSession(null);
                        Intent i= new Intent(Payments.this,Orderconfirmation.class) ;

                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        finish();

                    }else{

                        for (int i = 0; i < Data.length(); i++)
                        {
                            JSONObject obj = Data.optJSONObject(i);

                            AfterPay object=new AfterPay();


                            object.setProduct_id(obj.optString("product_id"));

                            object.setName(obj.optString("name"));

                            Arrays.AfterPayArray.add(object);



                        }
                        if( Arrays.CartArray.size()>0)
                        {
                            Intent i= new Intent(Payments.this,CartActivity.class) ;
                            startActivity(i);
                        }


                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    }
                }





            }

            mProgressHUD.dismiss();
        }


    }

    private class payment_mode extends AsyncTask<String, Void, JSONObject> {
        String url;
        JSONParser jsonParser = new JSONParser();

        ProgressHUD mProgressHUD;


        String Token;
        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(Payments.this,null, true);

            if(Global.LANG.equals("en"))
            {
                url= ApiLinks.payment_mode+"1";

            }
            else
            {
                url= ApiLinks.payment_mode+"2";
            }
            Token=user.get(SessionManager.KEY_CUSTOMER_TOKEN);

        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();
            JSONObject jsonObject_tocken=new JSONObject();
            HashMap<String,String> params=new HashMap<>();



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


                String status=json.optString("status");
                String message=json.optString("message");


                if(status.equals("ok")){




                    JSONArray jsonArray_data=json.optJSONArray("data");


                    PaymentArray=new ArrayList<>();
                    if(jsonArray_data!=null && jsonArray_data.length()>0) {

                        for (int i = 0; i < jsonArray_data.length(); i++) {

                            JSONObject obj = jsonArray_data.optJSONObject(i);

                            int id = obj.optInt("payment_mode_id");
                            String name = obj.optString("payment_mode_detail_name");
                            String deleted_at = obj.optString("deleted_at");
                            String created_at = obj.optString("created_at");
                            String updated_at = obj.optString("updated_at");


                            Payment_Class homeclass = new Payment_Class();

                            homeclass.setId(id);
                            homeclass.setName(name);
                            homeclass.setDeleted_at(deleted_at);
                            homeclass.setCreated_at(created_at);
                            homeclass.setUpdated_at(updated_at);


                            PaymentArray.add(homeclass);


                        }





                        if(PaymentAdapter!=null)
                        {
                            PaymentAdapter.notifyDataSetChanged();
                        }
                        else {

                            PetFoodReqRecycler = (RecyclerView) findViewById(R.id.PostReq_Recy);
                            PaymentAdapter = new PaymentAdapter(Payments.this, PaymentArray);
                            PetFoodReqRecycler.setAdapter(PaymentAdapter);
                            PetFoodReqRecycler.setHasFixedSize(true);
                            PetFoodReqRecycler.addItemDecoration(new OverlapDecoration());
                            PetFoodReqRecycler.setLayoutManager(new LinearLayoutManager(Payments.this, LinearLayoutManager.VERTICAL, false));
                            PaymentAdapter.notifyDataSetChanged();
                        }



                    }




                }else{


                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }

            }

            mProgressHUD.dismiss();
        }


    }


    public static Payments getInstance()
    {
        return Payments;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        buildCounterDrawable drawable=new buildCounterDrawable();
        getMenuInflater().inflate(R.menu.menu_new, menu);


        MenuItem menuItem = menu.findItem(R.id.action_cart);


        MenuItem myOrders=menu.findItem(R.id.action_notification);
        myOrders.setVisible(false);

        menuItem.setVisible(false);
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
//            case R.id.action_cart:
//                if(Arrays.CartArray.size()>0)
//                {
//                    Intent i= new Intent(Payments.this,CartActivity.class) ;
//                    startActivity(i);
//                }
//                else
//
//                {
//
//                    Toast.makeText(getApplicationContext(), R.string.Cart_is_empty, Toast.LENGTH_LONG).show();
//
//                }
//
//                break;
//            case R.id.action_notification:
//
//                Intent i= new Intent(Payments.this,MyOrders.class) ;
//                startActivity(i);
//
//                break;

        }

        return super.onOptionsItemSelected(item);


    }
    @Override
    public void  onBackPressed(){


        finish();

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
                            new payment_mode().execute();
                            invalidateOptionsMenu();
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
