package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangePassword extends AppCompatActivity {


    SessionManager session;
    HashMap<String,String> UserDetails;


    ImageView backArrow;

    EditText OldPassword;
    EditText NewPassword;
    EditText ConfirmPassword;

    LinearLayout UpdatePassword;

    String oldPass;
    String NewPass;
    String ConfirmPass;


    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;





    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent getData=getIntent();
        mTitle.setText(R.string.change_password);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);


        session=new SessionManager(getApplicationContext());
        UserDetails=session.getUserDetails();
        OldPassword=findViewById(R.id.OldPassword);
        NewPassword=findViewById(R.id.Password);
        ConfirmPassword=findViewById(R.id.confirm_Password);
        internet=(LinearLayout)findViewById(R.id.internet);


        UpdatePassword=findViewById(R.id.UpdatePassword);

        UpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    validatePassword();


            }
        });






    }

    private void validatePassword() {

        oldPass=OldPassword.getText().toString();
        NewPass=NewPassword.getText().toString();
        ConfirmPass=ConfirmPassword.getText().toString();

        if(!ValidateOldPass()){

            return;
        }
        if(!ValidateNewPass()){

            return;
        }
        if(!ValidateConfirmPass()){

            return;
        }



        if(internet_status){

            new ChangePasswords().execute();

        }else{


            if(!internet.isShown()) {
                internet.setVisibility(View.VISIBLE);
            }

        }





    }

    private boolean ValidateConfirmPass() {

        if (ConfirmPass.isEmpty()) {
            Toast.makeText(getApplicationContext(),R.string.confirm_password,Toast.LENGTH_LONG).show();
            requestFocus(ConfirmPassword);
            return false;
        }
        else if (ConfirmPass.length()>50) {
            Toast.makeText(getApplicationContext(),R.string.Password_character_limit_exceeded,Toast.LENGTH_LONG).show();
            requestFocus(ConfirmPassword);
            return false;
        }

        else if(!NewPass.equals(ConfirmPass))
        {
            Toast.makeText(getApplicationContext(),R.string.Password_doesnt_matches,Toast.LENGTH_LONG).show();
            requestFocus(ConfirmPassword);
            return false;
        }



        return true;
    }

    private boolean ValidateNewPass() {


        if (NewPass.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.Enter_new_password,Toast.LENGTH_LONG).show();
            requestFocus(NewPassword);
            return false;
        }
        else if (NewPass.length()>50) {
            Toast.makeText(getApplicationContext(),R.string.Password_character_limit_exceeded,Toast.LENGTH_LONG).show();
            requestFocus(NewPassword);
            return false;
        }

        return true;


    }

    private boolean ValidateOldPass() {


        if (oldPass.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.Enter_current_password,Toast.LENGTH_LONG).show();
            requestFocus(OldPassword);
            return false;
        }
        else if (oldPass.length()>50) {
            Toast.makeText(getApplicationContext(),R.string.Password_character_limit_exceeded,Toast.LENGTH_LONG).show();
            requestFocus(OldPassword);
            return false;
        }


        return true;

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


    private class ChangePasswords extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.changePassword;


        ProgressHUD mProgressHUD;

        String Token;

        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(ChangePassword.this,null, true);

            Token=UserDetails.get(SessionManager.KEY_CUSTOMER_TOKEN);

        }

        protected JSONObject doInBackground(String... arg0) {



            JSONObject jsonObject2=new JSONObject();
            HashMap<String,String> params=new HashMap<>();


            String NewPasswords=md5(NewPass);
            String oldPasswords=md5(oldPass);


            try {
                jsonObject2.put("user_id",UserDetails.get(SessionManager.KEY_CUSTOMER_ID));
                jsonObject2.put("old_password", oldPasswords);
                jsonObject2.put("new_password", NewPasswords);


                JSONObject json = jsonParser.SendHttpPosts(url,"POST",jsonObject2,null,Token);
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
                    Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                } else {


                    String status = json.optString("status");
                    String message = json.optString("message");


                    if (status.equals("ok")) {


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



    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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


        super.onBackPressed();
        finish();

    }


}
