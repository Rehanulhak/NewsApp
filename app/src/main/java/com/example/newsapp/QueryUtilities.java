package com.example.newsapp;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class QueryUtilities {

    private QueryUtilities(){

    }

    public static String createUrl() {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("api-key","5c7d0239-45ba-41a3-97e7-6761b8c9dd55")
                .appendQueryParameter("section", "technology")
                .appendQueryParameter("show-tags","contributor");

        return builder.build().toString();
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static String apiQuery(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("ConnectionError", "Unable to make connection");    //JSON response will be single space, handle on method return
            }
        } catch (IOException e) {
            Log.e("ConnectionError", "Unable to make connection");        //JSON response will be single space, handle on method return
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public static ArrayList<NewsItem> fetchData() throws MalformedURLException {

        URL url = new URL(createUrl());


        String jsonResponse = null;
        try {
            jsonResponse = apiQuery(url);
        } catch (IOException e) {
            Log.e("HTTP forming:", "Unable to make HTTP request");
        }

        return extractResults(jsonResponse);
    }

    private static ArrayList<NewsItem> extractResults(String newsJSON) {

        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        ArrayList<NewsItem> list = new ArrayList<>();

        try {

            JSONObject obj = new JSONObject(newsJSON).getJSONObject("response");

            JSONArray newsArray = obj.getJSONArray("results");


            for (int i = 0; i <newsArray.length(); i++){

                JSONObject temp = newsArray.getJSONObject(i);

                String sectionName = temp.getString("sectionName");
                String title = temp.getString("webTitle");
                String url = temp.getString("webUrl");
                String date = temp.getString("webPublicationDate");
                String author ="";
                JSONArray tempAuthorArray = temp.getJSONArray("tags");
                if(!tempAuthorArray.isNull(0)){
                    JSONObject tempAuthor = tempAuthorArray.getJSONObject(0);
                    author = tempAuthor.getString("webTitle");
                }
                list.add(new NewsItem(sectionName, title, url, date, author));
            }

        } catch (JSONException e) {

            Log.e("QueryUtilites", "Problem parsing the JSON results", e);
        }

        return list;
    }
}