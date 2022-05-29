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
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class imageget extends AppCompatActivity {
    ImageView hinh;
    Button button;
    Item[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageget);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hinh=(ImageView) findViewById(R.id.hinhchup);
        Intent intent = getIntent();
        String item = intent.getStringExtra("Chi tiet");
        button=(Button) findViewById(R.id.btnnon);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(imageget.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void getlistImage(boolean set){
        String path = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/temp";
        String deletepath = Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/trashbin";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        items=new Item[files.length];
        for (int i = 0; i < files.length; i++)
        {
            Toast.makeText(this,getIntent().getStringExtra("Chi tiet") , Toast.LENGTH_SHORT).show();
            if(files[i].getName()==getIntent().getStringExtra("Chi tiet")) {
                String filePath = files[i].getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                Toast.makeText(this,getIntent().getStringExtra("Chi tiet") , Toast.LENGTH_SHORT).show();
                hinh.setImageBitmap(bitmap);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }


}