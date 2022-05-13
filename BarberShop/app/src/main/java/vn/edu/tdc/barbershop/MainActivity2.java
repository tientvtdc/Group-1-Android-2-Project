package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    package vn.edu.tdc.barbershop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tdc.barbershop.adapter.UserAdapter;
import vn.edu.tdc.barbershop.models.User;

    public class AdminActivity {

        //   Này là 1 cái item trong recy
        private RecyclerView rcvUser;
        private UserAdapter userAdapter;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin);

            rcvUser = findViewById(R.id.rcv_user);
            userAdapter = new UserAdapter(this);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            rcvUser.setLayoutManager(linearLayoutManager);
//        Dữ liệu
            userAdapter.setData(getListUser());
            rcvUser.setAdapter(userAdapter);

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

}