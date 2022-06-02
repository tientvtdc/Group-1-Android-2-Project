package vn.edu.tdc.barbershop.apis;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.barbershop.ServiceListActivity;
import vn.edu.tdc.barbershop.adapter.UserAdapter;
import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.entity.User;

public class UserAPIS {
    // Fields


    public static void getAllUsers(UserAdapter mUserAdapter, ArrayList<User> mUser, Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mUser.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    mUser.add(user);
                    // here you can access to name property like university.name
                }
                mUserAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }
}
