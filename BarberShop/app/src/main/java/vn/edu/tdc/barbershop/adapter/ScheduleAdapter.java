package vn.edu.tdc.barbershop.adapter;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.tdc.barbershop.CusomerScreenActivity;
import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.ScheduleDetailsActivity;
import vn.edu.tdc.barbershop.application.NotificationApplication;
import vn.edu.tdc.barbershop.entity.Schedule;
import vn.edu.tdc.barbershop.service.AlarmScheduleReceiver;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<Schedule> schedules;
    private Context mContext;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public ScheduleAdapter(List<Schedule> schedules, Context context) {
        this.schedules = schedules;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule schedule = schedules.get(position);
        if (schedule == null) return;

        Glide.with(mContext).load(schedule.getService().getImage()).error(R.drawable.anh1).placeholder(new ColorDrawable(Color.BLACK)).into(holder.imgSchedule);
        holder.txtName.setText(schedule.getService().getName());

        //format time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        holder.txtTime.setText(simpleDateFormat.format(schedule.getCalendarOrder().getTime()));

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        holder.txtPrice.setText(currencyVN.format(schedule.getService().getPrice()));

        switch (schedule.getFinish()) {
            case 0:
                holder.txtState.setText("Đang đợi");
                holder.txtState.setTextColor(ContextCompat.getColor(mContext, R.color.comming));
                break;
            case 1:
                holder.txtState.setText("Đã xong");
                holder.txtState.setTextColor(ContextCompat.getColor(mContext, R.color.done));
                break;
            case 2:
                holder.txtState.setText("Đã Hủy");
                holder.txtState.setTextColor(ContextCompat.getColor(mContext, R.color.missed));
                break;
            default:
                break;
        }

        holder.cardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLayoutItem(schedule);
            }
        });
    }

    private void onClickLayoutItem(Schedule schedule) {
        Intent intent = new Intent(mContext, ScheduleDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("schedule", schedule);
        intent.putExtras(bundle);

        //register alarm schedule
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(mContext, AlarmScheduleReceiver.class);
        intent1.putExtras(bundle);

        pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent2 = new Intent();
        intent2.putExtras(bundle);
        try {
            pendingIntent.send(mContext, 0, intent2);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

        alarmManager.set(
                AlarmManager.RTC_WAKEUP
                ,(Calendar.getInstance().getTimeInMillis() + (1 * 60 * 1000))
                ,pendingIntent
                );

        mContext.startActivity(intent);
    }

    public void release() {
        mContext = null;
    }

    @Override
    public int getItemCount() {
        if (schedules != null) {
            return schedules.size();
        }
        return 0;
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {

        private CardView cardViewItem;
        private CircleImageView imgSchedule;

        private TextView txtName;
        private TextView txtTime;
        private TextView txtPrice;
        private TextView txtState;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSchedule = itemView.findViewById(R.id.imgSchedule);
            txtName = itemView.findViewById(R.id.txtName);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtState = itemView.findViewById(R.id.txtState);

            cardViewItem = itemView.findViewById(R.id.layout_item_schedule);
        }
    }
}
