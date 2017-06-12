package com.yper.feng.growup.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.umeng.analytics.MobclickAgent;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.MyApplication;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.BitmapCache;
import com.yper.feng.growup.Util.MDBTools;

public class PopPic extends AppCompatActivity {

    //弹出图片窗口


    private MDBTools mdbTools=new MDBTools();
    private Photopic photopic;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private NetworkImageView imageView;

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_pic);
        Intent intent=getIntent();
        String json=intent.getStringExtra("photo");
        Gson gson = new GsonBuilder().create();
        photopic=gson.fromJson(json,Photopic.class);
        imageView=(NetworkImageView) findViewById(R.id.poppicimage);
        imageView.requestFocus();

        requestQueue= Volley.newRequestQueue(getBaseContext());
        imageLoader=new ImageLoader(requestQueue,new BitmapCache());

        imageView.setImageUrl(MyApplication.getInstance().Url+photopic.getPicname(),imageLoader);
    }
}
