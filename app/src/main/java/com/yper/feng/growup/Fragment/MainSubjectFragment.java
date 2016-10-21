package com.yper.feng.growup.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yper.feng.growup.Activity.AddSubjectActivity;
import com.yper.feng.growup.Activity.MainActivity;
import com.yper.feng.growup.Activity.SubjectMain;
import com.yper.feng.growup.Adapter.MainSubjectListAdapter;
import com.yper.feng.growup.Adapter.SubjectListFragmentAdapter;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.LogRecord;

/**
 * Created by Feng on 2016/7/10.
 */
public class MainSubjectFragment extends ListFragment {
    //subject list
    Teacher teacher;
    List<Subject>  subjectList=new ArrayList<>();
    ImageView imageView;

    RelativeLayout linearLayout;


    public Handler mhandler=new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what)
            {
                case 1:
if(isVisible()) {
    setListAdapter(new MainSubjectListAdapter(subjectList, getContext()));
}
                    break;

            }
            super.handleMessage(msg);

        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_subject,container,false);
        imageView= (ImageView) view.findViewById(R.id.plussubjectimage);
        imageView.setImageResource(R.mipmap.plus);

        linearLayout=(RelativeLayout) view.findViewById(R.id.addnewsubject);
        linearLayout.setFocusable(true);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), AddSubjectActivity.class);
                intent.putExtra("Tid",teacher.getTid());
                intent.putExtra("name",teacher.getName());
                startActivityForResult(intent,200);
            }
        });

        teacher=((MainActivity)getActivity()).teacher;


        loaddata();

        return  view;
    }

    private void loaddata()
    {
        new Thread(){
            @Override
            public void run() {
                super.run();
                MDBTools mdb=new MDBTools();
                subjectList=mdb.getSubjects();
                Message message=new Message();
                message.what=1;
                mhandler.sendMessage(message);

            }
        }.start();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Gson gson=new GsonBuilder().create();

                Intent intent=new Intent(getContext(), SubjectMain.class);
            intent.putExtra("subject",gson.toJson((Subject)getListAdapter().getItem(position)));

            intent.putExtra("teacher",gson.toJson(teacher));

            startActivityForResult(intent, 1000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loaddata();


    }


}
