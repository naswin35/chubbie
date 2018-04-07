package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cgtin.admin.sherazipetshopkimo.Classes.BrandClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.Brands;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.ProductsList;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import static com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass.froms;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.SingleItemRowHolder> {

    private ArrayList<BrandClass> itemsList;

    private Context mContext;


    // ArrayList<PremiumData> StoreList;
    // public static   Dialog alertDialog_PosterReq;
    public BrandAdapter(Context context, ArrayList<BrandClass> itemsList) {
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

        View v = LayoutInflater.from(mContext).inflate(R.layout.brand, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);

        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;

    }
    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        holder.setIsRecyclable(false);
        //PostedReq_Model storeModel = itemsList.get(i);
        final BrandClass brands = itemsList.get(i);
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
//          if(items!=null)
//          {
//
//              final ProductDetailClass product = items.get(i);
//          }



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

        holder.brand_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(mContext,ProductsList.class) ;
                i.putExtra("id",brands.getProduct_brand_id());
                mContext.startActivity(i);

            }
        });
        holder.brand_image_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(mContext,ProductsList.class) ;
                i.putExtra("id",brands.getProduct_brand_id());
                mContext.startActivity(i);

            }
        });



        if(i==itemsList.size()-1 && froms< CommonClass.last_page_food)
        {

            holder.view_more.setVisibility(View.VISIBLE);

        }

        holder.view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Brands.getIntance().brand_async();
                holder.view_more.setVisibility(View.GONE);

            }
        });




        ImageLoader.getInstance().displayImage(ApiLinks.Brand_Image+brands.getBrand_logo(),holder.brand_image);
        ImageLoader.getInstance().displayImage(ApiLinks.Brand_Image+brands.getBrand_logo(),holder.brand_image_2);

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
        TextView submit;
        ImageView brand_image;
        ImageView brand_image_2;
        Button view_more;

        public SingleItemRowHolder(View view) {
            super(view);

            //submit=view.findViewById(R.id.submit);

            fstitem = view.findViewById(R.id.fstlayout);
            second = view.findViewById(R.id.second_layout);
            brand_image = view.findViewById(R.id.brand_image);
            brand_image_2 = view.findViewById(R.id.brand_image_2);
            view_more = view.findViewById(R.id.view_more);
        }

    }



}