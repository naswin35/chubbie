package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cgtin.admin.sherazipetshopkimo.Classes.PetFoodClass;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.Brands;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class PetFoodAdapter extends RecyclerView.Adapter<PetFoodAdapter.SingleItemRowHolder> {

    private ArrayList<PetFoodClass> itemsList;
    private Context mContext;


   // ArrayList<PremiumData> StoreList;
   // public static   Dialog alertDialog_PosterReq;
    public PetFoodAdapter(Context context, ArrayList<PetFoodClass> itemsList) {
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

        View v = LayoutInflater.from(mContext).inflate(R.layout.petfood, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);

        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;

    }
    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        holder.setIsRecyclable(false);
        //PostedReq_Model storeModel = itemsList.get(i);
        final PetFoodClass petfoods = itemsList.get(i);
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

//        holder.Name.setText(storeModel.getReqName());
//        holder.Email.setText(storeModel.getReqEmail());
//        holder.Phone.setText(storeModel.getReqPhone());

//        holder.edit_req.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Common_Global.PostedReq_ID=storeModel.getReqID();
//                Intent DetailsAction=new Intent(mContext, PostingRequirmentWeb.class);
//                mContext.startActivity(DetailsAction);
//                EditReq=true;
//            }
//        });
//        holder.delete_Req.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Common_Global.PostedReq_ID=storeModel.getReqID();
//                Delete_Posted_Req();
//
//            }
//        });

                holder.animal_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(mContext,Brands.class) ;
                i.putExtra("id",petfoods.getProduct_category_id());
                mContext.startActivity(i);

            }
        });
        holder.animal_pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(mContext,Brands.class) ;
                i.putExtra("id",petfoods.getProduct_category_id());
                mContext.startActivity(i);

            }
        });
        holder.food_name.setText(petfoods.getProduct_category_name());
        holder.food_name_2.setText(petfoods.getProduct_category_name());


//        if(i==itemsList.size()-1 && CommonClass.per_page==PetFood.postedReq_adapter.getItemCount()){
//
//            holder.view_more.setVisibility(View.GONE);
//
//        }else if(i==itemsList.size()-1 && PetFood.postedReq_adapter.getItemCount()< CommonClass.per_page){
//
//            holder.view_more.setVisibility(View.VISIBLE);
//
//            holder.view_more.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    PetFood.getInstance().petfoodjson(PetFood.pet_id_int++);
//                    holder.view_more.setVisibility(View.GONE);
//
//                }
//            });
//        }





    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {


        RelativeLayout fstitem;
        RelativeLayout second;
        TextView food_name;
        TextView food_name_2;
        ImageView animal_pic;
        ImageView animal_pic2;

        public SingleItemRowHolder(View view) {
            super(view);


            fstitem = view.findViewById(R.id.fstlayout);
            second = view.findViewById(R.id.second_layout);
            food_name = view.findViewById(R.id.food_name);
            food_name_2 = view.findViewById(R.id.food_name_2);
            animal_pic = view.findViewById(R.id.animal_pic);
            animal_pic2 = view.findViewById(R.id.animal_2);


        }

    }



}