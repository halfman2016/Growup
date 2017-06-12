package com.yper.feng.growup.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.umeng.analytics.MobclickAgent;
import com.yper.feng.growup.Adapter.MyPhotoListAdapter;
import com.yper.feng.growup.Module.Annouce;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.util.ArrayList;
import java.util.List;

public class MyPhotoList extends AppCompatActivity {

    private List<Photopic> photopicList =new ArrayList<>();
    private MDBTools mdb=new MDBTools();
    private Teacher teacher;
    private RecyclerView photolist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photo_list);
        Intent intent=getIntent();
        String teastr=   intent.getStringExtra("teacher");
        Gson gson=new GsonBuilder().create();
        teacher=gson.fromJson(teastr,Teacher.class);
        photolist= (RecyclerView) findViewById(R.id.myphotolist);

        photolist.setLayoutManager(new LinearLayoutManager(this));


        loaddata();


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

    private Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //加载
                   int size= photopicList.size();
                   if(size >0) {
                       photolist.setAdapter(new MyPhotoListAdapter(photopicList, getBaseContext()));
                   }
                   else
                {
                    Toast.makeText(getBaseContext(),"照片为空，请回退！",Toast.LENGTH_LONG).show();
                }
                    break;

            }
        }
    };


    private void loaddata(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                photopicList=mdb.getTeacherPics(teacher);
                if(photopicList==null)
                {return;}
                else
                {
                    Message msg=new Message();
                    msg.what=1;
                    myhandler.sendMessage(msg);
                }
            }
        }.start();

    }

}
