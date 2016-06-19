package com.example.dhivya.myapplication;

/**
 * Created by Dhivya on 09-May-16.
 */
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.os.AsyncTask;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.util.Log;
import java.util.*;
import java.io.InputStreamReader;
import java.io.IOException;


import java.util.ArrayList;
import java.util.List;


public class AppsManager extends AppCompatActivity {
    private Context mContext;
    //get category
   // public final static String GOOGLE_URL = "market://details?id=";
    public final static String GOOGLE_URL = "https://play.google.com/store/apps/details?id=";
    public static final String ERROR = "error";
    String category;


    public AppsManager(Context context) {
        mContext = context;
    }

    // Get a list of installed app
    public List<String> getInstalledPackages() {
        // Initialize a new Intent which action is main
        Intent intent = new Intent(Intent.ACTION_MAIN, null);

        // Set the newly created intent category to launcher
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        // Set the intent flags
        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        );

        // Generate a list of ResolveInfo object based on intent filter
        List<ResolveInfo> resolveInfoList = mContext.getPackageManager().queryIntentActivities(intent, 0);

        // Initialize a new ArrayList for holding non system package names
        List<String> packageNames = new ArrayList<>();

        // Loop through the ResolveInfo list
        for (ResolveInfo resolveInfo : resolveInfoList) {
            // Get the ActivityInfo from current ResolveInfo
            ActivityInfo activityInfo = resolveInfo.activityInfo;

            // If this is not a system app package
            if (!isSystemPackage(resolveInfo)) {
                // Add the non system package to the list
                packageNames.add(activityInfo.applicationInfo.packageName);
            }
        }

        return packageNames;

    }

    // Custom method to determine an app is system app
    public boolean isSystemPackage(ResolveInfo resolveInfo) {
        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    // Custom method to get application icon by package name
    public Drawable getAppIconByPackageName(String packageName) {
        Drawable icon;
        try {
            icon = mContext.getPackageManager().getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            // Get a default icon
            icon = ContextCompat.getDrawable(mContext, R.id.iv_icon);
        }
        return icon;
    }

    // Custom method to get application label by package name
    public String getApplicationLabelByPackageName(String packageName) {
        PackageManager packageManager = mContext.getPackageManager();
        ApplicationInfo applicationInfo;
        String label = "Unknown";
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            if (applicationInfo != null) {
                label = (String) packageManager.getApplicationLabel(applicationInfo);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return label;
    }

    // Custom method to get application Category by package namey d

    public String getCategory(String packageName) {

        String category = "Unknown";
       PackageManager packageManager = mContext.getPackageManager();
        ApplicationInfo applicationInfo;


        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            String query_url = GOOGLE_URL + applicationInfo.packageName;
            Document doc = Jsoup.connect(query_url).get();
            Element link = doc.select("span[itemprop=genre]").first();
            //return link.text();
            category=(String)link.text();
            // return  category;
            //System.out.println(link.text());
        }
        catch (Exception e) {
           return ERROR;
        }
        return category;

    }
}


