
package vn.edu.tdc.barbershop.adapter;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;

        import java.text.NumberFormat;
        import java.util.List;
        import java.util.Locale;

        import vn.edu.tdc.barbershop.R;
        import vn.edu.tdc.barbershop.entity.Service;
        import vn.edu.tdc.barbershop.interface_listener.IClickItemServiceListener;

public class FeaturedServiceAdapter extends RecyclerView.Adapter<FeaturedServiceAdapter.ServiceViewHolder>{
    private List<Service> serviceList;
    private Context context;
    private IClickItemServiceListener clickItemServiceListener;
    public void setIClickItemServiceListener(vn.edu.tdc.barbershop.interface_listener.IClickItemServiceListener IClickItemServiceListener) {
        this.clickItemServiceListener = IClickItemServiceListener;
    }
    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_featured_service,parent,false);
        context = parent.getContext();
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
        if (service!=null){
            Glide.with(context).load(service.getImage()).error(R.drawable.anh1).into(holder.imgService);
            holder.nameService.setText(service.getName());
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            holder.priceService.setText(currencyVN.format(service.getPrice()));
            holder.cardView.setOnClickListener(view -> {
                clickItemServiceListener.onClickItem(service);
            });
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
        private CardView cardView;
        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_feadtured_service);
            imgService = itemView.findViewById(R.id.img_service);
            nameService = itemView.findViewById(R.id.name_service);
            priceService  = itemView.findViewById(R.id.price_service);
        }
    }
}

