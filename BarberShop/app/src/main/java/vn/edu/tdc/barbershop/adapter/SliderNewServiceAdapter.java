package vn.edu.tdc.barbershop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.interface_listener.IClickItemServiceListener;

public class SliderNewServiceAdapter extends  SliderViewAdapter<SliderNewServiceAdapter.SliderNewServiceAdapterVH> {
    private Context context;
    private List<Service> serviceList = new ArrayList<>();
    private IClickItemServiceListener clickItemServiceListener;
    @Override
    public SliderNewServiceAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return  new SliderNewServiceAdapterVH(inflate);
    }
    public SliderNewServiceAdapter(Context context,List<Service> serviceList ) {
        this.serviceList = serviceList;
        this.context = context;
    }

    public void setIClickItemServiceListener(vn.edu.tdc.barbershop.interface_listener.IClickItemServiceListener IClickItemServiceListener) {
        this.clickItemServiceListener = IClickItemServiceListener;
    }

    @Override
    public void onBindViewHolder(SliderNewServiceAdapterVH viewHolder, int position) {
        Service service = serviceList.get(position);
        viewHolder.textViewDescription.setText(service.getName());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView)
                .load(service.getImage())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemServiceListener.onClickItem(service);
            }
        });
    }

    @Override
    public int getCount() {
        return serviceList.size();
    }

    class SliderNewServiceAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderNewServiceAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
//            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
