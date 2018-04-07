package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Orderconfirmation extends AppCompatActivity {

    TextView NewOrder;
    TextView Track;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderconfirmation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NewOrder=findViewById(R.id.NewOrder);
        Track=findViewById(R.id.track);

        Arrays.ProductItemsArray.clear();
        Arrays.CartArray.clear();
        CommonClass.AddressListArray.clear();
        CommonClass.Address_Id_Show=null;
        NewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(),HomeActivity.class) ;
                startActivity(i);
                finish();

            }
        });

        Track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(Orderconfirmation.this,MyOrders.class) ;
                startActivity(i);


            }
        });


    }


    @Override
    public void  onBackPressed(){



    }


}
