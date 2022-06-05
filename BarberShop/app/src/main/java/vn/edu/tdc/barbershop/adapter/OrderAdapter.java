package vn.edu.tdc.barbershop.adapter;

import android.content.Context;
import android.content.Intent;
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
        Order user = mOrder.get(position);

        holder.

        Glide.with(mContext).load(user.getImage()).into(holder.imgUser);
        holder.tvUserName.setText(user.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_user", user);
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
        private ImageView imgUser;
        private TextView tvUserName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
            tvUserName= itemView.findViewById(R.id.tv_username);
        }
    }
}
