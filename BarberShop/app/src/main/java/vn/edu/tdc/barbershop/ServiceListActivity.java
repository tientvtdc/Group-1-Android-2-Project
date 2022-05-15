package vn.edu.tdc.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.barbershop.adapter.ManageServiceAdapter;
import vn.edu.tdc.barbershop.adapter.ServiceAdpapter;
import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.models.ServiceModel;

public class ServiceListActivity extends AppCompatActivity {

    private RecyclerView rcvData;
    private ManageServiceAdapter manageServiceAdapter;
    private List<Service> mListServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        rcvData = findViewById(R.id.rcv_list_service);
        mListServices = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvData.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvData.addItemDecoration(itemDecoration);
        manageServiceAdapter = new ManageServiceAdapter(getListUsersFromReatimeDatabase(), new ServiceModel.IClickItemListener() {
            @Override
            public void onClickItemService(Service service) {
                onClickGoToDetail(service);
            }
        });
        rcvData.setAdapter(manageServiceAdapter);
    }

    private List<Service> getListUsersFromReatimeDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("services");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Service service = dataSnapshot.getValue(Service.class);
                    if (service != null) {
                        mListServices.add(service);
                    }

                }
                manageServiceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ServiceListActivity.this, "Get list service failed", Toast.LENGTH_SHORT).show();
            }
        });
        return mListServices;
    }

    private void onClickGoToDetail(Service service) {
        Intent intent = new Intent(this, DetailServiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_service", service);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }
}