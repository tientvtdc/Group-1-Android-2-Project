package vn.edu.tdc.barbershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private List<User> mListUsers;

    private IClickListener mIClickListener;

    public interface IClickListener{
        void onClickUpdateItem(User user);
        void onClickDeleteItem(User user);
    }

    public UserAdapter(List<User> mListUsers, IClickListener listener) {
        this.mListUsers = mListUsers;
        this.mIClickListener = listener;
    }

    //Sử lý danh sách
    public void setData(List<User> list) {
        this.mListUsers = list;
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
        User user = mListUsers.get(position);
        if (user == null) {
            return;
        }

        //thêm những trường khác vào đây 5
//        holder.txtId.setText(user.getId());
//        holder
//        holder.imgUser.setImageResource(user.getResourceId());
//        holder.txtName.setText(user.getName());


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.onClickDeleteItem(user);
            }
        });


    }

    @Override
    public int getItemCount() {
        //Nếu mlistUser khác null thì rerurn
        if (mListUsers != null){
            return mListUsers.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
//        Thêm những trường còn lại vào đây 3
        private TextView Id;

        private ImageView imgUser;
        private TextView txtName;
        private TextView txtPhone;

//        private Button btnUpdate;
        private  Button btnDelete;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

////            Thêm những trường khác vào đây 4
            //txtId = itemView.findViewById(R.id.txtId);
            //imgUser = itemView.findViewById(R.id.img_user);
            txtName = itemView.findViewById(R.id.txtName);
            //txtPhone = itemView.findViewById(R.id.txtPhone);
            //btnUpdate = itemView.findViewById(R.id.btn_Update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
