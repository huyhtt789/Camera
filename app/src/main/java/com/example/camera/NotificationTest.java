package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;

public class NotificationTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test2);
    }
    public void xuLyTaoNotification(View view){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentTitle("Có Thông báo nè");
        builder.setContentText("Tạo thành công Notification");
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent resultIntent=new Intent(this,NotificationTest.class);
        resultIntent.putExtra("ChiTIET","AMAZING GOOD JOB!!!!");
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationTest.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(341,builder.build());
    }
    public void xuLyDongNotification(View view){
            NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.cancel(341);
    }
}