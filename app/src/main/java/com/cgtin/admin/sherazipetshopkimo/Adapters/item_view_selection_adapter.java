package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cgtin.admin.sherazipetshopkimo.Classes.ItemImage;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.ProductDetails;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;




public class item_view_selection_adapter extends RecyclerView.Adapter<item_view_selection_adapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ItemImage> mModel;
    public static int mActiveUserPosition = -1;
    public static ItemImage selectedTimesdialog;
    boolean array[];

    boolean firstload=true;



    public item_view_selection_adapter(Context mContexts, ArrayList<ItemImage> model) {

        mContext = mContexts;
        mModel=model;
        array =new boolean[mModel.size()];


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
    public item_view_selection_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_selection, parent, false);
        return new ViewHolder(view);
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout item_border_view_image;
        public ImageView itemImge;


        public ViewHolder(View view) {
            super(view);
            item_border_view_image = (LinearLayout) view.findViewById(R.id.item_border_view_image);
            itemImge= (ImageView) view.findViewById(R.id.item_view_image);
            item_border_view_image.setOnClickListener(this);




        }

        @Override
        public void onClick(View v) {


            int position = getAdapterPosition();

            switch (v.getId()) {

                case R.id.item_border_view_image:


                    for (int i=0;i<array.length;i++) {
                        if (i == position) {
                            array[i] = true;
                            mActiveUserPosition = position;
                            ImageLoader.getInstance().displayImage(mModel.get(position).getImage(), ProductDetails.Pimage);
                            notifyDataSetChanged();
                            selectedTimesdialog=mModel.get(position);



                        } else {

                            array[i]=false;


                        }
                    }
            }
        }
    }


    @Override
    public void onBindViewHolder(item_view_selection_adapter.ViewHolder holder, final int position) {

        if(position==0 && firstload){

            holder.item_border_view_image.setBackgroundResource(R.drawable.item_selected_bg);
            ImageLoader.getInstance().displayImage(mModel.get(position).getImage(),holder.itemImge);

            ImageLoader.getInstance().displayImage(mModel.get(position).getImage(),ProductDetails.Pimage);
            firstload=false;

        }

        else if(array[position]){

            holder.item_border_view_image.setBackgroundResource(R.drawable.item_selected_bg);
            ImageLoader.getInstance().displayImage(mModel.get(position).getImage(),holder.itemImge);


        }
        else if(!array[position]){

            holder.item_border_view_image.setBackgroundResource(R.drawable.item_unselected_bg);
            ImageLoader.getInstance().displayImage(mModel.get(position).getImage(),holder.itemImge);

        }
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }


}
