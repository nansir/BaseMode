package com.sir.app.test;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sir.app.base.BaseActivity;
import com.sir.app.test.view.ModuleActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @OnClick(R.id.mvp)
    public void onClickBtn(View view) {
        Log.e("TAG", "onClickBtn");
        getOperation().forward(ModuleActivity.class);
    }
}
