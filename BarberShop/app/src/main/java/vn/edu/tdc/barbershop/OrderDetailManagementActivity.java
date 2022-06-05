package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;

import vn.edu.tdc.barbershop.apis.OrderAPIs;
import vn.edu.tdc.barbershop.entity.Order;

public class OrderDetailManagementActivity extends AppCompatActivity {
    MaterialToolbar btnBack;
    TextView tvOrderUserName;
    TextView tvOrderUserPhone;
    TextView tvOrderServiceName;
    TextView tvOrderDate;
    TextView tvOrderState;
    TextView tvOrderIsComplete;
    TextView tvOrderCancel;
    String TAG = "testcc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        Log.d(TAG, "onCreate: ");
        Order order = (Order) bundle.get("object_order");

        btnBack = (MaterialToolbar) findViewById(R.id.orderdetail_back_button);
        tvOrderUserName = (TextView) findViewById(R.id.order_detail_username);
        tvOrderUserPhone = (TextView) findViewById(R.id.order_detail_phone_number);
        tvOrderServiceName = (TextView) findViewById(R.id.order_detail_service_name);
        tvOrderDate = (TextView) findViewById(R.id.order_detail_order_date);
        tvOrderState = (TextView) findViewById(R.id.order_detail_order_state);
        tvOrderIsComplete = (TextView) findViewById(R.id.tv_order_finish);
        tvOrderCancel = (TextView) findViewById(R.id.tv_order_cancel);

        tvOrderUserName.setText(order.getCustomer().getName());
        tvOrderUserPhone.setText(order.getCustomer().getPhone());
        tvOrderServiceName.setText(order.getService().getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        tvOrderDate.setText(simpleDateFormat.format(order.getTimeOrder()));

        switch (order.getFinish()){
            case 0:
                tvOrderState.setText("Chưa Hoàn Thành");
                break;
            case 1:
                tvOrderState.setTextColor(Color.GREEN);
                tvOrderState.setText("Hoàn Thành");
                break;
            case 2:
                tvOrderState.setTextColor(Color.RED);
                tvOrderState.setText("Đã Huỷ");
        }

        tvOrderIsComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderAPIs.updateOrderStatus(order.getId(), 1);
                tvOrderState.setTextColor(Color.GREEN);
                tvOrderState.setText("Đã Hoàn Thành");
            }
        });


        tvOrderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderAPIs.updateOrderStatus(order.getId(), 2);
                tvOrderState.setTextColor(Color.RED);
                tvOrderState.setText("Đã Huỷ");
            }
        });

        btnBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}