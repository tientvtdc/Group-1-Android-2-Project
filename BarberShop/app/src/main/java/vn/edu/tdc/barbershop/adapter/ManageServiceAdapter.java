package vn.edu.tdc.barbershop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.models.ServiceModel;

public class ManageServiceAdapter extends RecyclerView.Adapter<ManageServiceAdapter.ManageServiceHolder> implements Filterable {
    private List<Service> mListService;
    private List<Service> mListServiceOld;
    private ServiceModel.IClickItemListener iClickItemListener;

    public ManageServiceAdapter(List<Service> mListService, ServiceModel.IClickItemListener iClickItemListener) {
        this.mListService = mListService;
        this.mListServiceOld = mListService;
        this.iClickItemListener = iClickItemListener;
    }

    @NonNull
    @Override
    public ManageServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_service, parent, false);
        return new ManageServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageServiceHolder holder, int position) {
        Service service = mListService.get(position);
        if (service == null) {
            return;
        }
        Picasso.with(holder.itemView.getContext()).load(service.getImage()).into(holder.imgAvtar);
        holder.name.setText(service.getName());
        holder.price.setText(service.getPrice() + "");
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemListener.onClickItemService(service);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListService != null) {
            return mListService.size();
        }
        return 0;
    }

    public class ManageServiceHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvtar;
        private TextView name, price;
        private RelativeLayout layoutItem;

        public ManageServiceHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item);
            imgAvtar = itemView.findViewById(R.id.img_avatar);
            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_price);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    mListService = mListServiceOld;
                } else {
                    List<Service> list = new ArrayList<>();
                    for (Service service : mListServiceOld) {
                        if (service.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(service);
                        }
                    }
                    mListService = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListService;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListService = (List<Service>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
