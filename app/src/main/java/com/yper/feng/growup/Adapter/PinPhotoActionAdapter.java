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
import android.widget.Spinner;
import android.widget.TextView;

import com.yper.feng.growup.Activity.PinPhotoActivity;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.Module.PicPinAction;
import com.yper.feng.growup.Module.PinAction;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangfeng on 2016/10/15.
 */

public class PinPhotoActionAdapter extends BaseAdapter {
    List<PinAction> pinActions=new ArrayList<>();
    Photopic photopic;
    Context context;
    LayoutInflater layoutInflater;

    public PinPhotoActionAdapter(List<PinAction> pinActions, Photopic photopic,Context context) {
super();
        this.pinActions = pinActions;
        this.photopic=photopic;
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
    vh.txtpinActionScore=(Spinner) convertView.findViewById(R.id.actionvalue);
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
        final List list=new ArrayList();
        list.add(item.getGoodvalue());
        list.add(item.getActionValue());
        list.add(item.getBadvalue());

        ArrayAdapter adapter=new ArrayAdapter(context,R.layout.day_check_item,list);
        adapter.setDropDownViewResource(R.layout.dropdownstyle);
        vh.txtpinActionScore.setAdapter(adapter);
        vh.txtpinActionScore.setSelection(1);
        vh.btnpinaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PinPhotoActivity pinPhotoActivity=(PinPhotoActivity) context;
                List<Student> students=((StudentsNameAdapter)pinPhotoActivity.gridView.getAdapter()).getStudents();

                if(students.size()==0) return;

                ListView listView=(ListView) pinPhotoActivity.findViewById(R.id.list_alreadypins);

                List list=((AlreadyPinAdapter)listView.getAdapter()).list;
                List<PicPinAction> picPinActions=((AlreadyPinAdapter)listView.getAdapter()).picPinActionList;
                List<PicPinAction> pics=new ArrayList<PicPinAction>();
                for(int i=0;i<list.size();i++)
                {
                    int j=Integer.parseInt(list.get(i).toString());
                    if(j==1) pics.add(picPinActions.get(i));

                }

                PicPinAction p1=new PicPinAction(item.getActionName(),item.getActionType(),Integer.parseInt(vh.txtpinActionScore.getSelectedItem().toString()));
                p1.setRelativeStus(students);
                p1.setPicsrcid(photopic.get_id());
                pics.add(p1);
                listView.setAdapter(new AlreadyPinAdapter(pics,context));
                Utils.setListViewHeightBasedOnChildren(listView);
            }
        });


        return convertView;
    }


    static class viewHolder{
        TextView txtPinname;
        Spinner txtpinActionScore;
        TextView txtpinactiontype;
        Button btnpinaction;

    }



}
