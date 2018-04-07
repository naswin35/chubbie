package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.cgtin.admin.sherazipetshopkimo.StartActivities.LoginActivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.cgtin.admin.sherazipetshopkimo.HomeActivity.AddressActivity.Resumeneeded;

public class AddressAddActivity extends AppCompatActivity {

    EditText name;
    EditText address;
    public static EditText pincode;
    EditText contact;

    String NameString;
    String AddressString;
    String PinString;
    String MobileString;
    SessionManager session;
    HashMap<String,String> user;
    LinearLayout add_address;
    LinearLayout google_click;

    String name_from;
    String address_from;
    String mobile_from;
    String Add_ID;
    public  String PIN;

    ProgressBar progressBar;
    TextView add_or_update;
    private static final int PLACE_PICKER_REQUEST = 1;

    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;

    Double latitude;
    Double longitude;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(23.8859, 45.0792), new LatLng(23.8859, 45.0792));

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView mTitle = (TextView) findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);

        add_or_update=findViewById(R.id.add_or_update);

        if(CommonClass.Address_edit_header)
        {
            mTitle.setText(R.string.update_address);
            add_or_update.setText(R.string.update_address);
            CommonClass.Address_edit_header=false;

        }
        else
        {
            mTitle.setText(R.string.add_address);
            add_or_update.setText(R.string.add_address);
        }

        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        internet=(LinearLayout)findViewById(R.id.internet);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        pincode=findViewById(R.id.pin);
        contact=findViewById(R.id.contact);
        add_address=findViewById(R.id.add_address);
        progressBar=findViewById(R.id.progressBar);

        google_click=findViewById(R.id.google_click);

        session=new SessionManager(getApplicationContext());
        user=session.getUserDetails();

        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                validateAddAddress();

            }
        });

        google_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progressBar.setVisibility(View.VISIBLE);


                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                     intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(AddressAddActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }





//               Intent i=new Intent(AddressAddActivity.this,PlacePickerActivity.class);
//               startActivity(i);

            }
        });

            Intent i=getIntent();
            name_from=i.getStringExtra("name");
            address_from=i.getStringExtra("address");
            mobile_from=i.getStringExtra("phone");
            Add_ID=i.getStringExtra("Add_ID");
           // PIN=i.getStringExtra("PIN");

            if(name_from!=null)
            {

                name.setText(name_from);
                name.setSelection(name.getText().toString().length());
                address.setText(address_from);
                address.setSelection(address.getText().toString().length());
//                pincode.setText(PIN);
//                pincode.setSelection(pincode.getText().toString().length());
                contact.setText(mobile_from);
                contact.setSelection(contact.getText().toString().length());

            }


    }
    private void validateAddAddress() {

        NameString=name.getText().toString();
        AddressString=address.getText().toString();
       // PinString=pincode.getText().toString();
        MobileString=contact.getText().toString();



        if(!validatename()){
            return;
        }
        if(!validateAddress()){

            return;
        }
//        if(!validatePin()){
//
//            return;
//        }
        if(!validateMobile()){

            return;
        }


        if(internet_status){

            new Address_Add().execute();

        }else{


            internet.setVisibility(View.VISIBLE);

        }


    }
    private boolean validatename() {



        if (NameString.isEmpty()) {

            Toast.makeText(getApplicationContext(), R.string.Please_enter_name,Toast.LENGTH_LONG).show();
            requestFocus(name);
            return false;
        }

        else if (!SpecialChara(NameString)) {

            Toast.makeText(getApplicationContext(),R.string.Special_characters_not_allowed,Toast.LENGTH_LONG).show();
            requestFocus(name);
            return false;
        }



        return true;
    }
    private boolean validateAddress() {
        if (AddressString.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.Please_enter_address,Toast.LENGTH_LONG).show();
            requestFocus(address);
            return false;
        }




        return true;
    }

    private boolean validatePin() {



        if (PinString.isEmpty()) {

            Toast.makeText(getApplicationContext(),R.string.select_google_location,Toast.LENGTH_LONG).show();
            requestFocus(pincode);
            return false;
        }

//        else if (!SpecialChara(PinString)) {
//
//            Toast.makeText(getApplicationContext(),"Special characters not allowed",Toast.LENGTH_LONG).show();
//            requestFocus(pincode);
//            return false;
//        }



        return true;
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {


        progressBar.setVisibility(View.GONE);

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();
            final CharSequence addres = place.getAddress();
            String attributions = (String) place.getAttributions();

            latitude = place.getLatLng().latitude;
            longitude = place.getLatLng().longitude;


            if (attributions == null) {
                attributions = "";
            }

            if(name.toString().contains("°"))
            {
                address.setText(addres);
            }
            else
            {
                address.setText(""+name+","+addres);
            }



//            mAddress.setText(address);
//            mAttributions.setText(Html.fromHtml(attributions));

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean CheckMob()
    {
        if (MobileString.length()==10) {

            return true;
        }
        else if (MobileString.length()==9) {

            return true;
        }
        else
        {
            return false;
        }
    }
    private boolean validateMobile() {





        if (!MobileString.isEmpty()&&!CheckMob()) {

            Toast.makeText(getApplicationContext(), R.string.enter_valid_mobile,Toast.LENGTH_LONG).show();
            requestFocus(contact);
            return false;
        }
        if (MobileString.isEmpty()) {

            Toast.makeText(getApplicationContext(), R.string.Enter_mobile_number,Toast.LENGTH_LONG).show();
            requestFocus(contact);
            return false;
        }

        else if (!validateMob(MobileString)) {

            Toast.makeText(getApplicationContext(), R.string.Special_characters_not_allowed,Toast.LENGTH_LONG).show();
            requestFocus(contact);
            return false;
        }



        return true;






    }





    public static final Pattern VALID_Name_REGEX =
            Pattern.compile("^[0-9a-zA-Z\\s]+$", Pattern.CASE_INSENSITIVE);


    public static boolean SpecialChara(String matchString) {
        Matcher matcher = VALID_Name_REGEX.matcher(matchString);
        return matcher.find();
    }
    public static final Pattern VALID_Number_REGEX =
            Pattern.compile("^[0-9\\s]+$", Pattern.CASE_INSENSITIVE);




    public static boolean validateMob(String emailStr) {
        Matcher matcher = VALID_Number_REGEX.matcher(emailStr);
        return matcher.find();
    }



    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class Address_Add extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url;
        ProgressHUD mProgressHUD;
        String Token;
        protected void onPreExecute(){

            if(CommonClass.Address_Edit)
            {

                url= ApiLinks.customer_address_add+Add_ID;

                CommonClass.Address_Edit=false;

            }
            else
            {

                url= ApiLinks.customer_address_add+"0";

            }

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(AddressAddActivity.this,null, true);

            Token=user.get(SessionManager.KEY_CUSTOMER_TOKEN);

        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();
            JSONObject jsonObject_tocken=new JSONObject();
            HashMap<String,String> params=new HashMap<>();
            try {

                jsonObject.put("user_id",user.get(SessionManager.KEY_CUSTOMER_ID));
                jsonObject.put("name",name.getText().toString());
                jsonObject.put("address",address.getText().toString());
                //jsonObject.put("pincode",pincode.getText().toString());
                jsonObject.put("contact",contact.getText().toString());
                jsonObject.put("longitude",longitude);
                jsonObject.put("latitude",latitude);






                JSONObject json = jsonParser.SendHttpPosts(url,"POST",jsonObject,null,Token);
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
                    Intent intent = new Intent(AddressAddActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                }
                else
                {
                    String status=json.optString("status");
                    String message=json.optString("message");


                    if(status.equals("ok")){

                        if(AddressActivityList.getInstance()!=null)
                        {
                            AddressActivityList.getInstance().finish();

                        }



                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),AddressActivityList.class);
                        startActivity(i);
                        finish();

                    }else{


                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                    }
                }





            }

            mProgressHUD.dismiss();
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()){

            case android.R.id.home:

                onBackPressed();
                return true;


        }

        return super.onOptionsItemSelected(item);


    }
    @Override
    public void  onBackPressed(){


        finish();
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
        // invalidateOptionsMenu();


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
