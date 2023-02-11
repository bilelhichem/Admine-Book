package com.example.adminbook.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.adminbook.MainActivity;
import com.example.adminbook.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class FirebaseService extends FirebaseMessagingService {
public static String CHANNEL_ID = "my channel";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Intent i = new Intent(this, MainActivity.class);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = new Random().nextInt();
        creatNotificationChanel(notificationManager);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle(message.getData().get("title"))
                .setContentText(message.getData().get("message"))
                .setSmallIcon(R.drawable._99047_address_book_icon)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(notificationId,notification);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void creatNotificationChanel(NotificationManager manager){
        String Chanel_name = "channelName";
        NotificationChannel channel  = new NotificationChannel(CHANNEL_ID,Chanel_name,NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("My channel description");
        channel.enableLights(true);
        channel.setLightColor(getColor(R.color.red));
        manager.createNotificationChannel(channel);

    }
}
