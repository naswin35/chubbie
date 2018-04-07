package com.cgtin.admin.sherazipetshopkimo.StartActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.PrefManager;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.HomeActivity;
import com.cgtin.admin.sherazipetshopkimo.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LanguageChoose extends AppCompatActivity {


    private PrefManager prefManager;

    Button English,Arabian;

    SessionManager session;

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {

            SharedPreferences settingss = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Global.LANG = settingss.getString("language","en");

            if(Global.LANG!=null){

                launchHomeScreen(Global.LANG);
                finish();
            }

        }


        setContentView(R.layout.activity_language_choose);

        English= (Button) findViewById(R.id.button_english);
        Arabian= (Button) findViewById(R.id.button_arabian);


        English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                launchHomeScreen("en");

            }
        });

        Arabian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                launchHomeScreen("ar");

            }
        });


    }


    private void launchHomeScreen(String en) {
        prefManager.setFirstTimeLaunch(false);

        session=new SessionManager(getApplicationContext());


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("language", en);
        editor.apply();
        editor.commit();



        if(!session.isLoggedIn()) {
            Intent i = new Intent(LanguageChoose.this, LoginActivity.class);
            startActivity(i);
            finish();
        }else{

            Intent i = new Intent(LanguageChoose.this, HomeActivity.class);
            startActivity(i);
            finish();

        }




    }
}
