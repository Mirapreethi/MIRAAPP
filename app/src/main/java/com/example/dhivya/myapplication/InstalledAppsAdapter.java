package com.example.dhivya.myapplication;

/**
 * Created by Dhivya on 09-May-16.
 */
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static java.lang.System.currentTimeMillis;
import android.support.v7.app.AppCompatActivity;
import java.util.List;
import java.util.Random;

public class InstalledAppsAdapter extends RecyclerView.Adapter<InstalledAppsAdapter.ViewHolder>{

    public final static String GOOGLE_URL = "https://play.google.com/store/apps/details?id=";
    public static final String ERROR = "error";
    String category;

    private Context mContext;
    private List<String> mDataSet;
   // private Activity mActivity;
    public InstalledAppsAdapter(Context context, List<String> list){
        mContext = context;
        mDataSet = list;
       // mActivity= InstalledAppsAdapter.this;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public TextView mTextViewLabel;
        public TextView mTextViewPackage;
        public TextView mTextViewCategory;
        public ImageView mImageViewIcon;

        public ViewHolder (View v){
            super(v);
            // Get the widgets reference from custom layout
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextViewLabel = (TextView) v.findViewById(R.id.app_label);
            mTextViewPackage = (TextView) v.findViewById(R.id.app_package);
            mTextViewCategory = (TextView) v.findViewById(R.id.app_category);
            mImageViewIcon = (ImageView) v.findViewById(R.id.iv_icon);

        }
    }

    @Override
    public InstalledAppsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_view,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        // Initialize a new instance of AppManager class
        AppsManager appsManager = new AppsManager(mContext);
       // FetchCategoryTask fetchCategory=new FetchCategoryTask(mContext);
        // Get the current package name
        final String packageName = (String) mDataSet.get(position);

        // Get the current app icon
        Drawable icon = appsManager.getAppIconByPackageName(packageName);

        // Get the current app label
        String label = appsManager.getApplicationLabelByPackageName(packageName);

        //Get the app category
        String category=appsManager.getCategory(packageName);

        // Set the current app label
        holder.mTextViewLabel.setText(label);

        // Set the current app package name
        holder.mTextViewPackage.setText(packageName);

        //Set the current app category name
        holder.mTextViewCategory.setText(category);

        // Set the current app icon
        holder.mImageViewIcon.setImageDrawable(icon);

        // Set a click listener for CardView
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the intent to launch the specified application
                Intent intent =mContext.getPackageManager().getLaunchIntentForPackage(packageName);

                if(intent != null){
                    mContext.startActivity(intent);
                    notification(packageName);
//create notification here
                }else {
                    Toast.makeText(mContext,packageName + " Launch Error.", Toast.LENGTH_SHORT).show();

                }

            }

        });
    }

    public String getCategory(String packageName) {

        String category = "Unknown";
       // PackageManager packageManager = mContext.getPackageManager();
       // ApplicationInfo applicationInfo;


        try {
           // applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            String query_url = GOOGLE_URL + packageName;
            Document doc = Jsoup.connect(query_url).get();
            Element link = doc.select("span[itemprop=genre]").first();
            return link.text();
            //category=(String)link.text();
            // return  category;
            //System.out.println(link.text());
        }
        catch (Exception e) {
            return ERROR;
        }
        //return category;

    }

    //@Override
    public void notification(String packageName)
   {
       AppsManager appsManager = new AppsManager(mContext);
       String label = appsManager.getApplicationLabelByPackageName(packageName);
      // String category=appsManager.getCategory(packageName);
       String category= getCategory(packageName);
       String rel=" related category apps";
   String url="market://search?q=" +label + rel+ "&c=apps&hl=en&country=in";
       int notificationId = new Random().nextInt(); // just use a counter in some util class...
       Intent intent = new Intent(Intent.ACTION_VIEW);
       //intent.putExtra(NOTIFICATION_ID, notificationId);
       intent.setData(Uri.parse(url));
       //intent.setData(Uri.parse("market://search?q=label&c=apps&hl=en&country=in"));
       PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent,0);
      // PendingIntent contentIntent = NotificationActivity.getContentIntent(notificationId, mContext);
       PendingIntent dismissIntent = NotificationActivity.getDismissIntent(notificationId, mContext);
       PendingIntent downloadIntent = NotificationActivity.getDownloadIntent(notificationId, mContext);

       NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
       builder.setPriority(NotificationCompat.PRIORITY_MAX) //HIGH, MAX, FULL_SCREEN and setDefaults(Notification.DEFAULT_ALL) will make it a Heads Up Display Style
               .setDefaults(Notification.DEFAULT_ALL) // also requires VIBRATE permission
               .setSmallIcon(R.drawable.ic_shop_two_white_24dp) // Required!
               .setContentTitle("Google Play")
               .setContentText("Install Apps from Google Play")
               .setAutoCancel(true)
               .setContentIntent(contentIntent)
               .addAction(R.drawable.ic_file_download_white_18dp, "Download", downloadIntent)
               .addAction(R.drawable.ic_close_white_18dp, "Not Now", dismissIntent);

       // Gets an instance of the NotificationManager service
       NotificationManager notifyMgr = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);

       // Builds the notification and issues it.
       notifyMgr.notify(notificationId, builder.build());
   }




    @Override
    public int getItemCount(){
        // Count the installed apps
        return mDataSet.size();
    }

}