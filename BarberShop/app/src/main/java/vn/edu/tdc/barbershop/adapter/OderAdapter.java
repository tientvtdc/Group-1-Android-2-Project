package vn.edu.tdc.barbershop.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.core.view.View;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.models.Oder;
import vn.edu.tdc.barbershop.models.User;

public class OderAdapter{

//    private List<Oder> oders;
//
//    public OderAdapter(List<Oder> oders) {
//        this.oders = oders;
//    }
//
//    @NonNull
//    @Override
//    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oder, parent, false);
//////        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oder, parent, false);
////        return new OderViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
//        Oder oder = oders.get(position);
//        if (oder == null) {
//            return;
//        }
//
////        holder.imgSchedule.setImageResource(oder.getImage());
////        holder.txtName.setText(oder.getName());
////        holder.txtTime.setText(oder.getTime());
////        holder.txtPrice.setText(oder.getPrice());
//    }
//
//    @Override
//    public int getItemCount() {
//        if (oders != null) {
//            return oders.size();
//        }
//        return 0;
//    }
//
//    public class OderViewHolder extends RecyclerView.ViewHolder {
//
//        private CircleImageView imgSchedule;
//        private TextView txtName;
//        private TextView txtTime;
//        private TextView txtPrice;
//
////        public OderViewHolder(@NonNull View view) {
////            super(view);
////
////            imgSchedule = itemView.findViewById(R.id.imgSchedule);
////            txtName = itemView.findViewById(R.id.txtName);
////            txtTime = itemView.findViewById(R.id.txtTime);
////            //txtPrice = itemView.findViewById(R.id.txtPrice);
////
////        }
//    }
}
