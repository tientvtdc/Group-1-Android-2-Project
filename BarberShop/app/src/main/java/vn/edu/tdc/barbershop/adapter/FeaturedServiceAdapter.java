
package vn.edu.tdc.barbershop.adapter;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import java.text.NumberFormat;
        import java.util.List;
        import java.util.Locale;

        import vn.edu.tdc.barbershop.R;
        import vn.edu.tdc.barbershop.entity.Service;

public class FeaturedServiceAdapter extends RecyclerView.Adapter<FeaturedServiceAdapter.ServiceViewHolder>{
    private List<Service> serviceList;

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_featured_service,parent,false);

        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
        if (service!=null){
            holder.imgService.setImageResource(R.drawable.anh1);
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

