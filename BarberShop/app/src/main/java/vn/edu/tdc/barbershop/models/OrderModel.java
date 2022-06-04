package vn.edu.tdc.barbershop.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.edu.tdc.barbershop.entity.Order;

public class OrderModel {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final static String NAME_TABLE = "orders";

    public void addOrder(Order order, IOrderListennerModel listennerModel) {
        DatabaseReference databaseReference = database.getReference(NAME_TABLE);
        String id = databaseReference.push().getKey();
        order.setId(id);

        databaseReference.child(order.getId()).setValue(order, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                listennerModel.onCompleteRegisterUser(error);
            }
        });
    }


    //thong bao
    public interface IOrderListennerModel {
        void onCompleteRegisterUser(DatabaseError error);
    };
}
