package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.CommonClasses.AddressClass;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.AddressActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.AddressActivityList;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.AddressAddActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.MyAccount;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import static com.cgtin.admin.sherazipetshopkimo.CommonClasses.ApiLinks.AddressFrom;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.SingleItemRowHolder> {

    private ArrayList<AddressClass> itemsList;

    private Context mContext;
    boolean array[];
    Dialog alertDialog;
    // ArrayList<PremiumData> StoreList;
    // public static   Dialog alertDialog_PosterReq;
    public AddressAdapter(Context context, ArrayList<AddressClass> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;

        array =new boolean[itemsList.size()];
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

        View v = LayoutInflater.from(mContext).inflate(R.layout.address_list, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);

        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;

    }
    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        holder.setIsRecyclable(false);
        //PostedReq_Model storeModel = itemsList.get(i);
        final AddressClass address = itemsList.get(i);

        holder.name.setText(address.getCustomer_address_name());
        holder.address.setText(address.getCustomer_address_address());
        holder.mobile.setText(address.getCustomer_address_contact());

        if(address.getCustomer_address_default().equals("1"))
        {
            holder.defaultadd.setText(R.string.Default);
        }
        else
        {
            holder.defaultadd.setText(R.string.Set_as_Default);
        }

//        if(AddressFrom== MyAccount.getMyAccount())
//        {
//           holder.check.setVisibility(View.INVISIBLE);
//
//        }
//        else
//        {
//            holder.check.setVisibility(View.VISIBLE);
//
//        }
        if(CommonClass.Address_Id_Show!=null) {
            if (AddressFrom == AddressActivity.getIntance() && !CommonClass.Address_Id_Show.equals(address.getCustomer_address_id())) {
                holder.SelectAddress.setVisibility(View.VISIBLE);
            } else {
                holder.SelectAddress.setVisibility(View.GONE);
            }
        }


        if(CommonClass.Address_Id_Show!=null)
        {
            if(CommonClass.Address_Id_Show.equals(address.getCustomer_address_id()))
            {

                holder.check.setBackgroundColor(Color.parseColor("#29c6d8"));
                //holder.check.setChecked(true);

            }


        }




        holder.address_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(AddressActivityList.internet_status){

                    int position=i;
                    for (int j=0;j<array.length;j++) {
                        if (j == position) {
                            array[j] = true;
                            //CommonClass.Address_Id_Show=itemsList.get(position).getCustomer_address_id();

                        } else {

                            array[j]=false;
                        }
                    }
                    notifyDataSetChanged();

                }else{

                    AddressActivityList.internet.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


           delete_fnction_property(address.getCustomer_address_id());

            }
        });

        holder.defaultadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddressActivityList.getInstance().defaddress(address.getCustomer_address_id());


            }
        });


        holder.edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CommonClass.Address_Edit=true;
                CommonClass.Address_edit_header=true;


                Intent i= new Intent(mContext,AddressAddActivity.class) ;
                i.putExtra("name",address.getCustomer_address_name());
                i.putExtra("address",address.getCustomer_address_address());
                i.putExtra("phone",address.getCustomer_address_contact());
                i.putExtra("Add_ID",address.getCustomer_address_id());
                i.putExtra("PIN",address.getCustomer_address_pincode());
                mContext.startActivity(i);

            }
        });

        if(array[i]){




            if(AddressFrom==AddressActivity.getIntance()) {


                if (AddressActivity.getIntance() != null) {

                    AddressActivity.getIntance().finish();

                }

                Intent j = new Intent(mContext, AddressActivity.class);
                j.putExtra("name", address.getCustomer_address_name());
                j.putExtra("address", address.getCustomer_address_address());
                j.putExtra("phone", address.getCustomer_address_contact());
                j.putExtra("AddID", address.getCustomer_address_id());
                mContext.startActivity(j);

                AddressActivityList.getInstance().finish();

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

        ImageButton add;
        ImageButton minus;
        TextView name;
        TextView address;
        public TextView defaultadd;
        TextView mobile;
        ImageView brand_image;
        ImageView brand_image_2;
        CardView address_click;
        TextView edit_address;
        TextView delete;
        LinearLayout check;
        LinearLayout SelectAddress;
        public SingleItemRowHolder(View view) {
            super(view);

            name=view.findViewById(R.id.name);
            address=view.findViewById(R.id.address);
            defaultadd=view.findViewById(R.id.defaultadd);
            mobile=view.findViewById(R.id.mobile);
            address_click=view.findViewById(R.id.address_click);
            delete=view.findViewById(R.id.delete);
            edit_address=view.findViewById(R.id.edit_address);
            check=view.findViewById(R.id.check);
            SelectAddress=view.findViewById(R.id.SelectAddress);


        }

    }

    public void delete_fnction_property(final String j)
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

                AddressActivityList.getInstance().addressdel(j);


            }
        });



        alertDialog.setContentView(Sort_layout);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(true);

        alertDialog.show();





    }

}