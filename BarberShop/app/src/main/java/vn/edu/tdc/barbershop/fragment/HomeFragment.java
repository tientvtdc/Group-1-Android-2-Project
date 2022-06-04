package vn.edu.tdc.barbershop.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.ServiceDetailActivity;
import vn.edu.tdc.barbershop.adapter.FeaturedServiceAdapter;
import vn.edu.tdc.barbershop.adapter.ServiceAdpapter;
import vn.edu.tdc.barbershop.adapter.SliderNewServiceAdapter;
import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.interface_listener.IClickItemServiceListener;

public class HomeFragment extends Fragment {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("services");

    private FeaturedServiceAdapter featuredServiceAdapter;
    private RecyclerView rcvFeaturedService;

    private RecyclerView rcvService;
    private ServiceAdpapter serviceAdpapter;

    private List<Service> serviceNewList;
    private SliderNewServiceAdapter sliderNewServiceAdapter;

    private List<Service> serviceCheapList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        List<Service> services = new ArrayList<Service>();

        rcvService = (RecyclerView) view.findViewById(R.id.service_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvService.setLayoutManager(linearLayoutManager);

        serviceAdpapter = new ServiceAdpapter(services, new IClickItemServiceListener() {
            @Override
            public void onClickItem(Service service) {
                onCLickGoDetaiService(service);
            }
        });

        serviceNewList = new ArrayList<>();
        SliderView sliderView = view.findViewById(R.id.imageSlider);
        sliderNewServiceAdapter = new SliderNewServiceAdapter(view.getContext(), serviceNewList);
        sliderView.setSliderAdapter(sliderNewServiceAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
        getNewServices();
        sliderNewServiceAdapter.setIClickItemServiceListener(new IClickItemServiceListener() {
            @Override
            public void onClickItem(Service service) {
                onCLickGoDetaiService(service);
            }
        });

        rcvService.setAdapter(serviceAdpapter);

        rcvFeaturedService = (RecyclerView) view.findViewById(R.id.featured_service_recycler_view);
        rcvFeaturedService.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        featuredServiceAdapter = new FeaturedServiceAdapter();
        serviceCheapList = new ArrayList<>();
        featuredServiceAdapter.setServiceList(serviceCheapList);
        rcvFeaturedService.setAdapter(featuredServiceAdapter);
        featuredServiceAdapter.setIClickItemServiceListener(new IClickItemServiceListener() {
            @Override
            public void onClickItem(Service service) {
                onCLickGoDetaiService(service);
            }
        });
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Service service = snapshot.getValue(Service.class);
                if (service != null) {
                    services.add(service);
                    serviceAdpapter.notifyDataSetChanged();
                    featuredServiceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Service service = snapshot.getValue(Service.class);
                if (service != null) {
                    services.remove(service);
                    serviceAdpapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mDatabase.orderByChild("price").limitToFirst(5).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Service service = snapshot.getValue(Service.class);
                if (service != null) {
                    serviceCheapList.add(service);
                    featuredServiceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void onCLickGoDetaiService(Service service) {
        Intent intent = new Intent(this.getActivity(), ServiceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("service", service);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getNewServices() {
        Query query = mDatabase.limitToFirst(5);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Service service = snapshot.getValue(Service.class);
                if (service != null) {
                    serviceNewList.add(service);
                    sliderNewServiceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Service service = snapshot.getValue(Service.class);
                if (service != null) {
                    serviceNewList.remove(service);
                    sliderNewServiceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
