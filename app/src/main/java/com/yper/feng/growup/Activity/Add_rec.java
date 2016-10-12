package com.yper.feng.growup.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yper.feng.growup.Adapter.AddRecListAdapter;
import com.yper.feng.growup.Module.DayCommonAction;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.util.ArrayList;

public class Add_rec extends  Activity {
private ArrayList<DayCommonAction> days=new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rec);
        listView= (ListView) findViewById(R.id.listrec);
       // LoadDayCommonAcions ld=new LoadDayCommonAcions();
        //ld.execute();
        initData();

        listView.setAdapter(new AddRecListAdapter(days,getBaseContext()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DayCommonAction day=(DayCommonAction)listView.getAdapter().getItem(position);
                Log.d("myapp","ok" + day.getActionName());

                Intent intent=new Intent();
                intent.putExtra("actiontype",day.getActionType());
                setResult(301,intent);
                finish();

            }
        });

    }


    void initData(){

        DayCommonAction day1=new DayCommonAction("毛巾合理摆放","卫生标兵",2);
        DayCommonAction day2=new DayCommonAction("被子折叠整洁","卫生标兵",2);
        DayCommonAction day3=new DayCommonAction("被子没有折叠整洁","卫生标兵",-2);
        DayCommonAction day4=new DayCommonAction("口杯摆放合适","卫生标兵",2);
        DayCommonAction day5=new DayCommonAction("毛巾乱放","卫生标兵",-2);
        DayCommonAction day6=new DayCommonAction("口杯乱放","卫生标兵",2);

        days.add(day1);
        days.add(day2);
        days.add(day3);days.add(day4);days.add(day5);days.add(day6);
    }

    class LoadDayCommonAcions extends AsyncTask<String,Integer,ArrayList<DayCommonAction>>{

    @Override
    protected ArrayList<DayCommonAction> doInBackground(String... params) {
        ArrayList<DayCommonAction> dayCommonActions;
        MDBTools mdbTools=new MDBTools();
        return  mdbTools.getTypedDayActions("");

    }

    @Override
    protected void onPostExecute(ArrayList<DayCommonAction> dayCommonActions) {
       super.onPostExecute(dayCommonActions);
       days=dayCommonActions;
       listView.setAdapter(new AddRecListAdapter(days,getBaseContext()));
    }
}

}