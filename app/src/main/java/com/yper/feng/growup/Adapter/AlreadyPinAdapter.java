package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yper.feng.growup.Module.PicPinAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangfeng on 2016/10/5.
 */

public class AlreadyPinAdapter extends BaseAdapter {

    private List<PicPinAction> picPinActions=new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater=null;



    @Override
    public int getCount() {
        return picPinActions.size();
    }

    @Override
    public Object getItem(int position) {
        return picPinActions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        return null;
    }
}
