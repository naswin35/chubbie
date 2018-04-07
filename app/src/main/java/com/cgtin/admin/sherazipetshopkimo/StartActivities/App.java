package com.cgtin.admin.sherazipetshopkimo.StartActivities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.cgtin.admin.sherazipetshopkimo.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Admin on 03-03-2018.
 */

public class App extends Application {

    public static final String MY_PREFERENCES = "MyPreferences";

    SharedPreferences myPreferences;

    @Override
    public void onCreate() {
        super.onCreate();


        myPreferences = getSharedPreferences (MY_PREFERENCES, Context.MODE_PRIVATE);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Light.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }




}

