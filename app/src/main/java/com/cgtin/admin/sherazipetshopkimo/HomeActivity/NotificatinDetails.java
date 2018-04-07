package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.R;

import org.json.JSONObject;

import java.util.HashMap;

public class NotificatinDetails extends AppCompatActivity {

    TextView Notify_title;
    TextView Notify_description;
    String NotificationStatus;
    String NotificationID;
    SessionManager session;
    HashMap<String,String> UserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificatin_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText(R.string.Order_Details);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);

        session=new SessionManager(getApplicationContext());
        UserDetails=session.getUserDetails();


        Notify_title=findViewById(R.id.notify_title);
        Notify_description=findViewById(R.id.notify_description);


        Intent getData=getIntent();

        Notify_title.setText(getData.getStringExtra("title"));
        Notify_description.setText(getData.getStringExtra("description"));
        NotificationStatus=getData.getStringExtra("status");
        NotificationID=getData.getStringExtra("notifyID");





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
