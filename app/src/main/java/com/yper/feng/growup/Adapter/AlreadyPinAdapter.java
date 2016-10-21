package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yper.feng.growup.Module.PicPinAction;
import com.yper.feng.growup.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangfeng on 2016/10/16.
 */

public class AlreadyPinAdapter extends BaseAdapter {
    List<PicPinAction> picPinActionList=new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public AlreadyPinAdapter(List<PicPinAction> picPinActionList,Context context) {
        this.picPinActionList = picPinActionList;
        this.context=context;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return picPinActionList.size();
    }

    @Override
    public Object getItem(int position) {
        return picPinActionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PicPinAction picPinAction=picPinActionList.get(position);
        viewHolder vh;
        if (convertView==null){

            vh=new viewHolder();
            convertView=layoutInflater.inflate(R.layout.alreadypinitem,parent,false);

            vh.txtalreadynames= (TextView) convertView.findViewById(R.id.txtalreadypinname);
            vh.txtalreadypinvalue=(TextView)convertView.findViewById(R.id.txtalreadypinvalue);
            vh.txtalreadynames=(TextView)convertView.findViewById(R.id.txtalreadypinnames);
            vh.alreadypincheck=(CheckBox)convertView.findViewById(R.id.alreadypincheck);

            convertView.setTag(vh);

        }
        else
        {
            vh= (viewHolder) convertView.getTag();

        }

        vh.txtalreadynames.setText(picPinAction.getActionName());
        vh.txtalreadypinvalue.setText(String.valueOf(picPinAction.getActionValue()));
     //   vh.txtalreadynames.setText(picPinAction.getRelativeStuNames().toString());

        return convertView;

    }

    static   class  viewHolder
    {
        TextView txtalreadypinname;
        TextView txtalreadypinvalue;
        TextView txtalreadynames;
        CheckBox alreadypincheck;
    }
}
