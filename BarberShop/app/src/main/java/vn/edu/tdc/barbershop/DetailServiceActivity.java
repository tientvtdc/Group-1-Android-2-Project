package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import vn.edu.tdc.barbershop.entity.Service;

public class DetailServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service);
//        TextView tvName = findViewById(R.id.tv_detail_name);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Service service = (Service) bundle.get("object_service");
        TextView textViewUser = findViewById(R.id.tv_detail_name);
        textViewUser.setText(service.getName());
    }
}