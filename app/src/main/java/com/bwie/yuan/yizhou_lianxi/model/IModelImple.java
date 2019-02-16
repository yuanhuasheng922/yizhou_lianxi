package com.bwie.yuan.yizhou_lianxi.model;

import com.bwie.yuan.yizhou_lianxi.utils.MyCallBack;
import com.bwie.yuan.yizhou_lianxi.utils.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class IModelImple implements IModel {
    @Override
    public void get(String url, final Class clazz, final MyCallBack callBack) {
        RetrofitManager.getInstance().get(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o=new Gson().fromJson(data,clazz);
                if (callBack!=null)
                {

                    callBack.onSuccess(o);
                }
            }

            @Override
            public void onFail(String error) {
                if (callBack!=null)
                {

                    callBack.onFail(error);
                }
            }
        });
    }

    @Override
    public void post(String url, final Class clazz, Map<String, String> map, final MyCallBack callBack) {
            RetrofitManager.getInstance().post(url, map, new RetrofitManager.HttpListener() {
                @Override
                public void onSuccess(String data) {
                    Object o=new Gson().fromJson(data,clazz);
                    if (callBack!=null)
                    {

                        callBack.onSuccess(o);
                    }
                }

                @Override
                public void onFail(String error) {
                    if (callBack!=null)
                    {

                       callBack.onFail(error);
                    }
                }
            });
    }
}
