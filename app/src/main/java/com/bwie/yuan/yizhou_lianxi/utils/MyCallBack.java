package com.bwie.yuan.yizhou_lianxi.utils;

public interface MyCallBack<T> {
    void onSuccess(T data);
    void onFail(String error);
}
