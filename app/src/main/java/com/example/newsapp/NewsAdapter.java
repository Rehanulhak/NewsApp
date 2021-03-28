package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsItem> {
    public NewsAdapter(@NonNull Context context, @NonNull List<NewsItem> newsItemList) {
        super(context,0, newsItemList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        NewsItem currentNewsItem = getItem(position);

        TextView titleView = convertView.findViewById(R.id.title);
        titleView.setText(currentNewsItem.getTitle());
        TextView idView = convertView.findViewById(R.id.sectionName);
        idView.setText(currentNewsItem.getSectionName());
        TextView dateView = convertView.findViewById(R.id.date);
        String date = currentNewsItem.getPublicationDate();
        dateView.setText(date.substring(0,date.indexOf('T')));
        TextView authorView = convertView.findViewById(R.id.author);
        authorView.setText(currentNewsItem.getAuthor());

        return convertView;
    }
}
