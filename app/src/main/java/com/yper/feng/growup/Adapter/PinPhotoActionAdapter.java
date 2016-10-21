package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yper.feng.growup.Activity.PinPhotoActivity;
import com.yper.feng.growup.Module.PicPinAction;
import com.yper.feng.growup.Module.PinAction;
import com.yper.feng.growup.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangfeng on 2016/10/15.
 */

public class PinPhotoActionAdapter extends BaseAdapter {
    List<PinAction> pinActions=new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public PinPhotoActionAdapter(List<PinAction> pinActions, Context context) {
super();
        this.pinActions = pinActions;
        this.context = context;
        this.layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return pinActions.size();
    }

    @Override
    public Object getItem(int position) {
        return pinActions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final PinAction item=pinActions.get(position);
       final viewHolder vh;
if (convertView==null){
    vh=new viewHolder();
    convertView=layoutInflater.inflate(R.layout.subject_list_pin_pic_item,parent,false);
    vh.txtPinname=(TextView)convertView.findViewById(R.id.txtPinName);
    vh.txtpinActionScore=(TextView)convertView.findViewById(R.id.txtPinActionScore);
    vh.txtpinactiontype=(TextView) convertView.findViewById(R.id.txtPinActionType);
    vh.btnpinaction=(Button)convertView.findViewById(R.id.btnpinAction);
    convertView.setTag(vh);
}
        else
{
    vh=(viewHolder)convertView.getTag();

}

        vh.txtPinname.setText(item.getActionName());
        vh.txtpinactiontype.setText(item.getActionType());
        vh.txtpinActionScore.setText(String.valueOf(item.getActionValue()));
        vh.btnpinaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PinPhotoActivity pinPhotoActivity=(PinPhotoActivity) context;
                ListView listView=(ListView) pinPhotoActivity.findViewById(R.id.list_alreadypins);

                List<PicPinAction> list=new ArrayList<PicPinAction>();
                PicPinAction p1=new PicPinAction("测试","eshi",2);
                PicPinAction p2=new PicPinAction("测试","eshi",2);
                PicPinAction p3=new PicPinAction("测试","eshi",2);
                list.add(p1);
                list.add(p2);
                list.add(p3);
                listView.setAdapter(new AlreadyPinAdapter(list,context));
            }
        });


        return convertView;
    }


    static class viewHolder{
        TextView txtPinname;
        TextView txtpinActionScore;
        TextView txtpinactiontype;
        Button btnpinaction;

    }



}
