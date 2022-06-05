package vn.edu.tdc.barbershop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import vn.edu.tdc.barbershop.OrderDetailActivity;
import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.UserDetailActivity;
import vn.edu.tdc.barbershop.entity.Order;
import vn.edu.tdc.barbershop.entity.User;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Order> mOrder;
    public OrderAdapter(Context mContext, ArrayList<Order> mOrder){
        this.mContext = mContext;
        this.mOrder = mOrder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View orderView = inflater.inflate(R.layout.layout_order_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(orderView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = mOrder.get(position);

        Glide.with(mContext).load(order.getService().getImage()).into(holder.imgService);
        holder.tvUserPhoneNumber.setText(order.getCustomer().getPhone());
        holder.tvServiceName.setText(order.getService().getName());
        holder.tvOrderDate.setText(order.getTimeOrder().toString());

        switch (order.getIsFinish()){
            case 0:
                holder.tvOrderState.setText("Chưa Hoàn Thành");
                break;
            case 1:
                holder.tvOrderState.setTextColor(Color.GREEN);
                holder.tvOrderState.setText("Hoàn Thành");
                break;
            case 2:
                holder.tvOrderState.setTextColor(Color.RED);
                holder.tvOrderState.setText("Đã Huỷ");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_order", order);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgService;
        private TextView tvUserPhoneNumber;
        private TextView tvServiceName;
        private TextView tvOrderDate;
        private TextView tvOrderState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgService = itemView.findViewById(R.id.img_service);
            this.tvUserPhoneNumber = itemView.findViewById(R.id.tv_phone_number);
            this.tvServiceName = itemView.findViewById(R.id.tv_service_name);
            this.tvOrderDate = itemView.findViewById(R.id.tv_order_time);
            this.tvOrderState = itemView.findViewById(R.id.tv_order_status);
        }
    }
}
