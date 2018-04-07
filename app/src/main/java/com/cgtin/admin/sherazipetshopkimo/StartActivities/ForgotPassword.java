package com.cgtin.admin.sherazipetshopkimo.StartActivities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.AddressActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.Brands;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.ChangeMobile;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.HomeActivity;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
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

public class ForgotPassword extends AppCompatActivity {

    EditText Mobile;
    EditText Otp;
    EditText Password;
    EditText ConfirmPassword;

    Button generate_otp;
    Button update_password;
    LinearLayout login_back;
    //TextView regerate;

    String password_string;
    String confirm_password_string;
    String Mobile_String;
    String Otp_string;

    SessionManager session;
    HashMap<String,String> UserDetails;

    JSONObject jsonObject;
    JSONObject jsonObject_update;
    TextView country_code;
    LinearLayout Country_Code_select;
    String FromActivity;
    String GeneratedMobile;

    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitle.setText(R.string.forgot_password);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);

        Mobile=findViewById(R.id.Mobile);
        Otp=findViewById(R.id.otp);
        Password=findViewById(R.id.Password);
        ConfirmPassword=findViewById(R.id.ConfirmPassword);

        login_back=findViewById(R.id.login_back);
        //regerate=findViewById(R.id.regenerate);

        country_code=findViewById(R.id.Country_Code);
        generate_otp=findViewById(R.id.otp_button);
        update_password=findViewById(R.id.update_button);

        Country_Code_select=findViewById(R.id.Country_Code_select);

        session=new SessionManager(getApplicationContext());
        internet=(LinearLayout)findViewById(R.id.internet);
        Intent from=getIntent();
        FromActivity=from.getStringExtra("from");

        Mobile.addTextChangedListener(new MyTextWatcher(Mobile));
        country_code.addTextChangedListener(new MyTextWatcher(country_code));

        generate_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(internet_status){

                    SubmittingDetails();

                }else{


                    internet.setVisibility(View.VISIBLE);

                }

            }
        });

        login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onBackPressed();


            }
        });

//        regerate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                if(internet_status){
//
//                    SubmittingDetails();
//
//                }else{
//
//
//                    internet.setVisibility(View.VISIBLE);
//
//                }
//
//
//
//
//            }
//        });

        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(internet_status){

                    SubmittingDetails_update();

                }else{


                    internet.setVisibility(View.VISIBLE);

                }


            }
        });

        Country_Code_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(getApplicationContext(), country_code);
                //inflating menu from xml resource
                popup.inflate(R.menu.option_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_india:

                                country_code.setText(R.string.india);
                                CallTextChange();
                                break;
                            case R.id.action_soudi:
                                country_code.setText(R.string.soudia);
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



    private void SubmittingDetails_update() {

        password_string=Password.getText().toString();
        Otp_string=Otp.getText().toString();
        confirm_password_string=ConfirmPassword.getText().toString();
        JSONObject jsonObject_tocken=new JSONObject();
        String encrypt=md5(Password.getText().toString());
        try {

            jsonObject_update = new JSONObject();
            jsonObject_tocken.put("device","android");
            jsonObject_tocken.put("value", FirebaseInstanceId.getInstance().getToken());
            jsonObject_update.put("username", Mobile.getText().toString());
            jsonObject_update.put("token",jsonObject_tocken);
            jsonObject_update.put("otp", Otp.getText().toString());
            jsonObject_update.put("password",encrypt );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        PerformValidation_update();

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

    private void PerformValidation_update() {


        if (!OTP()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        new UpdatePassword().execute();

    }
    private boolean OTP() {
        if (Otp.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),R.string.enter_otp,Toast.LENGTH_LONG).show();
            requestFocus(Otp);
            return false;
        }


        return true;
    }
    private boolean validatePassword() {
        if (password_string.isEmpty()) {
            Toast.makeText(getApplicationContext(),R.string.Enter_password,Toast.LENGTH_LONG).show();
            requestFocus(Password);
            return false;
        }
        else if (password_string.length()>50) {
            Toast.makeText(getApplicationContext(),R.string.Password_character_limit_exceeded,Toast.LENGTH_LONG).show();
            requestFocus(Password);
            return false;
        }

        else if(!password_string.equals(confirm_password_string))
        {
            Toast.makeText(getApplicationContext(),R.string.Password_doesnt_matches,Toast.LENGTH_LONG).show();
            requestFocus(ConfirmPassword);
            return false;
        }



        return true;
    }

    class UpdatePassword extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url = ApiLinks.updatePassword;

        ProgressHUD mProgressHUD;

        protected void onPreExecute() {


            mProgressHUD = ProgressHUD.show(ForgotPassword.this, "Loading", true);
            super.onPreExecute();
        }

        protected JSONObject doInBackground(String... arg0) {

            // JSONObject jsonObjSend = new JSONObject();

            try {


                JSONObject json = jsonParser.SendHttpPosts(url, "POST", jsonObject_update, null,null);
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


                String status=json.optString("status");
                String message=json.optString("message");
                String token=json.optString("token");

                if(status.equals("ok")){

                    JSONObject jsonObject=json.optJSONObject("data");

                    String userID=jsonObject.optString("userID");
                    String mobileno=jsonObject.optString("mobileno");
                    JSONObject user_data=jsonObject.optJSONObject("user_data");
                    String email=user_data.optString("user_email");

                    session.createLoginSession(userID,mobileno,token,mobileno,email,null);

                    UserDetails=session.getUserDetails();

                    if(FromActivity!=null) {
                        if (LoginActivity.getInstance() != null) {

                            LoginActivity.getInstance().finish();
                        }

                        if (AddressActivity.getIntance() != null) {
                            AddressActivity.Resumeneeded = "true";
                        }

                        finish();
                    }
                    else
                    {
                        Intent toHome=new Intent(ForgotPassword.this, HomeActivity.class);
                        startActivity(toHome);
                        finish();
                    }



                }else{


                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }

            }
            mProgressHUD.dismiss();
        }
    }



    private void SubmittingDetails() {


        try {

            jsonObject = new JSONObject();
            jsonObject.put("username", Mobile.getText().toString());
            jsonObject.put("country_code", country_code.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        PerformValidation();

    }

    private void PerformValidation() {
        Mobile_String=Mobile.getText().toString();
        if (!Mobile()) {
            return;
        }

        new OtpG().execute();

    }

    private boolean Mobile() {



        if (Mobile_String.isEmpty()) {

            Toast.makeText(getApplicationContext(),R.string.enter_valid_mobile,Toast.LENGTH_LONG).show();
            requestFocus(Mobile);
            return false;
        }

        else if (!validateMob(Mobile.getText().toString())) {

            Toast.makeText(getApplicationContext(),R.string.Special_characters_not_allowed,Toast.LENGTH_LONG).show();
            requestFocus(Mobile);
            return false;
        }



        return true;
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

                case R.id.Mobile:
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

            if(!GeneratedMobile.equals(country_code.getText().toString()+Mobile.getText().toString())){


                generate_otp.setText(getString(R.string.generate_otp));

            }else

            {


                generate_otp.setText(getString(R.string.regenerate_otp));


            }
        }else{

            generate_otp.setText(getString(R.string.generate_otp));



        }

    }

    class OtpG extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url = ApiLinks.forgotPassword;

        ProgressHUD mProgressHUD;

        protected void onPreExecute() {


            mProgressHUD = ProgressHUD.show(ForgotPassword.this, "Loading", true);
            super.onPreExecute();
        }

        protected JSONObject doInBackground(String... arg0) {

            // JSONObject jsonObjSend = new JSONObject();

            try {


                JSONObject json = jsonParser.SendHttpPosts(url, "POST", jsonObject, null,null);
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


                String status=json.optString("status");
                String message=json.optString("message");
                String token=json.optString("token");

                if(status.equals("ok")){

                    JSONObject jsonObject=json.optJSONObject("data");


                    GeneratedMobile=country_code.getText().toString()+Mobile.getText().toString();;

                    generate_otp.setText(getString(R.string.regenerate_otp));
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();


                }else{


                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }

            }
            mProgressHUD.dismiss();



        }

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

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void  onBackPressed(){



        super.onBackPressed();
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
                        UserDetails=session.getUserDetails();
                        internet_status=isConnectedToInternet;

                        if(isConnectedToInternet){
                            //snackbar1.dismiss();
                            internet.setVisibility(View.GONE);

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
