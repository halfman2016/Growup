package com.yper.feng.growup.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yper.feng.growup.Activity.SubjectMain;
import com.yper.feng.growup.Adapter.StudentsNameAdapter;
import com.yper.feng.growup.Module.GradeClass;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Feng on 2016/9/26.
 */

public class SubjectDetailFragment extends Fragment {
    private List<Student> allstudents =new ArrayList<>();
    private List<Student> instudents=new ArrayList<>();
    private MDBTools mdb=new MDBTools();

    private TextView sdate=null;
    private TextView edate=null;
    private EditText txtSubject_info;

    private int mYear;
    private int mMonth;
    private int mDay;

    private  int which;
    static final int DATE_DIALOG_ID=0;

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    private List<GradeClass> gradeClassList =new ArrayList<>();

    private Subject subject;

    private GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // return super.onCreateView(inflater, container, savedInstanceState);
        FragmentActivity activity=getActivity();
        if(activity instanceof SubjectMain)
        {
            SubjectMain subjectMain =(SubjectMain) activity;
            subject= subjectMain.subject;

        }


        instudents=subject.getStudents();


        final View view=inflater.inflate(R.layout.fragment_subject_details,container,false);
        gridView= (GridView) view.findViewById(R.id.mygridview);
        txtSubject_info=(EditText)view.findViewById(R.id.txtSubject_info);
        txtSubject_info.setText(subject.getSubjectInfo());
        sdate=(TextView)view.findViewById(R.id.txtsdate);
        edate=(TextView)view.findViewById(R.id.txtedate);

        sdate.setText( sdf.format(subject.getStartTime()));
        edate.setText(sdf.format(subject.getEndTime()));

        sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar d = Calendar.getInstance(Locale.CHINA);
//创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例
                Date myDate= null;
                try {
                    myDate = sdf.parse(sdate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//创建一个Date实例
                d.setTime(myDate);
//设置日历的时间，把一个新建Date实例myDate传入
                int year=d.get(Calendar.YEAR);
                int month=d.get(Calendar.MONTH);
                int day=d.get(Calendar.DAY_OF_MONTH);
//获得日历中的 year month day
                DatePickerDialog dlg=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        sdate.setText(Integer.toString(year)+"-" + Integer.toString(monthOfYear+1)+"-"+Integer.toString(dayOfMonth));
                        try {
                            Date ssdate=sdf.parse(sdate.getText().toString());
                            Date eedate=sdf.parse(edate.getText().toString());
                            if (ssdate.compareTo(eedate)>0)
                            {

                                edate.setText(sdate.getText());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);
//新建一个DatePickerDialog 构造方法中
//     （设备上下文，OnDateSetListener时间设置监听器，默认年，默认月，默认日）
                dlg.show();
            }
        });

        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar d = Calendar.getInstance(Locale.CHINA);
//创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例
                Date myDate= null;
                try {
                    myDate = sdf.parse(edate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//创建一个Date实例
                d.setTime(myDate);
//设置日历的时间，把一个新建Date实例myDate传入
                int year=d.get(Calendar.YEAR);
                int month=d.get(Calendar.MONTH);
                int day=d.get(Calendar.DAY_OF_MONTH);
//获得日历中的 year month day
                DatePickerDialog dlg=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        edate.setText(Integer.toString(year)+"-" + Integer.toString(monthOfYear+1)+"-"+Integer.toString(dayOfMonth));
                        try {
                            Date ssdate=sdf.parse(sdate.getText().toString());
                            Date eedate=sdf.parse(edate.getText().toString());
                            if (ssdate.compareTo(eedate)>0)
                            {

                                sdate.setText(edate.getText());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);
//新建一个DatePickerDialog 构造方法中
//     （设备上下文，OnDateSetListener时间设置监听器，默认年，默认月，默认日）
                dlg.show();

            }
        });

        //设置文本的内容：

loaddata();

        Button btn= (Button) view.findViewById(R.id.txtAddstustosubject);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subject.setSubjectInfo(txtSubject_info.getText().toString());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                try {
                    subject.setStartTime(sdf.parse(sdate.getText().toString()));
                    subject.setEndTime(sdf.parse(edate.getText().toString()));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (instudents!=null)
                {StudentsNameAdapter sta= (StudentsNameAdapter) gridView.getAdapter();
                    instudents=sta.getStudents();
                subject.setStudents(instudents);}

                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        mdb.updateSubject(subject);
                    }
                }.start();
            }
        });

        return  view;
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    void loaddata(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                gradeClassList=mdb.getGradeClasses();
                Message msg=new Message();
                msg.what=1;
                myhandler.sendMessage(msg);
            }
        }.start();
    }

    Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        switch (msg.what)
        {
            case 1:
                final LinearLayout linearLayout= (LinearLayout) getView().findViewById(R.id.classlist);
                linearLayout.removeAllViews();

                for(int i=0;i<gradeClassList.size();i++){
                    TextView txt = new TextView(getContext());
                    txt.setText(gradeClassList.get(i).getName());

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    txt.setLayoutParams(lp);
                    //  txt.setButtonDrawable(R.color.colorAccent);
                    linearLayout.addView(txt);

                    txt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView tmp=(TextView) v;
                            for(int i=0;i<gradeClassList.size();i++)
                            {
                                if (gradeClassList.get(i).getName()==tmp.getText())
                                {

                                    gridView.setAdapter(new StudentsNameAdapter(gradeClassList.get(i).getStus(),instudents,getContext()));

                                }
                            }
                        }
                    });

                    gridView.setAdapter(new StudentsNameAdapter(instudents,instudents,getContext()));

                }




                break;
        }
        }
    };
}
