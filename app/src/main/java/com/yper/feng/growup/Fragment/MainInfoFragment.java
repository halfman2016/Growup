package com.yper.feng.growup.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yper.feng.growup.Activity.MainActivity;
import com.yper.feng.growup.Adapter.MainInfoListAdapter;
import com.yper.feng.growup.Adapter.MainSubjectListAdapter;
import com.yper.feng.growup.Module.BaseInfoItem;
import com.yper.feng.growup.Module.DayCheckRec;
import com.yper.feng.growup.Module.DayCommonAction;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Feng on 2016/7/10.
 */
public class MainInfoFragment extends ListFragment {
    Teacher teacher;

    List<BaseInfoItem> items=new ArrayList<>();
    List<DayCheckRec> dayCheckRecs=new ArrayList<>();
    List<Photopic> photopics =new ArrayList<>();
    BaseInfoItem infoItem;

    MDBTools mdbTools=new MDBTools();

    public Handler mhandler=new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what)
            {
                case 1:
                    if(isVisible()) {

                        items.clear();

                        for (DayCheckRec day : dayCheckRecs) {

                            infoItem = new BaseInfoItem(day);
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                infoItem.setInfoTime(sdf.parse(day.getStrdate()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            items.add(infoItem);

                        }

                        for (Photopic pic : photopics)
                        {
                            infoItem=new BaseInfoItem(pic);
                            infoItem.setInfoTime(pic.getPhotodate());
                            items.add(infoItem);
                        }

                        Collections.sort(items, new Comparator<BaseInfoItem>() {
                            @Override
                            public int compare(BaseInfoItem o1, BaseInfoItem o2) {
                                Date dt1 = o1.getInfoTime();
                                Date dt2 = o2.getInfoTime();

                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                                try {
                                    if (dt1.getTime() > dt2.getTime()) {
                                        return 1;
                                    } else if (dt1.getTime() < dt2.getTime()) {
                                        return -1;
                                    } else {
                                        return 0;
                                    }
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            return  0;
                            }
                        });
                        setListAdapter(new MainInfoListAdapter(items, getContext()));
                    }
                    break;

            }
            super.handleMessage(msg);

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        TextView mainwho=(TextView)view.findViewById(R.id.mainWho);
        mainwho.setText(((MainActivity)getActivity()).teacher.getName());

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teacher=((MainActivity)getActivity()).teacher;
        loaddata();


    }
    void loaddata(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                photopics=mdbTools.getToppics();
//                dayCheckRecs=mdbTools.getTopDaychecks();
                Message msg=new Message();
                msg.what=1;
                mhandler.sendMessage(msg);

            }
        }.start();

    }
}
