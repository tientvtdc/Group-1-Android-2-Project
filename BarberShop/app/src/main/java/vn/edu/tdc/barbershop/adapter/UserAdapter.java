package vn.edu.tdc.barbershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.models.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private Context mContext;
//    List dùng để lưu những user
    private List<User> mListUser;

    public UserAdapter(Context mContext) {
        this.mContext = mContext;
    }

    //Sử lý danh sách
    public void setData(List<User> list) {
        this.mListUser = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Cái view này bằng với 1 cái layout tạo bên item_user(item_oder)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oder, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        //hàm xét dữ liệu lên
        User user = mListUser.get(position);
        if (user == null) {
            return;
        }

        //thêm những trường khác vào đây 5
        holder.imgUser.setImageResource(user.getResourceId());
        holder.tvName.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        //Nếu mlistUser khác null thì rerurn
        if (mListUser != null){
            return mListUser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
//        Thêm những trường còn lại vào đây 3
        private ImageView imgUser;
        private TextView tvName;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

////            Thêm những trường khác vào đây 4
//            imgUser = itemView.findViewById(R.id.img_user);
//            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
