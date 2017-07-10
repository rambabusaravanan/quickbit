package com.rambabusaravanan.quickbit.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rambabusaravanan.quickbit.MainActivity;
import com.rambabusaravanan.quickbit.R;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private int notificationId = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();
        if (data.size() > 0) {
            String title = data.get("title");
            String body = data.get("body");
            if(TextUtils.isEmpty(body)) body = data.get("message");
            sendNotification(title, body);
        }

        if (remoteMessage.getNotification() != null) {
            RemoteMessage.Notification n = remoteMessage.getNotification();
            sendNotification(n.getTitle(), n.getBody());
        }
    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param title       FCM message title received.
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String t = TextUtils.isEmpty(title) ? getApplicationContext().getString(R.string.app_name) : title;
        int color = ContextCompat.getColor(getApplicationContext(), R.color.primaryLight);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(color)
                .setContentTitle(t)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId++, notificationBuilder.build());
    }


}
