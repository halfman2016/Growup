package com.yper.feng.growup.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.yper.feng.growup.Activity.MyPhotoList;
import com.yper.feng.growup.Module.Photopic;
import com.yper.feng.growup.MyApplication;
import com.yper.feng.growup.R;
import com.yper.feng.growup.Util.BitmapCache;
import com.yper.feng.growup.Util.MDBTools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2017/4/3.
 */

public class MyPhotoListAdapter extends RecyclerView.Adapter {

    private List<Photopic> photolist =new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;
    private RequestQueue queue;
    private ImageLoader imageLoader;
    private MDBTools mdb=new MDBTools();

    public MyPhotoListAdapter(List<Photopic> photolist,Context context){
        super();
        this.photolist = photolist;
        this.context = context;
        queue= Volley.newRequestQueue(this.context);
        imageLoader=new ImageLoader(queue,new BitmapCache());
        this.layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.myphotoitem,parent,false);
        ViewHolder vh=new ViewHolder(view);

        return vh;
    }



        @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Photopic item=photolist.get(position);
        ViewHolder vh=(ViewHolder) holder;
        String url= MyApplication.getInstance().Url+item.getPicname();
        vh.photopic.setImageUrl(url,imageLoader);
        vh.photoauthor.setText(item.getPhotoauthor());
        vh.photomemo.setText(item.getPhotomemo());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if (item.getPhotodate()!=null )vh.photodate.setText(sdf.format(item.getPhotodate()));
        vh.btnDelPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("注意!").setMessage("即将删除本张图片")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //确定
                                Log.d("myapp","删除");
                                new Thread(){
                                    @Override
                                    public void run() {
                                        super.run();
                                       mdb.delPhotopic(item);


                                    }
                                }.start();
                                photolist.remove(position);
                                notifyDataSetChanged();

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("myapp","取消删除");
                    }
                }).show();

            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return photolist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView photopic;
        TextView photoauthor;
        TextView photodate;
        TextView photomemo;
        ImageButton btnDelPhoto;
        public ViewHolder(View view){
            super(view);
            photopic= (NetworkImageView) view.findViewById(R.id.photopic);
            photoauthor= (TextView) view.findViewById(R.id.photoauthor);
            photodate= (TextView) view.findViewById(R.id.photodate);
            photomemo= (TextView) view.findViewById(R.id.photomemo);
            btnDelPhoto=(ImageButton) view.findViewById(R.id.btnDelPhoto);

        }
    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        final Photopic item=photolist.get(position);
//        final viewHolder vh;
//
//
//        if (convertView==null)
//        {
//            vh=new viewHolder();
//            convertView=layoutInflater.inflate(R.layout.myphotoitem,parent,false);
//            vh.photopic= (NetworkImageView) convertView.findViewById(R.id.photopic);
//            vh.photoauthor= (TextView) convertView.findViewById(R.id.photoauthor);
//            vh.photodate= (TextView) convertView.findViewById(R.id.photodate);
//            vh.photomemo= (TextView) convertView.findViewById(R.id.photomemo);
//            vh.btnDelPhoto=(ImageButton) convertView.findViewById(R.id.btnDelPhoto);
//
//            convertView.setTag(vh);
//        }
//        else
//        {
//            vh= (viewHolder) convertView.getTag();
//        }
//
//        String url= MyApplication.getInstance().Url+item.getPicname();
//        vh.photopic.setImageUrl(url,imageLoader);
//        vh.photoauthor.setText(item.getPhotoauthor());
//        vh.photomemo.setText(item.getPhotomemo());
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        if (item.getPhotodate()!=null )vh.photodate.setText(sdf.format(item.getPhotodate()));
//
//        return convertView;
//    }
//    static class viewHolder{
//        NetworkImageView photopic;
//        TextView photoauthor;
//        TextView photodate;
//        TextView photomemo;
//        ImageButton btnDelPhoto;
//
//    }
}
