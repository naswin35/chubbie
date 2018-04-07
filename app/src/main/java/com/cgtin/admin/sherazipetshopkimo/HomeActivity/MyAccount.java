package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.BaseActivity;
import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.Classes.buildCounterDrawable;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.cgtin.admin.sherazipetshopkimo.StartActivities.LoginActivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import org.json.JSONObject;

import java.util.HashMap;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager.*;


import static com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks.AddressFrom;

public class MyAccount extends BaseActivity {

    DrawerLayout drawer;


    SessionManager session;
    HashMap<String,String> UserDetails;

    LinearLayout ChangePassword;
    LinearLayout ChangeMobile;
    LinearLayout ChangeEmailID;
    LinearLayout MyAddress;
    LinearLayout ChangeLanguage;

    ImageView home_icon;

    TextView UserName;
    TextView UserMobile;

    public static MyAccount myAccount;


    ImageView Women,Man;
    LinearLayout Middle_image;
    String Cliked="male";
    EditText EditName;


    RelativeLayout editname;
    RelativeLayout edittextlayout;


    ImageView success;
    ImageView close;

    TextView GenderSelectText;


    String Gender;


    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_my_account,frameLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);

        mTitle.setText(R.string.my_account);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);

        session=new SessionManager(getApplicationContext());
        UserDetails=session.getUserDetails();




        if(!session.isLoggedIn()) {
            Intent intent = new Intent(MyAccount.this, LoginActivity.class);
            intent.putExtra("from","Account");
            startActivity(intent);
        }




        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


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



        Women= (ImageView) findViewById(R.id.womenImage);
        Man= (ImageView) findViewById(R.id.manImage);
        Middle_image= (LinearLayout) findViewById(R.id.Middle_Layout);



        ChangePassword=findViewById(R.id.ChangePassword);
        ChangeMobile=findViewById(R.id.ChangeMobileNumber);
        ChangeEmailID=findViewById(R.id.ChangeEmailId);
        MyAddress=findViewById(R.id.MyAddress);
        ChangeLanguage=findViewById(R.id.ChangeLanguage);
        UserMobile=findViewById(R.id.UserMobile);
        UserName=findViewById(R.id.UserName);
        editname=findViewById(R.id.editname);
        EditName=findViewById(R.id.EditName);
        success=findViewById(R.id.success);
        close=findViewById(R.id.close);
        GenderSelectText=findViewById(R.id.GenderSelectText);
        internet=(LinearLayout)findViewById(R.id.internet);


        editname.setVisibility(View.VISIBLE);
        edittextlayout=findViewById(R.id.edittextlayout);


        myAccount=this;





        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toChangeP=new Intent(MyAccount.this,ChangePassword.class);
                startActivity(toChangeP);

            }
        });

        ChangeEmailID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent toChangeE=new Intent(MyAccount.this,ChangeEmail.class);
                startActivity(toChangeE);

            }
        });

        ChangeMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent toChangeM=new Intent(MyAccount.this,ChangeMobile.class);
                startActivity(toChangeM);

            }
        });

        MyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent toChangeM=new Intent(MyAccount.this,AddressActivityList.class);
                AddressFrom=MyAccount.getMyAccount();
                startActivity(toChangeM);

            }
        });

        ChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toChangeLang=new Intent(MyAccount.this,ChangeLanguage.class);
                startActivity(toChangeLang);

            }
        });


        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editname.setVisibility(View.GONE);
                String name=UserName.getText().toString();
                EditName.setText(name);
                EditName.setSelection(EditName.getText().toString().length());
                edittextlayout.setVisibility(View.VISIBLE);
            }
        });


        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(EditName.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(), R.string.Please_enter_your_name,Toast.LENGTH_SHORT).show();


                }else{

                    UpdateUsername();

                }
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                edittextlayout.setVisibility(View.GONE);
                editname.setVisibility(View.VISIBLE);

            }
        });



        UpdateName();





        Middle_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cliked="center";

                Women.setVisibility(View.VISIBLE);
                Man.setVisibility(View.VISIBLE);

                if(Global.LANG.equals("ar")){


                    Animation img1_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                            R.anim.img4_animation);
                    img1_Anim.setAnimationListener(animationListener);
                    Women.startAnimation(img1_Anim);

                    Animation img2_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                            R.anim.img3_animation);
                    img2_Anim.setAnimationListener(animationListener);
                    Man.startAnimation(img2_Anim);

                }else {

                    Animation img1_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                            R.anim.img3_animation);
                    img1_Anim.setAnimationListener(animationListener);
                    Women.startAnimation(img1_Anim);

                    Animation img2_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                            R.anim.img4_animation);
                    img2_Anim.setAnimationListener(animationListener);
                    Man.startAnimation(img2_Anim);

                }
                Middle_image.setVisibility(View.GONE);


                GenderSelectText.setVisibility(View.VISIBLE);


            }
        });

        Man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cliked="male";



                if(internet_status){

                    new  UpdateGender(Cliked).execute();

                }else{
                    if(!internet.isShown()) {
                        internet.setVisibility(View.VISIBLE);
                    }

                }






            }
        });

        Women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Cliked="female";


                if(internet_status){

                    new  UpdateGender(Cliked).execute();

                }else{
//
                    if(!internet.isShown()) {
                        internet.setVisibility(View.VISIBLE);
                    }

                }




            }
        });


    }

    private void UpdateUsername() {


        String Name=EditName.getText().toString();


        if(internet_status){

            new UpdateProfile(Name).execute();

        }else{


            if(!internet.isShown()) {
                internet.setVisibility(View.VISIBLE);
            }

        }



    }


    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {



        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {


            if(Cliked.equals("male")){

                Man.setVisibility(View.VISIBLE);
                Women.setVisibility(View.GONE);

                Man.setClickable(false);
                Women.setClickable(false);
            }
            else if(Cliked.equals("female")){

                Man.setVisibility(View.GONE);
                Women.setVisibility(View.VISIBLE);

                Man.setClickable(false);
                Women.setClickable(false);

            }
            else  if(Cliked.equals("center")){

                Man.setClickable(true);
                Women.setClickable(true);
            }

        }
    };



    public static  MyAccount getMyAccount(){

        return myAccount;
    }


    @Override
    protected void onResume() {
        super.onResume();

        invalidateOptionsMenu();
        UpdateName();

        internetConnectivitySubscription = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override public void call(Boolean isConnectedToInternet) {


                        session=new SessionManager(getApplicationContext());
                        UserDetails=session.getUserDetails();
                        internet_status=isConnectedToInternet;

                        if(isConnectedToInternet){

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




    private void UpdateName() {


        session=new SessionManager(getApplicationContext());
        UserDetails=session.getUserDetails();


        UserMobile.setText(UserDetails.get(SessionManager.KEY_CUSTOMER_MOBILE));
        initTypeface();
        UserName.setText(UserDetails.get(SessionManager.KEY_CUSTOMER_NAME));

        Gender=UserDetails.get(KEY_GENDER);

        if(Gender!=null && !Gender.equals("null")){


            if(Gender.equals("male")){

                Cliked="male";

                Man.bringToFront();

                if(Global.LANG.equals("ar")){

                    Animation img1_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                            R.anim.img2_animation);
                    img1_Anim.setAnimationListener(animationListener);
                    Women.startAnimation(img1_Anim);

                    Animation img2_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                            R.anim.img1_animation);
                    img2_Anim.setAnimationListener(animationListener);
                    Man.startAnimation(img2_Anim);

                }else{

                    Animation img1_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                            R.anim.img1_animation);
                    img1_Anim.setAnimationListener(animationListener);
                    Women.startAnimation(img1_Anim);

                    Animation img2_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                            R.anim.img2_animation);
                    img2_Anim.setAnimationListener(animationListener);
                    Man.startAnimation(img2_Anim);
                }


                Middle_image.setVisibility(View.VISIBLE);

            }else if(Gender.equals("female")){


                Cliked="female";

                Women.bringToFront();

                if(Global.LANG.equals("ar")){

                    Animation img1_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                            R.anim.img2_animation);
                    img1_Anim.setAnimationListener(animationListener);
                    Women.startAnimation(img1_Anim);


                    Animation img2_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                            R.anim.img1_animation);
                    img2_Anim.setAnimationListener(animationListener);
                    Man.startAnimation(img2_Anim);

                }else{

                    Animation img1_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                            R.anim.img1_animation);
                    img1_Anim.setAnimationListener(animationListener);
                    Women.startAnimation(img1_Anim);


                    Animation img2_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                            R.anim.img2_animation);
                    img2_Anim.setAnimationListener(animationListener);
                    Man.startAnimation(img2_Anim);

                }


                Middle_image.setVisibility(View.VISIBLE);

            }


        }
        else{


            GenderSelectText.setVisibility(View.VISIBLE);

        }


    }
    private class UpdateGender extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.updateCustomerProfile+UserDetails.get(SessionManager.KEY_CUSTOMER_ID);

        ProgressHUD mProgressHUD;
        String genderselected;
        String Token;

        public UpdateGender(String cliked) {

            genderselected=cliked;

        }

        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(MyAccount.this,null, true);
            Token=UserDetails.get(SessionManager.KEY_CUSTOMER_TOKEN);


        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();

            HashMap<String,String> params=new HashMap<>();


            try {

                jsonObject.put("gender",Cliked);

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

                    //session.logoutUser();
                    Intent intent = new Intent(MyAccount.this, LoginActivity.class);
                    intent.putExtra("from","Account");
                    startActivity(intent);


                } else {


                    String status = json.optString("status");
                    String message = json.optString("message");


                    if (status.equals("ok")) {


                        if (genderselected.equals("male")) {


                            Man.bringToFront();


                            if (Global.LANG.equals("ar")) {

                                Animation img1_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                                        R.anim.img2_animation);
                                img1_Anim.setAnimationListener(animationListener);
                                Women.startAnimation(img1_Anim);

                                Animation img2_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                                        R.anim.img1_animation);
                                img2_Anim.setAnimationListener(animationListener);
                                Man.startAnimation(img2_Anim);

                            } else {

                                Animation img1_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                                        R.anim.img1_animation);
                                img1_Anim.setAnimationListener(animationListener);
                                Women.startAnimation(img1_Anim);

                                Animation img2_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                                        R.anim.img2_animation);
                                img2_Anim.setAnimationListener(animationListener);
                                Man.startAnimation(img2_Anim);
                            }


                            Middle_image.setVisibility(View.VISIBLE);

                            GenderSelectText.setVisibility(View.GONE);

                        } else if (genderselected.equals("female")) {


                            Women.bringToFront();
                            if (Global.LANG.equals("ar")) {

                                Animation img1_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                                        R.anim.img2_animation);
                                img1_Anim.setAnimationListener(animationListener);
                                Women.startAnimation(img1_Anim);


                                Animation img2_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                                        R.anim.img1_animation);
                                img2_Anim.setAnimationListener(animationListener);
                                Man.startAnimation(img2_Anim);

                            } else {

                                Animation img1_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                                        R.anim.img1_animation);
                                img1_Anim.setAnimationListener(animationListener);
                                Women.startAnimation(img1_Anim);


                                Animation img2_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                                        R.anim.img2_animation);
                                img2_Anim.setAnimationListener(animationListener);
                                Man.startAnimation(img2_Anim);

                            }

                            Middle_image.setVisibility(View.VISIBLE);

                            GenderSelectText.setVisibility(View.GONE);


                        }


                        editor = pref.edit();
                        editor.putString(KEY_GENDER, genderselected);
                        editor.apply();
                        editor.commit();


                        initTypeface();

                    } else {


                        if (!genderselected.equals("center")) {


                            GenderSelectText.setVisibility(View.VISIBLE);

                            Cliked = "center";

                            Women.setVisibility(View.VISIBLE);
                            Man.setVisibility(View.VISIBLE);

                            if (Global.LANG.equals("ar")) {


                                Animation img1_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                                        R.anim.img4_animation);
                                img1_Anim.setAnimationListener(animationListener);
                                Women.startAnimation(img1_Anim);

                                Animation img2_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                                        R.anim.img3_animation);
                                img2_Anim.setAnimationListener(animationListener);
                                Man.startAnimation(img2_Anim);

                            } else {

                                Animation img1_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                                        R.anim.img4_animation);
                                img1_Anim.setAnimationListener(animationListener);
                                Women.startAnimation(img1_Anim);

                                Animation img2_Anim = AnimationUtils.loadAnimation(MyAccount.this,
                                        R.anim.img3_animation);
                                img2_Anim.setAnimationListener(animationListener);
                                Man.startAnimation(img2_Anim);

                            }
                            Middle_image.setVisibility(View.GONE);

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }

            mProgressHUD.dismiss();
        }


    }

    private class UpdateProfile extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.updateCustomerProfile+UserDetails.get(SessionManager.KEY_CUSTOMER_ID);

        ProgressHUD mProgressHUD;
        String Username;
        String Token;

        public UpdateProfile(String name) {

            Username=name;
        }


        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(MyAccount.this,null, true);

            Token=UserDetails.get(SessionManager.KEY_CUSTOMER_TOKEN);

        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();

            HashMap<String,String> params=new HashMap<>();


            try {

                jsonObject.put("name",Username);

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
                    Intent intent = new Intent(MyAccount.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                } else {


                    String status = json.optString("status");
                    String message = json.optString("message");


                    if (status.equals("ok")) {

                        editor = pref.edit();
                        editor.putString(KEY_CUSTOMER_NAME, Username);
                        editor.apply();
                        editor.commit();

                        editname.setVisibility(View.VISIBLE);
                        UserName.setText(Username);
                        edittextlayout.setVisibility(View.GONE);

                        initTypeface();
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
                    Intent i= new Intent(MyAccount.this,CartActivity.class) ;
                    startActivity(i);
                }
                else

                {

                    Toast.makeText(getApplicationContext(), R.string.Cart_is_empty, Toast.LENGTH_LONG).show();

                }

                break;
            case R.id.action_notification:


                Intent i= new Intent(MyAccount.this,MyOrders.class) ;
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



}
