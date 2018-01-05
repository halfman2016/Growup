package com.yper.feng.growup.Activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yper.feng.growup.Module.LogAction;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.MyApplication;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;
import com.yper.feng.growup.Util.MySqlTools;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddSubjectActivity extends AppCompatActivity {
    String teachername, Tid;
    private TextView startDatedisplay = null;
    private TextView endDatedisplay=null;
    private Button cancel;
    private Teacher teacher;
    private  SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
    private android.os.Handler mhandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    setResult(201);
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        cancel=(Button)findViewById(R.id.btncancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startDatedisplay = (TextView)findViewById(R.id.startdateDisplay);

        startDatedisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar d = Calendar.getInstance(Locale.CHINA);
//创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例
                Date myDate= null;
                try {
                    myDate = sdf.parse(startDatedisplay.getText().toString());
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
                DatePickerDialog dlg=new DatePickerDialog(AddSubjectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        startDatedisplay.setText(Integer.toString(year)+"-" + Integer.toString(monthOfYear+1)+"-"+Integer.toString(dayOfMonth));
                        try {
                            Date ssdate=sdf.parse(startDatedisplay.getText().toString());
                            Date eedate=sdf.parse(endDatedisplay.getText().toString());
                            if (ssdate.compareTo(eedate)>0)
                            {

                                endDatedisplay.setText(startDatedisplay.getText());
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

        endDatedisplay=(TextView)findViewById(R.id.endDateDisplay);

        endDatedisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar d = Calendar.getInstance(Locale.CHINA);
//创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例
                Date myDate= null;
                try {
                    myDate = sdf.parse(endDatedisplay.getText().toString());
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
                DatePickerDialog dlg=new DatePickerDialog(AddSubjectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        endDatedisplay.setText(Integer.toString(year)+"-" + Integer.toString(monthOfYear+1)+"-"+Integer.toString(dayOfMonth));
                        try {
                            Date ssdate=sdf.parse(startDatedisplay.getText().toString());
                            Date eedate=sdf.parse(endDatedisplay.getText().toString());
                            if (ssdate.compareTo(eedate)>0)
                            {

                                startDatedisplay.setText(endDatedisplay.getText());
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

//获得当前的日期：
        final Calendar currentDate = Calendar.getInstance();
        int mYear = currentDate.get(Calendar.YEAR);
        int mMonth = currentDate.get(Calendar.MONTH);
        int mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        //设置文本的内容：
        startDatedisplay.setText(new StringBuilder()
                .append(mYear).append("-")
                .append(mMonth + 1).append("-")//得到的月份+1，因为从0开始
                .append(mDay));
        endDatedisplay.setText(new StringBuilder()
                .append(mYear).append("-")
                .append(mMonth + 1).append("-")//得到的月份+1，因为从0开始
                .append(mDay+1));

        //教师姓名,发布人

            teachername=getIntent().getStringExtra("name");
            Tid=getIntent().getStringExtra("Tid");
        MyApplication myApplication=MyApplication.getInstance();
        teacher=myApplication.getTeacher();
    }

    public void saveSubject(View view)
    {
        Intent intent=new Intent();

        setResult(201, intent);

        final EditText txtSubjectName= (EditText) findViewById(R.id.txtsubjectname);
        final EditText txtSubjectInfo= (EditText) findViewById(R.id.txtsubjectinfo);
        final TextView txtsdate= (TextView) findViewById(R.id.startdateDisplay);
        final TextView txtedate= (TextView) findViewById(R.id.endDateDisplay);
        String temp= txtSubjectName.getText().toString();
        String temp2=txtSubjectInfo.getText().toString();
        if((temp.equals("") )||(temp2.equals("")) ) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("请输入专题名称和专题说明").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }
        else

        {
            new AlertDialog.Builder(this).setTitle("添加专题提示")
                    .setMessage("确认添加专题信息?添加后不能删除,请慎重!").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //save method
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        MDBTools mdb=new MDBTools();
                        Subject subject=new Subject(txtSubjectName.getText().toString());
                        subject.setSubjectInfo(txtSubjectInfo.getText().toString());
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            subject.setStartTime(simpleDateFormat.parse(txtsdate.getText().toString()));
                            subject.setEndTime(simpleDateFormat.parse(txtedate.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        mdb.saveSubject(subject);

                        //记录
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                MySqlTools mySqlTools=new MySqlTools();
                                mySqlTools.getConn();
                                LogAction logAction=new LogAction();
                                logAction.setActionname("增加专题");
                                logAction.setActiontime(new Timestamp(new Date().getTime()));
                                logAction.setActionpeoplename(teacher.getName());
                                logAction.setActionpeopleid(teacher.getTid());
                                logAction.setActiongradeclassname(teacher.getOnDutyGradeClassName());
                                mySqlTools.insertActionLog(logAction);
                                mySqlTools.closeConn();
                            }
                        }).start();

                        Message msg=new Message();
                        msg.what=1;
                        mhandler.sendMessage(msg);

                    }
                }).start();

                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
