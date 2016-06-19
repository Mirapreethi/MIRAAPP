package com.example.dhivya.myapplication;


import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;


public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;

    private RelativeLayout mRelativeLayout;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = MainActivity.this;

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Define a layout for RecyclerView
        mLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize a new adapter for RecyclerView
        mAdapter = new InstalledAppsAdapter(
                mContext,
                new AppsManager(mContext).getInstalledPackages()
        );

        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        //call StartPage.java using start button
        Button btn = (Button)findViewById(R.id.button_strt);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               // Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
               // Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               // startActivity(intent);

                Intent intent = new Intent(mActivity,StartPage.class);

                startActivity(intent); }});

    }

}