package com.sir.app.test.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sir.app.test.R;
import com.sir.library.base.BaseDialog;

/**
 * 消息对话
 * Created by zhuyinan on 2018/1/12.
 * Contact by 445181052@qq.com
 */
public class MessageDialog extends BaseDialog implements IBaseMessage {

    final int delay = 2000;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dismiss();
        }
    };

    public MessageDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int bindLayout() {
        return R.layout.dialog_message;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void doBusiness() {
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void showLoading(String loadingTip) {
        show();

        findViewById(R.id.ll_loading).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_message).setVisibility(View.GONE);

        findViewById(R.id.lr_progress).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_mark).setVisibility(View.GONE);
        setTextVal(R.id.tv_lod_msg, loadingTip);
    }

    @Override
    public void showMessage(String text) {
        show();

        findViewById(R.id.ll_loading).setVisibility(View.GONE);
        findViewById(R.id.ll_message).setVisibility(View.VISIBLE);

        setTextVal(R.id.tv_msg_content, text);
    }

    @Override
    public void showSuccess(String text) {
        show();
        findViewById(R.id.ll_loading).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_message).setVisibility(View.GONE);

        findViewById(R.id.lr_progress).setVisibility(View.GONE);
        findViewById(R.id.iv_mark).setVisibility(View.VISIBLE);

        setImageSrc(R.id.iv_mark, R.drawable.svg_msg_finish);
        setTextVal(R.id.tv_lod_msg, text);

        mHandler.sendEmptyMessageDelayed(0, delay);
    }

    private void setImageSrc(@IdRes int ivMark, @DrawableRes int res) {
        ImageView mark = findViewById(ivMark);
        mark.setImageResource(res);
    }

    @Override
    public void showWarning(String text) {
        show();
        findViewById(R.id.ll_loading).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_message).setVisibility(View.GONE);

        findViewById(R.id.lr_progress).setVisibility(View.GONE);
        findViewById(R.id.iv_mark).setVisibility(View.VISIBLE);

        setImageSrc(R.id.iv_mark, R.drawable.svg_msg_warning);
        setTextVal(R.id.tv_lod_msg, text);

        mHandler.sendEmptyMessageDelayed(0, delay);
    }

    @Override
    public void showError(String text) {
        show();

        findViewById(R.id.ll_loading).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_message).setVisibility(View.GONE);

        findViewById(R.id.lr_progress).setVisibility(View.GONE);
        findViewById(R.id.iv_mark).setVisibility(View.VISIBLE);

        setImageSrc(R.id.iv_mark, R.drawable.svg_msg_failure);
        setTextVal(R.id.tv_lod_msg, text);

        mHandler.sendEmptyMessageDelayed(0, delay);
    }

    @Override
    public void setTitle(String text) {
        setTextVal(R.id.tv_msg_title, text);
    }

    @Override
    public void setContent(String text) {
        setTextVal(R.id.tv_lod_msg, text);
    }

    @Override
    public void setLoading(String text) {
        setTextVal(R.id.tv_lod_msg, text);
    }

    @Override
    public void setSoleText(String text) {
        setTextVal(R.id.btn_sole, text);
    }

    @Override
    public void setCancelText(String text) {
        setButtonVal(R.id.btn_cancel, text);
    }

    private void setButtonVal(int reId, CharSequence text) {
        Button btnView = findViewById(reId);
        btnView.setText(text);
    }

    @Override
    public void setShowSoleBtn(Boolean isShow) {
        findViewById(R.id.ll_btn_option).setVisibility(isShow ? View.GONE : View.VISIBLE);
        findViewById(R.id.btn_sole).setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setConfirmText(String text) {
        setButtonVal(R.id.btn_confirm, text);
    }

    @Override
    public void setConfirmClickListener(View.OnClickListener listener) {
        findViewById(R.id.btn_confirm).setOnClickListener(listener);
    }

    @Override
    public void setCancelClickListener(View.OnClickListener listener) {
        findViewById(R.id.btn_cancel).setOnClickListener(listener);
    }

    @Override
    public void setSoleClickListener(View.OnClickListener listener) {
        findViewById(R.id.btn_sole).setOnClickListener(listener);
    }

    @Override
    public void destroy() {
        mHandler.removeCallbacksAndMessages(null);
        if (isShowing()) {
            dismiss();
        }
    }

    @Override
    public void show() {
        if (!isShowing()) {
            super.show();
        }
    }
}
