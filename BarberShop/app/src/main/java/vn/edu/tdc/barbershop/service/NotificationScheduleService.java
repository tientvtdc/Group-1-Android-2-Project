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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.tdc.barbershop.OrderDetailsActivity;
import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.application.NotificationApplication;
import vn.edu.tdc.barbershop.entity.Order;

public class NotificationScheduleService extends JobService {

    private boolean jobCancelled;
    private DatabaseReference dataRefOrders = FirebaseDatabase.getInstance().getReference("orders");
    private DatabaseReference dataRefNotifications = FirebaseDatabase.getInstance().getReference("notifications");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String idUser;
    private long timeNotification = (10 * 60 * 1000);
    private List<String> listNotification = new ArrayList<String>();

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("TAG", "onStartJob: start");
        //get list notification
        dataRefNotifications.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String notificationID = snapshot.getValue(String.class);
                if (notificationID != null) {
                    listNotification.add(notificationID);
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

        //notification
        notificationSchedule();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("TAG", "onStopJob: cancel");
        jobCancelled = true;
        return true;
    }

    private void notificationSchedule() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    if (jobCancelled) return;
                    Log.d("TAG", "run-background: " + i++);
                    //get id user client
                    if (user != null) {
                        idUser = user.getUid();
                    }
                    else {
                        return;
                    }
                    if (!listNotification.isEmpty()) {
                        //get schedule firebase
                        dataRefOrders.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                Order order = snapshot.getValue(Order.class);
                                if (order != null) {
                                    //customer = user id
                                    if (idUser.equalsIgnoreCase(order.getCustomer().getId())) {
                                        //check time notification < 10p
                                        if ((order.getTimeOrder() - Calendar.getInstance().getTimeInMillis()) < timeNotification) {
                                            if (!listNotification.contains(order.getId())) {
                                                sendNotification(order, getBaseContext());
                                                //loai tru thong bao
                                                DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("notifications");
                                                dataRef.child(order.getId()).setValue(order.getId());
                                            }
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
                    }

                    // delay
                    try {
                        Thread.sleep(3000);
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
    private void sendNotification(Order order, Context mContext) {

        Intent intent = new Intent(mContext, OrderDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", order);
        intent.putExtras(bundle);

        final Bitmap[] bitmap = {null};
        Glide.with(mContext)
                .asBitmap()
                .load(order.getService().getImage())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        bitmap[0] = resource;
                        //Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.anh1);
                        //Uri uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //set sound default

                        // start activity
                        //Intent intent = new Intent(mContext, ScheduleDetailsActivity.class);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
                        stackBuilder.addNextIntentWithParentStack(intent);
                        PendingIntent pendingIntent = stackBuilder.getPendingIntent(getNotificationId(), PendingIntent.FLAG_UPDATE_CURRENT);

                        //format time
                        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");

                        //Notification
                        Notification notification = new NotificationCompat.Builder(mContext, NotificationApplication.CHANNEL_ID_SCHEDULE)
                                .setContentTitle(order.getService().getName() + " vào lúc: " + sf.format(order.getCalendarOrder().getTime())) //title
                                .setContentText(order.getService().getDescription()) //content
                                .setSmallIcon(R.drawable.ic_account_box) //show anh nho ben canh content
                                .setLargeIcon(bitmap[0]) //icon
                                //.setSound(uriSound) //set sound default
                                .setContentIntent(pendingIntent) // start activity
                                .setAutoCancel(true) //auto clear notification click
                                .setPriority(NotificationCompat.PRIORITY_MAX) // set do uu tien <- 8.0
                                //.setColor(getResources().getColor(R.color.header_dialog)) //set color icon
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(order.getService().getDescription())) //show full content
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy: ok");
    }
}
