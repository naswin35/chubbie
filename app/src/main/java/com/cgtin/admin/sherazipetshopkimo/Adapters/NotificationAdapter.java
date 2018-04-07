package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Classes.NotificationClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.OrderListModel;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.JSONParser;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ProgressHUD;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.NotificatinDetails;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.NotificationActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.OrderDetailsActivity;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.SingleItemRowHolder> {

    private ArrayList<NotificationClass> itemsList;

    private Context mContext;

    SessionManager session;
    HashMap<String,String>Userdetails;


    // ArrayList<PremiumData> StoreList;
    // public static   Dialog alertDialog_PosterReq;
    public NotificationAdapter(Context context, ArrayList<NotificationClass> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        session=new SessionManager(mContext);
        Userdetails=session.getUserDetails();


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);


    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.notification_layout, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);



        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;

    }
    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        holder.setIsRecyclable(false);
        //PostedReq_Model storeModel = itemsList.get(i);
        final NotificationClass petfoods = itemsList.get(i);

        holder.Notify_Title.setText(petfoods.getNotifications_detail_title());
        holder.Notify_Description.setText(petfoods.getNotifications_detail_description());
        holder.Notify_date.setText(petfoods.getNotification_created_at());

        if(petfoods.getNotification_status().equals("unread")){

            holder.Notify_Image.setVisibility(View.VISIBLE);
        }else{

            holder.Notify_Image.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        TextView Notify_Title;
        TextView Notify_Description;
        TextView Notify_date;
        ImageView Notify_Image;
        LinearLayout ItemLayout;


        public SingleItemRowHolder(View view) {
            super(view);

            Notify_Title=view.findViewById(R.id.notify_title);
            Notify_Description=view.findViewById(R.id.notify_description);
            Notify_date=view.findViewById(R.id.notify_date);
            Notify_Image=view.findViewById(R.id.notify_status);
            ItemLayout=view.findViewById(R.id.ItemLayout);
            ItemLayout.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {


            int position=getAdapterPosition();

            int item=v.getId();

            switch (item){

                case R.id.ItemLayout:




                    if(itemsList.get(position).getNotification_type().equals("order")){


                        Intent toDetails=new Intent(mContext, OrderDetailsActivity.class);

                        toDetails.putExtra("type","notification");
                        toDetails.putExtra("OrderID",itemsList.get(position).getCustomer_order_id());
                        toDetails.putExtra("status",itemsList.get(position).getNotification_status());
                        toDetails.putExtra("notifyID",itemsList.get(position).getNotification_id());
                        mContext.startActivity(toDetails);
                    }

                    if(itemsList.get(position).getNotification_status().equals("unread")){

                        itemsList.get(position).setNotification_status("read");
                        notifyDataSetChanged();
                    }



                    break;
            }

        }
    }






}