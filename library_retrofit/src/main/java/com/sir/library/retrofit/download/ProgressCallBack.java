package com.sir.library.retrofit.download;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sir.library.retrofit.event.LiveBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

public abstract class ProgressCallBack<T> {

    //本地文件存放路径
    private String destFileDir;
    //文件名
    private String destFileName;

    public ProgressCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    /**
     * 订阅加载的进度条
     */
    public ProgressCallBack<T> subscribeLoadProgress(LifecycleOwner owner) {
        LiveBus.getDefault().subscribe("eventKey", DownLoadStateBean.class)
                .observe(owner, new Observer<DownLoadStateBean>() {
                    @Override
                    public void onChanged(@Nullable DownLoadStateBean downLoadStateBean) {
                        progress(downLoadStateBean.getBytesLoaded(), downLoadStateBean.getTotal());
                    }
                });
        return this;
    }

    public abstract void progress(long progress, long total);

    public abstract void onSuccess(T t);

    public void onStart() {

    }

    public void onCompleted() {

    }

    public abstract void onError(Throwable e);

    public void saveFile(ResponseBody body) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            unsubscribe();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }
    }

    /**
     * 取消订阅，防止内存泄漏
     */
    public void unsubscribe() {

    }
}