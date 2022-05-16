package vn.edu.tdc.barbershop;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataIntent();
        setTitleToolBar();
    }

    private void getDataIntent() {
        String strPhoneNumber = getIntent().getStringExtra("phone_number");
        TextView tvUserInfo = findViewById(R.id.tv_user_info);
        tvUserInfo.setText(strPhoneNumber);
    }

    private void setTitleToolBar() {
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Main Activity");
    }
}