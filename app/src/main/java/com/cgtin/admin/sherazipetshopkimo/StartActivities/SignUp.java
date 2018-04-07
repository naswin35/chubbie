package com.cgtin.admin.sherazipetshopkimo.StartActivities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.HomeActivity;
import com.cgtin.admin.sherazipetshopkimo.R;
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

public class SignUp extends AppCompatActivity {

    EditText UserName;
    //EditText EmailAddress;
    //EditText Password;
    //EditText ConfirmPassword;
    //EditText Name;

    LinearLayout sign_in;
    LinearLayout skip;
    String username;
    String UserPassword;
    String NameString;
    String emailaddress;
    SessionManager session;
    HashMap<String,String> UserDetails;
    Button register_button;
    TextView country_code;
    LinearLayout Country_Code_select;
    String FromActivity;
    public static SignUp signup;

    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        UserName=findViewById(R.id.Username);
        //EmailAddress=findViewById(R.id.email);
        //Password=findViewById(R.id.Password);
        //ConfirmPassword=findViewById(R.id.ConfirmPassword);
        register_button=findViewById(R.id.register);
        //Name=findViewById(R.id.name);
        skip=findViewById(R.id.skip);
        country_code=findViewById(R.id.Country_Code);
        Country_Code_select=findViewById(R.id.Country_Code_select);
        sign_in=findViewById(R.id.sign_in);
        Intent from=getIntent();
        internet=(LinearLayout)findViewById(R.id.internet);
        signup=this;
        FromActivity=from.getStringExtra("from");
        session=new SessionManager(getApplicationContext());
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(internet_status){

                    validateLogin();

                }else{


                    internet.setVisibility(View.VISIBLE);

                }





            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent i= new Intent(SignUp.this,LoginActivity.class);


              startActivity(i);
              finish();

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

                                break;
                            case R.id.action_soudi:
                                country_code.setText(R.string.soudia);

                                break;

                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();



            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toHome=new Intent(SignUp.this, HomeActivity.class);
                startActivity(toHome);

            }
        });

    }

    private void validateLogin() {

        username=UserName.getText().toString();
        //UserPassword=Password.getText().toString();
        //NameString=Name.getText().toString();


        if(!validateUsername()){
            return;
        }
//        if(!validatePassword()){
//
//            return;
//        }
//        if(!validateEmail()){
//
//            return;
//        }
//        if(!confirmpassword()){
//
//            return;
//        }
//
//        if(!validateName()){
//
//            return;
//        }

        new SignUp_jsn().execute();
    }
    private boolean CheckMob()
    {
        if (username.length()==10) {

            return true;
        }
        else if (username.length()==9) {

            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean validateUsername() {



        if (!username.isEmpty()&&!CheckMob()) {

            Toast.makeText(getApplicationContext(),R.string.enter_valid_mobile,Toast.LENGTH_LONG).show();
            requestFocus(UserName);
            return false;
        }
        if (username.isEmpty()) {

            Toast.makeText(getApplicationContext(),R.string.Enter_mobile_number,Toast.LENGTH_LONG).show();
            requestFocus(UserName);
            return false;
        }
        else if (!validateMob(username)) {

            Toast.makeText(getApplicationContext(),R.string.Special_characters_not_allowed,Toast.LENGTH_LONG).show();
            requestFocus(UserName);
            return false;
        }



        return true;
    }
//    private boolean validateName() {
//
//
//
//        if (NameString.isEmpty()) {
//
//            Toast.makeText(getApplicationContext(), R.string.Enter_name,Toast.LENGTH_LONG).show();
//            requestFocus(Name);
//            return false;
//        }
//
//        else if (validateMob(NameString)) {
//
//            Toast.makeText(getApplicationContext(),R.string.Special_characters_not_allowed,Toast.LENGTH_LONG).show();
//            requestFocus(Name);
//            return false;
//        }
//
//
//
//        return true;
//    }




//    private boolean validateEmail() {
//        String email = EmailAddress.getText().toString().trim();
//
//        if (email.isEmpty() || !isValidEmail(email)) {
//            Toast.makeText(getApplicationContext(), R.string.Please_enter_valid_email,Toast.LENGTH_LONG).show();
//
//            requestFocus(EmailAddress);
//            return false;
//        }
//
//        return true;
//    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
//    private boolean validatePassword() {
//        if (UserPassword.isEmpty()) {
//            Toast.makeText(getApplicationContext(),R.string.Enter_password,Toast.LENGTH_LONG).show();
//            requestFocus(Password);
//            return false;
//        }
//        else if (UserPassword.length()>50) {
//            Toast.makeText(getApplicationContext(),R.string.Password_character_limit_exceeded,Toast.LENGTH_LONG).show();
//            requestFocus(Password);
//            return false;
//        }
//
//
//
//        return true;
//    }

//    private boolean confirmpassword() {
//        if (ConfirmPassword.getText().toString().trim().isEmpty()) {
//            Toast.makeText(getApplicationContext(),R.string.Enter_password,Toast.LENGTH_LONG).show();
//
//            requestFocus(ConfirmPassword);
//            return false;
//        } else if (!(ConfirmPassword.getText().toString().trim().equals(Password.getText().toString().trim()))) {
//            Toast.makeText(getApplicationContext(), R.string.Password_doesnt_matches,Toast.LENGTH_LONG).show();
//            requestFocus(ConfirmPassword);
//            return false;
//
//        }
//
//
//        return true;
//    }



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


//    private static final String md5(final String password) {
//        try {
//
//            MessageDigest digest = java.security.MessageDigest
//                    .getInstance("MD5");
//            digest.update(password.getBytes());
//            byte messageDigest[] = digest.digest();
//
//            StringBuffer hexString = new StringBuffer();
//            for (int i = 0; i < messageDigest.length; i++) {
//                String h = Integer.toHexString(0xFF & messageDigest[i]);
//                while (h.length() < 2)
//                    h = "0" + h;
//                hexString.append(h);
//            }
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    public static SignUp getInstance()
    {

        return signup;
    }

    private class SignUp_jsn extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.SignUp_otp;


        ProgressHUD mProgressHUD;

        protected void onPreExecute(){

            mProgressHUD = ProgressHUD.show(SignUp.this, null, true);
            super.onPreExecute();



        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();
            JSONObject jsonObject2=new JSONObject();
            HashMap<String,String> params=new HashMap<>();


            //String encrypt=md5(UserPassword);


            try {
                jsonObject.put("mobileno",UserName.getText().toString());
                jsonObject.put("country_code",country_code.getText().toString());

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
                String token=json.optString("token");


                if(status.equals("ok")){
                    JSONObject jsonObject=json.optJSONObject("data");




                    JSONObject user_data=jsonObject.optJSONObject("sms");
                    CommonClass.smsid=user_data.optString("smsid");


                    // session.createLoginSession(userID,mobileno,token,UserName,email);


//                    session.createLoginSession(user_id,null,token,username,EmailAddress.getText().toString());
//                    UserDetails=session.getUserDetails();



//                        if(FromActivity!=null)
//                        {
//
//                            finish();
//                        }
//                        else
//                        {
//                            Intent toHome=new Intent(LoginActivity.this, HomeActivity.class);
//                            startActivity(toHome);
//                            finish();
//                        }

//                        if(LoginActivity.getInstance()!=null)
//                        {
//                            LoginActivity.getInstance().finish();
//                        }



                        Intent toHome=new Intent(SignUp.this, RegisterOtp.class);
                        if(FromActivity!=null)
                        {
                            toHome.putExtra("from","login");


                        }
                        toHome.putExtra("mobile",UserName.getText().toString());
                        toHome.putExtra("ccode",country_code.getText().toString());
                        startActivity(toHome);




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
