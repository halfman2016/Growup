package com.yper.feng.growup.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yper.feng.growup.Adapter.List_Stu_Day_action;
import com.yper.feng.growup.Adapter.StudentsNameAdapter;
import com.yper.feng.growup.Module.DayCheckListAction;
import com.yper.feng.growup.Module.DayChecksAcion;
import com.yper.feng.growup.Module.DayCommonAction;
import com.yper.feng.growup.Module.GradeClass;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.util.ArrayList;
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

                    listView.setAdapter(new List_Stu_Day_action(dayChecksAcionList, getBaseContext()));

                    final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.classlist);
                    linearLayout.removeAllViews();

                    for (int i = 0; i < gradeClassList.size(); i++) {
                        TextView txt = new TextView(getBaseContext());
                        txt.setText(gradeClassList.get(i).getName());

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                        txt.setLayoutParams(lp);
                        //  txt.setButtonDrawable(R.color.colorAccent);
                        linearLayout.addView(txt);

                        txt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView tmp = (TextView) v;
                                for (int i = 0; i < gradeClassList.size(); i++) {
                                    if (gradeClassList.get(i).getName() == tmp.getText()) {

                                        gridView.setAdapter(new StudentsNameAdapter(gradeClassList.get(i).getStus(), instudents, getBaseContext()));

                                    }
                                }
                            }
                        });

                        gridView.setAdapter(new StudentsNameAdapter(instudents, instudents, getBaseContext()));

                        break;
                    }
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

        new Thread() {
            @Override
            public void run() {
                super.run();
            dayChecksAcionList=mdb.getDayCheckListActions(typeName);
                gradeClassList=mdb.getGradeClasses();
                instudents=mdb.getStus();
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

   }
}
