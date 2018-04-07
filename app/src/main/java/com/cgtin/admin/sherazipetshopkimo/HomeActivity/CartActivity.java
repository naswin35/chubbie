package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Adapters.CartAdapter;
import com.cgtin.admin.sherazipetshopkimo.BaseActivity;
import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.CartClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.cgtin.admin.sherazipetshopkimo.Adapters.CartAdapter.found;

public class CartActivity extends BaseActivity {
    RecyclerView CartRecycler;
    ArrayList<CartClass> CartArray;
    LinearLayout Continue;
    DrawerLayout drawer;
    SessionManager session;
    HashMap<String,String> user;

    public static boolean internet_status;
    private Subscription internetConnectivitySubscription;
    public static LinearLayout internet;
    CartAdapter postedReq_adapter;

    public static LinearLayout No_Record;
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_cart);
        getLayoutInflater().inflate(R.layout.activity_cart,frameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        internet=(LinearLayout)findViewById(R.id.internet);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);
        mTitle.setText(R.string.my_cart);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        session=new SessionManager(getApplicationContext());
//        user=session.getUserDetails();
//
//        if(!session.isLoggedIn()) {
//            Intent intent = new Intent(CartActivity.this, LoginActivity.class);
//            intent.putExtra("from","Cart");
//            startActivity(intent);
//        }

        initTypeface();

        No_Record=findViewById(R.id.no_record);

        Continue=findViewById(R.id.continues);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(internet_status){

                    if( Arrays.CartArray.size()>0)
                    {
                        Intent i= new Intent(CartActivity.this,AddressActivity.class) ;
                        startActivity(i);
                    }

                    else
                    {

                        Toast.makeText(getApplicationContext(), R.string.Please_add_an_item_in_cart, Toast.LENGTH_LONG).show();

                    }






//                    if( Arrays.CartArray.size()>0&&!found)
//                    {
//                        Intent i= new Intent(CartActivity.this,AddressActivity.class) ;
//                        startActivity(i);
//                    }
//                    else if(found)
//                    {
//                        if(Arrays.AfterPayArray.size()==1)
//                        {
//                            Toast.makeText(getApplicationContext(), Arrays.AfterPayArray.size()+" Products is out of stock please remove it !", Toast.LENGTH_LONG).show();
//                        }
//                        else
//                        {
//
//                        }
//                        Toast.makeText(getApplicationContext(), Arrays.AfterPayArray.size()+" Products are out of stock please remove it !", Toast.LENGTH_LONG).show();
//                    }
//                    else
//                    {
//
//                        Toast.makeText(getApplicationContext(), R.string.Please_add_an_item_in_cart, Toast.LENGTH_LONG).show();
//
//                    }








                }else{


                    internet.setVisibility(View.VISIBLE);

                }

            }
        });



        if (Arrays.CartArray != null && Arrays.CartArray.size() > 0) {

            No_Record.setVisibility(View.GONE);

            CartRecycler = (RecyclerView) findViewById(R.id.PostReq_Recy);
            postedReq_adapter = new CartAdapter(CartActivity.this, Arrays.CartArray);
            CartRecycler.setAdapter(postedReq_adapter);
            CartRecycler.setHasFixedSize(true);

            CartRecycler.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
            postedReq_adapter.notifyDataSetChanged();

        }
        else
        {
            No_Record.setVisibility(View.VISIBLE);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        MenuItem myOrders=menu.findItem(R.id.action_cart);
        myOrders.setVisible(false);
        return true;
    }
    private static long back_pressed;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()){

            case android.R.id.home:

                onBackPressed();
                return true;

            case R.id.action_notification:

                Intent i= new Intent(CartActivity.this,MyOrders.class) ;
                startActivity(i);

                break;

        }

        return super.onOptionsItemSelected(item);


    }
    @Override
    public void  onBackPressed(){

        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        session=new SessionManager(getApplicationContext());
        user=session.getUserDetails();
        initTypeface();
        if (postedReq_adapter != null && postedReq_adapter.getItemCount() > 0) {

            assert postedReq_adapter != null;
            postedReq_adapter.notifyDataSetChanged();

        }

        internetConnectivitySubscription = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override public void call(Boolean isConnectedToInternet) {



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
