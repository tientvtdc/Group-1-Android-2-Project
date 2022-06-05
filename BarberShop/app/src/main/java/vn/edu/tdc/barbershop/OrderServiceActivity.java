package vn.edu.tdc.barbershop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.edu.tdc.barbershop.entity.Order;
import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.entity.User;

public class OrderServiceActivity extends AppCompatActivity {

    private MaterialToolbar topAppBarOrderService;
    private TextView tenDichVuOrderService;
    private TextView giaDichVuOrderService;
    private TextView thoiGianDichVuOrderService;
    private Spinner spinnerTime;
    private CalendarView datePicker;
    private Service service;
    private Date date;
    private Button orderServiceBtn;
    private List<String> listWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_service);
        initView();
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        service = (Service) bundle.get("service");
        topAppBarOrderService.setOnClickListener(view -> {
            finish();
        });
        listWork = getHourWork();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listWork);
        spinnerTime.setAdapter(adapter);
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        tenDichVuOrderService.setText("Tên dịch vụ: " + service.getName());
        giaDichVuOrderService.setText("Giá dịch vụ: " + currencyVN.format(service.getPrice()));
        thoiGianDichVuOrderService.setText("Thời lượng: " + service.getTime() + " phút");
        Date today = new Date();
        date = (Date) today.clone();
        today.setHours(0);
        today.setMinutes(0);
        datePicker.setMinDate(today.getTime());
        datePicker.setMaxDate(today.getTime() + (1000 * 60 * 60 * 24 * 20));
        datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date.setDate(i2);
                date.setMonth(i1);
                Log.d("Date", "onSelectedDayChange: " + date);
            }
        });
        orderServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new MaterialAlertDialogBuilder(view.getContext())).setTitle("Bạn muốn đặt dịch vụ " + service.getName()).setPositiveButton(R.string.text_positive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String time = listWork.get((int) spinnerTime.getSelectedItemId());
                        String[] str = time.split(":");
                        int hour = Integer.parseInt(str[0]);
                        int minute = Integer.parseInt(str[1]);
                        date.setHours(hour);
                        date.setMinutes(minute);
                        Date now = new Date();
                        if (now.getTime() <= date.getTime() + 1000 * 60 * 60 * 2) {
                            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                            String id = database.child("orders").push().getKey();
                            Query query = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user = snapshot.getValue(User.class);
                                    Order order = new Order(id, user, service, date.getTime(), date.getTime() + service.getTime() * 1000 * 60, 0);
                                    database.child("orders").child(id).setValue(order);
                                    Toast.makeText(getApplicationContext(), "Đặt lịch thành công", Toast.LENGTH_LONG).show();
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            (new MaterialAlertDialogBuilder(view.getContext())).setTitle("Vui lòng đặt trước 2 tiếng" + service.getName()).setPositiveButton(R.string.text_positive_btn, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).setNeutralButton(R.string.text_neutral_btn, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).show();
                        }

                    }
                }).setNeutralButton(R.string.text_neutral_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
            }
        });
    }

    private List<String> getHourWork() {
        List<String> strings = new ArrayList<>();
        strings.add("08:00");
        strings.add("08:30");
        strings.add("09:00");
        strings.add("09:30");
        strings.add("10:00");
        strings.add("10:30");
        strings.add("13:00");
        strings.add("13:30");
        strings.add("14:00");
        strings.add("14:30");
        strings.add("15:00");
        strings.add("15:30");
        strings.add("16:00");
        strings.add("16:30");
        strings.add("17:00");
        strings.add("17:30");
        strings.add("18:00");

        return strings;
    }

    private void initView() {
        topAppBarOrderService = (MaterialToolbar) findViewById(R.id.topAppBar_order_service);
        tenDichVuOrderService = (TextView) findViewById(R.id.ten_dich_vu_order_service);
        giaDichVuOrderService = (TextView) findViewById(R.id.gia_dich_vu_order_service);
        thoiGianDichVuOrderService = (TextView) findViewById(R.id.thoi_gian_dich_vu_order_service);
        spinnerTime = (Spinner) findViewById(R.id.spinner_time);
        datePicker = (CalendarView) findViewById(R.id.date_picker);
        orderServiceBtn = (Button) findViewById(R.id.order_service_btn);
    }

}