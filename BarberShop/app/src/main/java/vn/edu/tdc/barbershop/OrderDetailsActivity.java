package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import vn.edu.tdc.barbershop.entity.Order;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView textName, textDescription, textTime, textDay, textPrice;
    private LinearLayout btnReOrder;
    private Button btnPrevious;
    private ImageView imageView;
    private Order order;

    //firebase
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dataRef = database.getReference("orders");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        init();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        order = (Order) bundle.get("order");

        StorageReference storageRef = FirebaseStorage.getInstance().getReference("imgService");
        Glide.with(this).load(order.getService().getImage()).error(R.drawable.anh1).placeholder(new ColorDrawable(Color.BLACK)).into(imageView);

        textName.setText(order.getService().getName());
        textDescription.setText(order.getService().getDescription());

        //format time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        textTime.setText(simpleDateFormat.format(order.getCalendarOrder().getTime()));

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        textDay.setText(s.format(order.getCalendarOrder().getTime()));

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        textPrice.setText(currencyVN.format(order.getService().getPrice()));

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnReOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCancelOrderDialog(order);
            }
        });
    }


    private void openCancelOrderDialog(Order order) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cancel_order);

        Window window = dialog.getWindow();
        if (window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        //static dialog
        dialog.setCancelable(false);

        TextView message = dialog.findViewById(R.id.message_dialog);
        Button btnCancel = dialog.findViewById(R.id.cancel_dialog);
        Button btnAgree = dialog.findViewById(R.id.agree_dialog);

        message.setText("Bạn có muốn đổi lịch không ?");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OrderDetailsActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailsActivity.this, CustomerScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", order);
                intent.putExtras(bundle);

                Toast.makeText(OrderDetailsActivity.this, "Đổi lịch", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                startActivity(intent);
            }
        });

        dialog.show();
    }

    private void init() {
        imageView = findViewById(R.id.img_order);
        textName = findViewById(R.id.txt_name);
        textDescription = findViewById(R.id.txt_description);
        textTime = findViewById(R.id.txt_time);
        textDay = findViewById(R.id.txt_day);
        textPrice = findViewById(R.id.txt_price);

        btnReOrder = findViewById(R.id.btn_re_order);
        btnPrevious = findViewById(R.id.btn_prev);
    }
}