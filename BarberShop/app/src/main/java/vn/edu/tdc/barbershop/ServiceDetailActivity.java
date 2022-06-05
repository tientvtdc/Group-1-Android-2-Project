package vn.edu.tdc.barbershop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.NumberFormat;
import java.util.Locale;

import vn.edu.tdc.barbershop.entity.Service;

public class ServiceDetailActivity extends AppCompatActivity {

    private ImageView imgDetailService;
    private TextView nameServiceDetail;
    private TextView priceServiceDetail;
    private TextView descriptionServiceDetail;
    private MaterialToolbar topAppBar;
    private Button btnOrderDetailService;
private   Service service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

         service = (Service) bundle.get("service");
        initView();
        btnOrderDetailService.setOnClickListener(view -> {
            goToOrderService();
        });
        Glide.with(this).load(service.getImage()).error(R.drawable.anh1).into(imgDetailService);
        nameServiceDetail.setText(service.getName());

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        priceServiceDetail.setText(currencyVN.format(service.getPrice()));
        descriptionServiceDetail.setText(service.getDescription());
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void goToOrderService() {
        Intent intent = new Intent(this, OrderServiceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("service", service);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void initView() {
        imgDetailService = (ImageView) findViewById(R.id.img_detail_service);
        nameServiceDetail = (TextView) findViewById(R.id.name_service_detail);
        priceServiceDetail = (TextView) findViewById(R.id.price_service_detail);
        descriptionServiceDetail = (TextView) findViewById(R.id.decription_service_detail);
        topAppBar = (MaterialToolbar) findViewById(R.id.topAppBar);
        btnOrderDetailService = (Button) findViewById(R.id.btn_order_detail_service);
    }
}