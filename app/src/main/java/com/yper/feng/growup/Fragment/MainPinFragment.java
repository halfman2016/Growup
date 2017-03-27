package com.yper.feng.growup.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yper.feng.growup.Activity.MainActivity;
import com.yper.feng.growup.Activity.PinPhotoActivity;
import com.yper.feng.growup.Adapter.MainPinListAdapter;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2016/7/10.
 */
public class MainPinFragment extends ListFragment {
    private Teacher teacher;
    private Photopic photopic;
    private List<Photopic> photopics=new ArrayList<>();
    private MDBTools mdbTools=new MDBTools();
    public Handler myhandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
if(isVisible()) {
    setListAdapter(new MainPinListAdapter(photopics, getContext()));
}
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pin, container, false);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loaddata();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
                MainPinListAdapter mainPinListAdapter= (MainPinListAdapter) getListAdapter();
                Gson gson= new GsonBuilder().create();

                Intent intent =new Intent(getContext(), PinPhotoActivity.class);
                intent.putExtra("photopicjson",gson.toJson(mainPinListAdapter.getItem(position)));
                teacher=((MainActivity)getActivity()).teacher;
                intent.putExtra("teacherjson",gson.toJson(teacher));
                startActivity(intent);

    }



    public void loaddata()
    {
         new Thread(){
            @Override
            public void run() {
                super.run();
                photopics=  mdbTools.getfreePhotopic();
                Message msg=new Message();
                msg.what=1;
                myhandler.sendMessage(msg);
            }
        }.start();

    }
}
