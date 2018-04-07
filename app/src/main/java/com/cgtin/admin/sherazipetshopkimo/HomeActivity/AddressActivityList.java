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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Adapters.AddressAdapter;
import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.AddressClass;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.cgtin.admin.sherazipetshopkimo.StartActivities.LoginActivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks.AddressFrom;
import static com.cgtin.admin.sherazipetshopkimo.HomeActivity.AddressActivity.Resumeneeded;

public class AddressActivityList extends AppCompatActivity {
    RecyclerView PetFoodReqRecycler;

    HashMap<String,String> user;
    SessionManager session;
    public  static AddressActivityList addressActivityList;

    public static boolean internet_status;
    private Subscription internetConnectivitySubscription;
    public static LinearLayout internet;
    LinearLayout No_Record;
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        if(AddressFrom== MyAccount.getMyAccount())
        {

            mTitle.setText(R.string.my_address);

        }
        else
        {

            mTitle.setText(R.string.select_address);

        }

        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        internet=(LinearLayout)findViewById(R.id.internet);
        addressActivityList=this;

        No_Record=findViewById(R.id.no_record);

        session=new SessionManager(getApplicationContext());
        user=session.getUserDetails();









    }
    private class Address_List extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.address_get+user.get(SessionManager.KEY_CUSTOMER_ID);
        //user.get(SessionManager.KEY_CUSTOMER_ID)

        ProgressHUD mProgressHUD;
        String Token;
        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(AddressActivityList.this,null, true);
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

                if (json.has("error")) {

                    session.logoutUser();
                    Intent intent = new Intent(AddressActivityList.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                }
                else
                {

                    String status=json.optString("status");
                    String message=json.optString("message");
                    CommonClass.AddressListArray.clear();

                    if(status.equals("ok")){

                        JSONArray jsonArray_data=json.optJSONArray("data");

                        if (jsonArray_data != null && jsonArray_data.length() > 0) {

                            No_Record.setVisibility(View.GONE);
                            for (int i = 0; i < jsonArray_data.length(); i++) {

                                JSONObject obj = jsonArray_data.optJSONObject(i);

                                String customer_address_id = obj.optString("customer_address_id");
                                String customer_address_user_id = obj.optString("customer_address_user_id");
                                String customer_address_name = obj.optString("customer_address_name");
                                String customer_address_address = obj.optString("customer_address_address");
                                String customer_address_pincode = obj.optString("customer_address_pincode");
                                String customer_address_contact = obj.optString("customer_address_contact");
                                String customer_address_default = obj.optString("customer_address_default");


                                AddressClass homeclass = new AddressClass();

                                homeclass.setCustomer_address_id(customer_address_id);
                                homeclass.setCustomer_address_user_id(customer_address_user_id);
                                homeclass.setCustomer_address_name(customer_address_name);
                                homeclass.setCustomer_address_address(customer_address_address);
                                homeclass.setCustomer_address_pincode(customer_address_pincode);
                                homeclass.setCustomer_address_contact(customer_address_contact);
                                homeclass.setCustomer_address_default(customer_address_default);



                                CommonClass.AddressListArray.add(homeclass);


                            }




                            boolean fount=false;
                            AddressClass defaultid = null;
                            if(CommonClass.Address_Id_Show!=null) {
                                int i=0;
                                for (AddressClass Defid : CommonClass.AddressListArray) {
                                    defaultid = Defid;
                                    if (Defid.getCustomer_address_id().equals(CommonClass.Address_Id_Show)) {
                                        fount = true;
                                        break;
                                    }i++;


                                }


                                if(fount)
                                {
                                    AddressClass Defid=CommonClass.AddressListArray.get(i);
                                    {

                                        CommonClass.Address_Id_Show=Defid.getCustomer_address_id();
                                    }

                                }
                                else
                                {
                                    boolean deffound_id=false;
                                    for(AddressClass Defid : CommonClass.AddressListArray)
                                    {
                                        if(Defid.getCustomer_address_default().equals("1"))
                                        {
                                            deffound_id=true;
                                            CommonClass.Address_Id_Show=Defid.getCustomer_address_id();
                                            break;
                                        }
                                    }
                                    if(!deffound_id)
                                    {

                                        CommonClass.Address_Id_Show=CommonClass.AddressListArray.get(0).getCustomer_address_id();
                                    }

                                }
                            }
                            else
                            {
                                boolean deffound=false;

                                for(AddressClass Defid : CommonClass.AddressListArray)
                                {
                                    if(Defid.getCustomer_address_default().equals("1"))
                                    {
                                        deffound=true;
                                        CommonClass.Address_Id_Show=Defid.getCustomer_address_id();
                                        break;
                                    }
                                }

                                if(!deffound)
                                {

                                    CommonClass.Address_Id_Show=CommonClass.AddressListArray.get(0).getCustomer_address_id();
                                }

                            }




                            PetFoodReqRecycler = (RecyclerView) findViewById(R.id.PostReq_Recy);
                            AddressAdapter postedReq_adapter = new AddressAdapter(AddressActivityList.this, CommonClass.AddressListArray);
                            PetFoodReqRecycler.setAdapter(postedReq_adapter);
                            PetFoodReqRecycler.setHasFixedSize(true);
                            //PetFoodReqRecycler.addItemDecoration(new OverlapDecoration());
                            PetFoodReqRecycler.setLayoutManager(new LinearLayoutManager(AddressActivityList.this, LinearLayoutManager.VERTICAL, false));
                            postedReq_adapter.notifyDataSetChanged();


                        }
                        else
                        {
                            No_Record.setVisibility(View.VISIBLE);
                        }


                    }else{


                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    }
                }




            }

            mProgressHUD.dismiss();
        }


    }

    public void addressdel(String customer_address_id)
    {

        if(internet_status){

            new address_delete(customer_address_id).execute();

        }else{

            internet.setVisibility(View.VISIBLE);
        }
    }


    private class address_delete extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url;
        //user.get(SessionManager.KEY_CUSTOMER_ID)

        ProgressHUD mProgressHUD;

        String de_id;
        String Token;

        public address_delete(String del_id) {
            de_id=del_id;
        }

        protected void onPreExecute(){

             url= ApiLinks.address_delete+de_id;


            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(AddressActivityList.this,null, true);
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


                if (json.has("error")) {

                    session.logoutUser();
                    Intent intent = new Intent(AddressActivityList.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                }
                else
                {


                    String status = json.optString("status");
                    String message = json.optString("message");


                    if (status.equals("ok")) {

                        Resumeneeded="true";
                        Intent i = new Intent(getApplicationContext(), AddressActivityList.class);
                        startActivity(i);

                        finish();


                    } else {


                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

                }
            }

            mProgressHUD.dismiss();
        }


    }


    public void defaddress(String customer_address_id)
    {

        if(internet_status){

            new SetDefault(customer_address_id).execute();

        }else{

            internet.setVisibility(View.VISIBLE);
        }
    }

    private class SetDefault extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url;
        //user.get(SessionManager.KEY_CUSTOMER_ID)

        ProgressHUD mProgressHUD;

        String add_ids;
        String Token;

        public SetDefault(String add_id) {
            add_ids=add_id;
        }

        protected void onPreExecute(){

            url= ApiLinks.setAddressToDefault;


            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(AddressActivityList.this,null, true);
            Token=user.get(SessionManager.KEY_CUSTOMER_TOKEN);


        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();

            JSONObject jsonObject_tocken=new JSONObject();
            HashMap<String,String> params=new HashMap<>();


            try {

                jsonObject.put("user_id",user.get(SessionManager.KEY_CUSTOMER_ID));
                jsonObject.put("customer_address_id",add_ids);

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

                    session.logoutUser();
                    Intent intent = new Intent(AddressActivityList.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                }
                else
                {


                    String status = json.optString("status");
                    String message = json.optString("message");


                    if (status.equals("ok")) {



                        CommonClass.Address_Id_Show=add_ids;
                        Resumeneeded="true";
                        Intent i = new Intent(getApplicationContext(), AddressActivityList.class);
                        startActivity(i);

                        finish();


                    } else {


                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

                }
            }

            mProgressHUD.dismiss();
        }


    }


     public static AddressActivityList getInstance()
     {
         return addressActivityList;
     }




    private void safelyUnsubscribe(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.all_address, menu);



        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.address_in_menu:

                Intent i = new Intent(getApplicationContext(), AddressAddActivity.class);
                startActivity(i);


            default:
                return super.onOptionsItemSelected(item);


        }


    }

    @Override
    public void onBackPressed() {

//        if(AddressActivity.getIntance()!=null)
//        {
//
//            AddressActivity.getIntance().finish();
//            Intent i= new Intent(AddressActivityList.this,AddressActivity.class) ;
//            startActivity(i);
//            finish();
//
//        }

        Resumeneeded="true";
        finish();


    }

    @Override
    protected void onResume() {
        super.onResume();
       // invalidateOptionsMenu();


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
                            new Address_List().execute();
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
