package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ManageServiceScreenActivity extends AppCompatActivity {
    private Button btnGoToManageService,
            btnGoToManageAccount,
            btnGoToSchedule,
            btnGoToUserPage,
            btnLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_service_screen);
        init();
        btnGoToManageService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageServiceScreenActivity.this, ServiceListActivity.class);
                startActivity(intent);
            }
        });
        btnGoToManageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Intent intent = new Intent(ManageServiceScreenActivity.this,UserManagementActivity.class);
                startActivity(intent);
            }
        });
        btnGoToSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO

            }
        });
        btnGoToUserPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Intent intent = new Intent(ManageServiceScreenActivity.this,CustomerScreenActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ManageServiceScreenActivity.this,LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
    private void init() {
        btnGoToManageService = findViewById(R.id.btnGoToManageService);
        btnGoToManageAccount = findViewById(R.id.btnGoToManageAccount);
        btnGoToSchedule = findViewById(R.id.btnGoToSchedule);
        btnGoToUserPage = findViewById(R.id.btnGoToUserPage);
        btnLogOut = findViewById(R.id.btnLogOut);
    }
}