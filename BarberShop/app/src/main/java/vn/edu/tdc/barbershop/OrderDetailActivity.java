package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;

import vn.edu.tdc.barbershop.apis.UserAPIs;
import vn.edu.tdc.barbershop.entity.User;

public class OrderDetailActivity extends AppCompatActivity {
    MaterialToolbar btnBack;
    AutoCompleteTextView autoCompleteTextView;
    ImageView userImage;
    TextView tvUserName;
    TextView tvUserPhone;
    Button btnSaveUser;
    String TAG = "test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        User user = (User) bundle.get("object_user");

        btnBack = (MaterialToolbar) findViewById(R.id.userdetail_back_button);
        userImage = (ImageView) findViewById(R.id.img_user);
        Glide.with(this).load(user.getImage()).into(userImage);
        tvUserName = (TextView) findViewById(R.id.tv_userdetail_name);
        tvUserPhone = (TextView) findViewById(R.id.tv_userdetail_phonenumber);
        autoCompleteTextView = findViewById(R.id.autoCompleteText);
        btnSaveUser = findViewById(R.id.btn_save_user);

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

        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskOption(user);
                diaBox.show();
            }
        });

        btnBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private AlertDialog AskOption(User user)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Lưu người dùng")
                .setMessage("Bạn có muốn lưu người dùng hiện tại?")
                .setIcon(R.drawable.save)
                .setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = -1;
                        if(autoCompleteTextView.getText().toString().equals("Người quản trị")) position = 2;
                        else if(autoCompleteTextView.getText().toString().equals("Quản lý")) position = 1;
                        else if(autoCompleteTextView.getText().toString().equals("Người dùng")) position = 0;
                        UserAPIs.updateUserRole(user.getId(), position != -1 ? position : user.getRole());
                        finish();
                    }
                })
                .setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return myQuittingDialogBox;
    }
}