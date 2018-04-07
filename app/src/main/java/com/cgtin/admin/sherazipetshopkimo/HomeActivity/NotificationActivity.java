package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Adapters.NotificationAdapter;
import com.cgtin.admin.sherazipetshopkimo.Adapters.OrderListAdapter;
import com.cgtin.admin.sherazipetshopkimo.BaseActivity;
import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.NotificationClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.Classes.buildCounterDrawable;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.DateClass;
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

public class NotificationActivity extends BaseActivity {


    DrawerLayout drawer;

    SessionManager session;
    HashMap<String,String> user;

    RecyclerView MyNotifications;
    LinearLayout ViewmoreAction;
    LinearLayout No_Record;

    NotificationAdapter adapter;

    String nextpathurl;


    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;





    public  static NotificationActivity notificationActivity;


    String SessionLogined="true";

    ArrayList<NotificationClass>NotificationList;


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_notification,frameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText(R.string.notifications);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);

        session=new SessionManager(getApplicationContext());
        user=session.getUserDetails();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


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


        NotificationList=new ArrayList<>();

        MyNotifications=findViewById(R.id.MyNotifications);
        ViewmoreAction=findViewById(R.id.view_more);
        No_Record=findViewById(R.id.no_record);

        ViewmoreAction.setVisibility(View.GONE);

        internet=(LinearLayout)findViewById(R.id.internet);


        notificationActivity=this;


        if(!session.isLoggedIn()){


            Intent intent = new Intent(NotificationActivity.this, LoginActivity.class);
            startActivity(intent);
            SessionLogined="true";


        }



    }


    public static NotificationActivity getNotificationActivity(){
        return notificationActivity;
    }


    private class FetchNotifications extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url;
        //user.get(SessionManager.KEY_CUSTOMER_ID)

        ProgressHUD mProgressHUD;

        public FetchNotifications(String url) {


            this.url=url;


        }
        String Token;
        protected void onPreExecute(){

            super.onPreExecute();
            mProgressHUD = ProgressHUD.show(NotificationActivity.this,null, true);
            Token=user.get(SessionManager.KEY_CUSTOMER_TOKEN);
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

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(JSONObject json) {

            if(json!=null) {


                if (json.has("error")) {

                   // session.logoutUser();
                    SessionLogined="true";
                    Intent intent = new Intent(NotificationActivity.this, LoginActivity.class);
                    startActivity(intent);


                } else {


                    String status = json.optString("status");
                    String message = json.optString("message");


                    if (status.equals("ok")) {


                        JSONObject data = json.optJSONObject("data");

                        JSONObject customer_order_items = data.optJSONObject("notification_list");

                        JSONArray OrderList = customer_order_items.optJSONArray("data");


                        if (OrderList != null && OrderList.length() > 0) {

                            No_Record.setVisibility(View.GONE);
                            DateClass dates=new DateClass();
                            for (int i = 0; i < OrderList.length(); i++) {

                                JSONObject obj = OrderList.optJSONObject(i);


                                NotificationClass orderListModel = new NotificationClass();

                                orderListModel.setNotification_id(obj.optString("notification_id"));
                                orderListModel.setNotification_status(obj.optString("notification_status"));
                                orderListModel.setNotification_created_at(dates.getDate(obj.optString("notification_created_at")));
                                orderListModel.setNotifications_detail_title(obj.optString("notifications_detail_title"));
                                orderListModel.setNotifications_detail_description(obj.optString("notifications_detail_description"));
                                orderListModel.setNotification_type(obj.optString("notification_type"));
                                orderListModel.setCustomer_order_id(obj.optString("customer_order_id"));


                                NotificationList.add(orderListModel);


                            }


                            nextpathurl = customer_order_items.optString("next_page_url");


                            if (nextpathurl.equals("null")) {

                                ViewmoreAction.setVisibility(View.GONE);
                            } else {

                                ViewmoreAction.setVisibility(View.VISIBLE);
                            }


                            if (adapter != null && adapter.getItemCount() > 0) {

                                assert adapter != null;
                                adapter.notifyDataSetChanged();

                            } else {


                                adapter = new NotificationAdapter(NotificationActivity.this, NotificationList);
                                MyNotifications.setAdapter(adapter);
                                MyNotifications.setHasFixedSize(true);
                                MyNotifications.setNestedScrollingEnabled(false);
                                MyNotifications.setLayoutManager(new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false));
                                adapter.notifyDataSetChanged();


                            }


                            ViewmoreAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {



                                    if(internet_status){

                                        new FetchNotifications(nextpathurl).execute();

                                    }else{


                                        if(!internet.isShown()) {
                                            internet.setVisibility(View.VISIBLE);
                                        }

                                    }



                                }
                            });


                        }
                        else
                        {
                            No_Record.setVisibility(View.VISIBLE);
                        }
                    } else {


                        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

                }
            }

            mProgressHUD.dismiss();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        buildCounterDrawable drawable=new buildCounterDrawable();
        getMenuInflater().inflate(R.menu.menu_new, menu);


        MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setIcon(drawable.buildCounterDrawables(Arrays.CartArray.size(),  R.drawable.ic_cart,this));



        MenuItem myOrders=menu.findItem(R.id.action_notification);
        myOrders.setVisible(false);


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
                    Intent i= new Intent(NotificationActivity.this,CartActivity.class) ;
                    startActivity(i);
                }
                else

                {

                    Toast.makeText(getApplicationContext(), R.string.Cart_is_empty, Toast.LENGTH_LONG).show();

                }

                break;
            case R.id.action_notification:


                Intent i= new Intent(NotificationActivity.this,MyOrders.class) ;
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
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        invalidateOptionsMenu();

        internetConnectivitySubscription = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override public void call(Boolean isConnectedToInternet) {


                        session=new SessionManager(getApplicationContext());
                        user=session.getUserDetails();
                        internet_status=isConnectedToInternet;

                        if(isConnectedToInternet){


                            if(session.isLoggedIn()) {


                                if (SessionLogined.equals("true")) {

                                    new FetchNotifications(ApiLinks.NotificationApi + user.get(SessionManager.KEY_CUSTOMER_ID)).execute();

                                    //initTypeface();

                                    SessionLogined = "false";
                                }
                            }
                            internet.setVisibility(View.GONE);

                        }else{

                            internet.setVisibility(View.VISIBLE);
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


}
