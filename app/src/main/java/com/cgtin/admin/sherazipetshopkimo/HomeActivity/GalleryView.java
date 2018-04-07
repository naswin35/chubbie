package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Adapters.GalleryAdapter;
import com.cgtin.admin.sherazipetshopkimo.Adapters.item_view_selection_adapter;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.ImageWrapper;
import com.cgtin.admin.sherazipetshopkimo.Classes.ItemImage;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.newViewpager;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscription;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GalleryView extends AppCompatActivity {


    private GalleryAdapter adapter;
    public static newViewpager viewPager;
    ArrayList<ItemImage> imageArrayList;

    public  static LinearLayout thumbs;
    public static HorizontalScrollView hsc;
    TextView mTitle;




    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;
    SessionManager session;
    HashMap<String,String>user;

    String ProductID;


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTitle.setText(R.string.product_images);

        viewPager = (newViewpager) findViewById(R.id.pager);
        thumbs= (LinearLayout) findViewById(R.id.thumbnails);
        hsc= (HorizontalScrollView) findViewById(R.id.horizontal);
        imageArrayList=new ArrayList<>();

        session=new SessionManager(getApplicationContext());
        user=session.getUserDetails();

        Intent getData=getIntent();

        ProductID=getData.getStringExtra("ProductID");



        new getImageList().execute();




    }


    private class getImageList extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();
        String url= ApiLinks.productImages+ProductID;
        ProgressHUD mProgressHUD;
        String Token;
        protected void onPreExecute(){

            super.onPreExecute();
            mProgressHUD = ProgressHUD.show(GalleryView.this,null, true);
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


                String status=json.optString("status");
                String message=json.optString("message");

                if(status.equals("ok")){


                    JSONObject jsonObject=json.optJSONObject("data");
                    JSONArray ImageArray=jsonObject.optJSONArray("product_images");

                    imageArrayList.clear();
                    if(ImageArray!=null && ImageArray.length()>0){

                        for (int k = 0; k < ImageArray.length(); k++) {
                            JSONObject ImageObj = ImageArray.optJSONObject(k);
                            String id = ImageObj.optString("product_image_id");
                            String image = ImageObj.optString("product_image_image");

                            ItemImage ItemImages = new ItemImage();
                            ItemImages.setId(id);

                            ItemImages.setImage(ApiLinks.Product_image + image);

                            imageArrayList.add(ItemImages);
                        }


                    }

                    if(imageArrayList.size()!=0) {

                        adapter = new GalleryAdapter(GalleryView.this,imageArrayList);
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(6);
                    }



                }else{

                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                }

            }

            mProgressHUD.dismiss();
        }


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
}