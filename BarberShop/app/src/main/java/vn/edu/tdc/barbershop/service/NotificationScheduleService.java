package vn.edu.tdc.barbershop.service;

import android.app.AlarmManager;
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
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.ScheduleDetailsActivity;
import vn.edu.tdc.barbershop.application.NotificationApplication;
import vn.edu.tdc.barbershop.entity.Schedule;

public class NotificationScheduleService extends JobService {

    private boolean jobCancelled;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String idUser = "VGXCM73STFRxN1uxNBz6tJmy8s02";
    private long timeNotification = (20 * 60 * 1000);
    private List<String> listNotification = new ArrayList<String>();

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
                int i = 0;
                while (true) {
                    if (jobCancelled) return;
                    Log.d("TAG", "run: " + i++);
                    Log.d("TAG", "run: " + listNotification.toString());

                    //DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("time");
                    //dataRef.setValue(i);

                    //get id user client
//                    if (user != null) {
//                        idUser = user.getUid();
//                    }
//                    else {
//                        return;
//                    }

                    //get schedule firebase
                    databaseReference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Schedule schedule = snapshot.getValue(Schedule.class);
                            if (schedule != null) {
                                //customer = user id
                                if (idUser.equalsIgnoreCase(schedule.getCustomer().getId())) {
                                    //check time notification < 20p
                                    if ((schedule.getTimeOrder() - Calendar.getInstance().getTimeInMillis()) < timeNotification) {
//                                        if (!listNotification.contains(schedule.getId())) {
//                                            sendNotification(schedule, getBaseContext());
//                                            listNotification.add(schedule.getId());
//                                        }
                                        sendNotification(schedule, getBaseContext());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // delay
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Log.d("TAG", "run: finish");
                //jobFinished(jobParameters, false);
            }
        }).start();
    }

    //TODO: notification
    private void sendNotification(Schedule schedule, Context mContext) {

        Intent intent = new Intent(mContext, ScheduleDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("schedule", schedule);
        intent.putExtras(bundle);

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
                                .setPriority(NotificationCompat.PRIORITY_MAX) // set do uu tien <- 8.0
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
