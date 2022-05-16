package vn.edu.tdc.barbershop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.models.ServiceModel;

public class ServiceAdpapter extends RecyclerView.Adapter<ServiceAdpapter.ServiceViewHolder>{
    private List<Service> serviceList;
    private Context context;

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service,parent,false);
        this.context = parent.getContext();
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
        if (service!=null){
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("imgService");
            Glide.with(context).load(service.getImage()).error(R.drawable.anh1).placeholder(new ColorDrawable(Color.BLACK)).into(holder.imgService);
            holder.nameService.setText(service.getName());

            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            holder.priceService.setText(currencyVN.format(service.getPrice()));
        }
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgService;
        private TextView nameService;
        private TextView priceService;
        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imgService = itemView.findViewById(R.id.img_service);
            nameService = itemView.findViewById(R.id.name_service);
            priceService  = itemView.findViewById(R.id.price_service);
        }
    }
}

