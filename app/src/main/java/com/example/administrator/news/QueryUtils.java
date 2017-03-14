package com.example.administrator.news;

import android.text.TextUtils;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public final class QueryUtils {

    private QueryUtils(){

    }

    public static List<News> fetchNewsData(String requestUrl){
        URL url = createUrl(requestUrl);

        String jsonResponse = "";
        try{
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        List<News> newses = extractFeatureFromJson(jsonResponse);

        return newses;
    }


    private static URL createUrl(String url) {
        URL mUrl = null;
        try{
            mUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return mUrl;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (urlConnection!=null){
                urlConnection.disconnect();

            }if (inputStream!=null){
                inputStream.close();
            }

        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        List<News> newses = new ArrayList<News>();

        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject response = root.getJSONObject("response");
            JSONArray resultsArray = response.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++){
                JSONObject news = resultsArray.getJSONObject(i);
                String title = news.getString("webTitle");
                String sectionName = news.getString("sectionName");
                String webPublicationDate = news.getString("webPublicationDate");
                String url = news.getString("webUrl");

                News mNews = new News(title,sectionName,webPublicationDate,url);

                newses.add(mNews);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newses;
    }
}
