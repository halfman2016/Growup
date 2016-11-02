package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.MyApplication;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.BitmapCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangfeng on 2016/11/2.
 */

public class SubjectPinFragmentAdapter extends BaseAdapter {
    List<Photopic> photopics=new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    private RequestQueue queue;
    private ImageLoader imageLoader;

    public SubjectPinFragmentAdapter(List<Photopic> photopics, Context context) {
        this.photopics=photopics;
        this.context=context;
        queue= Volley.newRequestQueue(context);
        imageLoader=new ImageLoader(queue,new BitmapCache());
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return photopics.size();
    }

    @Override
    public Object getItem(int position) {
        return photopics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Photopic item=photopics.get(position);
        final viewHolder vh;

        if (convertView==null)
        {
            vh=new viewHolder();
            convertView=layoutInflater.inflate(R.layout.subject_pin_list_item,parent,false);
            vh.imageView=(NetworkImageView) convertView.findViewById(R.id.subject_pin_photo);
            convertView.setTag(vh);
        }
        else
        {
            vh=(viewHolder)convertView.getTag();
        }

        String url= MyApplication.getInstance().Url+item.getPicname();
        vh.imageView.setImageUrl(url,imageLoader);



   return convertView;
    }

    class viewHolder {

        NetworkImageView imageView;

    }


}
