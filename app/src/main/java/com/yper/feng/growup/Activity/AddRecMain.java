package com.yper.feng.growup.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yper.feng.growup.Adapter.DayActionStuListAdapter;
import com.yper.feng.growup.Module.DayCheckListAction;
import com.yper.feng.growup.Module.DayCommonAction;
import com.yper.feng.growup.Module.GradeClass;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;
import com.yper.feng.growup.Util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AddRecMain extends AppCompatActivity {

    private Teacher teacher;
    private List<DayCheckListAction> dayChecksAcionList=new ArrayList<>();
    private DayCommonAction dayCommonAction;
    private String typeName;
    private MDBTools mdb=new MDBTools();
    private ListView listView;

    private List<Student> instudents=new ArrayList<>();
    private List<GradeClass> gradeClassList =new ArrayList<>();
    private GridView gridView;

    Handler myhandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:


                    for( int i=0;i<gradeClassList.size();i++)
                {
                   if( teacher.getOnDutyGradeClassId().compareTo(gradeClassList.get(i).get_id())==0) {
                       instudents = gradeClassList.get(i).getStus();
                       break;
                   }
                    else
                   {
                       if(teacher.getOnDutyGradeClassName()=="all")
                       {
                           instudents.addAll(gradeClassList.get(i).getStus());
                       }
                   }
                }



                    listView.setAdapter(new DayActionStuListAdapter(instudents,dayChecksAcionList, getBaseContext()));
                    Utils.setListViewHeightBasedOnChildren(listView);

                    break;
                    }

        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rec_main);

        listView= (ListView) findViewById(R.id.list_stu_common_action);
        gridView=(GridView) findViewById(R.id.mygridview);
        Intent intent=getIntent();
        typeName=intent.getStringExtra("actiontype");
        Gson gson=new GsonBuilder().create();

        teacher=gson.fromJson(intent.getStringExtra("teacherjson"),Teacher.class);

        TextView txtActiondate= (TextView) findViewById(R.id.txtActiondate);
        txtActiondate.setText(new SimpleDateFormat("yy-MM-dd").format(new Date()));
        TextView txtteacher= (TextView) findViewById(R.id.txtactioncheckteacher);
        txtteacher.setText(teacher.getName());

        new Thread() {
            @Override
            public void run() {
                super.run();
                dayChecksAcionList=mdb.getDayCheckListActions(typeName);
                gradeClassList=mdb.getGradeClasses();
                Message message=new Message();
                message.what=1;
                myhandler.sendMessage(message);
            }


        }.start();
    }

   public void cancel(View view){

       finish();
   }
   public void save(View view){



      for(int i=0;i<listView.getChildCount();i++)
      {
          View resultview=listView.getChildAt(i);

      }

   }
}
