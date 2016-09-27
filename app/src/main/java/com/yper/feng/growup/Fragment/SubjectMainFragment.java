package com.yper.feng.growup.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.yper.feng.growup.Activity.MainActivity;
import com.yper.feng.growup.Activity.SubjectDetailMain;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.R;

/**
 * Created by Feng on 2016/7/10.
 */
public class SubjectMainFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_subject,container,false);
        return  view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


                Intent intent=new Intent(getContext(), SubjectDetailMain.class);
                intent.putExtra("subjectname",((Subject)getListAdapter().getItem(position)).getSubjectName());
                startActivity(intent);

    }

}
