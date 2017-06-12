package com.yper.feng.growup.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.umeng.analytics.MobclickAgent;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;

public class ChgPwd extends AppCompatActivity {

    private Teacher teacher;
    private EditText txtOldPwd;
    private EditText txtNewPwd;
    private EditText txtNewPwdRetry;
    private String newpwd;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chg_pwd);

        Intent intent=getIntent();
        final String teastr=   intent.getStringExtra("teacher");
        Gson gson=new GsonBuilder().create();
        teacher=gson.fromJson(teastr,Teacher.class);

        Button btnAction=(Button)findViewById(R.id.btnChgPwdAction);
        Button btnExit=(Button)findViewById(R.id.btnChgPwdback);
        txtOldPwd=(EditText)findViewById(R.id.txtChgPwdOldPwd);
        txtNewPwd=(EditText)findViewById(R.id.txtChgPwdNewPwd);
        txtNewPwdRetry=(EditText)findViewById(R.id.txtChgPwdNewPwdRetry);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stroldpwd=txtOldPwd.getText().toString();

                if(stroldpwd.equals(teacher.getPwd())){
                    String newpwdretry=txtNewPwdRetry.getText().toString();
                    newpwd=txtNewPwd.getText().toString();

                    if(newpwd.equals("") | newpwdretry.equals(""))
                    {
                        Toast.makeText(getBaseContext(),"新密码不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(newpwd.equals(newpwdretry))
                    {
                        //修改密码

                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                mdb.chgTeaPwd(teacher,newpwd);


                            }
                        }.start();

                        Toast.makeText(getBaseContext(),"密码修改成功，请返回！下次登录生效，请牢记新密码！（直到下次登录前，可以用老密码再次修改）",Toast.LENGTH_LONG).show();



                    }
                    else
                    {
                        //提示输入不符
                        Toast.makeText(getBaseContext(),"新密码两次输入不一致，请检查",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(getBaseContext(),"旧密码输入错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
            setTitle("修改密码");
    }
}
