package com.sir.library.retrofit.download;

import com.sir.library.retrofit.event.LiveBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 进度响应
 * Created by zhuyinan on 2018/3/28.
 */
public class ProgressResponse extends ResponseBody {

    private ResponseBody responseBody;

    private BufferedSource bufferedSource;

    private String tag;

    public ProgressResponse(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    public ProgressResponse(ResponseBody responseBody, String tag) {
        this.responseBody = responseBody;
        this.tag = tag;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long bytesLoaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesLoaded += bytesRead == -1 ? 0 : bytesRead;
                //使用RxBus的方式，实时发送当前已读取(上传/下载)的字节数据
                LiveBus.getDefault().postEvent(ProgressCallBack.LIVE_PROGRESS, new DownLoadStateBean(contentLength(), bytesLoaded, tag));
                return bytesRead;
            }
        };
    }
}
