package vn.edu.tdc.barbershop.adapter;

<<<<<<< HEAD
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
=======
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
>>>>>>> merge_tuan_and_phu
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
=======
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

>>>>>>> merge_tuan_and_phu

import java.text.SimpleDateFormat;
import java.util.ArrayList;

<<<<<<< HEAD
import vn.edu.tdc.barbershop.OrderDetailManagementActivity;
=======
import vn.edu.tdc.barbershop.OrderDetailActivity;
>>>>>>> merge_tuan_and_phu
import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.entity.Order;

public class OrderManagementAdapter extends RecyclerView.Adapter<OrderManagementAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Order> mOrder;
    public OrderManagementAdapter(Context mContext, ArrayList<Order> mOrder){
        this.mContext = mContext;
        this.mOrder = mOrder;
    }

    @NonNull
    @Override
<<<<<<< HEAD
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View orderView = inflater.inflate(R.layout.layout_order_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(orderView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = mOrder.get(position);
=======
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        if (order == null) return;
>>>>>>> merge_tuan_and_phu

        Glide.with(mContext).load(order.getService().getImage()).into(holder.imgService);
        holder.tvUserPhoneNumber.setText(order.getCustomer().getPhone());
        holder.tvServiceName.setText(order.getService().getName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
<<<<<<< HEAD
        holder.tvOrderDate.setText(simpleDateFormat.format(order.getTimeOrder()));


        switch (order.getFinish()){
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
                Intent intent = new Intent(mContext, OrderDetailManagementActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_order", order);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
=======
        holder.tvOrderDate.setText(simpleDateFormat.format(order.getTimeOrder().getTime()));


        switch (order.getIsFinish()){
            case 0:
                holder.tvOrderState.setText("Chưa Hoàn Thành");
                break;

        //format time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        holder.txtTime.setText(simpleDateFormat.format(order.getCalendarOrder().getTime()));

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        holder.txtPrice.setText(currencyVN.format(order.getService().getPrice()));

        holder.cardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLayoutItem(order);
>>>>>>> merge_tuan_and_phu
            }
        });
    }

<<<<<<< HEAD
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
=======
    private void onClickLayoutItem(Order order) {
        Intent intent = new Intent(mContext, OrderDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", order);
        intent.putExtras(bundle);

        mContext.startActivity(intent);
    }

    public void release() {
        mContext = null;
    }

    @Override
    public int getItemCount() {
        if (orders != null) {
            return orders.size();
        }
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private CardView cardViewItem;
        private CircleImageView imgOrder;

        private TextView txtName;
        private TextView txtTime;
        private TextView txtPrice;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            imgOrder = itemView.findViewById(R.id.imgOrder);
            txtName = itemView.findViewById(R.id.txtName);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtPrice = itemView.findViewById(R.id.txtPrice);

            cardViewItem = (CardView) itemView.findViewById(R.id.layout_item_order);
>>>>>>> merge_tuan_and_phu
        }
    }
}
