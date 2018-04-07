package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cgtin.admin.sherazipetshopkimo.Classes.Payment_Class;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.Payments;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.SingleItemRowHolder> {

    private ArrayList<Payment_Class> itemsList;
    private Context mContext;
    boolean array[];
    boolean array2[];
    boolean firtstime=false;

   // ArrayList<PremiumData> StoreList;
   // public static   Dialog alertDialog_PosterReq;
    public PaymentAdapter(Context context, ArrayList<Payment_Class> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;

        array =new boolean[itemsList.size()];
        array2 =new boolean[itemsList.size()];
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

        View v = LayoutInflater.from(mContext).inflate(R.layout.payment_layout, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);

        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;

    }
    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        holder.setIsRecyclable(false);
        //PostedReq_Model storeModel = itemsList.get(i);
        final Payment_Class petfoods = itemsList.get(i);
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

                holder.payment_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent i= new Intent(mContext,ProductsList.class) ;
//                mContext.startActivity(i);

                if(Payments.internet_status){

                    int position=i;

                    for (int j=0;j<array.length;j++) {
                        if (j == position) {
                            array[j] = true;

                        } else {

                            array[j]=false;
                        }
                    }

                    notifyDataSetChanged();

                }else{


                    Payments.internet.setVisibility(View.VISIBLE);

                }



            }
        });
        holder.payment_mode_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(Payments.internet_status){

                    int position=i;
                    for (int j=0;j<array.length;j++) {
                        if (j == position) {
                            array[j] = true;


                        } else {

                            array[j]=false;


                        }
                    }

                    notifyDataSetChanged();

                }else{


                    Payments.internet.setVisibility(View.VISIBLE);

                }





//                Intent i= new Intent(mContext,ProductsList.class) ;
//                mContext.startActivity(i);

            }
        });
        holder.payment_mode.setText(petfoods.getName());
        holder.payment_mode_2.setText(petfoods.getName());
        holder.payment_click.setText(petfoods.getName());
        holder.method_name_click_thrd.setText(petfoods.getName());


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

        if(array[i]){


            if(i==0)
            {


//                holder.click_layout_3rd.setVisibility(View.VISIBLE);
//                holder.fstitem.setVisibility(View.GONE);
                Payments.getInstance().Upload_Asy_Class(petfoods.getId());
                array[i]=false;

            }

            else
            {
//                holder.click_layout.setVisibility(View.VISIBLE);
//                holder.second.setVisibility(View.GONE);
                Payments.getInstance().Upload_Asy_Class(petfoods.getId());
                array[i]=false;

            }






        }


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {


        RelativeLayout fstitem;
        RelativeLayout second;
        RelativeLayout click_layout;
        RelativeLayout click_layout_3rd;
        TextView payment_mode;
        TextView payment_mode_2;
        TextView payment_click;
        TextView method_name_click_thrd;


        public SingleItemRowHolder(View view) {
            super(view);


            fstitem = view.findViewById(R.id.fstlayout);
            second = view.findViewById(R.id.second_layout);
            click_layout = view.findViewById(R.id.click_layout);
            click_layout_3rd = view.findViewById(R.id.click_layout_thrd);
            payment_mode = view.findViewById(R.id.food_name);
            payment_mode_2 = view.findViewById(R.id.food_name_2);
            payment_click = view.findViewById(R.id.method_name_click);
            method_name_click_thrd = view.findViewById(R.id.method_name_click_thrd);



        }

    }



}