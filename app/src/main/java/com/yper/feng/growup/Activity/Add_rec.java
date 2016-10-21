package com.yper.feng.growup.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yper.feng.growup.Adapter.AddRecListAdapter;
import com.yper.feng.growup.Module.DayChecksAcion;
import com.yper.feng.growup.Module.DayCommonAction;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.util.ArrayList;
import java.util.List;

public class Add_rec extends  Activity {
private List<DayChecksAcion> days=new ArrayList<>();
private MDBTools mdb=new MDBTools();
    ListView listView;

    Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    listView.setAdapter(new AddRecListAdapter(days,getBaseContext()));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            DayChecksAcion day=(DayChecksAcion) listView.getAdapter().getItem(position);
                            Intent intent=new Intent();
                            intent.putExtra("actiontype",day.getActionType());
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
            days=mdb.getDayChecksActions();
                Message msg=new Message();
                msg.what=1;
                myhandler.sendMessage(msg);
            }
        }.start();

    }





}