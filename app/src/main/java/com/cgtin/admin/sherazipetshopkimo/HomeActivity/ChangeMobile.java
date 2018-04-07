package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.BaseActivity;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.cgtin.admin.sherazipetshopkimo.StartActivities.LoginActivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangeMobile extends BaseActivity {


    SessionManager session;
    HashMap<String,String> UserDetails;

    ImageView backArrow;
    //ImageView regenerate;

    EditText MobileNumber;
    EditText Otp;
    EditText CurrentPassword;

    LinearLayout GenerateOtp;
    LinearLayout SubmitNewMobile;

    LinearLayout otpLayout;
    LinearLayout CountryCodeSelect;


    String UserMobile;
    String UserOtp;
    String UserPassword;

    TextView country_code;



    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;

    String GeneratedMobile;
    TextView GNText;


    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mobile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText(R.string.change_mobile);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);



        session=new SessionManager(getApplicationContext());
        UserDetails=session.getUserDetails();
        MobileNumber=findViewById(R.id.MobileNumber);
        Otp=findViewById(R.id.otp);
        CurrentPassword=findViewById(R.id.Password);
        GenerateOtp=findViewById(R.id.Otp_GenerateButton);
        GNText=findViewById(R.id.GNText);
        SubmitNewMobile=findViewById(R.id.UpdateMobile);
        //regenerate=(ImageView) findViewById(R.id.regenerate);
        country_code=findViewById(R.id.Country_Code);
        CountryCodeSelect=findViewById(R.id.CountryCodeSelect);
        internet=(LinearLayout)findViewById(R.id.internet);

        otpLayout=findViewById(R.id.otpLayout);

        GeneratedMobile=null;

        MobileNumber.addTextChangedListener(new MyTextWatcher(MobileNumber));
        country_code.addTextChangedListener(new MyTextWatcher(country_code));

        GenerateOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    validateMobile();


            }
        });


        SubmitNewMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    validateOtpAndMobile();


            }
        });



        CountryCodeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(getApplicationContext(), CountryCodeSelect);
                //inflating menu from xml resource
                popup.inflate(R.menu.option_menu);
                final Typeface face = Typeface.createFromAsset(getAssets(),
                        "fonts/Montserrat-Light.otf");

                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_india:

                                country_code.setText(R.string.india);
                                country_code.setTextSize(14);

                                country_code.setTypeface(face);

                                CallTextChange();

                                break;
                            case R.id.action_soudi:
                                country_code.setText(R.string.soudia);
                                country_code.setTextSize(14);

                                country_code.setTypeface(face);

                                CallTextChange();

                                break;

                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();



            }
        });




    }


    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            CallTextChange();
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.MobileNumber:
                    CallTextChange();
                    break;
                case R.id.Country_Code:
                    CallTextChange();
                    break;
            }
        }
    }


    private void CallTextChange() {

        if(GeneratedMobile!=null){

            if(!GeneratedMobile.equals(country_code.getText().toString()+MobileNumber.getText().toString())){


                GNText.setText(getString(R.string.generate_otp));

            }else

            {


                GNText.setText(getString(R.string.regenerate_otp));


            }
        }else{

            GNText.setText(getString(R.string.generate_otp));



        }

    }

    private void validateOtpAndMobile() {

        UserOtp=Otp.getText().toString();
        UserPassword=CurrentPassword.getText().toString();

        if(!ValidateOtp()){

            return;
        }
        if(!ValidatePassword()){

            return;
        }


        if(internet_status){

            new UpdateMobile().execute();

        }else{


            if(!internet.isShown()) {
                internet.setVisibility(View.VISIBLE);
            }

        }






    }

    private boolean ValidatePassword() {
        if (UserPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(),R.string.Enter_password,Toast.LENGTH_LONG).show();
            requestFocus(CurrentPassword);
            return false;
        }
        else if (UserPassword.length()>50) {
            Toast.makeText(getApplicationContext(),R.string.Password_character_limit_exceeded,Toast.LENGTH_LONG).show();
            requestFocus(CurrentPassword);
            return false;
        }



        return true;
    }

    private boolean ValidateOtp() {
        if (UserOtp.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.OTP_is_required,Toast.LENGTH_LONG).show();
            requestFocus(Otp);
            return false;
        }


        return true;
    }

    private void validateMobile() {

        UserMobile=MobileNumber.getText().toString();

        if(!ValidateMobile()){

            return;
        }

        if(internet_status){

            new GenerateOtpS().execute();

        }else{


            if(!internet.isShown()) {
                internet.setVisibility(View.VISIBLE);
            }

        }



    }

    private boolean ValidateMobile() {


        if (UserMobile.isEmpty()) {

            Toast.makeText(getApplicationContext(),R.string.enter_valid_mobile,Toast.LENGTH_LONG).show();
            requestFocus(MobileNumber);
            return false;
        }else if (!UserMobile.isEmpty()&&!CheckMob()) {

            Toast.makeText(getApplicationContext(),R.string.enter_valid_mobile,Toast.LENGTH_LONG).show();
            requestFocus(MobileNumber);
            return false;
        }

        else if (!validateMob(UserMobile)) {

            Toast.makeText(getApplicationContext(),R.string.Special_characters_not_allowed,Toast.LENGTH_LONG).show();
            requestFocus(MobileNumber);
            return false;
        }



        return true;

    }


    private boolean CheckMob()
    {
        if (country_code.getText().toString().equals("+91") && UserMobile.length()==10) {

            return true;
        }
        else if (country_code.getText().toString().equals("+966") && UserMobile.length()==9) {

            return true;
        }
        else
        {
            return false;
        }
    }


    public static final Pattern VALID_Name_REGEX =
            Pattern.compile("^[0-9\\s]+$", Pattern.CASE_INSENSITIVE);


    public static boolean validateMob(String emailStr) {
        Matcher matcher = VALID_Name_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    protected class GenerateOtpS extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url =ApiLinks.otpGenerateForNewMobileNo;

        ProgressHUD mProgressHUD;
        String Token;

        protected void onPreExecute() {


            mProgressHUD = ProgressHUD.show(ChangeMobile.this, null, true);
            super.onPreExecute();

            Token=UserDetails.get(SessionManager.KEY_CUSTOMER_TOKEN);
        }

        protected JSONObject doInBackground(String... arg0) {

            JSONObject jsonObjSend = new JSONObject();



            try {

                jsonObjSend.put("mobileno",UserMobile);
                jsonObjSend.put("user_id",UserDetails.get(SessionManager.KEY_CUSTOMER_ID));
                jsonObjSend.put("country_code", country_code.getText().toString());


                JSONObject json = jsonParser.SendHttpPosts(url, "POST", jsonObjSend, null,Token);
                if (json != null) {
                    return json;
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
                    Intent intent = new Intent(ChangeMobile.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                } else {


                    String status = json.optString("status");
                    String message = json.optString("message");


                    if (status.equals("ok")) {


                        otpLayout.setVisibility(View.VISIBLE);



                        GeneratedMobile=country_code.getText().toString()+UserMobile;

                        GNText.setText(getString(R.string.regenerate_otp));


                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


                    } else {


                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        GNText.setText(getString(R.string.generate_otp));
                    }

                }
            }
            mProgressHUD.dismiss();



        }

    }

    protected class UpdateMobile extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url =ApiLinks.updateMobile;

        ProgressHUD mProgressHUD;

        String Token;

        protected void onPreExecute() {


            mProgressHUD = ProgressHUD.show(ChangeMobile.this, null, true);
            super.onPreExecute();

            Token=UserDetails.get(SessionManager.KEY_CUSTOMER_TOKEN);
        }

        protected JSONObject doInBackground(String... arg0) {

            JSONObject jsonObjSend = new JSONObject();


            String Encrypt=md5(UserPassword);



            try {

                jsonObjSend.put("mobileno",UserMobile);
                jsonObjSend.put("user_id",UserDetails.get(SessionManager.KEY_CUSTOMER_ID));
                jsonObjSend.put("password",Encrypt);
                jsonObjSend.put("otp",UserOtp);


                JSONObject json = jsonParser.SendHttpPosts(url, "POST", jsonObjSend, null,Token);
                if (json != null) {
                    return json;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint({"ApplySharedPref", "CommitPrefEdits"})
        @Override
        protected void onPostExecute(JSONObject json) {





            if(json!=null) {


                if (json.has("error")) {

                    session.logoutUser();
                    Intent intent = new Intent(ChangeMobile.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                } else {

                    String status = json.optString("status");
                    String message = json.optString("message");

                    if (status.equals("ok")) {

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


                        SessionManager.editor = SessionManager.pref.edit();
                        SessionManager.editor.putString(SessionManager.KEY_CUSTOMER_MOBILE, UserMobile);
                        SessionManager.editor.apply();
                        SessionManager.editor.commit();

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        finish();


                    } else {


                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

                }
            }
            mProgressHUD.dismiss();



        }

    }



    private static final String md5(final String password) {
        try {

            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
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
                        UserDetails=session.getUserDetails();
                        internet_status=isConnectedToInternet;

                        if(isConnectedToInternet){
                            //snackbar1.dismiss();
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
      //  super.onBackPressed();
        finish();


    }



}
