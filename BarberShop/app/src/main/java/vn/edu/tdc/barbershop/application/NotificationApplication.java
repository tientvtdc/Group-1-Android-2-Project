package vn.edu.tdc.barbershop.application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import vn.edu.tdc.barbershop.R;

public class NotificationApplication extends Application {
    public static final String CHANNEL_ID_SCHEDULE = "CHANNEL_SCHEDULE";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            CharSequence name = getString(R.string.channel_name_schedule);
            String description = getString(R.string.channel_description_schedule);
            int importance = NotificationManager.IMPORTANCE_HIGH; //set do uu tien 8.0 ->
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_SCHEDULE, name, importance);
            channel.setDescription(description);
            channel.setSound(uri, attributes);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
