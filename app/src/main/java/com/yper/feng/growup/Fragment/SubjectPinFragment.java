package com.yper.feng.growup.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yper.feng.growup.R;

/**
 * Created by Feng on 2016/9/26.
 */

public class SubjectPinFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_subject_pin,container,false);
        return  view;
    }

}
