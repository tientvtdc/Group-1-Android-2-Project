package vn.edu.tdc.barbershop.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.barbershop.R;
import vn.edu.tdc.barbershop.adapter.OrderAdapter;
import vn.edu.tdc.barbershop.entity.Order;

public class OrderFragment extends Fragment {


    // TODO: Rename and change types of parameters
    private RecyclerView rcvOrder;
    private OrderAdapter orderAdapter;
    private List<Order> orderArrayList = new ArrayList<Order>();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String idUser;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        rcvOrder = view.findViewById(R.id.fragment_rcv);
        rcvOrder.setLayoutManager(new LinearLayoutManager(getContext()));

        getOrderArrayList();

        orderAdapter = new OrderAdapter(orderArrayList, getContext());

        rcvOrder.setAdapter(orderAdapter);
        return view;
    }

    private void getOrderArrayList() {
        //get id user
        if (user != null) {
            idUser = user.getUid();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("orders");
            mDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Order order = snapshot.getValue(Order.class);
                    if (order != null) {
                        // kiem tra id user
                        if (idUser.equalsIgnoreCase(order.getCustomer().getId())) {
                            orderArrayList.add(order);
                            orderAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Order order = snapshot.getValue(Order.class);
                    if (order != null) {
						// kiem tra id user
                        if (idUser.equalsIgnoreCase(order.getCustomer().getId())) {
                            if (orderArrayList == null || orderArrayList.isEmpty()) return;

							for (int i = 0; i < orderArrayList.size(); i++) {
								if (orderArrayList.get(i).getId().equals(order.getId())) {
									orderArrayList.set(i, order);
								}
							}
							orderAdapter.notifyDataSetChanged();
                        }
                        
                    }
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
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (orderAdapter != null) {
            orderAdapter.release();
        }
    }
}