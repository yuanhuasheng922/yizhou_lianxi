package com.bwie.yuan.yizhou_lianxi.presenter;

import com.bwie.yuan.yizhou_lianxi.model.IModelImple;
import com.bwie.yuan.yizhou_lianxi.utils.MyCallBack;
import com.bwie.yuan.yizhou_lianxi.view.IView;

import java.util.Map;

public class IPresenterImple implements IPresenter {
    private IView mIView;
    private IModelImple mIModelIMple;

    public IPresenterImple(IView mIView) {
        this.mIView = mIView;
        mIModelIMple = new IModelImple();
    }

    @Override
    public void get(String url, Class clazz) {
        mIModelIMple.get(url, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                mIView.getRequest(data);
            }

            @Override
            public void onFail(String error) {
                mIView.getRequest(error);
            }
        });
    }

    @Override
    public void post(String url, Class clazz, Map<String, String> map) {
        mIModelIMple.post(url, clazz, map, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                mIView.getRequest(data);
            }

            @Override
            public void onFail(String error) {
                mIView.getRequest(error);
            }
        });
    }
}
