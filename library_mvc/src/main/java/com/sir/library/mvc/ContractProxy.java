package com.sir.library.mvc;


import com.sir.library.mvc.base.BaseModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * MVC 模式的 代理
 * Created by zhuyinan on 2017/4/5.
 */
public class ContractProxy {

    private static final ContractProxy mInstance = new ContractProxy();

    public static ContractProxy getInstance() {
        return mInstance;
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
    public Class<BaseModel> getModelClazz(final Class clazz, final int index) {

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
}
