package com.example.dhivya.myapplication;

/**
 * Created by Dhivya on 13-May-16.
 *
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.pm.PackageManager;
import android.net.Uri;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.util.Log;
import android.os.AsyncTask;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;


public class StartPage extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);


        // Button listeners
        findViewById(R.id.button_pro).setOnClickListener(this);
        findViewById(R.id.button_uctxt).setOnClickListener(this);
        findViewById(R.id.button_upre).setOnClickListener(this);
    }
//call Weather app using user context button
    // Button btn = (Button) findViewById(R.id.button_uctxt);
    // btn.setOnClickListener(new View.OnClickListener() {
    // public void onClick(View view) {
    // Uri uri = Uri.parse("http://www.google.com");
    // Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    // startActivity(intent);
    // }});


    //show profile
    private void profile() {
        //Uri uri;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://search?q=maps&c=apps&hl=en&country=in"));
        startActivity(intent);
    }


//show user context

    private void userContext() {
        Intent i = new Intent();
        PackageManager manager = getPackageManager();
        i = manager.getLaunchIntentForPackage("com.yahoo.mobile.client.android.weather");
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(i);
        // Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //startActivity(intent);
    }

//show user preferences

    private void userPreferences() {
        // Intent intent = new Intent(StartPage.this,AppUsageStatisticsActivity.class);
        //startActivity(intent);
        //  Intent intent = new Intent(android.provider.Settings.INTENT_CATEGORY_USAGE_ACCESS_CONFIG);
        //startActivity(intent);
        // startActivity(getPackageManager().getLaunchIntentForPackage("com.example.android.appusagestatistics")
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "market://details?id=com.yahoo.mobile.client.android.weather \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) { //e.toString();
        }
    }


    //to see which button is clicked and to call respective method
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_pro:
                profile();
                break;
            case R.id.button_uctxt:
                userContext();
                break;
            case R.id.button_upre:
                userPreferences();
                break;
        }
    }



}

