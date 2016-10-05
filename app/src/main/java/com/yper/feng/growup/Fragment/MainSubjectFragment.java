package com.yper.feng.growup.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yper.feng.growup.Activity.AddSubjectActivity;
import com.yper.feng.growup.Activity.SubjectMain;
import com.yper.feng.growup.Adapter.MainSubjectListAdapter;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2016/7/10.
 */
public class MainSubjectFragment extends ListFragment {
    //subject list
    List<Subject>  subjectList=new ArrayList<>();
    ImageView imageView;
    RelativeLayout linearLayout;
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
                startActivityForResult(intent,101);
            }
        });

        MainSubjectListAdapter mainSubjectListAdapter= (MainSubjectListAdapter) getListAdapter();
        subjectList=mainSubjectListAdapter.items;
        Log.d("myapp","subjectlis is " + String.valueOf(subjectList.size()));
        return  view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


                Intent intent=new Intent(getContext(), SubjectMain.class);
                intent.putExtra("subjectname",((Subject)getListAdapter().getItem(position)).getSubjectName());
                intent.putExtra("subjectid",((Subject)getListAdapter().getItem(position)).get_id().toString());
                startActivityForResult(intent,100);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==0) return;  // 取消时退出
        if(requestCode==101)
        {
            Log.d("myapp",String.valueOf(resultCode));
        }

    }
}
