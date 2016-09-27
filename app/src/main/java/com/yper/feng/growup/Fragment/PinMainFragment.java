package com.yper.feng.growup.Fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yper.feng.growup.R;

/**
 * Created by Feng on 2016/7/10.
 */
public class PinMainFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pin, container, false);
        return view;
    }
}
