package com.example.administrator.news;

/**
 * Created by Administrator on 2017/3/14.
 */

public class News {

    private String mWebTitle;

    private String mSectionName;

    private String mWebPublicationDate;

    private String mUrl;

    public News(String webTitle, String sectionName, String webPublicationDate, String url){

        mWebTitle = webTitle;
        mSectionName = sectionName;
        mWebPublicationDate = webPublicationDate;
        mUrl = url;
    }

    public String getmWebTitle(){
        return mWebTitle;
    }

    public String getmSectionName(){
        return mSectionName;
    }

    public String getmWebPublicationDate(){
        return mWebPublicationDate;
    }

    public String getmUrl(){
        return mUrl;
    }
}
