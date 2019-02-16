package com.bwie.yuan.yizhou_lianxi.model;

import com.bwie.yuan.yizhou_lianxi.utils.MyCallBack;

import java.util.Map;

public interface IModel {
    void get(String url, Class clazz, MyCallBack callBack);
    void post(String url, Class clazz, Map<String,String> map, MyCallBack callBack);
}
