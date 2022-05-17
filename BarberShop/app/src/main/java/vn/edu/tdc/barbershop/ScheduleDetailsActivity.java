package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

import vn.edu.tdc.barbershop.entity.Schedule;

public class ScheduleDetailsActivity extends AppCompatActivity {

    private TextView textName, textDescription, textTime, textDay, textPrice;
    private LinearLayout btnCancelSchedule, btnReSchedule;
    private Button btnPrevious;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_details);

        init();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        Schedule schedule = (Schedule) bundle.get("schedule");

        StorageReference storageRef = FirebaseStorage.getInstance().getReference("imgService");
        Glide.with(this).load(schedule.getService().getImage()).error(R.drawable.anh1).placeholder(new ColorDrawable(Color.BLACK)).into(imageView);

        textName.setText(schedule.getService().getName());
        textDescription.setText(schedule.getService().getDescription());
        textTime.setText(String.valueOf(schedule.getTimeOrder().get(Calendar.HOUR_OF_DAY)));
        textDay.setText(String.valueOf(schedule.getTimeOrder().get(Calendar.DAY_OF_MONTH)));
        textPrice.setText(String.valueOf(schedule.getService().getPrice()));

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCancelSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScheduleDetailsActivity.this, "Huy lich", Toast.LENGTH_SHORT).show();
            }
        });
        btnReSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScheduleDetailsActivity.this, "Doi Lich", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        imageView = findViewById(R.id.img_schedule);
        textName = findViewById(R.id.txt_name);
        textDescription = findViewById(R.id.txt_description);
        textTime = findViewById(R.id.txt_time);
        textDay = findViewById(R.id.txt_day);
        textPrice = findViewById(R.id.txt_price);

        btnCancelSchedule = findViewById(R.id.btn_cancel_schedule);
        btnReSchedule = findViewById(R.id.btn_reschedule);
        btnPrevious = findViewById(R.id.btn_prev);
    }
}