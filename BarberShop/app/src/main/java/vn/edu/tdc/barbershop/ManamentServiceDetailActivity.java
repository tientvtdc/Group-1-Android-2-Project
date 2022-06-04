package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ManamentServiceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manament_schedule_detail);

        //Màn hình chi tiết một lịch hẹn Hiển thị ra chỗ này 
//        Bundle bundle = getIntent().getBundleExtra();
//        if(bundle == null){
//            return;
//        }
//
//        Oder oder = (Oder) bundle.get("object_oder");
//
//        txtName.setText(oder.getTest());

    }
}