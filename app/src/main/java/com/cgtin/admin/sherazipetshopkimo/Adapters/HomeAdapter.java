package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cgtin.admin.sherazipetshopkimo.Classes.HomeClass;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.Food_Accessories;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.SingleItemRowHolder> {

    private ArrayList<HomeClass> itemsList;

    private Context mContext;


    // ArrayList<PremiumData> StoreList;
    // public static   Dialog alertDialog_PosterReq;
    public HomeAdapter(Context context, ArrayList<HomeClass> itemsList) {
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

        View v = LayoutInflater.from(mContext).inflate(R.layout.home_layout, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);

        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;

    }
    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        holder.setIsRecyclable(false);
        //PostedReq_Model storeModel = itemsList.get(i);
        final HomeClass pets = itemsList.get(i);
        if(i==0)
        {

            holder.fstitem.setVisibility(View.VISIBLE);
            holder.second.setVisibility(View.GONE);


        }
        else
        {

            holder.fstitem.setVisibility(View.GONE);
            holder.second.setVisibility(View.VISIBLE);

        }


        holder.animal_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(mContext,Food_Accessories.class) ;
                i.putExtra("id",pets.getProduct_category_id());
                i.putExtra("Title",pets.getProduct_category_name());
                mContext.startActivity(i);

            }
        });
        holder.animal_pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(mContext,Food_Accessories.class) ;
                i.putExtra("id",pets.getProduct_category_id());
                i.putExtra("Title",pets.getProduct_category_name());
                mContext.startActivity(i);

            }
        });

        ImageLoader.getInstance().displayImage(ApiLinks.CommonImagePath_products_category+pets.getProduct_category_image(),holder.animal_pic);
        ImageLoader.getInstance().displayImage(ApiLinks.CommonImagePath_products_category+pets.getProduct_category_image(),holder.animal_pic2);
        holder.animal_name.setText(pets.getProduct_category_name());
        holder.animal_name_2.setText(pets.getProduct_category_name());



    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {


        RelativeLayout fstitem;
        RelativeLayout second;

        ImageButton add;
        ImageButton minus;
        TextView text_price;
        TextView animal_name;
        TextView animal_name_2;
        ImageView animal_pic;
        ImageView animal_pic2;

        public SingleItemRowHolder(View view) {
            super(view);

            //submit=view.findViewById(R.id.submit);

            fstitem = view.findViewById(R.id.fstlayout);
            second = view.findViewById(R.id.second_layout);
            animal_pic = view.findViewById(R.id.animal_pic);
            animal_pic2 = view.findViewById(R.id.animal_2);
            animal_name=view.findViewById(R.id.animal_name);
            animal_name_2=view.findViewById(R.id.animal_name_2);

        }

    }



}