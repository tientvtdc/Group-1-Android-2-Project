package vn.edu.tdc.barbershop.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

import vn.edu.tdc.barbershop.entity.Schedule;

public class AlarmScheduleReceiver extends BroadcastReceiver {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String idUser = "VGXCM73STFRxN1uxNBz6tJmy8s02";
    private long timeNotification = (20 * 60 * 1000);
    private List<String> listNotification = new ArrayList<String>();

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG", "onReceive: ok");

        sendNotification(context);
    }

    private void sendNotification(Context context) {
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("time");
        dataRef.setValue(Calendar.getInstance().getTimeInMillis());

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
                            if (!listNotification.contains(schedule.getId())) {
                                Log.d("notifi", "notification: ok");
                                Log.d("notifi", "schedule.getTimeOrder(): " + schedule.getTimeOrder());
                                Log.d("notifi", "Calendar.getInstance().getTimeInMillis(): " + Calendar.getInstance().getTimeInMillis());
                                Log.d("notifi", "---: " + (schedule.getTimeOrder() - Calendar.getInstance().getTimeInMillis()));
                                Log.d("notifi", "notification: " + timeNotification);
                                listNotification.add(schedule.getId());
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

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(context, AlarmScheduleReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(
                AlarmManager.RTC_WAKEUP
                , (Calendar.getInstance().getTimeInMillis())
                , pendingIntent
        );
    }
}
