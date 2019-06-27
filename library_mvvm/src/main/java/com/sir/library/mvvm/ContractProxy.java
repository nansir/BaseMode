package com.sir.library.mvvm;

import java.lang.reflect.ParameterizedType;

public class ContractProxy {

    /**
     * 实例
     *
     * @param object
     * @param i
     * @param <T>
     * @return
     */
    public static <T> T getNewInstance(Object object, int i) {
        if (object != null) {
            try {
                return ((Class<T>) ((ParameterizedType) (object.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> T getInstance(Object object, int i) {
        if (object != null) {
            return (T) ((ParameterizedType) object.getClass().getGenericSuperclass()).getActualTypeArguments()[i];
        }
        return null;
    }
}
