package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Adapters.OrderItemsAdapter;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.OrderitemClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.DateClass;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrderDetailsActivity extends AppCompatActivity {



    String OrderItemID;
    String NotifyID;
    String NotifyStatus;
    SessionManager session;
    HashMap<String,String>user;


    TextView CustomerName;
    TextView CustomerAddress;
    TextView CustomerMobile;

    TextView SellingPrice;
    TextView ShippingFee;
    TextView VatAmount;
    TextView TotalAmount;
    TextView PaymentMode;
    TextView vatPercent;

    TextView OrderID;

    RecyclerView MyItemListRecy;

    LinearLayout orderDetails;


    public static boolean internet_status;
    private Subscription internetConnectivitySubscription;
    public static LinearLayout internet;

    ArrayList<OrderitemClass>MyOrderItemsList;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Global.LANG = settings.getString("language","en");
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", Global.LANG).apply();
        Locale locale = new Locale(Global.LANG);
        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(locale);
        Locale.setDefault(locale);
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent getData=getIntent();
        OrderItemID=getData.getStringExtra("OrderID");
        mTitle.setText(R.string.order_details);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);

        session=new SessionManager(getApplicationContext());
        user=session.getUserDetails();


        OrderID=findViewById(R.id.order_id);



        CustomerName=findViewById(R.id.name);
        CustomerMobile=findViewById(R.id.mobile);
        CustomerAddress=findViewById(R.id.address);
        SellingPrice=findViewById(R.id.selling_price);
        ShippingFee=findViewById(R.id.shipping_fee);
        VatAmount=findViewById(R.id.vat_amount);
        vatPercent=findViewById(R.id.vatPercent);
        TotalAmount=findViewById(R.id.total_amount);
        PaymentMode=findViewById(R.id.payment_mode);
        MyItemListRecy=findViewById(R.id.orderIdItems);
        orderDetails=findViewById(R.id.orderDetails);

        internet=(LinearLayout)findViewById(R.id.internet);

        orderDetails.setVisibility(View.GONE);


        MyOrderItemsList=new ArrayList<>();



        if(getData.getStringExtra("type").equals("notification")){

            NotifyStatus=getData.getStringExtra("status");
            NotifyID=getData.getStringExtra("notifyID");



            if(NotifyStatus.equals("unread")){



                if(internet_status){

                    NotifyStatus="read";
                    new UpdateStatus(NotifyID).execute();

                }else{

                    if(!internet.isShown()) {
                        internet.setVisibility(View.VISIBLE);
                    }

                }


            }

        }


    }


    private class FetchDataofProduct extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url;
        //user.get(SessionManager.KEY_CUSTOMER_ID)

        ProgressHUD mProgressHUD;
        String Token;
        public FetchDataofProduct(String OrderItemID) {

            url= ApiLinks.specificCustomerOrderItemView+OrderItemID;
            Token=user.get(SessionManager.KEY_CUSTOMER_TOKEN);

        }

        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(OrderDetailsActivity.this,null, true);

        }

        protected JSONObject doInBackground(String... arg0) {

            JSONObject jsonObject=new JSONObject();
            try {
                if(Global.LANG.equals("en"))
                {
                    jsonObject.put("language_id", "1");

                }
                else
                {
                    jsonObject.put("language_id", "2");
                }

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


                String status=json.optString("status");
                String message=json.optString("message");


                if(status.equals("ok")){

                    orderDetails.setVisibility(View.VISIBLE);


                    JSONObject data=json.optJSONObject("data");

                    JSONObject order=data.optJSONObject("customer_order");
                    String customer_order_total_price=order.optString("customer_order_total_price");
                    String customer_order_item_vat_amount=order.optString("customer_order_vat_amount");
                    String customer_order_item_vat_percentage=order.optString("customer_order_vat_percentage");
                    String customer_order_item_shipping_amount=order.optString("customer_order_shipping_amount");
                    String user_mobile=order.optString("customer_address_contact");
                    String customer_address_name=order.optString("customer_address_name");
                    String customer_address_address=order.optString("customer_address_address");
                    //String customer_address_pincode=order.optString("customer_address_pincode");
                    String payment_mode_name=order.optString("payment_mode_name");






                    OrderID.setText(OrderItemID);
                    CustomerName.setText(customer_address_name);
                    CustomerMobile.setText(user_mobile);
                    CustomerAddress.setText(String.format("%s", customer_address_address));

                    ShippingFee.setText(customer_order_item_shipping_amount);
                    VatAmount.setText(customer_order_item_vat_amount);

                    float sellprice;
                    float vatprice;
                    float totalsprice;
                    float shipping;

                    vatprice= Float.parseFloat(customer_order_item_vat_amount);
                    shipping= Float.parseFloat(customer_order_item_shipping_amount);
                    totalsprice= Float.parseFloat(customer_order_total_price);

                    sellprice=totalsprice-(vatprice+shipping);


                    SellingPrice.setText(String.format(Locale.ENGLISH,"%.02f", sellprice));

                    vatPercent.setText(String.format(getString(R.string.vat)+" - %s%%", customer_order_item_vat_percentage));
                    TotalAmount.setText(customer_order_total_price);
                    PaymentMode.setText(payment_mode_name);

                    JSONArray OrderedItemsList=data.optJSONArray("customer_orders_item_view");

                    if(OrderedItemsList!=null && OrderedItemsList.length()>0){

                        MyOrderItemsList.clear();

                        DateClass dates=new DateClass();

                        for (int i = 0; i < OrderedItemsList.length(); i++) {

                            JSONObject object=OrderedItemsList.optJSONObject(i);
                            OrderitemClass orderitemClass=new OrderitemClass();
                            orderitemClass.setProduct_id(object.optString("product_id"));
                            orderitemClass.setProduct_name(object.optString("product_name"));
                            orderitemClass.setProduct_image(object.optString("product_image"));
                            orderitemClass.setProduct_price(object.optString("product_price"));
                            orderitemClass.setCustomer_order_item_quantity(object.optString("customer_order_item_quantity"));
                            orderitemClass.setOrder_status_name(object.optString("order_statuses_detail_name"));
                            orderitemClass.setCustomer_order_date(dates.getDate(object.optString("customer_order_item_created_at")));
                            orderitemClass.setProduct_size(object.optString("product_size"));

                            MyOrderItemsList.add(orderitemClass);

                        }


                        OrderItemsAdapter adapter = new OrderItemsAdapter(OrderDetailsActivity.this, MyOrderItemsList);
                        MyItemListRecy.setAdapter(adapter);
                        MyItemListRecy.setHasFixedSize(true);
                        MyItemListRecy.setNestedScrollingEnabled(false);
                        MyItemListRecy.setLayoutManager(new LinearLayoutManager(OrderDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
                        adapter.notifyDataSetChanged();

                    }


                }else{


                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }

            }

            mProgressHUD.dismiss();
        }


    }


    private class UpdateStatus extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url;
        //user.get(SessionManager.KEY_CUSTOMER_ID)

        ProgressHUD mProgressHUD;
        int positionofitem;
        String Token;

        public UpdateStatus(String notification_id) {


            url= ApiLinks.notificationUpdate+notification_id;

        }


        protected void onPreExecute(){

            super.onPreExecute();
            mProgressHUD = ProgressHUD.show(OrderDetailsActivity.this,null, true);
            Token=user.get(SessionManager.KEY_CUSTOMER_TOKEN);

        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();

            try {

                jsonObject.put("status","read");

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


                String status=json.optString("status");
                String message=json.optString("message");


                if(status.equals("ok")){



                }else{


                    Toast.makeText(NotificationActivity.getNotificationActivity(),message,Toast.LENGTH_LONG).show();
                }

            }

            mProgressHUD.dismiss();
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


                        internet_status=isConnectedToInternet;

                        if(isConnectedToInternet){

                            if(NotifyStatus!=null && NotifyStatus.equals("unread")){

                                new UpdateStatus(NotifyID).execute();

                            }

                            new FetchDataofProduct(OrderItemID).execute();
                            internet.setVisibility(View.GONE);

                        }

                    }
                });
    }


    private void safelyUnsubscribe(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        safelyUnsubscribe(internetConnectivitySubscription);
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void  onBackPressed(){


            super.onBackPressed();
            finish();


    }


}
