package com.yper.feng.growup.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yper.feng.growup.Adapter.MySubjectMainFragmentAdapter;
import com.yper.feng.growup.Fragment.SubjectAnnouceFragment;
import com.yper.feng.growup.Fragment.SubjectDetailFragment;
import com.yper.feng.growup.Fragment.SubjectListFragment;
import com.yper.feng.growup.Fragment.SubjectPartakeFragment;
import com.yper.feng.growup.Fragment.SubjectPinFragment;
import com.yper.feng.growup.MyApplication;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubjectDetailMain extends AppCompatActivity {

    private HashMap<String,Integer> defaultValues=new HashMap<>();

    //list subjectActions


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
        String name = intent.getStringExtra("subjectname");

        TextView title = (TextView) findViewById(R.id.titile_subjectName);
        title.setText(name);

        initDatas();
        initViews();
        initEvents();

        MyApplication myApplication = MyApplication.getInstance();
        // Log.d("myapp","NOw is who is" + myApplication.getUsername());


    }
        private void initDatas(){


    }

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

            SubjectListFragment subjectListFragment=new SubjectListFragment();
            SubjectDetailFragment subjectDetailFragment=new SubjectDetailFragment();
            SubjectAnnouceFragment subjectAnnouceFragment=new SubjectAnnouceFragment();
            SubjectPartakeFragment subjectPartakeFragment=new SubjectPartakeFragment();
            SubjectPinFragment subjectPinFragment=new SubjectPinFragment();


            allfragment.add(subjectListFragment);
            allfragment.add(subjectDetailFragment);
            allfragment.add(subjectAnnouceFragment);
            allfragment.add(subjectPartakeFragment);
            allfragment.add(subjectPinFragment);

            //ViewPager设置适配器
            viewPager.setAdapter(new MySubjectMainFragmentAdapter(getSupportFragmentManager(), allfragment));
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



        }
    }




