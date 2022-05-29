package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity2 extends AppCompatActivity {
    Button btnhengio,btndunglai,selectdate;
    TimePicker timePicker;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    TextView txdatetime;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);
        btnhengio=(Button) findViewById(R.id.btnHenGio);
        btndunglai=(Button) findViewById(R.id.btnDungLai);
        timePicker=(TimePicker) findViewById(R.id.timepick);
        txdatetime=findViewById(R.id.btndatepick);
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        Button test=(Button) findViewById(R.id.btntest);
        selectdate=(Button) findViewById(R.id.btndatepick);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        AlarmActivity2.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AlarmActivity2.this,NotificationTest.class);
                startActivity(intent);
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=day+"/"+month+"/"+year;
                txdatetime.setText(date);
            }
        };
        Intent intent=new Intent(AlarmActivity2.this,AlarmReceiver.class);
        btnhengio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gio=timePicker.getCurrentHour();
                int phut=timePicker.getCurrentMinute();

                String string_gio=String.valueOf(gio);
                String string_phu=String.valueOf(phut);

                Toast.makeText(AlarmActivity2.this,"giờ bạn đã đặt là :"+gio+" giờ",Toast.LENGTH_LONG).show();
                pendingIntent=PendingIntent.getBroadcast(AlarmActivity2.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            }
        });
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