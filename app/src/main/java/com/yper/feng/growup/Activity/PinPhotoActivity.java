package com.yper.feng.growup.Activity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yper.feng.growup.Adapter.PinPhotoActionAdapter;
import com.yper.feng.growup.Adapter.StudentsNameAdapter;
import com.yper.feng.growup.Module.BaseAction;
import com.yper.feng.growup.Module.DayCommonAction;
import com.yper.feng.growup.Module.GradeClass;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.Module.PicPinAction;
import com.yper.feng.growup.Module.PinAction;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PinPhotoActivity extends ListActivity {
    private MDBTools mdb=new MDBTools();

    private List<PinAction> pinActions=new ArrayList<>();

    private List<PicPinAction> mypinpcs=new ArrayList<>();


    private List<Student> instudents=new ArrayList<>();

    private List<GradeClass> gradeClassList =new ArrayList<>();

    private String subjectname;
    private String subjectid;

    private Teacher teacher;
    private Photopic photopic;
    private GridView gridView;
    private ImageView imageView;
    private ArrayList<HashMap<String,Object>> lstNameItem=new ArrayList<HashMap<String,Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_photo);
        Intent intent=getIntent();
        Gson gson= new GsonBuilder().create();
        teacher=gson.fromJson(intent.getStringExtra("teacherjson"),Teacher.class);
        photopic=gson.fromJson(intent.getStringExtra("photopicjson"),Photopic.class);

        if (intent.getStringExtra("subjectjson")==null)
        {
            //公共pin
        }
        else
        {
            //subject pin

        }

        imageView= (ImageView) findViewById(R.id.photopic);
        gridView=(GridView)findViewById(R.id.mygridview);
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
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(photopic.getPhotopreview(),0,photopic.getPhotopreview().length));
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
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            txt.setLayoutParams(lp);
            txt.setTextColor(this.getResources().getColor(R.color.green));
            linearLayout.addView(txt);
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tmp=(TextView) v;
                    for(int i=0;i<gradeClassList.size();i++)
                    {
                        if (gradeClassList.get(i).getName()==tmp.getText())
                        {
                            gridView.setAdapter(new StudentsNameAdapter(gradeClassList.get(i).getStus(),instudents,getBaseContext()));

                        }
                    }
                }
            });

        }

        gridView.setAdapter(new StudentsNameAdapter(instudents,instudents,getBaseContext()));
        //显示项目

        setListAdapter(new PinPhotoActionAdapter(pinActions,this));

    }
//    private  void initdata(){
//        //班级清单
//       // defaultValues= (HashMap<String, Integer>) mdb.getDefaultValues();
//
//        //学生花名册
//
//        //allstudents=mdb.getStus();
//        final Student stu1=new Student("胡涛");
//        Student stu2=new Student("江先明");
//        Student stu3=new Student("陈驿");
//        Student stu4=new Student("余辉煌");
//        Student stu5=new Student("彭堂博");
//        Student stu6=new Student("彭永硕");
//        Student stu7=new Student("刘尹康");
//        Student stu8=new Student("张金茂");
//        Student stu9=new Student("谢宗凌");
//        Student stu10=new Student("赵哲");
//        Student stu11=new Student("蔡康林");
//        Student stu12=new Student("袁冲冲");
//
//        stus1.add(stu1);
//        stus1.add(stu2);
//        stus1.add(stu3);
//        stus1.add(stu4);
//        stus1.add(stu5);
//        stus1.add(stu6);
//        stus1.add(stu7);
//        stus1.add(stu8);
//        stus1.add(stu9);
//        stus1.add(stu10);
//        stus1.add(stu11);
//        stus1.add(stu12);
//
//
//        gc1.setStus(stus1);
//
//
//        //92班学生名单
//
//        Student stu921=new Student("盛高林");
//        Student stu922=new Student("徐园超");
//        Student stu923=new Student("荣傲");
//        Student stu924=new Student("韩宇晨");
//
//        stus2.add(stu921);
//        stus2.add(stu922);
//        stus2.add(stu923);
//        stus2.add(stu924);
//
//        gc2.setStus(stus2);
//
//        //93班学生名单
//        Student stu931=new Student("杨雨萱");
//        Student stu932=new Student("刘雅婷");
//        Student stu933=new Student("李心怡");
//        Student stu934=new Student("熊婕");
//        Student stu935=new Student("杜佳怡");
//        Student stu936=new Student("袁媚琳");
//        Student stu937=new Student("柯凯因");
//
//        stus3.add(stu931);
//        stus3.add(stu932);
//        stus3.add(stu933);
//        stus3.add(stu934);
//        stus3.add(stu935);
//        stus3.add(stu936);
//        stus3.add(stu937);
//
//        gc3.setStus(stus3);
//
//        Student stu941=new Student("夏祺");
//        Student stu942=new Student("雷道琦");
//        Student stu943=new Student("罗丝绦");
//        Student stu944=new Student("卢玮韬");
//        Student stu945=new Student("黄学骏");
//        Student stu946=new Student("陈闻达");
//        Student stu947=new Student("徐鸿博");
//        Student stu948=new Student("陈子瑜");
//        Student stu949=new Student("易旻辉");
//        Student stu940=new Student("刘烨");
//
//        stus4.add(stu941);
//        stus4.add(stu942);
//        stus4.add(stu943);
//        stus4.add(stu944);
//        stus4.add(stu945);
//        stus4.add(stu946);
//        stus4.add(stu947);
//        stus4.add(stu948);
//        stus4.add(stu949);
//        stus4.add(stu940);
//
//        gc4.setStus(stus4);
//
//
//        Student stu951=new Student("曾艺浩");
//        Student stu952=new Student("肖恒阳");
//        Student stu953=new Student("肖旸");
//        Student stu954=new Student("李唯");
//        Student stu955=new Student("李泽瑾");
//        Student stu956=new Student("陈宣吉");
//        Student stu957=new Student("郑辉");
//        Student stu958=new Student("刘佳");
//        Student stu959=new Student("鲁胜雄");
//
//        stus5.add(stu951);
//        stus5.add(stu952);
//        stus5.add(stu953);
//        stus5.add(stu954);
//        stus5.add(stu955);
//        stus5.add(stu956);
//        stus5.add(stu957);
//        stus5.add(stu958);
//        stus5.add(stu959);
//
//        gc5.setStus(stus5);
//
//        Student stu961=new Student("屈国秋");
//        Student stu962=new Student("张碧咸");
//        Student stu963=new Student("徐号然");
//        Student stu964=new Student("张梦秋");
//        Student stu965=new Student("肖恩熙");
//
//        stus6.add(stu961);
//        stus6.add(stu962);
//        stus6.add(stu963);
//        stus6.add(stu964);
//        stus6.add(stu965);
//
//        gc6.setStus(stus6);
//
//        Student stu971=new Student("祝峰");
//        Student stu972=new Student("蔡智康");
//        Student stu973=new Student("郑传新");
//        stus7.add(stu971);
//        stus7.add(stu972);
//        stus7.add(stu973);
//
//        gc7.setStus(stus7);
//
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//            MDBTools mdb=new MDBTools();
//            gradeClassList.add(gc1);
//                gradeClassList.add(gc2);
//                gradeClassList.add(gc3);
//                gradeClassList.add(gc4);
//                gradeClassList.add(gc5);
//                gradeClassList.add(gc6);
//                gradeClassList.add(gc7);
//
//
//        mdb.saveGradeClasses((ArrayList<GradeClass>) gradeClassList);
//
//                for (int i=0;i<stus1.size();i++)
//                {
//                    stus1.get(i).setGradeclassid(gc1.get_id());
//                    mdb.addStu(stus1.get(i));
//                }
//                for (int i=0;i<stus2.size();i++)
//                {
//                    stus2.get(i).setGradeclassid(gc2.get_id());
//
//                    mdb.addStu(stus2.get(i));
//                }
//                for (int i=0;i<stus3.size();i++)
//                {
//                    stus3.get(i).setGradeclassid(gc3.get_id());
//
//                    mdb.addStu(stus3.get(i));
//                }
//                for (int i=0;i<stus4.size();i++)
//                {
//                    stus4.get(i).setGradeclassid(gc4.get_id());
//
//                    mdb.addStu(stus4.get(i));
//                }
//                for (int i=0;i<stus5.size();i++)
//                {
//                    stus5.get(i).setGradeclassid(gc5.get_id());
//
//                    mdb.addStu(stus5.get(i));
//                }
//                for (int i=0;i<stus6.size();i++)
//                {
//                    stus6.get(i).setGradeclassid(gc6.get_id());
//
//                    mdb.addStu(stus6.get(i));
//                }
//                for (int i=0;i<stus7.size();i++)
//                {
//                    stus7.get(i).setGradeclassid(gc7.get_id());
//
//                    mdb.addStu(stus7.get(i));
//                }
//
//            }
//        }.start();
//
//    }


}
