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
import com.umeng.analytics.MobclickAgent;
import com.yongchun.library.view.ImageSelectorActivity;
import com.yper.feng.growup.Adapter.MainFragmentAdapter;
import com.yper.feng.growup.Adapter.MainInfoListAdapter;
import com.yper.feng.growup.Adapter.MainPinListAdapter;
import com.yper.feng.growup.Adapter.MainSubjectListAdapter;
import com.yper.feng.growup.Fragment.MainAnalysisFragment;
import com.yper.feng.growup.Fragment.MainInfoFragment;
import com.yper.feng.growup.Fragment.MainPeopleFragment;
import com.yper.feng.growup.Fragment.MainPinFragment;
import com.yper.feng.growup.Fragment.MainSubjectFragment;
import com.yper.feng.growup.Module.BaseInfoItem;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.MyApplication;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;
import com.yper.feng.growup.Util.MySqlTools;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends FragmentActivity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public Teacher teacher;
    private List<BaseInfoItem> items = new ArrayList<>();
    private List<Subject> subjectItems=new ArrayList<>();
    private List<Photopic> photopics=new ArrayList<>();
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton rbInfo, rbSubject, rbPin, rbAnalysis,rbpeople;
    private Uri fileUri;
    private MainPinFragment mainPinFragment = new MainPinFragment();
    private MainSubjectFragment mainSubjectFragment=new MainSubjectFragment();

    private MyApplication myApplication=MyApplication.getInstance();
    private MDBTools mdb=new MDBTools();

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        final com.yper.feng.growup.Module.Log log=myApplication.getLog();
        log.setEtime(new Timestamp(new Date().getTime()));
        log.setDtime(log.getEtime().getTime()-log.getStime().getTime());

                        new Thread(new Runnable() {
                    @Override
                    public void run() {

                        MySqlTools mySqlTools=new MySqlTools();
                        mySqlTools.getConn();

                        mySqlTools.insertLoginNameDate(log);
                        mySqlTools.closeConn();
                    }
                }).start();
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("myapp",String.valueOf(resultCode) );


        Intent intent;
        if (resultCode==0) return;  // 取消时退出



        switch (requestCode) {
            case ImageSelectorActivity.REQUEST_IMAGE:

                    //照相
                intent = new Intent(MainActivity.this, TakePhoto.class);
                ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
                String filename= (images.get(0));
                intent.putExtra("filename",filename);
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
                //开启记录窗口


                break;

        }

        switch (resultCode){
            case 301:
                Log.d("myapp" ,"返回的值是:"+data.getStringExtra("actiontype"));
                intent=new Intent(getBaseContext(),AddRecMain.class);
                intent.putExtra("actiontype",data.getStringExtra("actiontype"));
                Gson gson=new GsonBuilder().create();
                intent.putExtra("teacherjson",gson.toJson(teacher));
                startActivityForResult(intent,400);
                break;
            case 101:
                //拍照返回

                viewPager.setCurrentItem(2);
                mainPinFragment.loaddata();

                break;

            case 401:
                //记录窗口返回



                break;

            case 1001:
                //subject刷新
                Log.d("myapp","subject refresh");
                mainSubjectFragment.loaddata();
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




//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String datetime = "2016-03-12 09:3:2";
//        DayCommonAction ac1 = new DayCommonAction("检查卫生合格", "日常规", 2);
//        DayCommonAction ac2 = new DayCommonAction("讲文明懂礼貌", "日常规", 2);
//        SubjectPinAction ac3 = new SubjectPinAction("洗漱用品摆放不整齐", "专题", -2);
//        HashMap<Integer, String> rank = new HashMap<Integer, String>();
//        rank.put(1, "赵一");
//        rank.put(2, "钱二");
//        rank.put(3, "孙三");
//        rank.put(4, "李四");
//        rank.put(5, "张五");
//        WeekReport ac4 = new WeekReport("2016年第2次", rank);
//        BaseInfoItem item1 = new BaseInfoItem(ac1), item2 = new BaseInfoItem(ac2), item3 = new BaseInfoItem(ac3), item4 = new BaseInfoItem(ac4);
////        try {
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
//        items.add(item1);
//        items.add(item2);
//        items.add(item3);
//        items.add(item4);
//




    }

    private void initViews() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rbInfo = (RadioButton) findViewById(R.id.rb_info);
        rbSubject = (RadioButton) findViewById(R.id.rb_suject);
        rbPin = (RadioButton) findViewById(R.id.rb_pin);
        rbAnalysis = (RadioButton) findViewById(R.id.rb_analysis);
        rbpeople=(RadioButton)findViewById(R.id.rb_people) ;
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
                    case R.id.rb_people:
                        viewPager.setCurrentItem(4,false);
                        break;
                }
            }
        });

        /**
         * ViewPager部分
         */
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        MainInfoFragment mainInfoFragment = new MainInfoFragment();
        MainAnalysisFragment mainAnalysisFragment = new MainAnalysisFragment();

        List<Fragment> alFragment = new ArrayList<>();


        MainInfoListAdapter mainInfoListAdapter = new MainInfoListAdapter(items, getBaseContext());
        mainInfoFragment.setListAdapter(mainInfoListAdapter);

        MainSubjectListAdapter mainSubjectListAdapter =new MainSubjectListAdapter(subjectItems,getBaseContext());
        mainSubjectFragment.setListAdapter(mainSubjectListAdapter);

        MainPinListAdapter mainPinListAdapter=new MainPinListAdapter(photopics,getBaseContext());
        mainPinFragment.setListAdapter(mainPinListAdapter);

        MainPeopleFragment mainPeopleFragment=new MainPeopleFragment();


        alFragment.add(mainInfoFragment);
        alFragment.add(mainSubjectFragment);
        alFragment.add(mainPinFragment);
        alFragment.add(mainAnalysisFragment);
        alFragment.add(mainPeopleFragment);

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
                    case 4:
                        radioGroup.check(R.id.rb_people);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private class MainActivityOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d("myapp", String.valueOf(v.getId()));
            Intent intent;
            switch (v.getId()) {
                case R.id.titlebar_addrec:
                    Log.d("myapp", "addrec");
                    intent = new Intent();
                    intent.setClass(MainActivity.this, Add_rec.class);
                    startActivityForResult(intent, 300);

                    break;
                case R.id.titlebar_camrec:
                    Log.d("myapp", "camrec");
                    intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, 200);

                    break;
                case R.id.titlebar_photo:
                    Log.d("myapp", "photo");

//                     intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                     fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
//
//                    startActivityForResult(intent, 100);
                    int maxSelectNum = 1;
                    ImageSelectorActivity.start(MainActivity.this, maxSelectNum, ImageSelectorActivity.MODE_SINGLE, true, true, false);

                    break;
            }
        }
    }


}
