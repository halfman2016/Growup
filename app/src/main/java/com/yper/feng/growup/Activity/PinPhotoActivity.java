package com.yper.feng.growup.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yper.feng.growup.Module.GradeClass;
import com.yper.feng.growup.Module.Student;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PinPhotoActivity extends AppCompatActivity {
    private HashMap<String,Integer> defaultValues=new HashMap<>();
    private MDBTools mdb=new MDBTools();

    private List<Student> allstudents =new ArrayList<>();
    private List<Student> stus1 =new ArrayList<>();
    private List<Student> stus2 =new ArrayList<>();
    private List<Student> stus3 =new ArrayList<>();
    private List<Student> stus4 =new ArrayList<>();
    private List<Student> stus5 =new ArrayList<>();
    private List<Student> stus6 =new ArrayList<>();
    private List<Student> stus7 =new ArrayList<>();
    private GradeClass gc1=new GradeClass("九1班");
    private GradeClass gc2=new GradeClass("九2班");
    private GradeClass gc3=new GradeClass("九3班");
    private GradeClass gc4=new GradeClass("八1班");
    private GradeClass gc5=new GradeClass("八2班");
    private GradeClass gc6=new GradeClass("八3班");
    private GradeClass gc7=new GradeClass("启新班");

    private List<GradeClass> gradeClassList =new ArrayList<>();

    private String subjectname;
    private String subjectid;

    private GridView gridView;
    private ArrayList<HashMap<String,Object>> lstNameItem=new ArrayList<HashMap<String,Object>>();
    private final String [] mStrings={"个人卫生不合格","文明用语","上课走神","积极回答问题","做操认真"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_photo);

        initdata();
        initView();


    }
    private  void initdata(){
        //班级清单
       // defaultValues= (HashMap<String, Integer>) mdb.getDefaultValues();

        gradeClassList.add(gc1);
        gradeClassList.add(gc2);
        gradeClassList.add(gc3);
        gradeClassList.add(gc4);
        gradeClassList.add(gc5);
        gradeClassList.add(gc6);
        gradeClassList.add(gc7);

        //学生花名册

        //allstudents=mdb.getStus();
        Student stu1=new Student("胡涛");
        Student stu2=new Student("江先明");
        Student stu3=new Student("陈驿");
        Student stu4=new Student("余辉煌");
        Student stu5=new Student("彭堂博");
        Student stu6=new Student("彭永硕");
        Student stu7=new Student("刘尹康");
        Student stu8=new Student("张金茂");
        Student stu9=new Student("谢宗凌");
        Student stu10=new Student("赵哲");
        Student stu11=new Student("蔡康林");
        Student stu12=new Student("袁冲冲");


        stus1.add(stu1);
        stus1.add(stu2);
        stus1.add(stu3);
        stus1.add(stu4);
        stus1.add(stu5);
        stus1.add(stu6);
        stus1.add(stu7);
        stus1.add(stu8);
        stus1.add(stu9);
        stus1.add(stu10);
        stus1.add(stu11);
        stus1.add(stu12);


        gc1.setStus(stus1);


        //92班学生名单

        Student stu921=new Student("盛高林");
        Student stu922=new Student("徐园超");
        Student stu923=new Student("荣傲");
        Student stu924=new Student("韩宇晨");

        stus2.add(stu921);
        stus2.add(stu922);
        stus2.add(stu923);
        stus2.add(stu924);

        gc2.setStus(stus2);

        //93班学生名单
        Student stu931=new Student("杨雨萱");
        Student stu932=new Student("刘雅婷");
        Student stu933=new Student("李心怡");
        Student stu934=new Student("熊婕");
        Student stu935=new Student("盛高林");
        Student stu936=new Student("徐园超");
        Student stu937=new Student("荣傲");

        stus3.add(stu931);
        stus3.add(stu932);
        stus3.add(stu933);
        stus3.add(stu934);
        stus3.add(stu935);
        stus3.add(stu936);
        stus3.add(stu937);

        gc3.setStus(stus3);
    }

    private  void initView(){

        final LinearLayout linearLayout= (LinearLayout) findViewById(R.id.classlist);
        linearLayout.removeAllViews();

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
                            setGridView(gradeClassList.get(i).getStus());
                        }
                    }
                }
            });

        }
    }
    private void setGridView(List<Student> tempstulist){

        gridView= (GridView) findViewById(R.id.mygridview);
        //gridView.setEmptyView(new View(getContext()));
        lstNameItem.clear();
        for(int i=0;i<tempstulist.size();i++){
            HashMap<String,Object> map=new HashMap<String, Object>();
            map.put("stuname",tempstulist.get(i).getName());
            lstNameItem.add(map);
        }

        SimpleAdapter adapter=new SimpleAdapter(getBaseContext(),lstNameItem,
                R.layout.subject_item_grid_student,new String[]{"stuname"},new int[]{R.id.stuname});
        gridView.setAdapter(adapter);


    }



}
