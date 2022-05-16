package vn.edu.tdc.barbershop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.barbershop.adapter.ManageServiceAdapter;
import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.models.ServiceModel;

public class ServiceListActivity extends AppCompatActivity {

    private RecyclerView rcvData;
    private ManageServiceAdapter manageServiceAdapter;
    private List<Service> mListServices;
    private FloatingActionButton floating_action_button;
    public static final int DELETE_SERVICE = 0;
    private MaterialToolbar service_list_bar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        service_list_bar = findViewById(R.id.service_list_bar);
        // Hỗ trợ gán menu cho toolbar
        setSupportActionBar(service_list_bar);
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
        floating_action_button = findViewById(R.id.floating_action_button);
        floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceListActivity.this, AddNewServiceActivity.class);
                ServiceListActivity.this.startActivity(intent);
            }
        });

    }

    private List<Service> getListUsersFromReatimeDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("services");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Service service = snapshot.getValue(Service.class);
                if (service != null) {
                    mListServices.add(service);
                    manageServiceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Service service = snapshot.getValue(Service.class);
                if (service != null) {
                    // Thêm service vào mảng
                    mListServices.add(service);
                    // Tìm sv trong mList sau
                    for (Service sv :
                            mListServices) {
                        if (service.getID().equals(sv.getID())) {
                            mListServices.add(sv);
                        }
                    }
                    manageServiceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Service service = snapshot.getValue(Service.class);
                if (service != null) {
                    mListServices.remove(service);
                    for (Service sv :
                            mListServices) {
                        if (service.getID().equals(sv.getID())) {
                            mListServices.remove(sv);
                        }
                    }
                    manageServiceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return mListServices;
    }

    private void onClickGoToDetail(Service service) {
        Intent intent = new Intent(this, ManageDetailServiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_service", service);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        service_list_bar.inflateMenu(R.menu.search_menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                manageServiceAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                manageServiceAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}