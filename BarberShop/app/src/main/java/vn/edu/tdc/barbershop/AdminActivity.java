package vn.edu.tdc.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.app.ActionBar;


import com.google.firebase.database.annotations.Nullable;
import vn.edu.tdc.barbershop.adapter.UserAdapter;
import vn.edu.tdc.barbershop.models.User;
import android.view.View;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView rcvUser;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //narba
//        androix.appcompat.app.Actionbar actionbar = getSupportActionBar();
//
//        actionbar.setHomeButtonEnabled(true);
//        actionbar.setDisplayHomeAsUpEnabled(true);
//        actionbar.setTitle("Quản lý lịch hẹn");

        rcvUser = findViewById(R.id.rcv_user);
        userAdapter = new UserAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvUser.setLayoutManager(linearLayoutManager);
//        Dữ liệu
        userAdapter.setData(getListUser());
        rcvUser.setAdapter(userAdapter);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Dữ liệu giả
    private List<User> getListUser(){
        List<User> list = new ArrayList<>();

        list.add(new User(R.drawable.anh1, "Tên khách hàng 1"));
        list.add(new User(R.drawable.anh2, "Tên khách hàng 2"));

        return list;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_admin, container, false);
    }
}