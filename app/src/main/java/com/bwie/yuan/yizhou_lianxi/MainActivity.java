package com.bwie.yuan.yizhou_lianxi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.yuan.yizhou_lianxi.adapter.ShopAdapter;
import com.bwie.yuan.yizhou_lianxi.bean.ShopBean;
import com.bwie.yuan.yizhou_lianxi.presenter.IPresenterImple;
import com.bwie.yuan.yizhou_lianxi.view.IView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoaderInterface;

import org.greenrobot.eventbus.EventBus;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {
@BindView(R.id.recyclerview)
    RecyclerView recyclerview;
@BindView(R.id.input)
    EditText input;
@BindView(R.id.sousuo)
    TextView sousuo;
    private IPresenterImple presenterImple;
    private ShopAdapter shopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Fresco.initialize(this);


        presenterImple = new IPresenterImple(this);

        initview();
    }


    private void initview() {
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerview.setLayoutManager(layoutManager);
        shopAdapter = new ShopAdapter(this);
        recyclerview.setAdapter(shopAdapter);

        shopAdapter.setClick(new ShopAdapter.OnClick() {
            @Override
            public void OnClick(String id) {

             EventBus.getDefault().postSticky(id);

             startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });

        getShow();
    }

    private void getShow() {
        presenterImple.get("commodity/v1/findCommodityByKeyword?keyword=卫衣&page=1&count=20",ShopBean.class);

    }
        @OnClick({R.id.sousuo})
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.sousuo:
                    presenterImple.get("commodity/v1/findCommodityByKeyword?keyword="+input.getText().toString()+"&page=1&count=20",ShopBean.class);
                    break;
                    default:
                        break;
            }
        }

    @Override
    public void getRequest(Object data) {
        if (data instanceof ShopBean)
        {
            ShopBean shopBean= (ShopBean) data;
            shopAdapter.setmDatas(shopBean.getResult());
        }

    }


}
