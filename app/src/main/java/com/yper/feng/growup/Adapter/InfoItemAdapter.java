package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yper.feng.growup.Module.BaseInfoItem;
import com.yper.feng.growup.Module.DayCommonAction;
import com.yper.feng.growup.Module.SubjectPinAction;
import com.yper.feng.growup.Module.WeekReport;
import com.yper.feng.growup.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Feng on 2016/7/10.
 */
public class InfoItemAdapter extends BaseAdapter {


    private List<BaseInfoItem> items = new ArrayList<>();
    private LayoutInflater layoutInflater = null;
    private Context context;

    public InfoItemAdapter(List<BaseInfoItem> items, Context context) {
        this.items = items;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BaseInfoItem item = items.get(position);

        Class c = item.getActionObject().getClass();

        String className = c.getName();



        if (convertView == null) {

            //按action对象类别获得布局
            switch (className) {

                case "com.yper.feng.growup.Module.DayCommonAction":
                    convertView = layoutInflater.inflate(R.layout.info_item_daycommon, parent, false);
                    break;
                case "com.yper.feng.growup.Module.SubjectPinAction":
                    convertView = layoutInflater.inflate(R.layout.info_item_subject, parent, false);
                    break;
                case "com.yper.feng.growup.Module.WeekReport":

                    convertView = layoutInflater.inflate(R.layout.info_item_weekreport, parent, false);
                    break;
            }


        }

        //按action对象类别获得数据

        switch (className) {
            case "com.yper.feng.growup.Module.DayCommonAction":

                com.yper.feng.growup.Module.DayCommonAction temp = (DayCommonAction) item.getActionObject();
                TextView itemtitle = (TextView) convertView.findViewById(R.id.itemtitle);
                itemtitle.setText(temp.getActionName());
                TextView itemtype = (TextView) convertView.findViewById(R.id.item_type);
                itemtype.setText(item.getInfotype());


                break;
            case "com.yper.feng.growup.Module.SubjectPinAction":
                SubjectPinAction stemp = (SubjectPinAction) item.getActionObject();
                TextView stitile = (TextView) convertView.findViewById(R.id.itemtitle);
                stitile.setText(stemp.getActionName());
                TextView stype = (TextView) convertView.findViewById(R.id.item_type);
                stype.setText(item.getInfotype());
                ImageView simage = (ImageView) convertView.findViewById(R.id.relateiveImage);
                Resources res = context.getResources();

                simage.setImageResource(res.getIdentifier("addrec", "mipmap", context.getPackageName()));

                break;
            case "com.yper.feng.growup.Module.WeekReport":
                WeekReport wtemp = (WeekReport) item.getActionObject();
                TextView wtitle = (TextView) convertView.findViewById(R.id.itemtitle);
                wtitle.setText(item.getInfoTitle());
                TextView wtype = (TextView) convertView.findViewById(R.id.item_type);
                wtype.setText(item.getInfotype());
                Map<Integer, String> rank = wtemp.getRank();

                LinearLayout ly = (LinearLayout) convertView.findViewById(R.id.ranklist);
                for (Integer key : rank.keySet()) {

                    TextView name = new TextView(context);
                    name.setText(String.valueOf(key) + ":" + rank.get(key));
                    name.setTextColor(Color.BLUE);
                    ly.addView(name);
                }

                break;
        }


        //TextView actionValue = (TextView) convertView.findViewById(R.id.actionValue);
        //actionValue.setText(String.valueOf(sa.getActionValue()));

//        SimpleDateFormat fmt = new SimpleDateFormat("m-d HH:mm:ss");
//
//
//        TextView actionOccurTime = (TextView) convertView.findViewById(R.id.actionOccurTime);
//        actionOccurTime.setText(fmt.format(sa.getActionOccurTime()));
//        TextView actionPinTime = (TextView) convertView.findViewById(R.id.actionPinTime);
//        actionPinTime.setText(fmt.format(sa.getActionPinTime()));
        return convertView;
    }


}
