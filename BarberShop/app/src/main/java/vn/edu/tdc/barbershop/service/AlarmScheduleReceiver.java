package vn.edu.tdc.barbershop.service;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import vn.edu.tdc.barbershop.entity.Schedule;

public class AlarmScheduleReceiver extends BroadcastReceiver {

    private static final int JOB_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG", "onReceive: ok");

        Bundle bundle = intent.getExtras();
        if (bundle == null) return;
        Schedule schedule = (Schedule) bundle.get("schedule");
        Log.d("TAG", "onReceive: get extra: " + schedule.getFinish());
//        ComponentName componentName = new ComponentName(context, NotificationScheduleService.class);
//        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, componentName)
//                .setPersisted(true)
//                .build();
//        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(context.JOB_SCHEDULER_SERVICE);
//        jobScheduler.schedule(jobInfo);
    }
}
