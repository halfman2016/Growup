package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.yper.feng.growup.Module.BaseInfoItem;
import com.yper.feng.growup.Module.DayCheckRec;
import com.yper.feng.growup.Module.DayCommonAction;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.Module.SubjectPinAction;
import com.yper.feng.growup.Module.WeekReport;
import com.yper.feng.growup.MyApplication;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.BitmapCache;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Feng on 2016/7/10.
 */
public class MainInfoListAdapter extends BaseAdapter {


    private List<BaseInfoItem> items = new ArrayList<>();
    private LayoutInflater layoutInflater ;
    private Context context;
    private RequestQueue queue;
    private ImageLoader imageLoader;

    public MainInfoListAdapter(List<BaseInfoItem> items, Context context) {
        this.items = items;
        this.context = context;
        queue= Volley.newRequestQueue(context);
        imageLoader=new ImageLoader(queue,new BitmapCache());
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



    //按action对象类别获得布局
//         switch (className) {
//
//        case "com.yper.feng.growup.Module.DayCheckRec":
//            convertView = layoutInflater.inflate(R.layout.info_item_daycommon, parent, false);
//            break;
//        case "com.yper.feng.growup.Module.Photopic":
//            convertView = layoutInflater.inflate(R.layout.info_item_subject, parent, false);
//            break;
//        case "com.yper.feng.growup.Module.WeekReport":
//
//            convertView = layoutInflater.inflate(R.layout.info_item_weekreport, parent, false);
//            break;
//    }
//


        final viewHolder vh;
        if (convertView==null)
        {
            vh=new viewHolder();
            convertView=layoutInflater.inflate(R.layout.info_item_subject,parent,false);
          //  vh.photopic= (NetworkImageView) convertView.findViewById(R.id.relateiveImage);
            vh.photoauthor= (TextView) convertView.findViewById(R.id.itemtitle);
            vh.photodate= (TextView) convertView.findViewById(R.id.item_type);
            vh.photomemo= (TextView) convertView.findViewById(R.id.imagememo);


            convertView.setTag(vh);
        }
        else
        {
            vh= (viewHolder) convertView.getTag();
        }


        //按action对象类别获得数据
//
//        switch (className) {
//            case "com.yper.feng.growup.Module.DayCheckRec":
//
//                com.yper.feng.growup.Module.DayCheckRec temp = (DayCheckRec) item.getActionObject();
//                TextView itemtitle = (TextView) convertView.findViewById(R.id.itemtitle);
//                itemtitle.setText(temp.getCheckedteachername());
//                TextView itemtype = (TextView) convertView.findViewById(R.id.item_type);
//                itemtype.setText(temp.getTypename());
//                TextView checkdate=(TextView) convertView.findViewById(R.id.checkdate);
//                TextView txtClass=(TextView) convertView.findViewById(R.id.txtclass);
//                checkdate.setText(temp.getStrdate());
//                txtClass.setText(temp.getGradeClass().getName());
//
//
//                break;
//            case "com.yper.feng.growup.Module.Photopic":
//                Photopic stemp = (Photopic) item.getActionObject();
//
//                String url= MyApplication.getInstance().Url+stemp.getPicname();
//
//                TextView stitile = (TextView) convertView.findViewById(R.id.itemtitle);
//                stitile.setText(stemp.getPhotoauthor());
//                TextView stype = (TextView) convertView.findViewById(R.id.item_type);
//                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                stype.setText(sdf.format(stemp.getPhotodate()));
//                NetworkImageView simage = (NetworkImageView) convertView.findViewById(R.id.relateiveImage);
//                simage.setImageUrl(url,imageLoader);
//                TextView imagememo=(TextView) convertView.findViewById(R.id.imagememo);
//                imagememo.setText(stemp.getPhotomemo());
//
//
//                break;
//            case "com.yper.feng.growup.Module.WeekReport":
//                WeekReport wtemp = (WeekReport) item.getActionObject();
//                TextView wtitle = (TextView) convertView.findViewById(R.id.itemtitle);
//                wtitle.setText(item.getInfoTitle());
//                TextView wtype = (TextView) convertView.findViewById(R.id.item_type);
//                wtype.setText(item.getInfotype());
//                Map<Integer, String> rank = wtemp.getRank();
//
//                LinearLayout ly = (LinearLayout) convertView.findViewById(R.id.ranklist);
//                for (Integer key : rank.keySet()) {
//
//                    TextView name = new TextView(context);
//                    name.setText(String.valueOf(key) + ":" + rank.get(key));
//                    name.setTextColor(Color.BLUE);
//                    ly.addView(name);
//                }
//
//                break;
//        }

        Photopic sitem = (Photopic) item.getActionObject();

//        final String imgurl= MyApplication.getInstance().Url+sitem.getPicname();
//        if (sitem.getPicname()!=null && !sitem.getPicname().equals("")){
//            vh.photopic.setDefaultImageResId(R.mipmap.no_pic);
//            vh.photopic.setErrorImageResId(R.mipmap.no_pic);
//            vh.photopic.setImageUrl(imgurl,imageLoader);
//        }

        vh.photoauthor.setText(sitem.getPhotoauthor());
        vh.photomemo.setText(sitem.getPhotomemo());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (sitem.getPhotodate()!=null )vh.photodate.setText(sdf.format(sitem.getPhotodate()));


        return convertView;
    }



    static class viewHolder{
       // NetworkImageView photopic;
        TextView photoauthor;
        TextView photodate;
        TextView photomemo;


    }
}
