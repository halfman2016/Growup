package com.yper.feng.growup.Fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yper.feng.growup.Activity.MainActivity;
import com.yper.feng.growup.R;

/**
 * Created by Feng on 2016/7/10.
 */
public class MainInfoFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        TextView mainwho=(TextView)view.findViewById(R.id.mainWho);
        mainwho.setText(((MainActivity)getActivity()).teacher.getName());
        return view;
    }
}
