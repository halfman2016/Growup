package com.yper.feng.growup.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yongchun.library.view.ImageSelectorActivity;
import com.yper.feng.growup.Adapter.SubjectMainFragmentAdapter;
import com.yper.feng.growup.Fragment.SubjectAnnouceFragment;
import com.yper.feng.growup.Fragment.SubjectDetailFragment;
import com.yper.feng.growup.Fragment.SubjectListFragment;
import com.yper.feng.growup.Fragment.SubjectPartakeFragment;
import com.yper.feng.growup.Fragment.SubjectPinFragment;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.MyApplication;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class SubjectMain extends FragmentActivity {

    private Uri fileUri;

    public Subject subject;
    public Teacher teacher;
    private Gson gson=new GsonBuilder().create();
    //list subjectActions

    private SubjectListFragment subjectListFragment;
    private SubjectDetailFragment subjectDetailFragment;
    private SubjectPartakeFragment subjectPartakeFragment;
    private SubjectAnnouceFragment subjectAnnouceFragment;
    private SubjectPinFragment subjectPinFragment;
   // private List<>


    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton rbSubjectlist, rbSubjectdetail, rbSubjectannouce, rbSubjectpartake,rbSubjectpin;

    private MDBTools mdb=new MDBTools();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail_main);
        Intent intent = getIntent();
        Gson gson = new GsonBuilder().create();

        subject = gson.fromJson(intent.getStringExtra("subject"), Subject.class);
        teacher = gson.fromJson(intent.getStringExtra("teacher"), Teacher.class);


        TextView title = (TextView) findViewById(R.id.titile_subjectName);
        if (subject.getSubjectName().length() > 13) {
            title.setText(subject.getSubjectName().substring(0, 13)+"...");
        } else
        {
            title.setText(subject.getSubjectName());
        }


        initDatas();


        MyApplication myApplication = MyApplication.getInstance();
        // Log.d("myapp","NOw is who is" + myApplication.getUsername());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(1001);
        finish();

    }

    private void initDatas(){

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    Message msg=new Message();
                    msg.what=1;
                    mhandler.sendMessage(msg);

                }
            }.start();


    }

    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    initViews();
                    initEvents();

                    break;
            }


        }
    };


        private void initViews(){

            viewPager= (ViewPager) findViewById(R.id.subjectPagers);
            List<Fragment> allfragment=new ArrayList<>();

            radioGroup= (RadioGroup) findViewById(R.id.radioGroupsubject);
            rbSubjectlist= (RadioButton) findViewById(R.id.rb_subject_list);
            rbSubjectdetail= (RadioButton) findViewById(R.id.rb_subject_details);
            rbSubjectpartake= (RadioButton) findViewById(R.id.rb_subject_partake);
            rbSubjectannouce= (RadioButton) findViewById(R.id.rb_subject_annouce);
            rbSubjectpin=(RadioButton)findViewById(R.id.rb_subject_pin);
            //radiogroup
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


                @Override

                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb_subject_list:
                            /**
                             * setCurrentItem第二个参数控制页面切换动画
                             * true:打开/false:关闭
                             */
                            viewPager.setCurrentItem(0, false);
                            break;
                        case R.id.rb_subject_details:
                            viewPager.setCurrentItem(1, false);
                            break;
                        case R.id.rb_subject_annouce:
                            viewPager.setCurrentItem(2, false);
                            break;
                        case R.id.rb_subject_partake:
                            viewPager.setCurrentItem(3, false);
                            break;
                        case R.id.rb_subject_pin:
                            viewPager.setCurrentItem(4,false);
                            break;
                    }
                }
            });



            //每个界面

                subjectListFragment=new SubjectListFragment();
             subjectDetailFragment=new SubjectDetailFragment();
             subjectAnnouceFragment=new SubjectAnnouceFragment();
             subjectPartakeFragment=new SubjectPartakeFragment();
             subjectPinFragment=new SubjectPinFragment();


            allfragment.add(subjectListFragment);
            allfragment.add(subjectDetailFragment);
            allfragment.add(subjectAnnouceFragment);
            allfragment.add(subjectPartakeFragment);
            allfragment.add(subjectPinFragment);

            //ViewPager设置适配器
            viewPager.setAdapter(new SubjectMainFragmentAdapter(getSupportFragmentManager(), allfragment));
            //ViewPager显示第一个Fragment
            viewPager.setCurrentItem(0);
            //ViewPager页面切换监听
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            radioGroup.check(R.id.rb_subject_list);
                            break;
                        case 1:
                            radioGroup.check(R.id.rb_subject_details);
                            break;
                        case 2:
                            radioGroup.check(R.id.rb_subject_annouce);
                            break;
                        case 3:
                            radioGroup.check(R.id.rb_subject_partake);
                            break;
                        case 4:
                            radioGroup.check(R.id.rb_subject_pin);
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


        }
        private void initEvents(){

            ImageButton btnPhoto = (ImageButton) findViewById(R.id.titlebar_photo);
            ImageButton btnCamrec = (ImageButton) findViewById(R.id.titlebar_camrec);

            btnPhoto.setOnClickListener(new myOnClickListener());
            btnCamrec.setOnClickListener(new myOnClickListener());


        }

    private class myOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d("myapp", String.valueOf(v.getId()));
            Intent intent;
            switch (v.getId()) {
                case R.id.titlebar_camrec:
                    Log.d("myapp", "camrec");
                    ImageSelectorActivity.start(SubjectMain.this,1,ImageSelectorActivity.MODE_SINGLE,true,true,false);
                    break;
                case R.id.titlebar_photo:
                    Log.d("myapp", "photo");

                    int maxSelectNum=1;
                    ImageSelectorActivity.start(SubjectMain.this, maxSelectNum, ImageSelectorActivity.MODE_SINGLE, true,true,false);

                    break;
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("myapp",String.valueOf(resultCode) );

        Intent intent;
        if (resultCode==0) return;  // 取消时退出

        switch (requestCode) {
            case ImageSelectorActivity.REQUEST_IMAGE:

                //照相
                intent = new Intent(this, TakePhoto.class);
                ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);

                String filname=images.get(0);

                intent.putExtra("filename",filname);
                intent.putExtra("teacher",gson.toJson(teacher));
                intent.putExtra("subject",gson.toJson(subject));
                startActivityForResult(intent, 103);
                break;
            case 200:
                //摄像
                intent = new Intent(this, TakeCam.class);
                intent.setData(fileUri);
                startActivityForResult(intent, 104);
                break;
            case 300:

                //


                break;

            case 400:

                break;

        }

        switch (resultCode){
            case 301:

                break;

            case 102:
                //拍照返回程序
                viewPager.setCurrentItem(0);
                subjectListFragment.loaddata();

                break;
        }

    }




}




