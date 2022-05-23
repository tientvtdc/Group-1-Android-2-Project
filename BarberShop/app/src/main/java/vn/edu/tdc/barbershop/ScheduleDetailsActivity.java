package vn.edu.tdc.barbershop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import vn.edu.tdc.barbershop.application.NotificationApplication;
import vn.edu.tdc.barbershop.entity.Schedule;

public class ScheduleDetailsActivity extends AppCompatActivity {

    private TextView textName, textDescription, textTime, textDay, textPrice;
    private LinearLayout btnCancelSchedule, btnReSchedule;
    private Button btnPrevious;
    private ImageView imageView;
    private Schedule schedule;

    //firebase
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dataRef = database.getReference("schedule");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_details);

        init();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        schedule = (Schedule) bundle.get("schedule");

        StorageReference storageRef = FirebaseStorage.getInstance().getReference("imgService");
        Glide.with(this).load(schedule.getService().getImage()).error(R.drawable.anh1).placeholder(new ColorDrawable(Color.BLACK)).into(imageView);

        textName.setText(schedule.getService().getName());
        textDescription.setText(schedule.getService().getDescription());
        textTime.setText(String.valueOf(schedule.getTimeOrder().getHours())
                + ":" + String.valueOf(schedule.getTimeOrder().getMinutes()));

        textDay.setText(String.valueOf(schedule.getTimeOrder().getDate())
                + "-" + String.valueOf(schedule.getTimeOrder().getMonth())
                + "-" + String.valueOf(schedule.getTimeOrder().getYear()));

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        textPrice.setText(currencyVN.format(schedule.getService().getPrice()));

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCancelSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCancelScheduleDialog(schedule);
            }
        });
        btnReSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScheduleDetailsActivity.this, "Doi Lich", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void openCancelScheduleDialog(Schedule schedule) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cancel_schedule);

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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScheduleDetailsActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schedule.setIsFinish(2);
                dataRef.child(schedule.getId()).updateChildren(schedule.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(ScheduleDetailsActivity.this, "Hủy lịch thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
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