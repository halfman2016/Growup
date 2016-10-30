package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.yper.feng.growup.Module.DayCheckListAction;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangfeng on 2016/10/25.
 */

public class DayActionStuListAdapter extends BaseAdapter {

    private List<Student> students=new ArrayList<>();
    private List<DayCheckListAction> dayCheckListActions=new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public DayActionStuListAdapter(List<Student> studentList, List<DayCheckListAction> dayCheckListActions, Context context) {
        this.students=studentList;
        this.dayCheckListActions=dayCheckListActions;
        this.context=context;
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
        Student student =students.get(position);

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
            Spinner spinner=new Spinner(context);
            TextView txtactionname=new TextView(context);
            txtactionname.setTextColor(Color.DKGRAY);
            txtactionname.setText(dayCheckListActions.get(i).getActionName());

           int value= dayCheckListActions.get(i).getDefaultvalue();
            List list = new ArrayList();
            list.add(value);
            for (int ii=0;ii<value*3+1;ii++)
            {
                list.add(2*value-ii);
            }

            ArrayAdapter adapter=new ArrayAdapter(context,R.layout.day_check_item,list);
            adapter.setDropDownViewResource(R.layout.dropdownstyle);
            spinner.setAdapter(adapter);
            ll.addView(txtactionname);
            ll.addView(spinner);
            vh.actions.addView(ll);


        }

        ViewGroup.LayoutParams lp=vh.actions.getLayoutParams();
        lp.height=100*dayCheckListActions.size();
        vh.actions.setLayoutParams(lp);

        return  convertView;
    }

    static class viewHolder
    {

        TextView stuname;
        LinearLayout actions;
    }
}
