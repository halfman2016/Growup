package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.yper.feng.growup.Fragment.SubjectDetailFragment;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.Util.MDBTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangfeng on 2016/10/19.
 */

public class StudentsNameAdapter extends BaseAdapter {
    private List<Student> studentList=new ArrayList<>();
    private List<Student> students=new ArrayList<>();
    private MDBTools mdb =new MDBTools();
    private Context context;
    public StudentsNameAdapter(List<Student> studentList,List<Student> instudents,Context context) {
        this.studentList = studentList;
        this.context=context;
        if (instudents!=null) {
            this.students = instudents;
        }
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Student student=studentList.get(position);

        CheckBox box = null;
        if(convertView ==null){
            box = new CheckBox(context);
            box.setText(student.getName());
        }else{
            box = (CheckBox)convertView;
        }

        {

            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).get_id().toString().equals(student.get_id().toString())) {
                    box.setChecked(true);
                    break;
                }
            }
        }
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CheckBox box=(CheckBox) buttonView;
                if (isChecked==true)
                {
                    students.add(student);


                }
                if (isChecked==false){
                    int result=studentshave(students,student);
                    if (result!=-1) {
                        students.remove(result);

                    }
                }

            }
        });
        return box;
    }

    int studentshave(List<Student> stus,Student student){
        int result=-1;

        for (int i=0;i<stus.size();i++){
            if (stus.get(i).get_id().toString().equals(student.get_id().toString()))
            {
                result=i;
                break;
            }
        }

        return result;

    }



    public List<Student> getStudents(){
        if (students!=null) {
            return students;
        }
        else
        {
            return new ArrayList<Student>();
        }
    }

}
