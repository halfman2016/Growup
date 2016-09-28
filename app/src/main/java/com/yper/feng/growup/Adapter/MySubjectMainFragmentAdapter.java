package com.yper.feng.growup.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Feng on 2016/9/25.
 */

public class MySubjectMainFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public MySubjectMainFragmentAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list=list;
        }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
