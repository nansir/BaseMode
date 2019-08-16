package com.sir.library.retrofit.download;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.sir.library.retrofit.event.LiveBus;
import com.sir.library.retrofit.exception.ResponseThrowable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import okhttp3.ResponseBody;

/**
 * 下载进度
 * Created by zhuyinan on 2018/3/28.
 */
public abstract class ProgressCallBack<T> {

    public static String LIVE_PROGRESS = UUID.randomUUID().toString();

    //本地文件存放路径
    private String fileDir;
    //文件名
    private String fileName;

    public ProgressCallBack(String destFileDir, String destFileName) {
        this.fileDir = destFileDir;
        this.fileName = destFileName;
        subscribeLoadProgress();
    }

    /**
     * 订阅加载的进度条
     */
    public void subscribeLoadProgress() {
        LiveBus.getDefault().subscribe(LIVE_PROGRESS, DownLoadStateBean.class)
                .observeForever(new Observer<DownLoadStateBean>() {
                    @Override
                    public void onChanged(@Nullable DownLoadStateBean stateBean) {
                        progress(stateBean);
                    }
                });
    }

    public abstract void progress(DownLoadStateBean stateBean);

    public void onStart() {

    }

    public void onCompleted() {

    }

    public abstract void onSuccess(T t);

    public abstract void onError(ResponseThrowable throwable);

    /**
     * 存档
     *
     * @param body
     */
    public void saveFile(ResponseBody body) {
        InputStream stream = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            stream = body.byteStream();
            File dir = new File(fileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            fos = new FileOutputStream(file);
            while ((len = stream.read(buf)) != -1) {
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
                if (stream != null) {
                    stream.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 取消订阅，防止内存泄漏
     */
    public void unsubscribe() {
        LiveBus.getDefault().clear("eventKey");
    }
}