package com.example.dhivya.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.net.Uri;


public class NotificationActivity extends Activity {

    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(getIntent().getIntExtra(NOTIFICATION_ID, -1));
        finish(); // since finish() is called in onCreate(), onDestroy() will be called immediately
    }

   // public static PendingIntent getContentIntent(int notificationId, Context context) {
     //   Intent intent = new Intent(Intent.ACTION_VIEW);
       // intent.putExtra(NOTIFICATION_ID, notificationId);
        //intent.setData(Uri.parse("market://search?q=maps&c=apps&hl=en&country=in"));
        //PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent,0);
        //return contentIntent;
   // }
    public static PendingIntent getDismissIntent(int notificationId, Context context) {
        Intent intent = new Intent(context, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(NOTIFICATION_ID, notificationId);
        PendingIntent dismissIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return dismissIntent;
    }
    public static PendingIntent getDownloadIntent(int notificationId, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
                //context, NotificationActivity.class);
        //intent.setFlags(Intent.ACTION_VIEW);
        //intent.putExtra(Intent.ACTION_VIEW);
        intent.putExtra(NOTIFICATION_ID, notificationId);
        intent.setData(Uri.parse("market://details?id=com.whatsapp"));
        PendingIntent downloadIntent = PendingIntent.getActivity(context, 0, intent,0);
        return downloadIntent;
    }

}