package com.example.administrator.news;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Activity context, ArrayList<News> newses){
        super(context, 0, newses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView==null){

            convertView = LayoutInflater.from(getContext()).inflate
                    (R.layout.news_item, parent, false);
        }

        final News currentNews = getItem(position);

        TextView webTitle = (TextView) convertView.findViewById(R.id.web_title);
        webTitle.setText(currentNews.getmWebTitle());

        TextView sectionName = (TextView) convertView.findViewById(R.id.section_name);
        sectionName.setText(currentNews.getmSectionName());

        TextView webPublicationDate = (TextView) convertView.findViewById(R.id.web_publication_date);
        webPublicationDate.setText(currentNews.getmWebPublicationDate());

        return convertView;
    }
}
