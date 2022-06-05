package vn.edu.tdc.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import vn.edu.tdc.barbershop.entity.User;

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
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Query query =  FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user.getRole() == 2) {
                            Intent intent = new Intent(ManageServiceScreenActivity.this,UserManagementActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("recent_user", user);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else{
                            new AlertDialog.Builder(ManageServiceScreenActivity.this)
                                    .setTitle("Cảnh báo")
                                    .setMessage("Chỉ có quản trị viên mới sử dụng được tính năng này!")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
                Intent intent = new Intent(ManageServiceScreenActivity.this, CustomerScreenActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                (new MaterialAlertDialogBuilder(ManageServiceScreenActivity.this)).setTitle(R.string.title_dialog_logout).setPositiveButton(R.string.text_positive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(ManageServiceScreenActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                }).setNeutralButton(R.string.text_neutral_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();

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