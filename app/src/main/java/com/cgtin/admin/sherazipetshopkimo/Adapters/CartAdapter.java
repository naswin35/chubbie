package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtin.admin.sherazipetshopkimo.Classes.AfterPay;
import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.Cart_show;
import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.ProductDetailClass;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.AddressActivityList;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.CartActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.ProductDetails;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.ProductsList;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.SingleItemRowHolder> {

    private ArrayList<ProductDetailClass> itemsList;

    private Context mContext;
    Dialog alertDialog;
    public static boolean found=false;
   // ArrayList<PremiumData> StoreList;
   // public static   Dialog alertDialog_PosterReq;
    public CartAdapter(Context context, ArrayList<ProductDetailClass> itemsList) {
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


        //boolean found=false;
        if(Arrays.AfterPayArray.size()!=0)
        {


           for(AfterPay pay:Arrays.AfterPayArray)
           {

              if( pay.getProduct_id().equals(petfoods.getProduct_id()))
               {
                   holder.outofstock.setVisibility(View.VISIBLE);
                   found=true;

                   //holder.conunt_number.setVisibility(View.GONE);
                   break;
               }

           }



        }



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                delete_fnction_property(i,holder.outofstock);
//                if(Arrays.ProductItemsArray.size()!=0)
//                {

//                    for(ProductsClass details: Arrays.PetFoodArray)
//                    {
//                        if(details.getId().equals(petfoods.get(i).getId()))
//                        {
//                            details.setCount(0);
//                            break;
//                        }
//                    }
//                }


            }
        });

        holder.cartitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CartActivity.internet_status){
                    Intent i= new Intent(mContext,ProductDetails.class) ;
                    i.putExtra("id",petfoods.getProduct_id());
                    mContext.startActivity(i);
                }
                else{
                    CartActivity.internet.setVisibility(View.VISIBLE);
                }

            }
        });

        holder.plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count=petfoods.getQuantity();
                count++;
                if(Integer.parseInt(petfoods.getStock_size())>=count) {

                    petfoods.setQuantity(count);
                    holder.Quantity.setText(""+petfoods.getQuantity());
                    petfoods.setTotalPrice((Double.valueOf(petfoods.getSingle_product_price())* count));
                    Double amount;
                    amount = Double.parseDouble(petfoods.getSingle_product_price()) * count;
                    petfoods.setPrice(String.valueOf(amount));
                    holder.Item_Price.setText(String.format(Locale.ENGLISH,"%.02f", petfoods.getTotalPrice()));
                    new Cart_show( Arrays.CartArray,mContext);

                }

                else
                {
                    Toast.makeText(mContext,R.string.Product_out_of_stock,Toast.LENGTH_LONG).show();

                }


            }
        });

        holder.minus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count=petfoods.getQuantity();
                if(count!=0 && count>0)
                {
                    count--;
                    if(count<1)
                    {
                        Arrays.ProductItemsArray.remove(i);
                        Arrays.CartArray.remove(i);
                        notifyDataSetChanged();
                        adapter_check();

                        ProductsList.getInstance().invalidateOptionsMenu();
                    }
                    petfoods.setQuantity(count);
                    Double amount;
                    petfoods.setTotalPrice((Double.valueOf(petfoods.getSingle_product_price())* count));
                    amount = Double.parseDouble(petfoods.getSingle_product_price()) * count;
                    petfoods.setPrice(String.valueOf(amount));
                    holder.Quantity.setText(""+petfoods.getQuantity());
                    holder.Item_Price.setText(String.format(Locale.ENGLISH,"%.02f", petfoods.getTotalPrice()));
                    new Cart_show( Arrays.CartArray,mContext);

                }

            }
        });



        holder.Quantity.setText(""+petfoods.getQuantity());
        ImageLoader.getInstance().displayImage(ApiLinks.Product_image+petfoods.getImage(),holder.product_image);
        holder.Size.setText(petfoods.getSize());

        if(Global.LANG.equals("en"))
        {
            holder.Name.setText(petfoods.getName());

        }
        else
        {
            holder.Name.setText(petfoods.getArabic_products_detail_name());
        }


        holder.Item_Price.setText(String.format(Locale.ENGLISH,"%.02f", petfoods.getTotalPrice()));
      //  holder.VatShow.setText(""+petfoods.getVat_percentage()+" %");


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
        TextView Size;
        TextView Name;
        TextView Item_Price;
        TextView VatShow;

        TextView Quantity;
        LinearLayout delete;
        ImageView product_image;
        CardView cartitem;
        ImageButton plus_button;
        ImageButton minus_button;
        LinearLayout outofstock;
       // LinearLayout conunt_number;

        public SingleItemRowHolder(View view) {
            super(view);

            delete=view.findViewById(R.id.delete);
            Quantity=view.findViewById(R.id.quantity);
            product_image=view.findViewById(R.id.product_image);
            Size=view.findViewById(R.id.size);
            Name=view.findViewById(R.id.item_name);
            Item_Price=view.findViewById(R.id.item_price);
            cartitem=view.findViewById(R.id.cart_item);
            plus_button=view.findViewById(R.id.add_button);
            minus_button=view.findViewById(R.id.delete_button);
            outofstock=view.findViewById(R.id.outofstock);
            //conunt_number=view.findViewById(R.id.conunt_number);
            //VatShow=view.findViewById(R.id.vatshow);



        }

    }

    public void delete_fnction_property(final int j, final LinearLayout outofstock)
    {


        alertDialog = new Dialog(mContext);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        LayoutInflater factory = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        final View Sort_layout = factory.inflate(R.layout.delete_popup, null);

        LinearLayout cancel = (LinearLayout) Sort_layout.findViewById(R.id.CancelPopUp);
        LinearLayout apply = (LinearLayout) Sort_layout.findViewById(R.id.ApplyPopUP);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();

            }
        });


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();


                Arrays.ProductItemsArray.remove(j);
                Arrays.CartArray.remove(j);
                new Cart_show( Arrays.CartArray,mContext);
                notifyDataSetChanged();
                adapter_check();
                found=false;
                if(Arrays.AfterPayArray.size()!=0)
                {



                    for(AfterPay pay:Arrays.AfterPayArray)
                    {

                        for(ProductDetailClass aClass:itemsList){

                            if( pay.getProduct_id().equals(aClass.getProduct_id()))
                            {
                                outofstock.setVisibility(View.VISIBLE);
                                found=true;

                                //conunt_number.setVisibility(View.GONE);
                                break;
                            }
                        }
                    }
                }
            }
        });



        alertDialog.setContentView(Sort_layout);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);

        alertDialog.show();





    }

   public void adapter_check()
   {

       if (Arrays.CartArray != null && Arrays.CartArray.size() > 0) {

           CartActivity.No_Record.setVisibility(View.GONE);
       }
       else
       {
           CartActivity.No_Record.setVisibility(View.VISIBLE);
       }

   }




}