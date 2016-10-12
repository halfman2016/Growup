package com.yper.feng.growup.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.yper.feng.growup.Adapter.SubjectListFragmentAdapter;
import com.yper.feng.growup.Module.Photopic;
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

    private MDBTools mdbTools=new MDBTools();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_subject_list, container, false);
        setListAdapter(new SubjectListFragmentAdapter(photopicList,getContext()));
        TextView txtannouce=(TextView) view.findViewById(R.id.txtAnnouce);
        txtannouce.setText(annouce);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        Photopic photopic1 =new Photopic();
        Photopic photopic2 =new Photopic();
        photopic1.setPhotoauthor("张晓帅");
        photopic2.setPhotoauthor("zhangli ");
        photopic1.setPhotodate(new Date());
        photopic2.setPhotodate(new Date());
        photopic1.setPhotomemo("哈哈,太阳真大啊");
        photopic2.setPhotomemo("美丽的花儿");
        photopicList.add(photopic1);
        photopicList.add(photopic2);

        annouce="通知,大家中午12点集合!不要错过时间!";

    }

    void  initData()
    {
        new Thread(){

            @Override
            public void run() {
                super.run();


            }
        }.start();
    }


}
