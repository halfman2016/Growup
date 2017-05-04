package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.yper.feng.growup.Module.DayCheckListAction;
import com.yper.feng.growup.Module.GradeClass;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangfeng on 2016/10/25.
 */

public class DayActionStuListAdapter extends BaseAdapter {

    public List<Student> students=new ArrayList<>();
    public List<DayCheckListAction> dayCheckListActions=new ArrayList<>();
    public Map<Student,List> map=new HashMap<>();
    private Context context;
    private LayoutInflater layoutInflater;
    private GradeClass gradeClass;

    public DayActionStuListAdapter(GradeClass gradeClass, List<DayCheckListAction> dayCheckListActions, Context context) {
        this.gradeClass=gradeClass;
        this.dayCheckListActions=dayCheckListActions;
        this.context=context;
        this.students=gradeClass.getStus();
        for (Student stu:gradeClass.getStus())
        {
            List temp=new ArrayList();
            for(int i=0;i<dayCheckListActions.size();i++)
            {
                temp.add(0);
            }
            map.put(stu,temp);
        }
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Student student =students.get(position);

        final  viewHolder vh;

        if (convertView==null)
        {
            vh=new viewHolder();
            convertView=layoutInflater.inflate(R.layout.add_rec_stu_action,parent,false);
            vh.stuname= (TextView) convertView.findViewById(R.id.stuname);
            vh.actions=(LinearLayout)convertView.findViewById(R.id.actions);

            convertView.setTag(vh);

        }
        else
        {
            vh=(viewHolder)convertView.getTag();

        }

        vh.stuname.setText(student.getName());
        vh.stuname.setTextColor(Color.BLUE);
        for (int i=0;i<dayCheckListActions.size();i++)
        {
            LinearLayout ll=new LinearLayout(context);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            Log.d("myapp",String.valueOf(ll.getHeight()));
            final Spinner spinner=new Spinner(context);
            TextView txtactionname=new TextView(context);
            txtactionname.setWidth(230);
            txtactionname.setTextColor(Color.DKGRAY);
            txtactionname.setBackgroundColor(Color.WHITE);
            txtactionname.setText(dayCheckListActions.get(i).getActionName());

           int value= dayCheckListActions.get(i).getDefaultvalue();

            if(value>=0)
            {
                List list = new ArrayList();

                for (int ii=0;ii<value*3+1;ii++)
                {
                    list.add(2*value-ii);
                }

                ArrayAdapter adapter=new ArrayAdapter(context,R.layout.day_check_item,list);
                adapter.setDropDownViewResource(R.layout.dropdownstyle);
                spinner.setAdapter(adapter);

                if (dayCheckListActions.get(i).isDayaddscore())
                {
                    spinner.setSelection(value);

                }
                else {
                    spinner.setSelection(2*value);

                }

            }
            else
            {
                List list=new ArrayList();
                for(int ii=0;ii>2*value-1;ii--)
                {
                    list.add(ii);
                }
                ArrayAdapter adapter=new ArrayAdapter(context,R.layout.day_check_item,list);
                adapter.setDropDownViewResource(R.layout.dropdownstyle);
                spinner.setAdapter(adapter);

                spinner.setSelection(0);


            }



            ll.addView(txtactionname);
            ll.addView(spinner);
            final int finalI = i;
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    map.get(student).set(finalI,Integer.parseInt(spinner.getSelectedItem().toString()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            vh.actions.addView(ll);


        }



        return  convertView;
    }

    static class viewHolder
    {

        TextView stuname;
        LinearLayout actions;
    }
}
