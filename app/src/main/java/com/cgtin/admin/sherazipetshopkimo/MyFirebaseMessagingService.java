package com.cgtin.admin.sherazipetshopkimo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cgtin.admin.sherazipetshopkimo.Classes.SessionManager;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.HomeActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.NotificatinDetails;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.NotificationActivity;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.OrderDetailsActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private NotificationManager notifManager;

    SessionManager sessionManager;
    HashMap<String,String>UserData;
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        Map<String, String> data = remoteMessage.getData();
        String Title = data.get("title");
        String Description = data.get("message");
        String type = data.get("type");
        String id = data.get("id");
        String OrderID = data.get("order_id");
        String UserID = data.get("user_id");



        sendNotification(Title,Description,type,id,OrderID,UserID,this);
        Log.d(TAG, "From: " + remoteMessage);



    }
    private void sendNotification(String titles, String descriptions, String types, String ids, String OrderID, String userID, MyFirebaseMessagingService myFirebaseMessagingService) {


        sessionManager = new SessionManager(myFirebaseMessagingService);
        UserData=sessionManager.getUserDetails();

       /* if (userID.equals(UserData.get(SessionManager.KEY_CUSTOMER_ID))) {*/

        Intent intent;


        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 250;
        NotificationCompat.Builder builder;


        if(types.equals("order") && sessionManager.isLoggedIn() && userID.equals(UserData.get(SessionManager.KEY_CUSTOMER_ID))) {

            intent = new Intent(this, OrderDetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            intent.putExtra("status", "unread");
            intent.putExtra("notifyID", ids);
            intent.putExtra("OrderID", OrderID);
            intent.putExtra("type", "notification");


            intent.setAction(Long.toString(System.currentTimeMillis()));



            if (notifManager == null) {
                notifManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);




                builder = new NotificationCompat.Builder(this);

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);


                builder.setContentTitle(titles)                           // required
                        .setSmallIcon(R.mipmap.ic_launcher) // required
                        .setContentText(descriptions)  // required
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                        .setPriority(Notification.PRIORITY_HIGH);



                Notification notification = builder.build();
                notifManager.notify(m, notification);


            }


        }else {

            if((!types.equals("order"))) {

                intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setAction(Long.toString(System.currentTimeMillis()));


                if (notifManager == null) {
                    notifManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);




                    builder = new NotificationCompat.Builder(this);

                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);


                    builder.setContentTitle(titles)                           // required
                            .setSmallIcon(R.mipmap.ic_launcher) // required
                            .setContentText(descriptions)  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                            .setPriority(Notification.PRIORITY_HIGH);



                    Notification notification = builder.build();
                    notifManager.notify(m, notification);
                }
            }

        }
    }

}
