package com.yper.feng.growup.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yper.feng.growup.Adapter.SubjectAnnouceListFragmentAdapter;
import com.yper.feng.growup.Module.Annouce;
import com.yper.feng.growup.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Feng on 2016/9/26.
 */

public class SubjectAnnouceFragment extends ListFragment {
    List<Annouce> annouceslists=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //data init

        Annouce annouce1=new Annouce("郑主任","通知,中午去吃饭哦,吃完饭,去河边集合!",new Date());
        Annouce annouce2=new Annouce("郑主任","通知,完成晚上的作业,然后开会点评!",new Date());
        annouceslists.add(annouce1);
        annouceslists.add(annouce2);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_subject_annouce,container,false);
        setListAdapter(new SubjectAnnouceListFragmentAdapter(getContext(),annouceslists));
        return  view;
    }

}
