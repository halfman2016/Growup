package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.yper.feng.growup.Module.DayCommonAction;

import java.util.ArrayList;

/**
 * Created by Feng on 2016/8/25.
 */
public class AddRecListAdapter extends BaseAdapter {
    ArrayList<DayCommonAction> dayCommonActions =new ArrayList<>();
    Context context;

    public AddRecListAdapter(ArrayList<DayCommonAction> dayCommonActions,Context context){
        this.dayCommonActions=dayCommonActions;
        this.context=context;
    };

    @Override

    public int getCount() {
        return dayCommonActions.size();
    }

    @Override
    public Object getItem(int position) {


        return dayCommonActions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EditText editText=new EditText(context);
        editText.setBackgroundColor(0xffffff);

        DayCommonAction dayCommonAction=dayCommonActions.get(position);
        editText.setText(dayCommonAction.getActionName()+dayCommonAction.getActionType());
        return editText;
    }





}
