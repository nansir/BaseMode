package com.sir.library.retrofit.download;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 下载状态
 * Created by zhuyinan on 2018/3/28.
 */
public class DownLoadStateBean implements Serializable, Parcelable {

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
    //文件总大小
    private long total;
    //已加载文件的大小
    private long loaded;
    //多任务下载时的一个标记
    private String tag;

    public DownLoadStateBean(long total, long loaded) {
        this.total = total;
        this.loaded = loaded;
    }

    public DownLoadStateBean(long total, long loaded, String tag) {
        this.total = total;
        this.loaded = loaded;
        this.tag = tag;
    }

    protected DownLoadStateBean(Parcel in) {
        this.total = in.readLong();
        this.loaded = in.readLong();
        this.tag = in.readString();
    }

    public long getTotal() {
        return total;
    }

    public long getLoaded() {
        return loaded;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getPercent() {
        return (long) ((new BigDecimal((float) loaded / total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) * 100);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.total);
        dest.writeLong(this.loaded);
        dest.writeString(this.tag);
    }
}
