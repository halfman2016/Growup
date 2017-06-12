package com.yper.feng.growup.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.umeng.analytics.MobclickAgent;
import com.yper.feng.growup.R;

public class TakeCam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_cam);
        Intent intent=getIntent();
        Uri fileUri=intent.getData();

        final VideoView videoView= (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(fileUri);
        videoView.start();


    }
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

}
