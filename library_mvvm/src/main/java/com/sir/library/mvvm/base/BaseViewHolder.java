package com.sir.library.mvvm.base;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhuyinan on 2019/8/22.
 */
public abstract class BaseViewHolder {

    protected View mView;

    protected Activity mContent;

    protected Unbinder unbinder;

    public void initView(Activity context, View view) {
        mView = view;
        mContent = context;
        unbinder = ButterKnife.bind(this, mView);
    }

    public abstract void doBusiness();

    public void setTextVal(int reId, CharSequence text) {
        TextView textView = findViewById(reId);
        textView.setText(text);
    }

    public <T extends View> T findViewById(int reId) {
        return mView.findViewById(reId);
    }

    public String getTextVal(int reId) {
        TextView textView = findViewById(reId);
        return textView.getText().toString();
    }

    public String getEditVal(int reId) {
        EditText editText = findViewById(reId);
        return editText.getText().toString();
    }

    public void onDestroyView() {
        unbinder.unbind();
        mContent = null;
        mView = null;
    }
}
