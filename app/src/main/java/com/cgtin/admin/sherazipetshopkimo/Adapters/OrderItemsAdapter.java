package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cgtin.admin.sherazipetshopkimo.Classes.OrderitemClass;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.AddressActivityList;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.OrderDetailsActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.ProductDetails;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.SingleItemRowHolder> {

    private ArrayList<OrderitemClass> itemsList;

    private Context mContext;


   // ArrayList<PremiumData> StoreList;
   // public static   Dialog alertDialog_PosterReq;
    public OrderItemsAdapter(Context context, ArrayList<OrderitemClass> itemsList) {
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

        itemsList=new ArrayList<>();
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.orderitemview, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);



        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;

    }
    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        holder.setIsRecyclable(false);
        //PostedReq_Model storeModel = itemsList.get(i);
        final OrderitemClass OrderitemClass = itemsList.get(i);

        holder.Price.setText(OrderitemClass.getProduct_price());
        holder.quantity.setText(OrderitemClass.getCustomer_order_item_quantity());
        holder.OrderStatus.setText(OrderitemClass.getOrder_status_name());
        holder.Product_name.setText(OrderitemClass.getProduct_name());
        holder.Ordered_date.setText(OrderitemClass.getCustomer_order_date());
        holder.order_size.setText(OrderitemClass.getProduct_size());


        Glide.with(mContext)
                .load(ApiLinks.Product_image+OrderitemClass.getProduct_image())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        holder.product_image.setImageBitmap(bitmap);

                    }
                });




    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        CardView itemLayout;
        TextView Price;
        TextView quantity;
        TextView Product_name;
        TextView Ordered_date;
        TextView order_size;
        TextView OrderStatus;
        ImageView product_image;

        public SingleItemRowHolder(View view) {
            super(view);

            itemLayout=view.findViewById(R.id.itemLayout);
            Price=view.findViewById(R.id.price);
            product_image=view.findViewById(R.id.product_image);
            quantity=view.findViewById(R.id.quantity);
            Ordered_date=view.findViewById(R.id.order_date);
            order_size=view.findViewById(R.id.order_size);
            OrderStatus=view.findViewById(R.id.order_status);
            Product_name=view.findViewById(R.id.item_name);
            itemLayout.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {


            int position=getAdapterPosition();

            int item=v.getId();

            switch (item){

                case R.id.itemLayout:



                    if(OrderDetailsActivity.internet_status){
                        Intent toItemDetails=new Intent(mContext,ProductDetails.class);
                        toItemDetails.putExtra("id",itemsList.get(position).getProduct_id());
                        mContext.startActivity(toItemDetails);
                    }
                    else{
                        OrderDetailsActivity.internet.setVisibility(View.VISIBLE);
                    }




            }

        }
    }



}