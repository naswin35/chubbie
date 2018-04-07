package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cgtin.admin.sherazipetshopkimo.Classes.ProductDetailClass;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.SingleItemRowHolder> {

    private ArrayList<ProductDetailClass> itemsList;

    private Context mContext;


   // ArrayList<PremiumData> StoreList;
   // public static   Dialog alertDialog_PosterReq;
    public MyOrderAdapter(Context context, ArrayList<ProductDetailClass> itemsList) {
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

        View v = LayoutInflater.from(mContext).inflate(R.layout.cartlayout, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);



        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;

    }
    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        holder.setIsRecyclable(false);
        //PostedReq_Model storeModel = itemsList.get(i);
        final ProductDetailClass petfoods = itemsList.get(i);




//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//                Arrays.ProductItemsArray.remove(i);
//
////                if(Arrays.ProductItemsArray.size()!=0)
////                {
//
////                    for(ProductsClass details: Arrays.PetFoodArray)
////                    {
////                        if(details.getId().equals(petfoods.get(i).getId()))
////                        {
////                            details.setCount(0);
////                            break;
////                        }
////                    }
////                }
//                notifyDataSetChanged();
//
//
//            }
//        });
//        holder.Quantity.setText(""+petfoods.getCount());




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
        TextView Quantity;
        LinearLayout delete;

        public SingleItemRowHolder(View view) {
            super(view);

            delete=view.findViewById(R.id.delete);
            Quantity=view.findViewById(R.id.quantity);


        }

    }



}