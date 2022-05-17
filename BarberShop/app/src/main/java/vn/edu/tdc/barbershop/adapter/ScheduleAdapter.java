package vn.edu.tdc.barbershop.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.firebase.database.core.view.View;

import java.util.List;

import vn.edu.tdc.barbershop.ManamentServiceDetailActivity;
import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.models.Schedule;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.OderViewHolder>{

    private Context mContext;
    private List<Schedule> mListSchedule;

    public ScheduleAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Schedule> list){
        this.mListSchedule = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manament_schedule, parent, false);
        return new OderViewHolder(view);
    }
//*
    @Override
    public void onBindViewHolder(@NonNull OderViewHolder holder, int position) {
        final Schedule schedule = mListSchedule.get(position);
        if (schedule == null){
            return;
        }

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCLickGoToDetail(schedule);
            }
        });

//        Thêm những trường khác vào đây
//        holder.txtId.setText(oder.getId);
//        holder.imgService.setImageResource(oder.getService());
//        holder.txtName.setText(oder.getName);
//        holder.txtPhone.setText(oder.get);
//        holder.txtTimeOder.getText(oder.getTimeOder());
//        holder.txtTimeFinish.getText(oder.getTimeOder());
        holder.txtTest.setText(schedule.getTest());
    }

//    Đi tới màn hình chi tiết lịch hẹn
    private void onCLickGoToDetail(Schedule schedule){
        Intent intent = new Intent(mContext, ManamentServiceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_oder", schedule);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (mListSchedule != null){
            return mListSchedule.size();
        }
        return 0;
    }
        private List<Schedule> schedules;

    public ScheduleAdapter(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public class OderViewHolder extends RecyclerView.ViewHolder {

        private CardView layoutItem;
//        Thêm những trường khác vào đây

//        private TextView txtId;
//        private CircleImageView imgService;
//        private TextView txtName;
//        private TextView txtPhone;
//        private TextView txtTimeOder;
//        private TextView txtTimeFinish;
        private TextView txtTest;
//*
    public OderViewHolder(@NonNull View itemView) {
        super(itemView);

        layoutItem = itemView.findViewById(R.id.layout_item);
//        Thêm những trường khác vào đây
//        txtId = itemView.findViewById(R.id.txtId);
//        imgService = itemView.findViewById(R.id.imgService);
//        txtName = itemView.findViewById(R.id.txtName);
//        txtPhone = itemView.findViewById(R.id.txtPhone);
//        txtTimeOder = itemView.findViewById(R.id.txtTimeOder);
//        txtTimeFinish = itemView.findViewById(R.id.txtTimeFinish);
        txtTest = itemView.findViewById(R.id.txtTest);
    }
    }
}
