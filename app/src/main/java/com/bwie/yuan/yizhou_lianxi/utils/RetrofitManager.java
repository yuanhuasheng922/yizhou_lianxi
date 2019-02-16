package com.bwie.yuan.yizhou_lianxi.utils;

import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {
    private static String BACE_URL="http://172.17.8.100/small/";
    private static RetrofitManager instance;
    private final BaceApi baceApi;

    public static RetrofitManager getInstance()
    {
        if (instance==null)
        {
            synchronized (RetrofitManager.class)
            {
                instance=new RetrofitManager();
            }
        }
        return  instance;
    }

    public RetrofitManager() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();

        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .client(client)
                .baseUrl(BACE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        baceApi = retrofit.create(BaceApi.class);
    }
    public void get(String url,HttpListener listener)
    {
        baceApi.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    public void post(String url, Map<String,String> map,HttpListener listener)
    {
        baceApi.post(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    private Observer getObserver(final HttpListener listener) {
        Observer observer=new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (listener!=null)
                {
                    listener.onFail(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                String s = null;
                try {
                    s = responseBody.string();
                    if (listener!=null)
                    {
                        listener.onSuccess(s);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener!=null)
                    {
                        listener.onFail(e.getMessage());
                    }
                }

            }
        };
        return observer;
    }

    HttpListener listener;

    public void setListener(HttpListener listener) {
        this.listener = listener;
    }

    public interface HttpListener{
        void onSuccess(String data);
        void onFail(String error);
    }

}
