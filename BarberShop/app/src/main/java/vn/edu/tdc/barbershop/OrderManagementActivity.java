package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import vn.edu.tdc.barbershop.adapter.OrderAdapter;
import vn.edu.tdc.barbershop.adapter.UserAdapter;
import vn.edu.tdc.barbershop.apis.OrderAPIs;
import vn.edu.tdc.barbershop.apis.UserAPIs;
import vn.edu.tdc.barbershop.entity.Order;
import vn.edu.tdc.barbershop.entity.User;

public class OrderManagementActivity extends AppCompatActivity {
    private ArrayList<Order> mOrder;
    private RecyclerView mRecyclerUser;
    private OrderAdapter mOrderAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);
        mOrder = new ArrayList<Order>();
        mRecyclerUser = findViewById(R.id.rcv_order);
        mOrderAdapter = new OrderAdapter(this, mOrder);
        OrderAPIs.getAllOrders(mOrderAdapter, this.mOrder, this);
        mRecyclerUser.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerUser.setAdapter(mOrderAdapter);
    }
}