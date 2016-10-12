package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yper.feng.growup.Activity.PinPhotoActivity;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2016/9/25.
 * 所有图片pin
 * 区别不仅仅是专题的,临时拍摄的也加入pin
 */

public class MainPinListAdapter extends BaseAdapter {
    private List<Photopic> photolist =new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public MainPinListAdapter(List<Photopic> photolist, Context context) {
        this.photolist = photolist;
        this.context = context;
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photolist.size();
    }

    @Override
    public Object getItem(int position) {
        return photolist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Photopic item=photolist.get(position);
        final viewHolder vh;
        if (convertView==null)
        {
            vh=new viewHolder();
            convertView=layoutInflater.inflate(R.layout.pin_item,parent,false);
            vh.photopic= (ImageView) convertView.findViewById(R.id.photopic);
            vh.photoauthor= (TextView) convertView.findViewById(R.id.photoauthor);
            vh.photodate= (TextView) convertView.findViewById(R.id.photodate);
            vh.photomemo= (TextView) convertView.findViewById(R.id.photomemo);
            vh.pinphoto=(ImageButton)convertView.findViewById(R.id.pinphoto);

            convertView.setTag(vh);
        }
        else
        {
            vh= (viewHolder) convertView.getTag();
        }

        vh.photopic.setImageBitmap(BitmapFactory.decodeByteArray(item.getPhotopreview(), 0, item.getPhotopreview().length));
        vh.photoauthor.setText(item.getPhotoauthor());
        vh.photomemo.setText(item.getPhotomemo());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if (item.getPhotodate()!=null )vh.photodate.setText(sdf.format(item.getPhotodate()));
        vh.pinphoto.setImageResource(R.mipmap.pin);
        vh.pinphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myapp","click on pinphoto");
                Intent intent =new Intent();
                intent.putExtra("photopid",item.get_id().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClassName(context, "com.yper.feng.growup.Activity.PinPhotoActivity");
                context.startActivity(intent);
            }
        });

        return convertView;

    }
    static class viewHolder{
        ImageView photopic;
        TextView photoauthor;
        TextView photodate;
        TextView photomemo;
        ImageButton pinphoto;


    }
}
