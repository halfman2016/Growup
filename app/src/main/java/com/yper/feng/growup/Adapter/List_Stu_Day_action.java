package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.yper.feng.growup.Module.DayCheckListAction;
import com.yper.feng.growup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangfeng on 2016/10/21.
 */

public class List_Stu_Day_action extends BaseAdapter {
    List<DayCheckListAction> dayCheckListActions=new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public List_Stu_Day_action(List<DayCheckListAction> dayCheckListActions, Context context) {
        this.dayCheckListActions = dayCheckListActions;
        this.context = context;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return dayCheckListActions.size();
    }

    @Override
    public Object getItem(int position) {
        return dayCheckListActions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DayCheckListAction dayCheckListAction=dayCheckListActions.get(position);
        final viewHolder vh;
        if (convertView==null)
        {
            vh=new viewHolder();
            convertView=layoutInflater.inflate(R.layout.day_check_item,parent,false);
            vh.actionname= (TextView) convertView.findViewById(R.id.actionname);
            vh.spinner=(Spinner)convertView.findViewById(R.id.spinner);
            convertView.setTag(vh);

        }

        else

        {
            vh= (viewHolder) convertView.getTag();
        }

        vh.actionname.setText(dayCheckListAction.getActionName());

        return convertView;
    }

    static class viewHolder{
        TextView actionname;
        Spinner spinner;
    }
}
