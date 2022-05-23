package vn.edu.tdc.barbershop.adapter;

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

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<Schedule> schedules;
    private Context mContext;

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
        if (schedule == null) {
            return;
        }

        Glide.with(mContext).load(schedule.getService().getImage()).error(R.drawable.anh1).placeholder(new ColorDrawable(Color.BLACK)).into(holder.imgSchedule);
        holder.txtName.setText(schedule.getService().getName());

        //format time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        holder.txtTime.setText(simpleDateFormat.format(schedule.getTimeOrder()));

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        holder.txtPrice.setText(currencyVN.format(schedule.getService().getPrice()));

        switch (schedule.getIsFinish()) {
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

        //test notification
        sendNotification(intent, schedule);

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

    //TODO: notification
    private void sendNotification(Intent intent, Schedule schedule) {
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
