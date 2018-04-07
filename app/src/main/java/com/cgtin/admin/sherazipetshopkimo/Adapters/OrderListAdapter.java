package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cgtin.admin.sherazipetshopkimo.Classes.OrderListModel;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.OrderDetailsActivity;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.SingleItemRowHolder> {

    private ArrayList<OrderListModel> itemsList;

    private Context mContext;


   // ArrayList<PremiumData> StoreList;
   // public static   Dialog alertDialog_PosterReq;
    public OrderListAdapter(Context context, ArrayList<OrderListModel> itemsList) {
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

        View v = LayoutInflater.from(mContext).inflate(R.layout.orderlayout, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);



        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;

    }
    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        holder.setIsRecyclable(false);
        //PostedReq_Model storeModel = itemsList.get(i);
        final OrderListModel petfoods = itemsList.get(i);

        holder.TotalPrice.setText(String.format("%s%s", mContext.getString(R.string.total_price), petfoods.getCustomer_order_total_price()));
        ImageLoader.getInstance().displayImage(ApiLinks.Product_image+petfoods.getProductImage(),holder.product_image);
        holder.Size.setText(String.format("%s - %s",mContext.getString(R.string.ordered_on), petfoods.getCustomer_order_created_at()));
        holder.Name.setText(String.format("%s%s", mContext.getString(R.string.orderid), petfoods.getCustomer_order_id()));

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        LinearLayout itemLayout;
        TextView TotalPrice;
        TextView Size;
        TextView Name;

        ImageView product_image;

        public SingleItemRowHolder(View view) {
            super(view);

            itemLayout=view.findViewById(R.id.itemLayout);
            TotalPrice=view.findViewById(R.id.price);
            product_image=view.findViewById(R.id.product_image);
            Size=view.findViewById(R.id.size);
            Name=view.findViewById(R.id.item_name);
            itemLayout.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {


            int position=getAdapterPosition();

            int item=v.getId();

            switch (item){

                case R.id.itemLayout:

                    Intent toItemDetails=new Intent(mContext,OrderDetailsActivity.class);
                    toItemDetails.putExtra("OrderID",itemsList.get(position).getCustomer_order_id());
                    toItemDetails.putExtra("type","order");
                    mContext.startActivity(toItemDetails);

            }

        }
    }



}