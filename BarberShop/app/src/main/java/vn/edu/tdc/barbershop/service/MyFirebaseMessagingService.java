package vn.edu.tdc.barbershop.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.application.NotificationApplication;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        RemoteMessage.Notification notification = message.getNotification();
        if (notification == null) return;

        String title = notification.getTitle();
        String body = notification.getBody();

        //Log.d("test", "ok");
        sendNotification(title, body);
    }

    private void sendNotification(String title, String body) {
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this, NotificationApplication.CHANNEL_ID_SCHEDULE)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_account_box);

        Notification notification = noBuilder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }
}
