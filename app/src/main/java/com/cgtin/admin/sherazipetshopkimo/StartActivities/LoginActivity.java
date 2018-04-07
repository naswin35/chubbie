package com.cgtin.admin.sherazipetshopkimo.StartActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.BaseActivity;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.AddressActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.Brands;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.HomeActivity;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    LinearLayout SignUp;
    Button Login;
    EditText username;
    EditText password;
    LinearLayout skip;
    String UserName;
    String UserPassword;
    TextView forgot_password;
    private static long back_pressed;
    private static final boolean DOBLETAP =true ;
    SessionManager session;
    HashMap<String,String> UserDetails;

   // TextView country_code;

    String FromActivity;
    public static  LoginActivity loginActivity;

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

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Global.LANG = settings.getString("language","en");
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", Global.LANG).apply();
        Locale locale = new Locale(Global.LANG);
        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(locale);
        Locale.setDefault(locale);
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_login);
        SignUp=(LinearLayout)findViewById(R.id.signup);
        Login=(Button)findViewById(R.id.login_button);
        username=findViewById(R.id.Username);
        password=findViewById(R.id.Password);
        forgot_password=findViewById(R.id.forgot_pass);
        skip=findViewById(R.id.skip);
        session=new SessionManager(getApplicationContext());

       // country_code=findViewById(R.id.Country_Code);
        Intent from=getIntent();
        FromActivity=from.getStringExtra("from");
        loginActivity=this;
        internet=(LinearLayout)findViewById(R.id.internet);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(LoginActivity.this,SignUp.class) ;

                if(FromActivity!=null)
                {
                    i.putExtra("from","login");

                }

                startActivity(i);


            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(internet_status){

                    validateLogin();

                }else{


                    internet.setVisibility(View.VISIBLE);

                }


            }
        });


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toHome=new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(toHome);

            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toHome=new Intent(LoginActivity.this, ForgotPassword.class);
                if(FromActivity!=null)
                {
                    toHome.putExtra("from","login");

                }
                startActivity(toHome);


            }
        });
//        country_code.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //creating a popup menu
//                PopupMenu popup = new PopupMenu(getApplicationContext(), country_code);
//                //inflating menu from xml resource
//                popup.inflate(R.menu.option_menu);
//                //adding click listener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.action_india:
//
//                                country_code.setText("+91");
//
//                                break;
//                            case R.id.action_soudi:
//                                country_code.setText("+966");
//
//                                break;
//
//                        }
//                        return false;
//                    }
//                });
//                //displaying the popup
//                popup.show();
//
//
//
//            }
//        });









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
    private void validateLogin() {

        UserName=username.getText().toString();
        UserPassword=password.getText().toString();



        if(!validateUsername()){
            return;
        }
        if(!validatePassword()){

            return;
        }

        if(internet_status){

            new UserLogin().execute();

        }else{


            internet.setVisibility(View.VISIBLE);

        }

    }
    private boolean CheckMob()
    {
        if (UserName.length()==10) {

            return true;
        }
        else if (UserName.length()==9) {

            return true;
        }
        else
        {
         return false;
        }
    }

    private boolean validateUsername() {



        if (!UserName.isEmpty()&&!CheckMob()) {

            Toast.makeText(getApplicationContext(), R.string.enter_valid_mobile,Toast.LENGTH_LONG).show();
            requestFocus(username);
            return false;
        }
        if (UserName.isEmpty()) {

            Toast.makeText(getApplicationContext(), R.string.Enter_mobile_number,Toast.LENGTH_LONG).show();
            requestFocus(username);
            return false;
        }

        else if (!validateMob(UserName)) {

            Toast.makeText(getApplicationContext(), R.string.Special_characters_not_allowed,Toast.LENGTH_LONG).show();
            requestFocus(username);
            return false;
        }



        return true;
    }

    private boolean validatePassword() {
        if (UserPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.Enter_password,Toast.LENGTH_LONG).show();
            requestFocus(password);
            return false;
        }
        else if (UserPassword.length()>50) {
            Toast.makeText(getApplicationContext(), R.string.Password_character_limit_exceeded,Toast.LENGTH_LONG).show();
            requestFocus(password);
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

    private void safelyUnsubscribe(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    private class UserLogin extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.Login;


        ProgressHUD mProgressHUD;

        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(LoginActivity.this,null, true);



        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();
            JSONObject jsonObject_tocken=new JSONObject();
            HashMap<String,String> params=new HashMap<>();


            String encrypt=md5(UserPassword);


            try {

                jsonObject_tocken.put("device","android");
                jsonObject_tocken.put("value", FirebaseInstanceId.getInstance().getToken());

                jsonObject.put("username",UserName);
                jsonObject.put("password",encrypt);
                jsonObject.put("token",jsonObject_tocken);
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

                    String userID=jsonObject.optString("userID");
                    String mobileno=jsonObject.optString("mobileno");
                    JSONObject user_data=jsonObject.optJSONObject("user_data");
                    String email=user_data.optString("user_email");
                    String customer_profile_name=user_data.optString("customer_profile_name");
                    String customer_profile_gender=user_data.optString("customer_profile_gender");

                   // session.createLoginSession(userID,mobileno,token,UserName,email);

                    session.createLoginSession(userID,customer_profile_name,token,mobileno,email,customer_profile_gender);
                    UserDetails=session.getUserDetails();

                    if(FromActivity!=null)
                    {

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
                        if(LoginActivity.getInstance()!=null)
                        {
                            LoginActivity.getInstance().finish();

                        }
                        if(AddressActivity.getIntance()!=null)
                        {
                            AddressActivity.Resumeneeded="true";
                        }

                        finish();
                    }
                    else
                    {
                        Intent toHome=new Intent(LoginActivity.this, HomeActivity.class);
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
    @Override
    public void onBackPressed() {




        if(DOBLETAP==true){
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();
                System.exit(0);

            } else
                Toast.makeText(getBaseContext(), R.string.Press_once_again_to_exit, Toast.LENGTH_SHORT).show();

            back_pressed = System.currentTimeMillis();
        }

        else{
        }

    }

    public static LoginActivity getInstance()
    {

        return loginActivity;
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
