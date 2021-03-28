package com.example.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<NewsItem>> {


    public NewsLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<NewsItem> loadInBackground() {
        ArrayList<NewsItem> list = null;
        try {
            Log.e("ConnectionError", "Meow");
            list = QueryUtilities.fetchData();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
