package vn.edu.tdc.barbershop;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import vn.edu.tdc.barbershop.adapter.OrderManagementAdapter;
import vn.edu.tdc.barbershop.apis.OrderAPIs;
import vn.edu.tdc.barbershop.entity.Order;

public class OrderManagementActivity extends AppCompatActivity {
    private ArrayList<Order> mOrder;
    private RecyclerView mRecyclerUser;
    private OrderManagementAdapter mOrderManagementAdapter;
    private MaterialToolbar btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);
        btnBack = (MaterialToolbar)  findViewById(R.id.order_back_button);
        mOrder = new ArrayList<Order>();
        mRecyclerUser = findViewById(R.id.rcv_order);
        mOrderManagementAdapter = new OrderManagementAdapter(this, mOrder);
        OrderAPIs.getAllOrders(mOrderManagementAdapter, this.mOrder, this);
        mRecyclerUser.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerUser.setAdapter(mOrderManagementAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}