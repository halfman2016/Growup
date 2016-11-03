package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yper.feng.growup.Module.DayCheckRec;
import com.yper.feng.growup.Module.DayCommonAction;
import com.yper.feng.growup.R;

/**
 * Created by jiangfeng on 2016/11/3.
 */

public class DayActionAlreadyhave extends BaseAdapter {
   private DayCheckRec dayCheckRec;
    private Context context;
    private LayoutInflater layoutInflater;
    public DayActionAlreadyhave(DayCheckRec dayCheckRec, Context context) {
        this.dayCheckRec = dayCheckRec;
        this.context=context;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return dayCheckRec.getDayCommonActions().size();
    }

    @Override
    public Object getItem(int position) {
        return dayCheckRec.getDayCommonActions().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DayCommonAction dayCommonAction=dayCheckRec.getDayCommonActions().get(position);
        convertView=layoutInflater.inflate(R.layout.daycheckalreadyhave,parent,false);

        TextView stuname= (TextView) convertView.findViewById(R.id.stuname);
        TextView actionname=(TextView) convertView.findViewById(R.id.actionname);
        TextView actionvalue=(TextView) convertView.findViewById(R.id.actionvalue);

        stuname.setText(dayCommonAction.getRelativeStus().get(0).getName());
        actionname.setText(dayCommonAction.getActionName());
        actionvalue.setText(String.valueOf(dayCommonAction.getActionValue()));

        return convertView;
    }
}
