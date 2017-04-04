package com.yper.feng.growup.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yper.feng.growup.Activity.MainActivity;
import com.yper.feng.growup.Activity.MyPhotoList;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;


public class MainPeopleFragment extends Fragment {
    TextView txtTeacherName;
    Teacher teacher;

    public MainPeopleFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main_people, container, false);
        teacher=((MainActivity)getActivity()).teacher;

        txtTeacherName= (TextView) view.findViewById(R.id.teacher_name);
        txtTeacherName.setText(teacher.getName());

        Button btnmyphoto =(Button) view.findViewById(R.id.btnMyPhoto);
        btnmyphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MyPhotoList.class);
                Gson gson=new GsonBuilder().create();
                String teastr=gson.toJson(teacher);

                intent.putExtra("teacher",teastr);
                startActivity(intent);
            }
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
