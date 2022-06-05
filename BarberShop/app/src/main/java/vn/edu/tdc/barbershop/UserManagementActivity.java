package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import vn.edu.tdc.barbershop.adapter.UserAdapter;
import vn.edu.tdc.barbershop.apis.UserAPIs;
import vn.edu.tdc.barbershop.entity.User;

public class UserManagementActivity extends AppCompatActivity {
    MaterialToolbar btnBack;
    private ArrayList<User> mUser;
    private RecyclerView mRecyclerUser;
    private UserAdapter mUserAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_list);

        btnBack = (MaterialToolbar) findViewById(R.id.btn_user_management_back);
        mUser = new ArrayList<User>();
        mRecyclerUser = findViewById(R.id.rcv_user);
        mUserAdapter = new UserAdapter(this, mUser);
        UserAPIs.getAllUsers(this.mUserAdapter, this.mUser, this);
        mRecyclerUser.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerUser.setAdapter(mUserAdapter);

        btnBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}