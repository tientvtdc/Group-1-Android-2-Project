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

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.tdc.barbershop.ManamentServiceDetailActivity;
import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.models.Oder;
import vn.edu.tdc.barbershop.models.User;

public class OderAdapter extends RecyclerView.Adapter<OderAdapter.OderViewHolder>{

    private Context mContext;
    private List<Oder> mListOder;

    public OderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Oder> list){
        this.mListOder = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oder, parent, false);
        return new OderViewHolder(view);
    }
//*
    @Override
    public void onBindViewHolder(@NonNull OderViewHolder holder, int position) {
        final Oder oder = mListOder.get(position);
        if (oder == null){
            return;
        }

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCLickGoToDetail(oder);
            }
        });

//        Thêm những trường khác vào đây
//        holder.txtId.setText(oder.getId);
//        holder.imgService.setImageResource(oder.getService());
//        holder.txtName.setText(oder.getName);
//        holder.txtPhone.setText(oder.get);
//        holder.txtTimeOder.getText(oder.getTimeOder());
//        holder.txtTimeFinish.getText(oder.getTimeOder());
        holder.txtTest.setText(oder.getTest());
    }

//    Đi tới màn hình chi tiết lịch hẹn
    private void onCLickGoToDetail(Oder oder){
        Intent intent = new Intent(mContext, ManamentServiceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_oder", oder);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (mListOder != null){
            return mListOder.size();
        }
        return 0;
    }
        private List<Oder> oders;

    public OderAdapter(List<Oder> oders) {
        this.oders = oders;
    }

    public class OderViewHolder extends RecyclerView.ViewHolder {

        private CardView layoutItem;
//        Thêm những trường khác vào đây
//        private CircleImageView imgService;
//        private TextView txtName;
//        private TextView txtPhone;
//        private TextView txtTimeOder;
//        private TextView txtDen;
//        private TextView txtTimeFinish;
        private TextView txtTest;
//*
    public OderViewHolder(@NonNull View itemView) {
        super(itemView);

        layoutItem = itemView.findViewById(R.id.layout_item);
//        Thêm những trường khác vào đây
//        imgService = itemView.findViewById(R.id.imgService);
//        txtName = itemView.findViewById(R.id.txtName);
//        txtPhone = itemView.findViewById(R.id.txtPhone);
//        txtTimeOder = itemView.findViewById(R.id.txtTimeOder);
//        txtDen = itemView.findViewById(R.id.txtDen);
//        txtTimeFinish = itemView.findViewById(R.id.txtTimeFinish);
        txtTest = itemView.findViewById(R.id.txtTest);
    }
    }
}
