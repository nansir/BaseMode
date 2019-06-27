package com.sir.library.mvp;

import com.sir.library.mvp.base.BaseModel;
import com.sir.library.mvp.base.BasePresenter;
import com.sir.library.mvp.base.BaseView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * MVP 模式的 代理
 * Created by zhuyinan on 2017/4/5.
 */
public class ContractProxy {

    private static final ContractProxy mInstance = new ContractProxy();

    private Map<Class, Object> mObjects;

    private ContractProxy() {
        mObjects = new HashMap<>();
    }

    public static ContractProxy getInstance() {
        return mInstance;
    }

    /**
     * Presenter
     * 通过反射, 获得定义Class时声明的父类的泛型参数的类型.
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     * determined
     */
    @SuppressWarnings("unchecked")
    public static Class<BasePresenter> getPresenterClazz(final Class clazz, final int index) {

        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return BasePresenter.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return BasePresenter.class;
        }
        if (!(params[index] instanceof Class)) {
            return BasePresenter.class;
        }
        return (Class) params[index];
    }

    /**
     * Model
     * 通过反射, 获得定义Class时声明的父类的泛型参数的类型.
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     * determined
     */
    @SuppressWarnings("unchecked")
    public static Class<BaseModel> getModelClazz(final Class clazz, final int index) {

        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return BaseModel.class;
        }
        //返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return BaseModel.class;
        }
        if (!(params[index] instanceof Class)) {
            return BaseModel.class;
        }
        return (Class) params[index];
    }

    /**
     * 获取presenter
     *
     * @param clzz
     * @param <T>
     * @return
     */
    public <T> T presenter(Class clzz) {
        if (!mObjects.containsKey(clzz)) {
            initInstance(clzz);
        }
        BasePresenter presenter = null;
        try {
            presenter = ((BasePresenter) clzz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) presenter;
    }

    /**
     * 进行初始化
     *
     * @param clss
     */
    public void initInstance(Class clss) {
        try {
            mObjects.put(clss, clss.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 绑定 View
     *
     * @param presenter
     * @param <V>
     * @return
     */
    public <V> V bindView(BaseView view, BasePresenter presenter) {
        if (view != presenter.getView()) {
            if (presenter.getView() != null) {
                presenter.detachView();
            }
            presenter.attachView(view);
        }
        return (V) view;
    }


    //初始化model
    public <M> M bindModel(Class clzz, BasePresenter presenter) {
        if (!mObjects.containsKey(clzz)) {
            initInstance(clzz);
        }
        BaseModel model = null;
        try {
            model = ((BaseModel) clzz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (model != presenter.getModel()) {
            if (presenter.getModel() != null) {
                presenter.detachModel();
            }
            presenter.attachModel(model);
        }
        return (M) model;
    }

    //解除绑定 移除map
    public void unbindPresenter(Class clzz, BaseView var1) {
        if (mObjects.containsKey(clzz)) {
            BasePresenter presenter = null;
            try {
                presenter = ((BasePresenter) clzz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (var1 != presenter.getView()) {
                if (presenter.getView() != null)
                    presenter.detachView();
                mObjects.remove(clzz);
            }
        }
    }

    //解除绑定
    public void unbindView(BaseView view, BasePresenter presenter) {
        if (view != presenter.getView()) {
            if (presenter.getView() != null)
                presenter.detachView();
        }
    }

    // 解除绑定
    public void unbindModel(Class clzz, BasePresenter presenter) {
        if (mObjects.containsKey(clzz)) {
            BaseModel model = null;
            try {
                model = ((BaseModel) clzz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (model != presenter.getModel()) {
                if (presenter.getModel() != null)
                    presenter.detachModel();
                mObjects.remove(clzz);
            }
        }
    }
}
