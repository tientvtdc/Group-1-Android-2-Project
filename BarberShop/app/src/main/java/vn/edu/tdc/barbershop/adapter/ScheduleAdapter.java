package vn.edu.tdc.barbershop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.tdc.barbershop.CusomerScreenActivity;
import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.ScheduleDetailsActivity;
import vn.edu.tdc.barbershop.entity.Schedule;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>{

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
        holder.txtTime.setText(String.valueOf(schedule.getTimeOrder().getHours()));
        holder.txtPrice.setText(String.valueOf(schedule.getService().getPrice()));

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
        mContext.startActivity(intent);
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

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSchedule = itemView.findViewById(R.id.imgSchedule);
            txtName = itemView.findViewById(R.id.txtName);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtPrice = itemView.findViewById(R.id.txtPrice);

            cardViewItem = itemView.findViewById(R.id.layout_item_schedule);
        }
    }
}
