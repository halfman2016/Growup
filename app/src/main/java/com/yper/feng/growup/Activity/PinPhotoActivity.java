package com.yper.feng.growup.Activity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yper.feng.growup.Adapter.AlreadyPinAdapter;
import com.yper.feng.growup.Adapter.PinPhotoActionAdapter;
import com.yper.feng.growup.Adapter.StudentsNameAdapter;
import com.yper.feng.growup.Module.GradeClass;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.Module.PicPinAction;
import com.yper.feng.growup.Module.PinAction;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.MyApplication;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.BitmapCache;
import com.yper.feng.growup.Util.MDBTools;
import com.yper.feng.growup.Util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PinPhotoActivity extends ListActivity {
    private MDBTools mdb=new MDBTools();

    private List<PinAction> pinActions=new ArrayList<>();

    public List<PicPinAction> mypinpcs=new ArrayList<>();


    public List<Student> instudents=new ArrayList<>();

    private ArrayList<GradeClass> gradeClassList =new ArrayList<>();

    private String subjectname;
    private String subjectid;

    private Teacher teacher;
    private Photopic photopic;
    private Subject subject;
    public GridView gridView;
    private ListView listalreadypin;
    private ImageLoader imageLoader;
    private RequestQueue queue;
    private NetworkImageView imageView;
    private ArrayList<HashMap<String,Object>> lstNameItem=new ArrayList<HashMap<String,Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_photo);
        queue= Volley.newRequestQueue(getBaseContext());
        imageLoader=new ImageLoader(queue,new BitmapCache());
        Intent intent=getIntent();
        Gson gson= new GsonBuilder().create();
        teacher=gson.fromJson(intent.getStringExtra("teacherjson"),Teacher.class);
        photopic=gson.fromJson(intent.getStringExtra("photopicjson"),Photopic.class);

        if (intent.getStringExtra("subjectjson")==null)
        {
            //公共pin
            subject=null;
        }
        else
        {
            //subject pin
            subject=gson.fromJson(intent.getStringExtra("subjectjson"),Subject.class);


        }


        imageView= (NetworkImageView) findViewById(R.id.photopic);
        imageView.requestFocus();
        gridView=(GridView)findViewById(R.id.mygridview);
        listalreadypin=(ListView) findViewById(R.id.list_alreadypins);

        loaddata();





    }
    private Handler myhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        switch (msg.what)
        {
            case 1:
                initView();

                imageView.setImageUrl(MyApplication.getInstance().Url+photopic.getPicname(),imageLoader);
                break;
        }
        }
    };

    private void loaddata(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                gradeClassList=mdb.getGradeClasses();
                pinActions=mdb.getPinActions();
                mypinpcs=mdb.getPicpinactions(photopic);
                Message msg=new Message();
                msg.what=1;
                myhandler.sendMessage(msg);
            }
        }.start();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }
    private  void initView(){

        final LinearLayout linearLayout= (LinearLayout) findViewById(R.id.classlist);

        linearLayout.removeAllViews();

        //显示姓名

        for(int i=0;i<gradeClassList.size();i++){
            TextView txt = new TextView(getBaseContext());
            txt.setText(gradeClassList.get(i).getName());
            txt.setBackgroundColor(Color.WHITE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            txt.setLayoutParams(lp);
            txt.setTextColor(this.getResources().getColor(R.color.green));
            txt.setTextSize(15);

            linearLayout.addView(txt);

            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tmp=(TextView) v;

                    LinearLayout linearLayout1= (LinearLayout) findViewById(R.id.classlist);

                    for(int j=0;j<linearLayout1.getChildCount();j++)
                   {
                       linearLayout1.getChildAt(j).setBackgroundColor(Color.WHITE);
                   }

                    for(int i=0;i<gradeClassList.size();i++)
                    {
                        if (gradeClassList.get(i).getName()==tmp.getText())
                        {

                            gridView.setAdapter(new StudentsNameAdapter(gradeClassList.get(i).getStus(),instudents,getBaseContext()));

                            tmp.setBackgroundColor(Color.YELLOW);
                            Utils.setGridViewHeightBasedOnChildren(3,gridView);

                        }
                    }

                }


            });

        }

        gridView.setAdapter(new StudentsNameAdapter(instudents,instudents,getBaseContext()));
        Utils.setGridViewHeightBasedOnChildren(3,gridView);

        //显示项目



        setListAdapter(new PinPhotoActionAdapter(pinActions,mypinpcs,photopic,this));
        Utils.setListViewHeightBasedOnChildren(getListView());

        listalreadypin.setAdapter(new AlreadyPinAdapter(mypinpcs,this));
        Utils.setListViewHeightBasedOnChildren(listalreadypin);
    }
    void writedata(){

        final PinAction pinAction=new PinAction("test","日常规",1,3,-1);
        new Thread(){
            @Override
            public void run() {
                super.run();

            mdb.addPinAction(pinAction);
            }
        }.start();

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        new Thread(){
            @Override
            public void run() {
                super.run();

                ListView listView=(ListView) findViewById(R.id.list_alreadypins);

                List list=((AlreadyPinAdapter)listView.getAdapter()).list;
                List<PicPinAction> picPinActions=((AlreadyPinAdapter)listView.getAdapter()).picPinActionList;
                List<PicPinAction> pics=new ArrayList<PicPinAction>();
                for(int i=0;i<list.size();i++)
                {
                    int j=Integer.parseInt(list.get(i).toString());
                    if(j==1) pics.add(picPinActions.get(i));

                }



                mdb.savePicPinAcions(pics,photopic);

            }


        }.start();
    }
}
