package com.example.camera;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
            Log.e("hocerror","NOI TUYET VONG KHUNG KHIEP");
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentTitle("Có Thông báo nè");
        builder.setContentText("Tạo thành công Notification");
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent resultIntent=new Intent(context,MainActivity.class);
        resultIntent.putExtra("selfie","selfietime");
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
        stackBuilder.addParentStack(NotificationTest.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(341,builder.build());
    }
}
