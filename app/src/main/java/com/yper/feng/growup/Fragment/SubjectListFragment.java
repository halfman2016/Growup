package com.yper.feng.growup.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.yper.feng.growup.Activity.SubjectMain;
import com.yper.feng.growup.Adapter.SubjectListFragmentAdapter;
import com.yper.feng.growup.Module.Annouce;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Feng on 2016/9/26.
 */

public class SubjectListFragment extends ListFragment {
    private List<Photopic> photopicList =new ArrayList<>();
    private String annouce;
    private TextView txtannouce;
    private MDBTools mdb=new MDBTools();
private Subject subject;
    private Teacher teacher;
    private Annouce ann;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_subject_list, container, false);
        txtannouce=(TextView) view.findViewById(R.id.txtAnnouce);
        txtannouce.setText("");

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teacher=((SubjectMain)getActivity()).teacher;
        subject=((SubjectMain)getActivity()).subject;
        loaddata();
//        Photopic photopic1 =new Photopic();
//        Photopic photopic2 =new Photopic();
//        photopic1.setPhotoauthor("张晓帅");
//        photopic2.setPhotoauthor("zhangli ");
//        photopic1.setPhotodate(new Date());
//        photopic2.setPhotodate(new Date());
//        photopic1.setPhotomemo("哈哈,太阳真大啊");
//        photopic2.setPhotomemo("美丽的花儿");
//        photopicList.add(photopic1);
//        photopicList.add(photopic2);

     //   annouce="通知,大家中午12点集合!不要错过时间!";

    }
private Handler myhandler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what)
        {
            case 1:
                if (isVisible()) {
                    setListAdapter(new SubjectListFragmentAdapter(photopicList, getContext(),teacher));
                    txtannouce.setText(ann.getAnnouceBody());
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
                ann=mdb.getAnnouceLatest(subject);
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
