package com.sir.app.test.common;

import android.os.Bundle;

import com.sir.library.mvvm.ContractProxy;
import com.sir.library.mvvm.base.BaseViewHolder;
import com.sir.library.mvvm.base.BaseViewModel;


/**
 * 应用持有人活动
 * Created by zhuyinan on 2019/8/22.
 */
public abstract class AppHolderActivity<T extends BaseViewModel, H extends BaseViewHolder> extends AppMVVMActivity<T> {

    protected H mViewHolder;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mViewHolder = ContractProxy.getNewInstance(this, 1);
        mViewHolder.initView(getActivity(), getWindow().getDecorView());
        mViewHolder.doBusiness();
    }

    @Override
    protected void onDestroy() {
        mViewHolder.onDestroyView();
        super.onDestroy();
    }

}
