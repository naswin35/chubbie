package com.cgtin.admin.sherazipetshopkimo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cgtin.admin.sherazipetshopkimo.Classes.Arrays;
import com.cgtin.admin.sherazipetshopkimo.Classes.CommonClass;
import com.cgtin.admin.sherazipetshopkimo.Classes.Global;
import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.CartActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.Food_Accessories;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.HomeActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.MyAccount;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.MyOrders;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.NotificationActivity;
import com.cgtin.admin.sherazipetshopkimo.StartActivities.LoginActivity;

import java.util.HashMap;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity {
    public DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    protected FrameLayout frameLayout;
    public NavigationView navigationView;
    ImageView drawer_bg;
    //View headerView;
    HashMap<String,String> user;
    public String GoogleName,GoogleEmail,GoogleId;
    public static String gcustomerids,gcustomernames,gcustomer_mobile;
    TextView number;
    TextView name;
    TextView email;
    TextView location;
    //  public SessionManager session;

    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */

    RelativeLayout Cat;
    RelativeLayout Dog;
    RelativeLayout Home;
    RelativeLayout Notification;
    RelativeLayout Cart;
    RelativeLayout my_orders;
    RelativeLayout my_account;
    RelativeLayout logout;


    SessionManager session;
    private boolean mIntentInProgress;

    private boolean mSignInClicked;
    public HashMap<String,String > users;
    public HashMap<String,Object> startdetails;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Global.LANG = settings.getString("language","en");
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", Global.LANG).apply();
        Locale locale = new Locale(Global.LANG);
        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(locale);
        Locale.setDefault(locale);
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_base);



        session=new SessionManager(getApplicationContext());
        user=session.getUserDetails();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;

        logout = (RelativeLayout) findViewById(R.id.logout);
        initTypeface();

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        Dog =  (RelativeLayout) findViewById(R.id.dog);
        Cat =  (RelativeLayout) findViewById(R.id.cat);
        Home = (RelativeLayout) findViewById(R.id.home);
        Notification = (RelativeLayout) findViewById(R.id.notification);
        my_orders = (RelativeLayout) findViewById(R.id.my_orders);
        my_account = (RelativeLayout) findViewById(R.id.my_account);
        Cart = (RelativeLayout) findViewById(R.id.cart);

       // drawer_bg=findViewById(R.id.drawer_bg);

        //drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        // mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                return false;
            }

        });


        Cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i= new Intent(BaseActivity.this,Food_Accessories.class) ;
                i.putExtra("Title", CommonClass.CatTitle);
                i.putExtra("id",CommonClass.CatID);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(i);
                mDrawerLayout.closeDrawer(GravityCompat.START);


            }
        });

        Dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i= new Intent(BaseActivity.this,Food_Accessories.class) ;
                i.putExtra("Title", CommonClass.DogTitle);
                i.putExtra("id", CommonClass.DogID);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        });

        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if( Arrays.CartArray.size()>0)
                {
                    Intent i= new Intent(BaseActivity.this,CartActivity.class) ;
                    startActivity(i);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                else

                {

                    Toast.makeText(getApplicationContext(), R.string.Cart_is_empty, Toast.LENGTH_LONG).show();

                }

                mDrawerLayout.closeDrawer(GravityCompat.START);


            }
        });
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent i= new Intent(BaseActivity.this,HomeActivity.class) ;

                startActivity(i);

                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        });

        Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(NotificationActivity.getNotificationActivity()!=null){

                    NotificationActivity.getNotificationActivity().finish();
                }

                Intent i= new Intent(BaseActivity.this,NotificationActivity.class) ;

                startActivity(i);

                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        });


        my_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if(MyOrders.getMyOrders()!=null){

                    MyOrders.getMyOrders().finish();
                }

                Intent i= new Intent(BaseActivity.this,MyOrders.class) ;

                startActivity(i);

                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        });

        my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(MyAccount.getMyAccount()!=null){

                    MyAccount.getMyAccount().finish();
                }

                Intent i= new Intent(BaseActivity.this,MyAccount.class) ;

                startActivity(i);

                mDrawerLayout.closeDrawer(GravityCompat.START);


            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                session.logoutUser();
                clearNotification();
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Arrays.ProductItemsArray.clear();
                Arrays.CartArray.clear();
                CommonClass.Address_Id_Show=null;
                startActivity(intent);
                finish();

            }
        });

      /*  Glide.with(BaseActivity.this)
                .load(R.drawable.drawer_image)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        drawer_bg.setImageBitmap(bitmap);
                    }
                });*/

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);


        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }
    public  void LogoutFromall() {

    }
    private void HideMenuItems() {
        Menu nav_Menu = navigationView.getMenu();
    }


    public void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) BaseActivity.this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.cancelAll();
    }


    public void initTypeface() {


        TextView number;
        TextView name;
        TextView email;
        final ImageView userImage;
        LinearLayout UserDetails;
        LinearLayout LogLayout;

        user=session.getUserDetails();

        number=(TextView) findViewById(R.id.number);
        name=(TextView) findViewById(R.id.Name);
        UserDetails=(LinearLayout) findViewById(R.id.UserDetails);
        LogLayout=(LinearLayout) findViewById(R.id.log_layout);

        email=(TextView) findViewById(R.id.email);
        userImage=(ImageView) findViewById(R.id.userImage);

        number.setText(user.get(SessionManager.KEY_CUSTOMER_MOBILE));
        email.setText(user.get(SessionManager.KEY_USER_EMAIL));
        name.setText(user.get(SessionManager.KEY_CUSTOMER_NAME));

        if(!session.isLoggedIn()) {

          UserDetails.setVisibility(View.GONE);
          LogLayout.setVisibility(View.VISIBLE);
          logout.setVisibility(View.GONE);
        }
        else
        {

            LogLayout.setVisibility(View.GONE);
            UserDetails.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);

        }

        LogLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                intent.putExtra("from","Account");
                startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        });

        String Gender=user.get(SessionManager.KEY_GENDER);

        if(Gender!=null) {

            if (Gender.equals("male")) {

                Glide.with(BaseActivity.this)
                        .load(R.drawable.proficon)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                userImage.setImageBitmap(bitmap);

                            }
                        });


            } else if (Gender.equals("female")) {

                Glide.with(BaseActivity.this)
                        .load(R.drawable.proficon2)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                userImage.setImageBitmap(bitmap);

                            }
                        });

            }
        }else{

            Glide.with(BaseActivity.this)
                    .load(R.drawable.proficon)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            userImage.setImageBitmap(bitmap);

                        }
                    });

        }

    }

    /* We can override onBackPressed method to toggle navigation drawer*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
