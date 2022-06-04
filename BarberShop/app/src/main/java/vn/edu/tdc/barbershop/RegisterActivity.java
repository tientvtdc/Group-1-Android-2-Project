package vn.edu.tdc.barbershop;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Magnifier;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import vn.edu.tdc.barbershop.entity.User;
import vn.edu.tdc.barbershop.models.UserModel;

public class RegisterActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent == null) return;
                        uri = intent.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgUser.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    //TODO: method
    private ImageView imgUser;
    private EditText editName;
    private Button btn_success;
    private String id, name, phone, image;
    private int role = 0;
    private Uri uri;
    private Button btn_prev;
    private ProgressBar bar;

    //logout and login
    private FirebaseAuth mAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = (EditText) findViewById(R.id.name);
        btn_success = findViewById(R.id.btn_success);
        imgUser = findViewById(R.id.imgUser);
        btn_prev = findViewById(R.id.btn_prev);
        bar = findViewById(R.id.bar);

        //set default image user
        uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.avatar);

        try {
            Bitmap bitmap = null;
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            imgUser.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editName.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "Tên không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    bar.setVisibility(View.VISIBLE);
                    name = editName.getText().toString().trim();
                    //get id and phone
                    if (user != null) {
                        id = user.getUid();
                        phone = user.getPhoneNumber();
                        register();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại, bạn cần đăng nhập", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openDallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openDallery();
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, MY_REQUEST_CODE);
        }
    }

    private void register() {
        if (uri != null) {
            Calendar calendar = Calendar.getInstance();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference reference = storage.getReference().child("imgUser/" + calendar.getTimeInMillis());
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            image = String.valueOf(uri);
                            User user = new User(id, name, phone, image, role);
                            UserModel userModel = new UserModel();
                            userModel.regiterUser(user, new UserModel.IUserListennerModel() {
                                @Override
                                public void onCompleteRegisterUser(DatabaseError error) {
                                    if (error == null) {
                                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, CusomerScreenActivity.class));
                                        finish();
                                    } else {
                                        bar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            });
        } else {
            Toast.makeText(this, "Bạn cần có ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO: check user exist in database
    private void isUserExist(FirebaseUser user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users/" + user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userValue = snapshot.getValue(User.class);
                if (userValue != null) {
                    if (userValue.getPhone().equalsIgnoreCase(user.getPhoneNumber())) {
                        startActivity(new Intent(RegisterActivity.this, CusomerScreenActivity.class));
                        finish();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, getString(R.string.toast_please_register_for_an_account), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        openDallery();
                    }
                }
            }
        }
    }

    private void openDallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check login
        if (user == null) {
            finish();
            return;
        }
        else {
            isUserExist(user);
        }
    }
}