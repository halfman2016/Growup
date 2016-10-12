package com.yper.feng.growup.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yper.feng.growup.R;

public class AddRecMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rec_main);


    }

   public void cancel(View view){

       finish();
   }
   public void save(View view){

   }
}
