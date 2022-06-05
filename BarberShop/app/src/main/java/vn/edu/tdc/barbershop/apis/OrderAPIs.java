package vn.edu.tdc.barbershop.apis;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vn.edu.tdc.barbershop.adapter.OrderAdapter;
import vn.edu.tdc.barbershop.adapter.UserAdapter;
import vn.edu.tdc.barbershop.entity.Order;

public class OrderAPIs {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef =  database.getReference("orders");

    public static void getAllOrders(OrderAdapter mOrderAdapter, ArrayList<Order> mOrder, Context context) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mOrder.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Order other = postSnapshot.getValue(Order.class);
                    mOrder.add(other);
                    // here you can access to name property like university.name
                }
                mOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

    public static void updateOrderStatus(String id, int status){
        myRef.child(id).child("status").setValue(status) ;
    }
}
