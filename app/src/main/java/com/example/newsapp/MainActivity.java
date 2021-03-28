package com.example.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<NewsItem>> {

    private NewsAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        adapter = new NewsAdapter(this, new ArrayList<NewsItem>());
        listView.setAdapter(adapter);



        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            getLoaderManager().initLoader(1, null, this);
        }
        else {
            Toast.makeText(this,"There is no internet connection, connect to internet and restart application"
                    , Toast.LENGTH_LONG).show();
        }

        listView.setOnItemClickListener((adapterView, view, position, l) -> {

            NewsItem currentNewsItem = adapter.getItem(position);
            Uri earthquakeUri = Uri.parse(currentNewsItem.getUrl());
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
            startActivity(websiteIntent);
        });
    }

    @Override
    public Loader<ArrayList<NewsItem>> onCreateLoader(int i, Bundle bundle) {

        Log.d("InOnCreateLoader","Working");

        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<NewsItem>> loader, ArrayList<NewsItem> list) {
        if (list != null && !list.isEmpty()) {
            adapter.addAll(list);
            ProgressBar sign = findViewById(R.id.loading_indicator);
            sign.setVisibility(View.INVISIBLE);
            Log.d("InOnLoadFinished","Working");
        }
        else{
            TextView emptyView = findViewById(R.id.empty);
            emptyView.setVisibility(View.VISIBLE);
            ProgressBar sign = findViewById(R.id.loading_indicator);
            sign.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<NewsItem>> loader) {

        Log.d("InOnLoaderReset","Wokring");adapter.clear();
    }
}