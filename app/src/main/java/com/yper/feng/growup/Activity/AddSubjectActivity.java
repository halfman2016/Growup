package com.yper.feng.growup.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.yper.feng.growup.R;

import java.util.Calendar;

public class AddSubjectActivity extends AppCompatActivity {
    private TextView startDatedisplay = null;

    private TextView endDatedisplay=null;

    private Button cancel;
    private int which;
    //用来保存年月日：
    private int mYear;
    private int mMonth;
    private int mDay;
    //声明一个独一无二的标识，来作为要显示DatePicker的Dialog的ID：
    static final int DATE_DIALOG_ID = 0;
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
                //调用Activity类的方法来显示Dialog:调用这个方法会允许Activity管理该Dialog的生命周期，
                //并会调用 onCreateDialog(int)回调函数来请求一个Dialog
                showDialog(DATE_DIALOG_ID);
                which=1;
            }
        });

        endDatedisplay=(TextView)findViewById(R.id.endDateDisplay);

        endDatedisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //调用Activity类的方法来显示Dialog:调用这个方法会允许Activity管理该Dialog的生命周期，
                //并会调用 onCreateDialog(int)回调函数来请求一个Dialog
                showDialog(DATE_DIALOG_ID);
                which=2;
            }
        });

//获得当前的日期：
        final Calendar currentDate = Calendar.getInstance();
        mYear = currentDate.get(Calendar.YEAR);
        mMonth = currentDate.get(Calendar.MONTH);
        mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        //设置文本的内容：
        startDatedisplay.setText(new StringBuilder()
                .append(mYear).append("/")
                .append(mMonth + 1).append("/")//得到的月份+1，因为从0开始
                .append(mDay));
        endDatedisplay.setText(new StringBuilder()
                .append(mYear).append("/")
                .append(mMonth + 1).append("/")//得到的月份+1，因为从0开始
                .append(mDay+1));
    }



    private DatePickerDialog.OnDateSetListener mDateSetListener =new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            //设置文本的内容：

            switch (which) {
                case 1:

                    startDatedisplay.setText(new StringBuilder()
                            .append(mYear).append("/")
                            .append(mMonth + 1).append("/")//得到的月份+1，因为从0开始
                            .append(mDay));
                    break;
                case 2:
                    endDatedisplay.setText(new StringBuilder()
                        .append(mYear).append("/")
                        .append(mMonth + 1).append("/")//得到的月份+1，因为从0开始
                        .append(mDay));
            }
        }
    };

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,mDateSetListener,mYear, mMonth, mDay);
        }
        return null;
    }
}
