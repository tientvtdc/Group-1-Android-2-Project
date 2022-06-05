package vn.edu.tdc.barbershop.apis;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import vn.edu.tdc.barbershop.adapter.OrderAdapter;
import vn.edu.tdc.barbershop.adapter.UserAdapter;
import vn.edu.tdc.barbershop.entity.Order;
import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.entity.User;

public class OrderAPIs {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef =  database.getReference("orders");

    public static void getAllOrders(OrderAdapter mOrderAdapter, ArrayList<Order> mOrder, Context context) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mOrder.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String id = postSnapshot.child("id").getValue(String.class);
                    Date timeOrder = new Date(postSnapshot.child("timeOrder").getValue(Long.class));
                    Date timeFinish = new Date(postSnapshot.child("timeFinish").getValue(Long.class));
                    int isFinish = postSnapshot.child("finish").getValue(Integer.class);
                    User user = postSnapshot.child("customer").getValue(User.class);
                    Service service = postSnapshot.child("service").getValue(Service.class);
                    Order other = new Order(id, user, service, timeOrder, timeFinish, isFinish);
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
        myRef.child(id).child("finish").setValue(status) ;
    }
}
