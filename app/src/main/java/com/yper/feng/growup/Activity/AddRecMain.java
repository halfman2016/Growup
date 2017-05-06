package com.yper.feng.growup.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yper.feng.growup.Adapter.DayActionAlreadyhave;
import com.yper.feng.growup.Adapter.DayActionStuListAdapter;
import com.yper.feng.growup.Module.DayCheckListAction;
import com.yper.feng.growup.Module.DayCheckRec;
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
import java.util.Map;


public class AddRecMain extends AppCompatActivity {

    private Teacher teacher;
    private List<DayCheckListAction> dayChecksAcionList=new ArrayList<>();
    private DayCommonAction dayCommonAction;
    private String typeName;
    private MDBTools mdb=new MDBTools();
    private ListView listView;
    private DayCheckRec dayCheckRec;
    private List<Student> instudents=new ArrayList<>();
    private List<GradeClass> gradeClassList =new ArrayList<>();
    private GridView gridView;
    private Button buttonsave;
    private TextView txtteacher;
    private Button buttoncacel;
    private GradeClass gradeClass;

    private int isSaved=0;
    Handler myhandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

//
//                    for( int i=0;i<gradeClassList.size();i++)
//                {
//                   if( teacher.getOnDutyGradeClassId().compareTo(gradeClassList.get(i).get_id())==0) {
//                       instudents = gradeClassList.get(i).getStus();
//                       gradeClass=gradeClassList.get(i);
//                       break;
//                   }
//                    else
//                   {
//                       if(teacher.getOnDutyGradeClassName()=="all")
//                       {
//                           instudents.addAll(gradeClassList.get(i).getStus());
//                       }
//                   }
//                }

                    if (dayCheckRec.getStrdate()==null) {
                        listView.setAdapter(new DayActionStuListAdapter(gradeClass, dayChecksAcionList, getBaseContext()));
                        Utils.setListViewHeightBasedOnChildren(listView);
                    }
                    else
                    {
                        listView.setAdapter(new DayActionAlreadyhave(dayCheckRec,getBaseContext()));
                        Utils.setListViewHeightBasedOnChildren(listView);
                        buttoncacel.setText("退出");
                        buttonsave.setText("已经保存");
                        buttonsave.setClickable(false);
                        txtteacher.setText(dayCheckRec.getCheckedteachername());
                        isSaved=1;
                    }
                    break;

                case 2:

                    buttoncacel.setText("退出");
                    buttonsave.setText("已经保存");
                    buttonsave.setClickable(false);
                    isSaved=1;

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
         txtteacher= (TextView) findViewById(R.id.txtactioncheckteacher);
        txtteacher.setText(teacher.getName());

        buttonsave= (Button) findViewById(R.id.btndaycommonsave);
        buttoncacel=(Button)findViewById(R.id.btndaycommoncancel);

        new Thread() {
            @Override
            public void run() {
                super.run();
                dayChecksAcionList=mdb.getDayCheckListActions(typeName);
                gradeClass=mdb.getGradeClass(teacher.getOnDutyGradeClassId());

                dayCheckRec=mdb.getDayCheckRec(new Date(),gradeClass,typeName);
                Message message=new Message();
                message.what=1;
                myhandler.sendMessage(message);
            }


        }.start();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("日常规-"+typeName);
        setSupportActionBar(toolbar);
    }

   public void cancel(View view){

       finish();
   }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void save(View view){

        if(isSaved==0)

        {

            new AlertDialog.Builder(AddRecMain.this).setTitle("系统提示")//设置对话框标题

                    .setMessage("日记录即将保存（每日只能保存一次）！")//设置显示的内容

                    .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮

                        @Override

                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                            DayActionStuListAdapter dayActionStuListAdapter= (DayActionStuListAdapter) listView.getAdapter();
                            Map<Student,List> map=dayActionStuListAdapter.map;
                            final List<DayCommonAction>  resultActions=new ArrayList<>();
                            for (Student stu:map.keySet())
                            {
                                List temp=map.get(stu);
                                for(int i=0;i<temp.size();i++)
                                {
                                    if(Integer.parseInt(temp.get(i).toString())!=0)
                                    {
                                        DayCommonAction dayCommonAction=new DayCommonAction(dayChecksAcionList.get(i).getActionName(),dayChecksAcionList.get(i).getActionType(),Integer.parseInt(temp.get(i).toString()));
                                        List<Student> stus=new ArrayList<>();
                                        stus.add(stu);
                                        dayCommonAction.setRelativeStus(stus);
                                        dayCommonAction.setCheckedTeaid(teacher.get_id());
                                        dayCommonAction.setCheckedTeaName(teacher.getName());
                                        dayCommonAction.setActionPinTime(new Date());
                                        resultActions.add(dayCommonAction);
                                    }
                                }
                            }

                            new Thread() {
                                @Override
                                public void run() {

                                    if (resultActions.size() > 0) {
                                        mdb.addDayCommonActionRecs(resultActions, gradeClass, teacher, typeName, new Date());
                                        Message msg = new Message();
                                        msg.what = 2;
                                        myhandler.sendMessage(msg);
                                    }

                            }}.start();





                        }

                    }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮
                @Override

                public void onClick(DialogInterface dialog, int which) {//响应事件

                    // TODO Auto-generated method stub


                }

            }).show();//在按键响应事件中显示此对话框



            }
        else
        {

        }

   }
}
