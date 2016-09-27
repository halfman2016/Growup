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

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.MDBTools;
import com.yper.feng.growup.Util.Utils;

import java.io.File;

public class TakePhoto extends AppCompatActivity  {

Uri fileUri;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
      //  fileUri=savedInstanceState.getBundle("fileUri");
        Intent intent=getIntent();
        fileUri=intent.getData();

        imageView= (ImageView) findViewById(R.id.imageView);
        imageView.setImageURI(fileUri);
        String str= Utils.getPathByUri4kitkat(getBaseContext(),fileUri);
        Bitmap bm=BitmapFactory.decodeFile(str);

        int rawHeight=bm.getHeight();
        int rawWidth=bm.getWidth();

        int newHeight=1000;
        float heightScale=((float)newHeight)/rawHeight;
        float widthScale=heightScale;
        Matrix matrix = new Matrix();
        matrix.postScale(heightScale,widthScale);

        Bitmap newBitMap=Bitmap.createBitmap(bm,0,0,rawWidth,rawHeight,matrix,true);

        imageView.setImageBitmap(newBitMap);

        Utils.compressAndSaveBitmapToSDCard(newBitMap,"id.jpg",80);
        Button btn= (Button) findViewById(R.id.btnLoadPhoto);
        MyListener myListener=new MyListener();
        btn.setOnClickListener(myListener);

        Button btnbtn= (Button) findViewById(R.id.btnReadPhoto);
        btnbtn.setOnClickListener(myListener);


    }

    private  class  MyListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            if (view.getId()==R.id.btnLoadPhoto)
            {
                Log.d("myapp","hello loadphoto");
                String str= Utils.getSDCardPath()+File.separator+"id.jpg";
                addPhotoTask ad=new addPhotoTask();
                ad.execute(str);
            }

            if(view.getId()==R.id.btnReadPhoto){
                Log.v("myapp","hllo");
                getPhotoTask gp=new getPhotoTask();
                gp.execute("id.jpg");
            }

        }
    }




private  class addPhotoTask extends AsyncTask<String,Integer,Boolean>
{


    @Override
    protected Boolean doInBackground(String... strings) {
        String filename=strings[0];
        MDBTools mdbTools=new MDBTools();
        mdbTools.addPhoto(filename);
        return true;
    }


}

    private class getPhotoTask extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... strings) {

            MDBTools mdbTools=new MDBTools();
            byte[] txt=mdbTools.getPhoto(strings[0]);
            File mediaStorageDir=new File(getBaseContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),"growup");
            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "id.jpg");

            mdbTools.putFileFromBytes(txt,mediaFile.getPath());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            File mediaStorageDir=new File(getBaseContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),"growup");
            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "id.jpg");
            Bitmap bm=Utils.getLoacalBitmap(mediaFile.getPath());

            imageView.setImageBitmap(bm);

        }
    }
}

