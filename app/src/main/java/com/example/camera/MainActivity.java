package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    Item[] items;
    ListView _myList;
    Button chup;
    Button selectall;
    Button delete;
    Button trashbin;
    Button btnalarmset;
    CustomListItemAdapter adapter;
    OutputStream output;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create an instance of Camera
        getlistImage(false);
        _myList = (ListView) findViewById(R.id.myList);
        adapter = new CustomListItemAdapter(this, R.layout.list_item_lnk_img, items);
        _myList.setAdapter(adapter);
        chup=(Button) findViewById(R.id.btnSelfie);
        delete=(Button) findViewById(R.id.btndelete);
        selectall=(Button) findViewById(R.id.btnAll);
        trashbin=(Button) findViewById(R.id.btntrashbin);
        btnalarmset=(Button) findViewById(R.id.btnalarmset);
        btnalarmset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AlarmActivity2.class);
                startActivity(intent);
            }
        });
        chup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteimg();
                adapter = new CustomListItemAdapter(MainActivity.this, R.layout.list_item_lnk_img, items);
                _myList.setAdapter(adapter);
            }
        });
        selectall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getlistImage(true);
                adapter = new CustomListItemAdapter(MainActivity.this, R.layout.list_item_lnk_img, items);
                _myList.setAdapter(adapter);
            }
        });
        trashbin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this,TrashBin.class);
                    startActivity(intent);
                    finish();
            }
        });
        Intent intent=getIntent();
        if(intent.hasExtra("selfie")){
            takePicture();
        }
    }
    private void takePicture(){
        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,0);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            finish();
            startActivity(getIntent());
        }
    }
    public void getlistImage(boolean set){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang lấy dữ liệu.... ");
        progressDialog.show();
        String path = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/temp";
        String deletepath = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/trashbin";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        items=new Item[files.length];
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getName());
            String filePath = files[i].getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            Date lastModDate = new Date(files[i].lastModified());
            String date=lastModDate.getDate()+"/"+
            lastModDate.getMonth()+"/"+
            lastModDate.getYear()+" || "+
            lastModDate.getHours()+":"+
            lastModDate.getMinutes()+":"+
            lastModDate.getSeconds();
            items[i]=new Item();
            items[i].setName(files[i].getName());
            items[i].setPath(filePath);
            items[i].setImage(bitmap);
            items[i].setNgay(date);
            items[i].setSelected(set);
        }
        progressDialog.dismiss();
    }
    public void deleteimg(){
        String path = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/temp";
        String deletepath = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/trashbin";
        Log.d("Files", "Path: " + path);
        for (int i = 0; i < items.length; i++) {
            if(items[i].isSelected()) {
                File file = new File(items[i].getPath());
                File dirpath=new File(deletepath);
                dirpath.mkdir();
                File deletefile=new File(dirpath,items[i].getName());
                try {
                    output = new FileOutputStream(deletefile);
                }catch(FileNotFoundException e){
                    Toast.makeText(this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                }
                items[i].getImage().compress(Bitmap.CompressFormat.JPEG,100,output);
                try {
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(file.delete()) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();}
            }

        }
        getlistImage(false);
    }
}