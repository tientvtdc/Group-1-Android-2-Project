package vn.edu.tdc.barbershop.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.adapter.FeaturedServiceAdapter;
import vn.edu.tdc.barbershop.adapter.ServiceAdpapter;
import vn.edu.tdc.barbershop.entity.Service;

public class HomeFragment extends Fragment {
    private FeaturedServiceAdapter featuredServiceAdapter;
    private RecyclerView rcvFeaturedService;

    private RecyclerView rcvService;
    private ServiceAdpapter serviceAdpapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        List<Service> services = new ArrayList<Service>();

        rcvService = (RecyclerView) view.findViewById(R.id.service_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvService.setLayoutManager(linearLayoutManager);

        serviceAdpapter = new ServiceAdpapter();

        serviceAdpapter.setServiceList(services);

        rcvService.setAdapter(serviceAdpapter);

        rcvFeaturedService = (RecyclerView) view.findViewById(R.id.featured_service_recycler_view);
        rcvFeaturedService.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        featuredServiceAdapter = new FeaturedServiceAdapter();
        featuredServiceAdapter.setServiceList(getServiceList());
        rcvFeaturedService.setAdapter(featuredServiceAdapter);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("services");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Service service = snapshot.getValue(Service.class);
                if (service != null) {
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference("imgService");

                    services.add(service);
                    serviceAdpapter.notifyDataSetChanged();
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

    private List<Service> getServiceList() {
        List<Service> list = new ArrayList<>();
//        list.add(new Service(R.drawable.anh1,"Massage cổ vai gáy bạc hà",50000));
//        list.add(new Service(R.drawable.anh1,"Massage cổ vai gáy bạc hà",50000));
//        list.add(new Service(R.drawable.anh1, "Massage cổ vai gáy bạc hà", 50000));
//        list.add(new Service(R.drawable.anh2,"Massage cổ vai gáy bạc hà",50000));
        return list;
    }
}
