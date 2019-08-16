package com.sir.app.test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.gson.Gson;
import com.sir.app.test.api.MovieApi;
import com.sir.app.test.common.AppActivity;
import com.sir.app.test.common.AppConstant;
import com.sir.app.test.common.AppLog;
import com.sir.app.test.entity.MovieResult;
import com.sir.app.test.mvc.vc.MVCActivity;
import com.sir.app.test.mvp.view.MVPActivity;
import com.sir.app.test.mvvm.view.MVVMActivity;
import com.sir.library.retrofit.HttpUtils;
import com.sir.library.retrofit.callback.RxCallback;
import com.sir.library.retrofit.exception.ResponseThrowable;

import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppActivity {

    final int RC_WRITE = 1001;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void doBusiness() {
        setSwipeBackEnable(false);
        checkPermissions();
    }

    /**
     * APP检查更新
     */
    private void checkPermissions() {
        String[] perms = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {

        } else {
            // 申请权限
            EasyPermissions.requestPermissions(this, "检查应用更新需要存储权限", RC_WRITE, perms);
        }
    }

    @OnClick({R.id.mvc, R.id.mvp, R.id.mvvm, R.id.request})
    public void onClickBtn(View view) {
        switch (view.getId()) {
            case R.id.mvc:
                mOperation.forward(MVCActivity.class);
                break;
            case R.id.mvp:
                mOperation.forward(MVPActivity.class);
                break;
            case R.id.mvvm:
                mOperation.forward(MVVMActivity.class);
                break;
            case R.id.request:
                setTextVal(R.id.message, "开始请求");
                HttpUtils.getInstance(this)
                        .setLoadMemoryCache(true)//是否加载内存缓存数据
                        .setLoadDiskCache(true)//是否加载内存缓存数据
                        .getRetrofitClient()
                        .setBaseUrl(AppConstant.URL_HOST)
                        .builder(MovieApi.class)
                        .getMovieC("eff63ec0285b079f8fe418a13778a10d", "杭州")
                        .enqueue(new RxCallback<MovieResult>() {
                            @Override
                            protected void onFailure(ResponseThrowable ex) {
                                setTextVal(R.id.message, ex.message);
                            }

                            @Override
                            protected void onSuccess(MovieResult movieResult) {
                                setTextVal(R.id.message, new Gson().toJson(movieResult));
                            }
                        });
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RC_WRITE:
                //grantResults数组存储的申请的返回结果，
                //PERMISSION_GRANTED 表示申请成功
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //授权成功，

                } else {
                    //授权失败，简单提示用户
                    AppLog.toast("授权失败");
                }
                break;
            default:
                break;
        }
    }
}
