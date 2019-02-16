package com.bwie.yuan.yizhou_lianxi.presenter;

import com.bwie.yuan.yizhou_lianxi.utils.MyCallBack;

import java.util.Map;

public interface IPresenter {
    void get(String url, Class clazz);
    void post(String url, Class clazz, Map<String,String> map);
}
