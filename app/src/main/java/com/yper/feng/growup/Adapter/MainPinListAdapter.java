package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.MyApplication;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.BitmapCache;

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
    private RequestQueue queue;
    private ImageLoader imageLoader;

    public MainPinListAdapter(List<Photopic> photolist, Context context) {
        super();
        this.photolist = photolist;
        this.context = context;
        queue= Volley.newRequestQueue(this.context);
        imageLoader=new ImageLoader(queue,new BitmapCache());
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
            vh.photopic= (NetworkImageView) convertView.findViewById(R.id.photopic);
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


        final String imgurl= MyApplication.getInstance().Url+item.getPicname();
        if (item.getPicname()!=null && !item.getPicname().equals("")){
            vh.photopic.setDefaultImageResId(R.mipmap.no_pic);
            vh.photopic.setErrorImageResId(R.mipmap.no_pic);
            vh.photopic.setImageUrl(imgurl,imageLoader);
        }

        vh.photoauthor.setText(item.getPhotoauthor());
        vh.photomemo.setText(item.getPhotomemo());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (item.getPhotodate()!=null )vh.photodate.setText(sdf.format(item.getPhotodate()));
        vh.pinphoto.setImageResource(R.mipmap.pin);

        return convertView;

    }
    static class viewHolder{
        NetworkImageView photopic;
        TextView photoauthor;
        TextView photodate;
        TextView photomemo;
        ImageButton pinphoto;


    }
}
