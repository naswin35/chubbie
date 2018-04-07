package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.BaseActivity;
import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.ProductDetailClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.Classes.buildCounterDrawable;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.AddressClass;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.cgtin.admin.sherazipetshopkimo.StartActivities.LoginActivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks.AddressFrom;

public class AddressActivity extends AppCompatActivity {

    LinearLayout Contiue;
    JSONObject jsonObject_item=new JSONObject();
    JSONArray jsonArray=new JSONArray();
    CardView add_addres;
    CardView address_show;
    RecyclerView PetFoodReqRecycler;

    TextView name;
    TextView address_text;
    TextView mobile;
    LinearLayout address_btn;
    String name_from;
    String address_from;
    String mobile_from;
    HashMap<String,String> user;
    SessionManager session;


    TextView total_display;
    TextView grand_total;
    TextView Dcharge;
    TextView vat_amount;

    TextView vat;
    String SessionLogined="true";
    public static String Resumeneeded="false";

    public static  AddressActivity AddressActivity;

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
        setContentView(R.layout.activity_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView mTitle = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);

        internet=(LinearLayout)findViewById(R.id.internet);
        mTitle.setText(R.string.delivery_header);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);

        Intent i=getIntent();
        name_from=i.getStringExtra("name");
        address_from=i.getStringExtra("address");
        mobile_from=i.getStringExtra("phone");
        if(name_from!=null)
        {
            CommonClass.Address_Id_Show=i.getStringExtra("AddID");

        }


        vat=findViewById(R.id.vat);


        total_display=findViewById(R.id.total_display);
        grand_total=findViewById(R.id.grand_total);

        add_addres=findViewById(R.id.add_address);
        address_show=findViewById(R.id.address_show);
        address_btn=findViewById(R.id.address_btn);

        name=findViewById(R.id.name);
        address_text=findViewById(R.id.address);
        mobile=findViewById(R.id.mobile);
        Dcharge=findViewById(R.id.Dcharge);
        vat_amount=findViewById(R.id.vat_amount);

        session=new SessionManager(getApplicationContext());
        user=session.getUserDetails();

        AddressActivity=this;

        AddressFrom= this;


        if(!session.isLoggedIn()) {
            Resumeneeded="true";
            Intent intent = new Intent(AddressActivity.this, LoginActivity.class);
            intent.putExtra("from","address");
            startActivity(intent);
            SessionLogined="true";
        }





        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Contiue=findViewById(R.id.cntnue);
        Contiue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(internet_status){
                    if(CommonClass.AddressListArray.size()>0)
                    {
                        Intent i= new Intent(AddressActivity.this,Payments.class) ;
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), R.string.Please_add_address,Toast.LENGTH_LONG).show();
                    }

                }else{

                    internet.setVisibility(View.VISIBLE);
                }
            }
        });

        double Total_display = 0;
        double Grand_total = 0;
        for (ProductDetailClass items :  Arrays.CartArray) {

            String price = String.valueOf(items.getTotalPrice());
            Double tprice=Double.parseDouble(price);
            Total_display += tprice;

        }
        total_display.setText(String.format(Locale.ENGLISH,"%.02f", Total_display));
        Double VatAmount=(Total_display*Double.valueOf(CommonClass.VATpercentage))/100;
        Grand_total= Total_display+ Double.parseDouble(CommonClass.DeliveryCharge)+VatAmount;
        grand_total.setText(String.format(Locale.ENGLISH,"%.02f", Grand_total));

        Dcharge.setText(String.format(Locale.ENGLISH,"%.02f", Double.valueOf(CommonClass.DeliveryCharge)));
        vat.setText(String.format(" - %s %%", CommonClass.VATpercentage));
        vat_amount.setText(String.format(Locale.ENGLISH,"%.02f", VatAmount));





        add_addres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AddressActivity.this,AddressAddActivity.class) ;
                startActivity(i);
                CommonClass.Address_back_for_add="Delivery";
            }
        });

        address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(internet_status){
                    AddressFrom= AddressActivity.getIntance();
                    Intent i= new Intent(AddressActivity.this,AddressActivityList.class) ;
                    startActivity(i);
                }else{
                    internet.setVisibility(View.VISIBLE);
                }
            }
        });



    }

    public static AddressActivity getIntance()
    {
        return  AddressActivity;
    }


    private class Address_List extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.address_get+user.get(SessionManager.KEY_CUSTOMER_ID);

        //user.get(SessionManager.KEY_CUSTOMER_ID)
        String Token;
        ProgressHUD mProgressHUD;

        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(AddressActivity.this,null, true);
            Token=user.get(SessionManager.KEY_CUSTOMER_TOKEN);



        }

        protected JSONObject doInBackground(String... arg0) {


            try {


                JSONObject json = jsonParser.SendHttpPosts(url,"GET",null,null,Token);
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

                    // session.logoutUser();
                    Resumeneeded="true";
                    Intent intent = new Intent(AddressActivity.this, LoginActivity.class);
                    intent.putExtra("from","address");
                    startActivity(intent);
                    SessionLogined="true";

                }
                else

                {

                    String status = json.optString("status");
                    String message = json.optString("message");


                    if (status.equals("ok")) {
                        JSONArray jsonArray_data = json.optJSONArray("data");
                        CommonClass.AddressListArray.clear();
                        for (int i = 0; i < jsonArray_data.length(); i++) {

                            JSONObject obj = jsonArray_data.optJSONObject(i);

                            String customer_address_id = obj.optString("customer_address_id");
                            String customer_address_user_id = obj.optString("customer_address_user_id");
                            String customer_address_name = obj.optString("customer_address_name");
                            String customer_address_address = obj.optString("customer_address_address");
                            String customer_address_pincode = obj.optString("customer_address_pincode");
                            String customer_address_contact = obj.optString("customer_address_contact");

                            AddressClass homeclass = new AddressClass();

                            homeclass.setCustomer_address_id(customer_address_id);
                            homeclass.setCustomer_address_user_id(customer_address_user_id);
                            homeclass.setCustomer_address_name(customer_address_name);
                            homeclass.setCustomer_address_address(customer_address_address);
                            homeclass.setCustomer_address_pincode(customer_address_pincode);
                            homeclass.setCustomer_address_contact(customer_address_contact);
                            CommonClass.AddressListArray.add(homeclass);


                        }

                        if(CommonClass.AddressListArray.size()==0)
                        {

                            CommonClass.Address_Id_Show=null;
                        }

                        if (CommonClass.Address_Id_Show==null) {

                            if (CommonClass.AddressListArray.size() == 0) {

                                add_addres.setVisibility(View.VISIBLE);
                                address_show.setVisibility(View.GONE);

                            } else {
                                add_addres.setVisibility(View.GONE);
                                address_show.setVisibility(View.VISIBLE);

                                AddressClass address = CommonClass.AddressListArray.get(0);

                                name.setText(address.getCustomer_address_name());
                                address_text.setText(address.getCustomer_address_address());
                                mobile.setText(address.getCustomer_address_contact());
                                CommonClass.Address_Id_Show = address.getCustomer_address_id();

                                for (ProductDetailClass items : Arrays.CartArray) {


                                    items.setAddressID(address.getCustomer_address_id());

                                }

                            }

                        } else {

                            if (CommonClass.AddressListArray.size() > 0) {


                                boolean datafound=false;


                                int i=0;
                                for (AddressClass addresses : CommonClass.AddressListArray) {

                                    if (addresses.getCustomer_address_id().equals(CommonClass.Address_Id_Show)) {

                                        datafound=true;

                                        break;

                                    }i++;
                                }

                                if(datafound){

                                    address_show.setVisibility(View.VISIBLE);
                                    add_addres.setVisibility(View.GONE);
                                    AddressClass addresses=CommonClass.AddressListArray.get(i);

                                    name.setText(addresses.getCustomer_address_name());
                                    address_text.setText(addresses.getCustomer_address_address());
                                    mobile.setText(addresses.getCustomer_address_contact());
                                }else {


                                    if(CommonClass.AddressListArray.size() != 0)
                                    {
                                        add_addres.setVisibility(View.GONE);
                                        address_show.setVisibility(View.VISIBLE);

                                        AddressClass address = CommonClass.AddressListArray.get(0);

                                        name.setText(address.getCustomer_address_name());
                                        address_text.setText(address.getCustomer_address_address());
                                        mobile.setText(address.getCustomer_address_contact());
                                        CommonClass.Address_Id_Show = address.getCustomer_address_id();

                                    }
                                    else
                                    {
                                        add_addres.setVisibility(View.VISIBLE);
                                        address_show.setVisibility(View.GONE);
                                    }


                                }


                            } else {

                                add_addres.setVisibility(View.VISIBLE);
                                address_show.setVisibility(View.GONE);

                            }
                        }
                    }
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
//    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View view = inflater.inflate(R.layout.counter_menuitem_layout, null);
//        view.setBackgroundResource(backgroundImageId);
//
//        if (count == 0) {
//            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
//            counterTextPanel.setVisibility(View.GONE);
//        } else {
//            TextView textView = (TextView) view.findViewById(R.id.count);
//            textView.setText("" + count);
//        }
//
//        view.measure(
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//
//        view.setDrawingCacheEnabled(true);
//        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
//        view.setDrawingCacheEnabled(false);
//
//        return new BitmapDrawable(getResources(), bitmap);
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        buildCounterDrawable drawable=new buildCounterDrawable();
        getMenuInflater().inflate(R.menu.menu_new, menu);


        MenuItem menuItem = menu.findItem(R.id.action_cart);
        MenuItem myOrders=menu.findItem(R.id.action_notification);
        myOrders.setVisible(false);
        menuItem.setIcon(drawable.buildCounterDrawables( Arrays.CartArray.size(),  R.drawable.ic_cart,this));
        menuItem.setVisible(false);
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
//            case R.id.action_cart:
//                if( Arrays.CartArray.size()>0)
//                {
//                    Intent i= new Intent(AddressActivity.this,CartActivity.class) ;
//                    startActivity(i);
//                }
//                else
//
//                {
//
//                    Toast.makeText(getApplicationContext(), R.string.Cart_is_empty, Toast.LENGTH_LONG).show();
//
//                }
//
//                break;
//            case R.id.action_notification:
//
//                Intent i= new Intent(AddressActivity.this,MyOrders.class) ;
//                startActivity(i);
//
//                break;

        }

        return super.onOptionsItemSelected(item);


    }
    @Override
    public void  onBackPressed(){

        finish();


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
                        user=session.getUserDetails();
                        internet_status=isConnectedToInternet;

                        if(isConnectedToInternet){
                            //snackbar1.dismiss();
                            internet.setVisibility(View.GONE);
                           // if(Resumeneeded.equals("true")){
                            //Resumeneeded="false";
                            //}
                            if(session.isLoggedIn()) {

                                    new Address_List().execute();
                                   // SessionLogined = "false";
                                    Resumeneeded="false";

                            }
                            else
                            {
                                internet.setVisibility(View.VISIBLE);
                            }





                        }


                    }
                });



        invalidateOptionsMenu();
        //initTypeface();
    }

    @Override
    protected void onPause() {

        super.onPause();
        safelyUnsubscribe(internetConnectivitySubscription);
    }


}
