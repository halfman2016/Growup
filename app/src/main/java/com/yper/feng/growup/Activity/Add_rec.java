package com.yper.feng.growup.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yper.feng.growup.Adapter.AddRecListAdapter;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.util.List;

public class Add_rec extends  Activity {
private MDBTools mdb=new MDBTools();
    ListView listView;
    private List typelist;
    Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    listView.setAdapter(new AddRecListAdapter(typelist,getBaseContext()));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String day= (String) listView.getAdapter().getItem(position);
                            Intent intent=new Intent();
                            intent.putExtra("actiontype",day);
                            setResult(301,intent);
                            finish();

                        }
                    });
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rec);
        listView= (ListView) findViewById(R.id.listrec);

        new Thread(){
            @Override
            public void run() {
                super.run();
               typelist= mdb.getDayCheckListType();
                Message msg=new Message();
                msg.what=1;
                myhandler.sendMessage(msg);
            }
        }.start();

    }





}