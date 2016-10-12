package com.yper.feng.growup.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yper.feng.growup.Activity.SubjectMain;
import com.yper.feng.growup.Module.GradeClass;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.R;

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
    private List<Student> stus1 =new ArrayList<>();
    private List<Student> stus2 =new ArrayList<>();
    private List<Student> stus3 =new ArrayList<>();
    private List<Student> stus4 =new ArrayList<>();
    private List<Student> stus5 =new ArrayList<>();
    private List<Student> stus6 =new ArrayList<>();
    private List<Student> stus7 =new ArrayList<>();
    private GradeClass gc1=new GradeClass("九1班");
    private GradeClass gc2=new GradeClass("九2班");
    private GradeClass gc3=new GradeClass("九3班");
    private GradeClass gc4=new GradeClass("八1班");
    private GradeClass gc5=new GradeClass("八2班");
    private GradeClass gc6=new GradeClass("八3班");
    private GradeClass gc7=new GradeClass("启新班");


    private TextView sdate=null;
    private TextView edate=null;

    private int mYear;
    private int mMonth;
    private int mDay;

    private  int which;
    static final int DATE_DIALOG_ID=0;

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    private List<GradeClass> gradeClassList =new ArrayList<>();

    private Subject subject;

    private GridView gridView;
    private ArrayList<HashMap<String,Object>> lstNameItem=new ArrayList<HashMap<String,Object>>();

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



        final View view=inflater.inflate(R.layout.fragment_subject_details,container,false);


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
                Log.d("myapp","heloo a clik");
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

        final LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.classlist);



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
                            setGridView(gradeClassList.get(i).getStus(),view);
                        }
                    }
                }
            });

        }


        return  view;
    }



    private void setGridView(List<Student> tempstulist,View view){

        gridView= (GridView) view.findViewById(R.id.mygridview);
        //gridView.setEmptyView(new View(getContext()));
        lstNameItem.clear();
        for(int i=0;i<tempstulist.size();i++){
            HashMap<String,Object> map=new HashMap<String, Object>();
            map.put("stuname",tempstulist.get(i).getName());
            lstNameItem.add(map);
        }

        SimpleAdapter adapter=new SimpleAdapter(getContext(),lstNameItem,
                R.layout.subject_item_grid_student,new String[]{"stuname"},new int[]{R.id.stuname});
        gridView.setAdapter(adapter);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //班级清单


        gradeClassList.add(gc1);
        gradeClassList.add(gc2);
        gradeClassList.add(gc3);
        gradeClassList.add(gc4);
        gradeClassList.add(gc5);
        gradeClassList.add(gc6);
        gradeClassList.add(gc7);

        //学生花名册

        //allstudents=mdb.getStus();
        Student stu1=new Student("胡涛");
        Student stu2=new Student("江先明");
        Student stu3=new Student("陈驿");
        Student stu4=new Student("余辉煌");
        Student stu5=new Student("彭堂博");
        Student stu6=new Student("彭永硕");
        Student stu7=new Student("刘尹康");
        Student stu8=new Student("张金茂");
        Student stu9=new Student("谢宗凌");
        Student stu10=new Student("赵哲");
        Student stu11=new Student("蔡康林");
        Student stu12=new Student("袁冲冲");


        stus1.add(stu1);
        stus1.add(stu2);
        stus1.add(stu3);
        stus1.add(stu4);
        stus1.add(stu5);
        stus1.add(stu6);
        stus1.add(stu7);
        stus1.add(stu8);
        stus1.add(stu9);
        stus1.add(stu10);
        stus1.add(stu11);
        stus1.add(stu12);


        gc1.setStus(stus1);


        //92班学生名单

        Student stu921=new Student("盛高林");
        Student stu922=new Student("徐园超");
        Student stu923=new Student("荣傲");
        Student stu924=new Student("韩宇晨");

        stus2.add(stu921);
        stus2.add(stu922);
        stus2.add(stu923);
        stus2.add(stu924);

        gc2.setStus(stus2);

        //93班学生名单
        Student stu931=new Student("杨雨萱");
        Student stu932=new Student("刘雅婷");
        Student stu933=new Student("李心怡");
        Student stu934=new Student("熊婕");
        Student stu935=new Student("盛高林");
        Student stu936=new Student("徐园超");
        Student stu937=new Student("荣傲");

        stus3.add(stu931);
        stus3.add(stu932);
        stus3.add(stu933);
        stus3.add(stu934);
        stus3.add(stu935);
        stus3.add(stu936);
        stus3.add(stu937);

        gc3.setStus(stus3);



    }


}
