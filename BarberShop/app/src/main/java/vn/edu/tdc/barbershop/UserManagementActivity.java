package vn.edu.tdc.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vn.edu.tdc.barbershop.adapter.ManageServiceAdapter;
import vn.edu.tdc.barbershop.adapter.UserAdapter;
import vn.edu.tdc.barbershop.apis.UserAPIS;
import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.entity.User;
import vn.edu.tdc.barbershop.models.ServiceModel;

public class UserManagementActivity extends AppCompatActivity {
    private ArrayList<User> mUser;
    private RecyclerView mRecyclerUser;
    private UserAdapter mUserAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_list);

        mUser = new ArrayList<User>();
        mRecyclerUser = findViewById(R.id.rcv_user);
        mUserAdapter = new UserAdapter(this, mUser);
        UserAPIS.getAllUsers(this.mUserAdapter, this.mUser, this);
        mRecyclerUser.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerUser.setAdapter(mUserAdapter);
    }

//    private void onClickGoToDetail(User user) {
//        Intent intent = new Intent(this, DetailServiceActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("object_service", service);
//        intent.putExtras(bundle);
//        this.startActivity(intent);
//    }
}