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
        LoadDayCommonAcions ld=new LoadDayCommonAcions();
        ld.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("myapp","ok" + String.valueOf(position));
            }
        });

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