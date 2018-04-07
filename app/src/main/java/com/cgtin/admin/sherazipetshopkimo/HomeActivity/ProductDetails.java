package com.cgtin.admin.sherazipetshopkimo.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Adapters.item_view_selection_adapter;
import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.Cart_show;
import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.ImageWrapper;
import com.cgtin.admin.sherazipetshopkimo.Classes.ItemImage;
import com.cgtin.admin.sherazipetshopkimo.Classes.ProductDetailClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.cgtin.admin.sherazipetshopkimo.StartActivities.LoginActivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProductDetails extends AppCompatActivity {

    TextView Pprice;
    TextView Psize;
    WebView Pnutrition;
    TextView Pname;
    TextView mTitle;
    TextView VatPer;
    TextView Quantity_Show;
    LinearLayout list_show;
    TextView stockCount;
    public static ImageView Pimage;
    ImageButton add_button;

    RecyclerView contents_items;
    ArrayList<ItemImage> image_list;

    String P_ID;
    HashMap<String,String> user;
    SessionManager session;

    boolean internet_status;
    private Subscription internetConnectivitySubscription;
    LinearLayout internet;
    String product_id;
    String product_size;
    String product_price;
    String product_image;
    String product_name;
    String arabic_products_detail_name;
    String product_nutrition_contents;
    String arabic_products_detail_nutrition_contents;
    String product_stock;
    String VatPercentage;
    ImageView add_tocart;
    ImageView delete_button;
    ImageView close;
    ImageView show;
    CardView list;
    boolean clicked;
    LinearLayout stock_count_layout;
    LinearLayout outofstock_show;
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_tool);

        getSupportActionBar().setDisplayShowTitleEnabled(false);



        Intent i=getIntent();
        P_ID=i.getStringExtra("id");
        mTitle.setText(R.string.product_details);
        final Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Montserrat-Light.otf");
        mTitle.setTypeface(face);


        Pimage=findViewById(R.id.product_image);
        Pprice=findViewById(R.id.product_price);
        Psize=findViewById(R.id.product_size);
        Pnutrition=findViewById(R.id.nutrition);
        Pname=findViewById(R.id.product_name);
        contents_items= (RecyclerView) findViewById(R.id.contents_items);
        Quantity_Show=findViewById(R.id.text_price);
        add_button=findViewById(R.id.add_button);
        add_tocart=findViewById(R.id.add_tocart);
        delete_button=findViewById(R.id.delete_button);
        list_show=findViewById(R.id.click_linear);
        close=findViewById(R.id.close);
        show=findViewById(R.id.show);
        list=findViewById(R.id.list);
        stockCount=findViewById(R.id.stockCount);
        // VatPer=findViewById(R.id.vatpercentage);

//        size=i.getStringExtra("size");
//        name=i.getStringExtra("name");
//        item_price=i.getStringExtra("item_price");
//        image=i.getStringExtra("image");
//        nutrition=i.getStringExtra("nutrition");
        outofstock_show=findViewById(R.id.outofstock);
        stock_count_layout=findViewById(R.id.stock_count_layout);
        internet=(LinearLayout)findViewById(R.id.internet);
        session=new SessionManager(getApplicationContext());
        user=session.getUserDetails();

        Pnutrition.setBackgroundColor(Color.TRANSPARENT);
        image_list=new ArrayList<ItemImage>();
        clicked=false;

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext().getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);



//        Psize.setText(size);
//        ImageLoader.getInstance().displayImage(image,Pimage);
//        Pname.setText(name);
//        Pprice.setText(item_price);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            Pnutrition.setText(Html.fromHtml(nutrition, Html.FROM_HTML_MODE_LEGACY)); //add .toString() to remove html tags
//        } else {
//            Pnutrition.setText(Html.fromHtml(nutrition));
//        }


        Pimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent GoGlallery=new Intent(ProductDetails.this,GalleryView.class);
                GoGlallery.putExtra("ProductID",P_ID);
                startActivity(GoGlallery);


            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // int count=petfoods.getQuantity();
               // count++;

                    boolean fount=false;
                    ProductDetailClass DetailObj = null;
                    int count = 0;
                    for(ProductDetailClass Detail:Arrays.ProductItemsArray)
                    {

                        if(Detail.getProduct_id().equals(product_id))
                        {

                            DetailObj=Detail;
                            fount=true;
                            break;

                        }

                    }
                    if(fount)
                    {
                        count = DetailObj.getQuantity();
                        count++;
                        if(Integer.parseInt(product_stock)>=count) {


                            DetailObj.setQuantity(count);
                            Quantity_Show.setText("" + count);


                            Double amount;
                            amount = Double.parseDouble(product_price) * count;
                            DetailObj.setPrice(String.valueOf(amount));

                            DetailObj.setImage(product_image);
                            DetailObj.setSize(product_size);
                            DetailObj.setName(product_name);
                            DetailObj.setArabic_products_detail_name(arabic_products_detail_name);
                            DetailObj.setNutrition(product_nutrition_contents);
                            DetailObj.setStock_size(product_stock);
                            DetailObj.setSingle_product_price(product_price);
                            //Double VatAmount=((Double.valueOf(petfoods.getProduct_price())* counts)*Double.valueOf(petfoods.getVatPercentage()))/100;
                            DetailObj.setVat_percentage(VatPercentage);
                            DetailObj.setTotalPrice((Double.valueOf(product_price) * count));

                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(),R.string.Product_out_of_stock,Toast.LENGTH_LONG).show();

                        }
                    }
                    else
                    {

//                        count++;
//                        DetailObj.setQuantity(count);
//                        Quantity_Show.setText(""+count);
                        int counts=1;
                        if(Integer.parseInt(product_stock)>=counts) {

                            Quantity_Show.setText("" + counts);
                            ProductDetailClass object = new ProductDetailClass();
                            object.setQuantity(counts);
                            object.setProduct_id(product_id);

                            Double amount;
                            amount = Double.parseDouble(product_price) * counts;
                            object.setPrice(String.valueOf(amount));

                            object.setImage(product_image);
                            object.setSize(product_size);
                            object.setName(product_name);
                            object.setArabic_products_detail_name(arabic_products_detail_name);
                            object.setNutrition(product_nutrition_contents);
                            object.setStock_size(product_stock);
                            object.setSingle_product_price(product_price);
                            //Double VatAmount=((Double.valueOf(petfoods.getProduct_price())* counts)*Double.valueOf(petfoods.getVatPercentage()))/100;
                            object.setVat_percentage(VatPercentage);
                            object.setTotalPrice((Double.valueOf(product_price) * counts));

                            Arrays.ProductItemsArray.add(object);


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),R.string.Product_out_of_stock,Toast.LENGTH_LONG).show();

                        }

                    }

                }


        });

        list_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!clicked)
                {

                    show.setVisibility(View.GONE);
                    close.setVisibility(View.VISIBLE);
                    list.setVisibility(View.VISIBLE);
                    clicked=true;

                }
                else
                {

                    close.setVisibility(View.GONE);
                    show.setVisibility(View.VISIBLE);
                    list.setVisibility(View.GONE);
                    clicked=false;

                }




            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if(Arrays.ProductItemsArray.size()!=0)
                {


                    boolean data_found=false;
                    for (int j = 0; j <Arrays.ProductItemsArray.size() ; j++) {

                        ProductDetailClass Obj=Arrays.ProductItemsArray.get(j);


                        if(Obj.getProduct_id().equals(product_id)){

                            data_found=true;

                            int count=Obj.getQuantity();
                            if(count!=0 && count>0)
                            {
                                count--;
                                if(count<1)
                                {
                                    Arrays.ProductItemsArray.remove(j) ;
                                    if(Arrays.CartArray.size()!=0)
                                    {
                                        Arrays.CartArray.remove(j) ;
                                        new Cart_show( Arrays.CartArray,getApplicationContext());
                                    }



                                }

                                Double amount;
                                amount = Double.parseDouble(product_price) * count;
                                Obj.setPrice(String.valueOf(amount));

                                Obj.setPrice(String.valueOf(amount));


                                Obj.setQuantity(count);
                                Obj.setProduct_id(product_id);

                                Obj.setSize(product_size);
                                Obj.setNutrition(product_nutrition_contents);

                                // Double VatAmount=((Double.valueOf(petfoods.getProduct_price())* count)*Double.valueOf(petfoods.getVatPercentage()))/100;
                                Obj.setVat_percentage(VatPercentage);
                                Obj.setTotalPrice((Double.valueOf(product_price)* count));
                                Obj.setStock_size(product_stock);
                                Obj.setSingle_product_price(product_price);
                                Quantity_Show.setText("" + count);


                                break;

                            }

                        }


                    }


//                    if(!data_found){
//
//                        int count=0;
//
//                        count--;
//
//                        holder.text_price.setText(""+count);
//
//
//
//                        ProductDetailClass object=new ProductDetailClass();
//
//                        object.setCount(Integer.valueOf(holder.text_price.getText().toString()));
//                        object.setId(petfoods.getId());
//                        Arrays.ProductItemsArray.add(object);
//
//                    }




                }





            }
        });




        add_tocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Arrays.CartArray.clear();
                if(Arrays.ProductItemsArray.size()>0)
                {

                    Arrays.CartArray.addAll(Arrays.ProductItemsArray);
                    Intent i=new Intent(getApplicationContext(), CartActivity.class);
                    startActivity(i);
                    new Cart_show( Arrays.CartArray,getApplicationContext());

                }
                else
                {

                    Toast.makeText(getApplicationContext(), R.string.Please_select_an_item, Toast.LENGTH_LONG).show();
                }




            }
        });





    }

    private class vat extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();

        String url= ApiLinks.orderSettings;

        ProgressHUD mProgressHUD;
        String Token;
        protected void onPreExecute(){

            super.onPreExecute();

            mProgressHUD = ProgressHUD.show(ProductDetails.this,null, true);
            Token=user.get(SessionManager.KEY_CUSTOMER_TOKEN);

        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();

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

                    session.logoutUser();
                    Intent intent = new Intent(ProductDetails.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                }

                {
                    String status = json.optString("status");
                    String message = json.optString("message");


                    if (status.equals("ok")) {

                        JSONObject jsonObject = json.optJSONObject("data");
                        JSONObject order_settings = jsonObject.optJSONObject("order_settings");
                        String vatPercentage = order_settings.optString("vat_percentage");
                        String shipping_amount = order_settings.optString("shipping_amount");
                        CommonClass.DeliveryCharge = shipping_amount;
                        CommonClass.VATpercentage = vatPercentage;
                        VatPercentage = vatPercentage;
                        if(internet_status){
                            //snackbar1.dismiss();
                            internet.setVisibility(View.GONE);
                            new Product_Item().execute();
                        }
                        else
                        {
                            internet.setVisibility(View.VISIBLE);
                        }

                    } else {


                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                }

            }

            mProgressHUD.dismiss();
        }


    }







    private class Product_Item extends AsyncTask<String, Void, JSONObject> {

        JSONParser jsonParser = new JSONParser();
        String url= ApiLinks.productShow+P_ID;
        ProgressHUD mProgressHUD;
        String Token;
        protected void onPreExecute(){

            super.onPreExecute();
            mProgressHUD = ProgressHUD.show(ProductDetails.this,null, true);
            Token=user.get(SessionManager.KEY_CUSTOMER_TOKEN);
        }

        protected JSONObject doInBackground(String... arg0) {


            JSONObject jsonObject=new JSONObject();
            JSONObject jsonObject_tocken=new JSONObject();
            HashMap<String,String> params=new HashMap<>();


            try {
//                jsonObject.put("product_category_id",category_id);
//                jsonObject.put("brand_id","0");
                if(Global.LANG.equals("en"))
                {
                    jsonObject.put("language_id", "0");

                }
                else
                {
                    jsonObject.put("language_id", "0");
                }

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


                String status=json.optString("status");
                String message=json.optString("message");

                if(status.equals("ok")){

                    JSONObject jsonObject=json.optJSONObject("data");
                    JSONObject product=jsonObject.optJSONObject("product");
                    product_size=product.optString("product_size");
                    product_name=product.optString("english_products_detail_name");
                    arabic_products_detail_name=product.optString("arabic_products_detail_name");
                    product_price=product.optString("product_price");
                    product_nutrition_contents=product.optString("english_products_detail_nutrition_contents");
                    arabic_products_detail_nutrition_contents=product.optString("arabic_products_detail_nutrition_contents");
                    product_image=product.optString("product_image");
                    product_stock=product.optString("product_stock");
                    product_id=product.optString("product_id");







                    Psize.setText(product_size);
                    ImageLoader.getInstance().displayImage(ApiLinks.Product_image+product_image,Pimage);

                    if(Global.LANG.equals("en"))
                    {
                        Pname.setText(product_name);

                    }
                    else
                    {
                        Pname.setText(arabic_products_detail_name);
                    }

                    Pprice.setText(product_price);
                    //  VatPer.setText("( "+"VAT "+CommonClass.VATpercentage+" %"+" )");


                    if(Integer.parseInt(product_stock)<=0){

                        outofstock_show.setVisibility(View.VISIBLE);
                        add_tocart.setVisibility(View.GONE);

                    }

                    if(Integer.parseInt(product_stock)>0 && Integer.parseInt(product_stock)<5){
                        stock_count_layout.setVisibility(View.VISIBLE);
                        stockCount.setText(String.format(Locale.ENGLISH,getApplicationContext().getString(R.string.only)+" %d "+getApplicationContext().getString(R.string.left), Integer.parseInt(product_stock)));


                    }else{

                        stock_count_layout.setVisibility(View.GONE);

                    }
                    String mydata;
                    if(Global.LANG.equals("en"))
                    {
                         mydata="<html><head>"
                                + "<style type=\"text/css\">@font-face {font-family: MyFont; src: url(\"file:///android_asset/fonts/Montserrat-LightItalic.otf\");}body {color: #fff; font-family: MyFont; font-size: 5sp;}"
                                + "</style></head>"
                                + "<body>"
                                + product_nutrition_contents
                                + "</body></html>";

                    }
                    else
                    {
                         mydata="<html><head>"
                                + "<style type=\"text/css\">@font-face {font-family: MyFont; src: url(\"file:///android_asset/fonts/Montserrat-LightItalic.otf\");}body {color: #fff; font-family: MyFont; font-size: 5sp;}"
                                + "</style></head>"
                                + "<body>"
                                + arabic_products_detail_nutrition_contents
                                + "</body></html>";
                    }




                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                        Pnutrition.loadDataWithBaseURL(null,mydata, "text/html; charset=utf-8", "utf-8",null); //add .toString() to remove html tags
                        //add .toString() to remove html tags
                    } else {
                        Pnutrition.loadDataWithBaseURL(null,mydata, "text/html; charset=utf-8", "utf-8",null); //
                    }





                    JSONArray ImageArray=jsonObject.optJSONArray("product_images");

                    image_list.clear();
                    if(ImageArray!=null && ImageArray.length()>0){

                        for (int k = 0; k < ImageArray.length(); k++) {
                            JSONObject ImageObj = ImageArray.optJSONObject(k);
                            String id = ImageObj.optString("product_image_id");
                            String image = ImageObj.optString("product_image_image");

                            ItemImage ItemImages = new ItemImage();
                            ItemImages.setId(id);

                            ItemImages.setImage(ApiLinks.Product_image + image);

                            image_list.add(ItemImages);
                        }


                    }

                    if(image_list.size()!=0) {

                        item_view_selection_adapter adapter = new item_view_selection_adapter(ProductDetails.this, image_list);
                        contents_items.setAdapter(adapter);
                        contents_items.setHasFixedSize(true);
                        contents_items.setLayoutManager(new LinearLayoutManager(ProductDetails.this, LinearLayoutManager.HORIZONTAL, false));
                        adapter.notifyDataSetChanged();
                    }



                    if(Arrays.ProductItemsArray.size()>0) {

                        boolean fount=false;
                        ProductDetailClass DetailObj = null;


                            for (ProductDetailClass Detail : Arrays.ProductItemsArray) {

                                if (Detail.getProduct_id().equals(product_id)) {

                                    DetailObj = Detail;
                                    fount = true;
                                    break;

                                }

                            }
                            if (fount) {
                                Quantity_Show.setText("" + DetailObj.getQuantity());
                            }
                            else
                            {
                                Quantity_Show.setText("0");
                            }

                        }
                    else
                    {
                        Quantity_Show.setText("0");
                    }

                }else{

                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
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
                            new vat().execute();
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
