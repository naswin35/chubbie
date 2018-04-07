package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.Cart_show;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.ProductDetailClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.ProductsClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.AddressAddActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.CartActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.GalleryView;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.ProductsList;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Locale;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.SingleItemRowHolder> {

    private ArrayList<ProductsClass> itemsList;

    private Context mContext;



   // ArrayList<PremiumData> StoreList;
   // public static   Dialog alertDialog_PosterReq;
    public ProductsAdapter(Context context, ArrayList<ProductsClass> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)

                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)

                .build();
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
        itemsList=new ArrayList<>();
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.products_layout, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);

        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;

    }
    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        holder.setIsRecyclable(false);
        //PostedReq_Model storeModel = itemsList.get(i);
        final ProductsClass petfoods = itemsList.get(i);
        petfoods.setClick(false);




        if(Arrays.ProductItemsArray.size()==0)
        {

            holder.text_price.setText("0");

        }
        else {
            boolean data_found = false;
            for (int j = 0; j < Arrays.ProductItemsArray.size(); j++) {

                ProductDetailClass Obj = Arrays.ProductItemsArray.get(j);


                if (Obj.getProduct_id().equals(petfoods.getId())) {

                    data_found = true;

                    int count = Obj.getQuantity();

                    holder.text_price.setText("" + count);
                    break;

                }


            }


            if (!data_found) {

                holder.text_price.setText("0");

            }
        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              //  petfoods.setCount(Integer.valueOf(holder.text_price.getText().toString()));



                if(Arrays.ProductItemsArray.size()==0)
                {

                    int count=1;


                    if(Integer.parseInt(petfoods.getProduct_stock())>=count)
                    {
                        holder.text_price.setText("" + count);
                        ProductDetailClass object = new ProductDetailClass();
                        object.setQuantity(count);
                        object.setProduct_id(petfoods.getId());

                        Double amount;
                        amount = Double.parseDouble(petfoods.getProduct_price()) * count;
                        object.setPrice(String.valueOf(amount));

                        object.setImage(petfoods.getProduct_image());
                        object.setSize(petfoods.getProduct_size());
                        object.setName(petfoods.getProduct_name());
                        object.setArabic_products_detail_name(petfoods.getArabic_products_detail_name());
                        object.setNutrition(petfoods.getProduct_nutrition_contents());

                        object.setStock_size(petfoods.getProduct_stock());
                        object.setSingle_product_price(petfoods.getProduct_price());
                        //Double VatAmount=((Double.valueOf(petfoods.getProduct_price())* count)*Double.valueOf(petfoods.getVatPercentage()))/100;
                        object.setVat_percentage(petfoods.getVatPercentage());
                        object.setTotalPrice((Double.valueOf(petfoods.getProduct_price())* count));

                        Arrays.ProductItemsArray.add(object);
                    }
                    else
                    {
                        Toast.makeText(mContext, R.string.Product_out_of_stock,Toast.LENGTH_LONG).show();

                    }

                }
                else
                {
                    boolean data_found=false;
                    for (int j = 0; j <Arrays.ProductItemsArray.size() ; j++) {

                       ProductDetailClass Obj=Arrays.ProductItemsArray.get(j);
                        if(Obj.getProduct_id().equals(petfoods.getId())){

                            data_found=true;

                            int count=Obj.getQuantity();
                            count++;
                            if(Integer.parseInt(petfoods.getProduct_stock())>=count) {

                                Obj.setQuantity(count);

                                Obj.setProduct_id(petfoods.getId());

                                Double amount;
                                amount = Double.parseDouble(petfoods.getProduct_price()) * count;
                                Obj.setPrice(String.valueOf(amount));


                                Obj.setImage(petfoods.getProduct_image());
                                Obj.setSize(petfoods.getProduct_size());
                                Obj.setName(petfoods.getProduct_name());
                                Obj.setArabic_products_detail_name(petfoods.getArabic_products_detail_name());
                                Obj.setNutrition(petfoods.getProduct_nutrition_contents());
                                Obj.setStock_size(petfoods.getProduct_stock());
                                Obj.setSingle_product_price(petfoods.getProduct_price());
                                //Double VatAmount=((Double.valueOf(petfoods.getProduct_price())* count)*Double.valueOf(petfoods.getVatPercentage()))/100;
                                Obj.setVat_percentage(petfoods.getVatPercentage());
                                Obj.setTotalPrice((Double.valueOf(petfoods.getProduct_price())* count));
                                holder.text_price.setText("" + count);
                            }
                            else
                            {
                                Toast.makeText(mContext,R.string.Product_out_of_stock,Toast.LENGTH_LONG).show();

                            }
                            break;

                        }


                    }


                    if(!data_found){

                        int count=1;
                        if(Integer.parseInt(petfoods.getProduct_stock())>=count) {
                            holder.text_price.setText("" + count);
                            ProductDetailClass object = new ProductDetailClass();
                            object.setQuantity(count);

                            Double amount;
                            amount = Double.parseDouble(petfoods.getProduct_price()) * count;
                            object.setPrice(String.valueOf(amount));

                            object.setProduct_id(petfoods.getId());
                            object.setPrice(String.valueOf(amount));
                            object.setImage(petfoods.getProduct_image());
                            object.setSize(petfoods.getProduct_size());
                            object.setName(petfoods.getProduct_name());
                            object.setArabic_products_detail_name(petfoods.getArabic_products_detail_name());
                            object.setNutrition(petfoods.getProduct_nutrition_contents());
                            object.setStock_size(petfoods.getProduct_stock());
                            object.setSingle_product_price(petfoods.getProduct_price());
                           // Double VatAmount=((Double.valueOf(petfoods.getProduct_price())* count)*Double.valueOf(petfoods.getVatPercentage()))/100;
                            object.setVat_percentage(petfoods.getVatPercentage());
                            object.setTotalPrice((Double.valueOf(petfoods.getProduct_price())* count));
                            Arrays.ProductItemsArray.add(object);
                        }
                        else
                        {
                            Toast.makeText(mContext,R.string.Product_out_of_stock,Toast.LENGTH_LONG).show();

                        }

                    }




                }



                //notifyDataSetChanged();


            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if(Arrays.ProductItemsArray.size()!=0)
                {


                    boolean data_found=false;
                    for (int j = 0; j <Arrays.ProductItemsArray.size() ; j++) {

                        ProductDetailClass Obj=Arrays.ProductItemsArray.get(j);


                        if(Obj.getProduct_id().equals(petfoods.getId())){

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
                                        new Cart_show( Arrays.CartArray,mContext);
                                    }


                                    ProductsList.getInstance().invalidateOptionsMenu();
                                }

                                Double amount;
                                amount = Double.parseDouble(petfoods.getProduct_price()) * count;
                                Obj.setPrice(String.valueOf(amount));

                                Obj.setPrice(String.valueOf(amount));


                                Obj.setQuantity(count);
                                Obj.setProduct_id(petfoods.getId());

                                Obj.setSize(petfoods.getProduct_size());
                                Obj.setNutrition(petfoods.getProduct_nutrition_contents());

                               // Double VatAmount=((Double.valueOf(petfoods.getProduct_price())* count)*Double.valueOf(petfoods.getVatPercentage()))/100;
                                Obj.setVat_percentage(petfoods.getVatPercentage());
                                Obj.setTotalPrice((Double.valueOf(petfoods.getProduct_price())* count));
                                Obj.setStock_size(petfoods.getProduct_stock());
                                Obj.setSingle_product_price(petfoods.getProduct_price());
                                holder.text_price.setText(""+count);

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

        holder.add_tocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Arrays.CartArray.clear();

                if(Arrays.ProductItemsArray.size()>0)
                {

                    Arrays.CartArray.addAll(Arrays.ProductItemsArray);
                    Intent i=new Intent(mContext, CartActivity.class);
                    mContext.startActivity(i);

                    new Cart_show( Arrays.CartArray,mContext);

                }
                else
                {

                    Toast.makeText(mContext, R.string.Please_select_an_item, Toast.LENGTH_LONG).show();
                }




            }
        });


        holder.list_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!petfoods.isClick())
                {

                    holder.show.setVisibility(View.GONE);
                    holder.close.setVisibility(View.VISIBLE);
                    holder.list.setVisibility(View.VISIBLE);
                    petfoods.setClick(true);

                }
                else
                {

                    holder.close.setVisibility(View.GONE);
                    holder.show.setVisibility(View.VISIBLE);
                    holder.list.setVisibility(View.GONE);
                    petfoods.setClick(false);

                }



            }
        });
        ImageLoader.getInstance().displayImage(ApiLinks.Product_image+petfoods.getProduct_image(),holder.products_image);
        if(Global.LANG.equals("en"))
        {
            holder.products.setText(petfoods.getProduct_name());

        }
        else
        {
            holder.products.setText(petfoods.getArabic_products_detail_name());
        }


        holder.products_price.setText(petfoods.getProduct_price());
        holder.product_size.setText(petfoods.getProduct_size());
       // holder.vatpercentage.setText("( "+"VAT "+petfoods.getVatPercentage()+" %"+" )");
        String htmlstxt;
        if(Global.LANG.equals("en"))
        {
             htmlstxt=petfoods.getProduct_nutrition_contents();

        }
        else
        {
            htmlstxt=petfoods.getArabic_products_detail_nutrition_contents();
        }

        holder.nutrition.setBackgroundColor(Color.TRANSPARENT);




      String html_font_change=  "<head><style>@font-face {font-family: 'Montserrat';src: url('file://"
              + mContext.getFilesDir().getAbsolutePath()
              + "fonts/Montserrat-Light.otf');}body {font-family: 'Montserrat';}</style></head>";

        String mydata="<html><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont; src: url(\"file:///android_asset/fonts/Montserrat-LightItalic.otf\");}body {color: #fff; font-family: MyFont; font-size: 5sp;}"
                + "</style></head>"
                + "<body>"
                + htmlstxt
                + "</body></html>";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            holder.nutrition.loadDataWithBaseURL(null,mydata, "text/html; charset=utf-8", "utf-8",null); //add .toString() to remove html tags
           //add .toString() to remove html tags
        } else {
            holder.nutrition.loadDataWithBaseURL(null,mydata, "text/html; charset=utf-8", "utf-8",null); //
        }

        int stocksCount= Integer.parseInt(petfoods.getProduct_stock());
        if(stocksCount<=0){

            holder.outofstock_show.setVisibility(View.VISIBLE);
            holder.add_tocart.setVisibility(View.GONE);

        }

        if(stocksCount>0 && stocksCount<5){
            holder.stock_count_layout.setVisibility(View.VISIBLE);
            holder.stockCount.setText(String.format(Locale.ENGLISH,mContext.getString(R.string.only)+" %d "+mContext.getString(R.string.left), stocksCount));
            

        }else{

            holder.stock_count_layout.setVisibility(View.GONE);

        }

        holder.products_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i= new Intent(mContext,GalleryView.class) ;
                i.putExtra("ProductID",petfoods.getProduct_id());
                mContext.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {


        RelativeLayout fstitem;
        LinearLayout stock_count_layout;

        ImageButton add;
        ImageButton minus;
        TextView text_price;
        TextView submit;
        ImageView add_tocart;
        ImageView close;
        ImageView show;
        CardView list;
        LinearLayout list_show;
        ImageView products_image;
        TextView products;
        TextView products_price;
        TextView product_size;
        WebView nutrition;
        TextView stockCount;
        LinearLayout outofstock_show;
       // TextView vatpercentage;

        public SingleItemRowHolder(View view) {
            super(view);
            add=view.findViewById(R.id.add_button);
            minus=view.findViewById(R.id.delete_button);
            text_price=view.findViewById(R.id.text_price);
            add_tocart=view.findViewById(R.id.add_tocart);
            close=view.findViewById(R.id.close);
            show=view.findViewById(R.id.show);
            list=view.findViewById(R.id.list);
            list_show=view.findViewById(R.id.click_linear);
            products_image=view.findViewById(R.id.product_image);
            products=view.findViewById(R.id.product_name);
            products_price=view.findViewById(R.id.product_price);
            product_size=view.findViewById(R.id.product_size);
            nutrition=view.findViewById(R.id.nutrition);
            stock_count_layout=view.findViewById(R.id.stock_count_layout);
            stockCount=view.findViewById(R.id.stockCount);
            outofstock_show=view.findViewById(R.id.outofstock);


           // vatpercentage=view.findViewById(R.id.vatpercentage);

            //submit=view.findViewById(R.id.submit);



        }

    }



}