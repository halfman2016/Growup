package com.yper.feng.growup.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.Module.Subject;
import com.yper.feng.growup.Module.Teacher;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;
import com.yper.feng.growup.Util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TakePhoto extends AppCompatActivity  {

    Uri fileUri;
    Teacher teacher;
    Subject subject=null;
    ImageView imageView;
    Photopic photopic=new Photopic();
    EditText photomemo;
    TextView photoauthor;
    TextView photodate;
    ProgressBar progressBar;
    Gson gson=new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        Intent intent=getIntent();
        fileUri=intent.getData();

        teacher=gson.fromJson(intent.getStringExtra("teacher"),Teacher.class);

        if(intent.getStringExtra("subject")==null)
        {

        }
        else
        {
            subject=gson.fromJson(intent.getStringExtra("subject"),Subject.class);
            photopic.setBelongToSubject(subject.get_id());
        }


        Bitmap bm=BitmapFactory.decodeFile(Utils.getPathByUri4kitkat(getBaseContext(),fileUri));
        bm=bitmapresize(bm,1000);

        imageView=(ImageView)findViewById(R.id.imageView);
        photomemo=(EditText)findViewById(R.id.photomemo);
        photoauthor=(TextView) findViewById(R.id.photoauthor);
        photodate=(TextView)findViewById(R.id.photodate);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        photoauthor.setText(teacher.getName());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        photodate.setText(sdf.format(new Date()));

        imageView.setImageBitmap(bm);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        bm=bitmapresize(bm,220);
        //photopic.setPhotofile(baos.toByteArray());
        bm.compress(Bitmap.CompressFormat.JPEG,80,baos);
        photopic.setPhotopreview(baos.toByteArray());
        Button btn= (Button) findViewById(R.id.btnLoadPhoto);
        MyListener myListener=new MyListener();
        btn.setOnClickListener(myListener);



    }

    private Bitmap bitmapresize(Bitmap bitmap,int newwidth){

        int rawHeight=bitmap.getHeight();
        int rawWidth=bitmap.getWidth();


        float widthScale=((float)newwidth)/rawWidth;
        float heightScale=widthScale;

        Matrix matrix = new Matrix();

        matrix.postScale(heightScale,widthScale);

        bitmap=Bitmap.createBitmap(bitmap,0,0,rawWidth,rawHeight,matrix,true);
        return bitmap;
    }

    private  class  MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            if (view.getId()==R.id.btnLoadPhoto)
            {
                Log.d("myapp","hello loadphoto");
                addPhotoTask ad=new addPhotoTask();
                progressBar.setVisibility(View.VISIBLE);
                ad.execute();
            }



        }
    }




private  class addPhotoTask extends AsyncTask<String,Integer,Boolean> {


    @Override
    protected Boolean doInBackground(String... strings) {

        MDBTools mdbTools = new MDBTools();
        photopic.setPhotoauthorid(teacher.get_id());
        photopic.setPhotodate(new Date());
        photopic.setPhotoauthor(teacher.getName());
        publishProgress(1,1);
        mdbTools.addPhoto(photopic);
        return true;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        photopic.setPhotomemo(photomemo.getText().toString());
       if(subject==null) {
           setResult(101);  //main调用
       }
        else
       {
           setResult(102);  //subject调用
       }
           finish();
    }
}

    private class getPhotoTask extends AsyncTask<String,Integer,String>{
        Photopic photopic;

        @Override
        protected String doInBackground(String... strings) {

            MDBTools mdbTools=new MDBTools();
            photopic=mdbTools.getPhoto(UUID.fromString(strings[0]));

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            File mediaStorageDir=new File(getBaseContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),"growup");
//            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "id.jpg");
            Bitmap bm=BitmapFactory.decodeByteArray(photopic.getPhotofile(),0,photopic.getPhotofile().length);
            imageView.setImageBitmap(bm);

        }
    }
}

