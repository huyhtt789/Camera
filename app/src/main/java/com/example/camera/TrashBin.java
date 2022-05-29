package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class TrashBin extends AppCompatActivity {
    Item[] items;
    ListView _myList;
    CustomListItemAdapter adapter;
    OutputStream output;
    ProgressDialog progressDialog;
    Button revive;
    Button deletefor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash_bin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getlistImage(false);
        _myList = (ListView) findViewById(R.id.myListTrash);
        adapter = new CustomListItemAdapter(this, R.layout.list_item_lnk_img, items);
        _myList.setAdapter(adapter);
        revive=(Button) findViewById(R.id.btnrevive);
        deletefor=(Button) findViewById(R.id.btndelete);
        deletefor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteimgforever();
                adapter = new CustomListItemAdapter(TrashBin.this, R.layout.list_item_lnk_img, items);
                _myList.setAdapter(adapter);
            }
        });
        revive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviveimg();
                adapter = new CustomListItemAdapter(TrashBin.this, R.layout.list_item_lnk_img, items);
                _myList.setAdapter(adapter);
            }
        });
    }
    public void getlistImage(boolean set){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang lấy dữ liệu.... ");
        progressDialog.show();
        String path = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/trashbin";
        String deletepath = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/temp";
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

            items[i]=new Item();
            items[i].setName(files[i].getName());
            items[i].setPath(filePath);
            items[i].setImage(bitmap);
            items[i].setNgay(lastModDate.toString());
            items[i].setSelected(set);
        }
        progressDialog.dismiss();
    }
    public void reviveimg(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xóa.... ");
        progressDialog.show();
        String path = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/trashbin";
        String deletepath = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/temp";
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
                    Toast.makeText(this, "khôi phục không thành công", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, "khôi phục thành công", Toast.LENGTH_SHORT).show();}
            }

        }
        getlistImage(false);
    }
    public void deleteimgforever(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xóa.... ");
        progressDialog.show();
        String path = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/trashbin";
        String deletepath = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/temp";
        Log.d("Files", "Path: " + path);
        for (int i = 0; i < items.length; i++) {
            if(items[i].isSelected()) {
                File file = new File(items[i].getPath());
                File dirpath=new File(deletepath);
                dirpath.mkdir();
                File deletefile=new File(dirpath,items[i].getName());
                if(file.delete()) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }
            }

        }
        getlistImage(false);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent=new Intent(TrashBin.this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}