package vn.edu.tdc.barbershop.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Calendar;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.application.NotificationApplication;
import vn.edu.tdc.barbershop.entity.Schedule;

public class NotificationScheduleService extends JobService {

    private boolean jobCancelled;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("TAG", "onStartJob: start");
        notificationSchedule(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("TAG", "onStopJob: cancel");
        jobCancelled = true;
        return true;
    }

    //notification schedule
    private void notificationSchedule(JobParameters jobParameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    if (jobCancelled) return;
                    Log.d("TAG", "run: " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("TAG", "run: finish");
                jobFinished(jobParameters, false);
            }
        }).start();
    }

    //TODO: notification
    private void sendNotification(Intent intent, Schedule schedule, Context mContext) {
        final Bitmap[] bitmap = {null};
        Glide.with(mContext)
                .asBitmap()
                .load(schedule.getService().getImage())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        bitmap[0] = resource;

                        //Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.anh1);
                        Uri uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //set sound default

                        // start activity
                        //Intent intent = new Intent(mContext, ScheduleDetailsActivity.class);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        PendingIntent pendingIntent = stackBuilder.getPendingIntent(getNotificationId(), PendingIntent.FLAG_UPDATE_CURRENT);

                        //Notification
                        Notification notification = new NotificationCompat.Builder(mContext, NotificationApplication.CHANNEL_ID_SCHEDULE)
                                .setContentTitle(schedule.getService().getName()) //title
                                .setContentText(schedule.getService().getDescription()) //content
                                .setSmallIcon(R.drawable.ic_account_box) //show anh nho ben canh content
                                .setLargeIcon(bitmap[0]) //icon
                                .setSound(uriSound) //set sound default
                                .setContentIntent(pendingIntent) // start activity
                                .setAutoCancel(true) //auto clear notification click
                                //.setPriority(NotificationCompat.PRIORITY_MAX) // set do uu tien <- 8.0
                                //.setColor(getResources().getColor(R.color.header_dialog)) //set color icon
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(schedule.getService().getDescription())) //show full content
                                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap[0]).bigLargeIcon(null)) //show full image
                                .build();

                        //send notification
                        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                        if (notificationManager != null) {
                            notificationManager.notify(getNotificationId(), notification);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    private int getNotificationId() {
        return (int) Calendar.getInstance().getTimeInMillis();
    }
}
