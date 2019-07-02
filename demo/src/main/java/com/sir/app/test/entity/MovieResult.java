package com.sir.app.test.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuyinan on 2019/6/25.
 */
public class MovieResult {

    private String title;

    private String url;

    private String m_url;

    private List<MovieData> data ;

    private String morelink;

    private String date;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getM_url() {
        return m_url == null ? "" : m_url;
    }

    public void setM_url(String m_url) {
        this.m_url = m_url;
    }

    public List<MovieData> getData() {
        if (data == null) {
            return data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<MovieData> data) {
        this.data = data;
    }

    public String getMorelink() {
        return morelink == null ? "" : morelink;
    }

    public void setMorelink(String morelink) {
        this.morelink = morelink;
    }

    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
