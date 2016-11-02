package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yper.feng.growup.Module.PicPinAction;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangfeng on 2016/10/16.
 */

public class AlreadyPinAdapter extends BaseAdapter {
    public List<PicPinAction> picPinActionList=new ArrayList<>();
    public List list=new ArrayList();
    private LayoutInflater layoutInflater;
    private Context context;

    public AlreadyPinAdapter(List<PicPinAction> picPinActionList,Context context) {
        this.picPinActionList = picPinActionList;
        this.context=context;
        for (int i=0;i<picPinActionList.size();i++)
        {
            list.add(1);
        }

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        PicPinAction picPinAction=picPinActionList.get(position);
        viewHolder vh;
        if (convertView==null){

            vh=new viewHolder();
            convertView=layoutInflater.inflate(R.layout.alreadypinitem,parent,false);

            vh.txtalreadypinname= (TextView) convertView.findViewById(R.id.txtalreadypinname);
            vh.txtalreadypinvalue=(TextView)convertView.findViewById(R.id.txtalreadypinvalue);
            vh.txtalreadynames=(TextView)convertView.findViewById(R.id.txtalreadypinnames);
            vh.alreadypincheck=(CheckBox)convertView.findViewById(R.id.alreadypincheck);

            convertView.setTag(vh);

        }
        else
        {
            vh= (viewHolder) convertView.getTag();

        }

        vh.txtalreadypinvalue.setText(String.valueOf(picPinAction.getActionValue()));
        vh.txtalreadypinname.setText(picPinAction.getActionName());

        vh.alreadypincheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CheckBox box=(CheckBox) buttonView;
                if(isChecked==false)
                {
                    list.set(position,0);
                }
                else
                {
                    list.set(position,1);
                }
            }
        });

        String names="";
        List<Student> students= picPinAction.getRelativeStus();
        for (Student stu:students){
            names=names+stu.getName()+" ";
        }

         vh.txtalreadynames.setText(names);

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
