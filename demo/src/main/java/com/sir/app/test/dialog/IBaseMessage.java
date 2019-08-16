package com.sir.app.test.dialog;

import android.view.View;

/**
 * 消息
 * Created by zhuyinan on 2019/7/16.
 */
public interface IBaseMessage {

    void showLoading(String loadingTip);

    void showMessage(String text);

    void showSuccess(String text);

    void showWarning(String text);

    void showError(String text);

    void setTitle(String text);

    void setContent(String text);

    void setLoading(String text);

    void setSoleText(String text);

    void setCancelText(String text);

    void setShowSoleBtn(Boolean isShow);

    void setConfirmText(String text);

    void setConfirmClickListener(View.OnClickListener listener);

    void setCancelClickListener(View.OnClickListener listener);

    void setSoleClickListener(View.OnClickListener listener);

    void destroy();
}
