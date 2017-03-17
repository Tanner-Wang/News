package com.example.administrator.news;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import static com.example.administrator.news.MainActivity.holder;


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

        holder.webTitle = (TextView) convertView.findViewById(R.id.web_title);
        holder.webTitle.setText(currentNews.getmWebTitle());

        holder.sectionName = (TextView) convertView.findViewById(R.id.section_name);
        holder.sectionName.setText(currentNews.getmSectionName());

       holder.webPublicationDate = (TextView) convertView.findViewById(R.id.web_publication_date);
        holder.webPublicationDate.setText(currentNews.getmWebPublicationDate());

        return convertView;
    }
}
