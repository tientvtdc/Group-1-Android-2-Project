package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.entity.User;

public class UserDetailActivity extends AppCompatActivity {
    MaterialToolbar btnBack;
    AutoCompleteTextView autoCompleteTextView;
    ImageView userImage;
    TextView tvUserName;
    TextView tvUserPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        User user = (User) bundle.get("object_user");

        btnBack = (MaterialToolbar) findViewById(R.id.userdetail_back_button);
        userImage = (ImageView) findViewById(R.id.img_user);
        Glide.with(this).load(user.getImage()).into(userImage);
        tvUserName = (TextView) findViewById(R.id.tv_userdetail_name);
        tvUserPhone = (TextView) findViewById(R.id.tv_userdetail_phonenumber);
        autoCompleteTextView = findViewById(R.id.autoCompleteText);

        tvUserName.setText(user.getName());
        tvUserPhone.setText(user.getPhone());

        String []option = {"Người quản trị", "Người dùng", "Quản lý"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.option_item, option);
        switch (user.getRole()){
            case 0:
                autoCompleteTextView.setText(arrayAdapter.getItem(1).toString(), false);
                break;
            case 1:
                autoCompleteTextView.setText(arrayAdapter.getItem(2).toString(), false);
                break;
            default:
                autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
                break;
        }

        autoCompleteTextView.setAdapter(arrayAdapter);

        btnBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}