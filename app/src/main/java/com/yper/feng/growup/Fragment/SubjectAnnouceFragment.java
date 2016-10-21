package com.yper.feng.growup.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yper.feng.growup.Activity.SubjectMain;
import com.yper.feng.growup.Adapter.SubjectAnnouceListFragmentAdapter;
import com.yper.feng.growup.Module.Annouce;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Feng on 2016/9/26.
 */

public class SubjectAnnouceFragment extends ListFragment {
  private   List<Annouce> annouceslists=new ArrayList<>();
private MDBTools mdb=new MDBTools();
    private EditText txtAnnoucebody;
   private Subject subject;
    private Teacher teacher;
    private Annouce annouce;
   Handler myhandler=new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
        switch (msg.what){
            case 1:
                setListAdapter(new SubjectAnnouceListFragmentAdapter(getContext(),annouceslists));
                break;
            case 2:
                loaddata();
                break;
        }
       }
   };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //data init

        subject=((SubjectMain)getActivity()).subject;
        teacher=((SubjectMain)getActivity()).teacher;


    loaddata();


    }

    void loaddata(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                annouceslists=mdb.getAnnouces(subject);
                Message msg=new Message();
                msg.what=1;
                myhandler.sendMessage(msg);
            }
        }.start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_subject_annouce,container,false);

        TextView txtannouceauthor=(TextView)view.findViewById(R.id.txtannouceauthor);
        txtannouceauthor.setText(teacher.getName());

        TextView txtAnnoucetime=(TextView)view.findViewById(R.id.txtAnnoucetime);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        txtAnnoucetime.setText(sdf.format(new Date()));

        txtAnnoucebody=(EditText)view.findViewById(R.id.txtAnnoucebody);
        txtAnnoucebody.setText("");
        Button btnannouce=(Button) view.findViewById(R.id.btnAnnouce);
        btnannouce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annouce=new Annouce(teacher.getName(),txtAnnoucebody.getText().toString(),new Date());
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        mdb.addAnnouce(subject,annouce);
                        Message msg=new Message();
                        msg.what=2;
                        myhandler.sendMessage(msg);
                    }
                }.start();

            }
        });


        return  view;


    }



}
