package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.yper.feng.growup.Module.DayChecksAcion;
import com.yper.feng.growup.Module.DayCommonAction;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2016/8/25.
 */
public class AddRecListAdapter extends BaseAdapter {
    List<DayChecksAcion> dayChecksAcions =new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;


    public AddRecListAdapter(List<DayChecksAcion> dayChecksAcions, Context context){
        this.dayChecksAcions=dayChecksAcions;
        this.context=context;
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    };

    @Override

    public int getCount() {
        return dayChecksAcions.size();
    }

    @Override
    public Object getItem(int position) {


        return dayChecksAcions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final DayChecksAcion item=dayChecksAcions.get(position);
        final viewHolder vh;
        if (convertView==null)
        {
            vh=new viewHolder();
            convertView=layoutInflater.inflate(R.layout.addrec_item,parent,false);
            vh.txtRecName= (TextView) convertView.findViewById(R.id.txtRecName);
            convertView.setTag(vh);

        }

        else

        {
            vh= (viewHolder) convertView.getTag();
        }

        vh.txtRecName.setText(item.getActionType());

        return convertView;
    }

    static class viewHolder{
        TextView txtRecName;
    }



}
