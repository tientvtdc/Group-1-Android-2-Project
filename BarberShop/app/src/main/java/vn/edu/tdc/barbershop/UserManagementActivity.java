package vn.edu.tdc.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.barbershop.adapter.UserAdapter;
import vn.edu.tdc.barbershop.entity.User;

public class UserManagementActivity extends AppCompatActivity{
    private RecyclerView rcvUser;
    private List<User> mUserList;
    private UserAdapter userAdapter;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_management);

        rcvUser = findViewById(R.id.rcv_user);

        mUserList = new ArrayList<>();
        User sv1 = new User(1, "Tuan", "12344","213123", "C:\\Users\\Tuan\\Documents\\Android\\project\\BarberShop\\app\\src\\main\\res\\drawable-v24", 1);
        User sv2 = new User(1, "Tien", "12344","213123", "C:\\Users\\Tuan\\Documents\\Android\\project\\BarberShop\\app\\src\\main\\res\\drawable-v24", 1);
        mUserList.add(sv1);
        mUserList.add(sv2);
        userAdapter = new UserAdapter(mUserList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUser.setLayoutManager(linearLayoutManager);
        rcvUser.setAdapter(userAdapter);
    }
}