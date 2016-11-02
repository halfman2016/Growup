package com.yper.feng.growup.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yper.feng.growup.Activity.MainActivity;
import com.yper.feng.growup.Activity.PinPhotoActivity;
import com.yper.feng.growup.Activity.SubjectMain;
import com.yper.feng.growup.Adapter.MainPinListAdapter;
import com.yper.feng.growup.Adapter.SubjectListFragmentAdapter;
import com.yper.feng.growup.Adapter.SubjectPinFragmentAdapter;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2016/9/26.
 */

public class SubjectPinFragment extends ListFragment {
    List<Photopic> photopicList=new ArrayList<>();

    MDBTools mdb=new MDBTools();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_subject_pin,container,false);

        loaddata();
        return  view;
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Teacher teacher;
        Subject subject=((SubjectMain)getActivity()).subject;

        SubjectPinFragmentAdapter subjectPinFragmentAdapter= (SubjectPinFragmentAdapter) getListAdapter();
        Gson gson= new GsonBuilder().create();

        Intent intent =new Intent(getContext(), PinPhotoActivity.class);
        intent.putExtra("photopicjson",gson.toJson(subjectPinFragmentAdapter.getItem(position)));
        teacher=((SubjectMain)getActivity()).teacher;
        intent.putExtra("teacherjson",gson.toJson(teacher));
        intent.putExtra("subjectjson",gson.toJson(subject));
        startActivity(intent);

    }

    private Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    if (isVisible()) {
                        setListAdapter(new SubjectPinFragmentAdapter(photopicList, getContext()));
                    }
                    break;
            }
        }
    };
    public void  loaddata()
    {
        new Thread(){

            @Override
            public void run() {
                super.run();
                photopicList=mdb.getSubjectPhoto(((SubjectMain)getActivity()).subject);

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
