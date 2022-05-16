package vn.edu.tdc.barbershop.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.entity.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> mUserList;

    public UserAdapter(List<User> mUserList) {
        this.mUserList = mUserList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_management_recyclerview_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mUserList.get(position);
        if (user == null) return;
        holder.txtName.setText(user.getName() + "");
        holder.txtPhone.setText(user.getPhone() + "");
        holder.imgItem.setImageResource(R.drawable.google);
    }

    @Override
    public int getItemCount() {
        if (mUserList != null) return mUserList.size();
        return 0;
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName, txtPhone;
        private ImageView imgItem;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.tv_user_name);
            txtPhone = itemView.findViewById(R.id.tv_phone_number);
            imgItem = itemView.findViewById(R.id.img_user_img);
        }
    }
}
