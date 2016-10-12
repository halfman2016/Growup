package com.yper.feng.growup.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yper.feng.growup.Adapter.MainInfoListAdapter;
import com.yper.feng.growup.Adapter.MainFragmentAdapter;
import com.yper.feng.growup.Adapter.MainPinListAdapter;
import com.yper.feng.growup.Adapter.MainSubjectListAdapter;
import com.yper.feng.growup.Fragment.MainAnalysisFragment;
import com.yper.feng.growup.Fragment.MainInfoFragment;
import com.yper.feng.growup.Fragment.MainPinFragment;
import com.yper.feng.growup.Fragment.MainSubjectFragment;
import com.yper.feng.growup.Module.BaseInfoItem;
import com.yper.feng.growup.Module.DayCommonAction;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.Module.SubjectPinAction;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.Module.WeekReport;
import com.yper.feng.growup.MyApplication;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MainActivity extends FragmentActivity {
    private HashMap<String,Integer> defaultValues=new HashMap<>();
    private List<BaseInfoItem> items = new ArrayList<>();
    private List<Subject> subjectItems=new ArrayList<>();
    private List<Photopic> photopics=new ArrayList<>();

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton rbInfo, rbSubject, rbPin, rbAnalysis;

    public Teacher teacher;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private MainPinFragment mainPinFragment = new MainPinFragment();


    private MDBTools mdb=new MDBTools();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initViews();
        initEvents();
        MyApplication myApplication=MyApplication.getInstance();

        Intent intent=getIntent();
        String teacherjson=intent.getStringExtra("teacherjson");
        Gson gson= new GsonBuilder().create();
        teacher=gson.fromJson(teacherjson,Teacher.class);

    }

    private void initEvents() {
        ImageButton btnPhoto = (ImageButton) findViewById(R.id.titlebar_photo);
        ImageButton btnAddrec = (ImageButton) findViewById(R.id.titlebar_addrec);
        ImageButton btnCamrec = (ImageButton) findViewById(R.id.titlebar_camrec);

        btnPhoto.setOnClickListener(new MainActivityOnClickListener());
        btnCamrec.setOnClickListener(new MainActivityOnClickListener());
        btnAddrec.setOnClickListener(new MainActivityOnClickListener());


    }

    private class MainActivityOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d("myapp", String.valueOf(v.getId()));
            Intent intent;
            switch (v.getId()) {
                case R.id.titlebar_addrec:
                    Log.d("myapp", "addrec");
                    intent=new Intent();
                    intent.setClass(MainActivity.this,Add_rec.class);
                    startActivityForResult(intent,300);

                    break;
                case R.id.titlebar_camrec:
                    Log.d("myapp", "camrec");
                    intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    fileUri=getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
                    startActivityForResult(intent,200);

                    break;
                case R.id.titlebar_photo:
                    Log.d("myapp", "photo");

                     intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                     fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                    startActivityForResult(intent, 100);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("myapp",String.valueOf(resultCode) );

        Intent intent;
        if (resultCode==0) return;  // 取消时退出

        switch (requestCode) {
            case 100:

                    //照相
                    intent = new Intent(MainActivity.this, TakePhoto.class);
                    intent.setData(fileUri);
                Gson gson=new GsonBuilder().create();
                intent.putExtra("teacher",gson.toJson(teacher));
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
                Log.d("myapp" ,"返回的值是:"+data.getStringExtra("actiontype"));
                intent=new Intent(getBaseContext(),AddRecMain.class);
                intent.putExtra("actiontype",data.getStringExtra("actiontype"));
                startActivityForResult(intent,400);
                break;
            case 101:
                //拍照返回
                mainPinFragment.setListAdapter(new MainPinListAdapter(photopics,getBaseContext()));

                break;
        }

    }

    /** Create a file Uri for saving an image or video */
    private  Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir=new File(getBaseContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),"growup");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("myapp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private void initDatas() {



        MyApplication.getInstance().setDefaultValues(defaultValues);

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = "2016-03-12 09:3:2";
        DayCommonAction ac1 = new DayCommonAction("检查卫生合格", "日常规", 2);
        DayCommonAction ac2 = new DayCommonAction("讲文明懂礼貌", "日常规", 2);
        SubjectPinAction ac3 = new SubjectPinAction("洗漱用品摆放不整齐", "专题", -2);
        HashMap<Integer, String> rank = new HashMap<Integer, String>();
        rank.put(1, "赵一");
        rank.put(2, "钱二");
        rank.put(3, "孙三");
        rank.put(4, "李四");
        rank.put(5, "张五");
        WeekReport ac4 = new WeekReport("2016年第2次", rank);
        BaseInfoItem item1 = new BaseInfoItem(ac1), item2 = new BaseInfoItem(ac2), item3 = new BaseInfoItem(ac3), item4 = new BaseInfoItem(ac4);
//        try {
//            ac1.setActionOccurTime(fmt.parse(datetime));
//            ac2.setActionOccurTime(fmt.parse("2016-03-14 19:03:12"));
//            ac3.setActionOccurTime(fmt.parse("2016-03-2 12:03:02"));
//            ac4.setActionOccurTime(fmt.parse("2016-03-22 09:3:02"));
//            ac1.setActionPinTime(fmt.parse("2016-04-12 09:3:2"));
//            ac2.setActionPinTime(fmt.parse("2016-04-14 12:33:2"));
//            ac3.setActionPinTime(fmt.parse("2016-04-12 17:34:2"));
//            ac4.setActionPinTime(fmt.parse("2016-04-16 09:3:2"));
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);

        Subject s1=new Subject("黄陂游学活动");
        Subject s2=new Subject("参观科技馆");

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        try {
            s1.setStartTime(simpleDateFormat.parse("2016-10-1"));
            s1.setEndTime(simpleDateFormat.parse("2016-10-4"));
            s2.setStartTime(simpleDateFormat.parse("2016-10-1"));
            s2.setEndTime(simpleDateFormat.parse("2016-10-3"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        s1.setSubjectInfo("我们组织去黄陂游学,住在大余湾,4天时间。从小伙伴身上学习必须学习到的东西。大家努力!");
        s2.setSubjectInfo("去江滩的武汉科技馆,3天时间,大家学习新技术,脑子里存在的问题,现场钻研琢磨。");
        subjectItems.add(s1);
        subjectItems.add(s2);

        //pinphoto数据初始化
//
//        Photopic photopic1=new Photopic();
//        Photopic photopic2=new Photopic();
//
//        photopic1.setPhotomemo("抓拍一下");
//        photopic1.setPhotodate(new Date());
//        //photopic1.setPhotopic();
//        photopic2.setPhotomemo("做的真棒");
//        photopic2.setPhotodate(new Date());
//        photopic1.setPhotoauthor("王强");
//        photopic2.setPhotoauthor("李老师");
//
//        photopics.add(photopic1);
//        photopics.add(photopic2);


    }

    private void initViews() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rbInfo = (RadioButton) findViewById(R.id.rb_info);
        rbSubject = (RadioButton) findViewById(R.id.rb_suject);
        rbPin = (RadioButton) findViewById(R.id.rb_pin);
        rbAnalysis = (RadioButton) findViewById(R.id.rb_analysis);
        //RadioGroup选中状态改变监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_info:
                        /**
                         * setCurrentItem第二个参数控制页面切换动画
                         * true:打开/false:关闭
                         */
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_suject:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_pin:
                        viewPager.setCurrentItem(2, false);
                        break;
                    case R.id.rb_analysis:
                        viewPager.setCurrentItem(3, false);
                        break;
                }
            }
        });

        /**
         * ViewPager部分
         */
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        MainInfoFragment mainInfoFragment = new MainInfoFragment();
        MainSubjectFragment mainSubjectFragment = new MainSubjectFragment();
        MainPinFragment mainPinFragment = new MainPinFragment();
        MainAnalysisFragment mainAnalysisFragment = new MainAnalysisFragment();

        List<Fragment> alFragment = new ArrayList<>();


        MainInfoListAdapter mainInfoListAdapter = new MainInfoListAdapter(items, getBaseContext());
        mainInfoFragment.setListAdapter(mainInfoListAdapter);

        MainSubjectListAdapter mainSubjectListAdapter =new MainSubjectListAdapter(subjectItems,getBaseContext());
        mainSubjectFragment.setListAdapter(mainSubjectListAdapter);

        MainPinListAdapter mainPinListAdapter=new MainPinListAdapter(photopics,getBaseContext());
        mainPinFragment.setListAdapter(mainPinListAdapter);

        alFragment.add(mainInfoFragment);
        alFragment.add(mainSubjectFragment);
        alFragment.add(mainPinFragment);
        alFragment.add(mainAnalysisFragment);
        //ViewPager设置适配器
        viewPager.setAdapter(new MainFragmentAdapter(getSupportFragmentManager(), alFragment));
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
                        radioGroup.check(R.id.rb_info);
                        break;
                    case 1:
                        radioGroup.check(R.id.rb_suject);
                        break;
                    case 2:
                        radioGroup.check(R.id.rb_pin);
                        break;
                    case 3:
                        radioGroup.check(R.id.rb_analysis);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
