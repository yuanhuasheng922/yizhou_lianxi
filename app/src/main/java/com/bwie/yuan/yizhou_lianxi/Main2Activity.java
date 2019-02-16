package com.bwie.yuan.yizhou_lianxi;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bwie.yuan.yizhou_lianxi.adapter.WebBean;
import com.bwie.yuan.yizhou_lianxi.presenter.IPresenterImple;
import com.bwie.yuan.yizhou_lianxi.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoaderInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Main2Activity extends AppCompatActivity implements IView {
//        @BindView(R.id.webView)
//        WebView webview;
        @BindView(R.id.banner)
    Banner banner;
    private IPresenterImple presenterImple;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        //webview.getSettings().setJavaScriptEnabled(true);
        presenterImple = new IPresenterImple(this);
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Uri uri = Uri.parse((String) path);
                imageView.setImageURI(uri);

            }

            @Override
            public ImageView createImageView(Context context) {
                SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
                return simpleDraweeView;
            }
        });


    }

    //接受
    @Subscribe(sticky = true)
    public void recis(String msg)
    {
        presenterImple.get("commodity/v1/findCommodityDetailsById?commodityId="+msg+"",WebBean.class);
    }

    @Override
    public void getRequest(Object data) {
        if (data instanceof WebBean)
        {
            WebBean webBean= (WebBean) data;
            list = new ArrayList<>();
            String[] split = webBean.getResult().getPicture().split("\\,");
            for(int i=0;i<split.length;i++)
            {
                list.add(split[i]);
            }
            banner.setImages(list);
            banner.start();

//            WebSettings settings = webview.getSettings();
//            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//            webview.loadDataWithBaseURL(null, webBean.getResult().getDetails(),"text/html","utf-8",null);
        }
    }
}
