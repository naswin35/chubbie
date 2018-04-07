package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cgtin.admin.sherazipetshopkimo.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangeLanguage extends AppCompatActivity {


    TextView English;
    TextView Arabic;
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText(R.string.select_default_language);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);

        English=findViewById(R.id.english);
        Arabic=findViewById(R.id.arabic);



        English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("language", "en");
                editor.apply();
                editor.commit();

                Intent trainingIntent = new Intent(ChangeLanguage.this, HomeActivity.class);
                trainingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                trainingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                trainingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(trainingIntent);
                finish();


            }
        });

        Arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("language", "ar");
                editor.apply();
                editor.commit();

                Intent trainingIntent = new Intent(ChangeLanguage.this, HomeActivity.class);
                trainingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                trainingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                trainingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(trainingIntent);
                finish();

            }
        });


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
