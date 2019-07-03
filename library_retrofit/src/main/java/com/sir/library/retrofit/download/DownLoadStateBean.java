package com.sir.library.retrofit.download;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 下载状态
 * Created by zhuyinan on 2018/3/28.
 */
public class DownLoadStateBean implements Serializable, Parcelable {

    //文件总大小
    private long total;
    //已加载文件的大小
    private long bytesLoaded;
    //多任务下载时的一个标记
    private String tag;

    public static final Creator<DownLoadStateBean> CREATOR = new Creator<DownLoadStateBean>() {
        @Override
        public DownLoadStateBean createFromParcel(Parcel source) {
            return new DownLoadStateBean(source);
        }

        @Override
        public DownLoadStateBean[] newArray(int size) {
            return new DownLoadStateBean[size];
        }
    };

    public DownLoadStateBean(long total, long bytesLoaded) {
        this.total = total;
        this.bytesLoaded = bytesLoaded;
    }

    public DownLoadStateBean(long total, long bytesLoaded, String tag) {
        this.total = total;
        this.bytesLoaded = bytesLoaded;
        this.tag = tag;
    }

    protected DownLoadStateBean(Parcel in) {
        this.total = in.readLong();
        this.bytesLoaded = in.readLong();
        this.tag = in.readString();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getBytesLoaded() {
        return bytesLoaded;
    }

    public void setBytesLoaded(long bytesLoaded) {
        this.bytesLoaded = bytesLoaded;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.total);
        dest.writeLong(this.bytesLoaded);
        dest.writeString(this.tag);
    }
}
