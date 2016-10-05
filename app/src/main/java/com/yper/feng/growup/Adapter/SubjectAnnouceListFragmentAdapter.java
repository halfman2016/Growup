package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yper.feng.growup.Module.Annouce;
import com.yper.feng.growup.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangfeng on 2016/10/5.
 */

public class SubjectAnnouceListFragmentAdapter extends BaseAdapter {

    private List<Annouce> annouceList=new ArrayList<>();
    private LayoutInflater layoutInflater = null;
    private Context context;

    public SubjectAnnouceListFragmentAdapter(Context context, List<Annouce> annouceList) {
        this.context = context;
        this.annouceList = annouceList;
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return annouceList.size();
    }

    @Override
    public Object getItem(int position) {
        return annouceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Annouce item=annouceList.get(position);
        final viewHolder vh;
        if (convertView==null)
        {
            vh=new viewHolder();
            convertView=layoutInflater.inflate(R.layout.subject_list_annouce_item,parent,false);
            vh.annouceauthor= (TextView) convertView.findViewById(R.id.txtAnnouceauthor);
            vh.annoucetime= (TextView) convertView.findViewById(R.id.txtAnnoucetime);
            vh.annoucebody= (TextView) convertView.findViewById(R.id.txtAnnoucebody);


            convertView.setTag(vh);
        }
        else
        {
            vh= (viewHolder) convertView.getTag();
        }

        vh.annouceauthor.setText(item.getAnnouceauthor());
        vh.annoucebody.setText(item.getAnnouceBody());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (item.getAnnouceTime()!=null ) vh.annoucetime.setText(sdf.format(item.getAnnouceTime()));

        return convertView;


    }

    static class viewHolder{
        TextView annouceauthor;
        TextView annoucetime;
        TextView annoucebody;

    }
}
