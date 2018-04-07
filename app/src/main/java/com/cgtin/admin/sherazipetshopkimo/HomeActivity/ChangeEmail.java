package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.regex.Pattern;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangeEmail extends BaseActivity {


    SessionManager session;
    HashMap<String,String> UserDetails;

    ImageView backArrow;


    EditText EmailId;
    EditText Password;

    String EmailIds;
    String Passwords;

    LinearLayout UpdateEmailID;




    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;




    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent getData=getIntent();
        mTitle.setText(R.string.Change_email);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);


        session=new SessionManager(getApplicationContext());
        UserDetails=session.getUserDetails();


        EmailId=findViewById(R.id.EmailID);
        Password=findViewById(R.id.Password);

        UpdateEmailID=findViewById(R.id.UpdateEmail);
        internet=(LinearLayout)findViewById(R.id.internet);

        UpdateEmailID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    ValidateFields();


            }
        });


    }

    private void ValidateFields() {

        EmailIds=EmailId.getText().toString();
        Passwords=Password.getText().toString();


        if(!validateUsername()){
            return;
        }
        if(!validatePassword()){

            return;
        }



        if(internet_status){

            new UpdateEmail().execute();


        }else{


            if(!internet.isShown()) {
                internet.setVisibility(View.VISIBLE);
            }

        }




    }

    private boolean validateUsername() {



        if (EmailIds.isEmpty()) {

            Toast.makeText(getApplicationContext(), R.string.Enter_email,Toast.LENGTH_LONG).show();
            requestFocus(EmailId);
            return false;
        }

        else if (!isValidEmail(EmailIds)) {

            Toast.makeText(getApplicationContext(),R.string.Please_enter_valid_email,Toast.LENGTH_LONG).show();
            requestFocus(EmailId);
            return false;
        }



        return true;
    }

    private boolean validatePassword() {
        if (Passwords.isEmpty()) {
            Toast.makeText(getApplicationContext(),R.string.Enter_password,Toast.LENGTH_LONG).show();
            requestFocus(Password);
            return false;
        }
        else if (Passwords.length()>50) {
            Toast.makeText(getApplicationContext(),R.string.Password_character_limit_exceeded,Toast.LENGTH_LONG).show();
            requestFocus(Password);
            return false;
        }



        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static final Pattern VALID_Name_REGEX =
            Pattern.compile("^[0-9\\s]+$", Pattern.CASE_INSENSITIVE);


   /* public static boolean validateMob(String emailStr) {
        Matcher matcher = VALID_Name_REGEX.matcher(emailStr);
        return matcher.find();
    }*/

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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


    private class UpdateEmail extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url=ApiLinks.updateEmail;


        ProgressHUD mProgressHUD;

        String Token;

        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(ChangeEmail.this,null, true);

            Token=UserDetails.get(SessionManager.KEY_CUSTOMER_TOKEN);

        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();

            HashMap<String,String> params=new HashMap<>();


            String encrypt=md5(Passwords);


            try {
                jsonObject.put("user_id",UserDetails.get(SessionManager.KEY_CUSTOMER_ID));
                jsonObject.put("password", encrypt);
                jsonObject.put("email", EmailIds);


                JSONObject json = jsonParser.SendHttpPosts(url,"POST",jsonObject,null,Token);
                if (json != null) {
                    return  json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @SuppressLint({"CommitPrefEdits", "ApplySharedPref"})
        @Override
        protected void onPostExecute(JSONObject json) {

            if(json!=null) {


                if (json.has("error")) {

                    session.logoutUser();
                    Intent intent = new Intent(ChangeEmail.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                } else {


                    String status = json.optString("status");
                    String message = json.optString("message");


                    if (status.equals("ok")) {

                        SessionManager.editor= SessionManager.pref.edit();
                        SessionManager.editor.putString(SessionManager.KEY_USER_EMAIL,EmailIds);
                        SessionManager.editor.apply();
                        SessionManager.editor.commit();

                        finish();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    } else {


                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

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



        finish();


    }


}
