package vn.edu.tdc.barbershop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.models.ServiceModel;

public class ManageDetailServiceActivity extends AppCompatActivity {

    private MaterialToolbar materialToolbar;
    private Button btnSave, btnDelete;
    ShapeableImageView img;
    private TextInputEditText edtName, edtPrice, edtDes;
    private int REQ = 1;
    private Service service;

    private Uri filePath;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private ServiceModel serviceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service);
        init();
        materialToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImg();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImg();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDeleteData(service.getID());
            }
        });
    }

    private void onClickDeleteData(String serviceId) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn có muốn xóa dịch vụ này không?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("services/" + serviceId);
                        myRef.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(ManageDetailServiceActivity.this, "Delete data success", Toast.LENGTH_SHORT).show();

                                // gọi hàm finish() để đóng Activity hiện tại và trở về MainActivity.
                                finish();

                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void init() {
        serviceModel = new ServiceModel();
        materialToolbar = findViewById(R.id.detail_edit_topAppBar);
        edtName = findViewById(R.id.textInputEditName);
        edtPrice = findViewById(R.id.textInputEditPrice);
        edtDes = findViewById(R.id.textInputEditDes);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_del);
        img = findViewById(R.id.imgEditInput);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        service = (Service) bundle.get("object_service");
        edtName.setText(service.getName());
        edtPrice.setText(service.getPrice() + "");
        edtDes.setText(service.getDescription());

        Picasso.with(ManageDetailServiceActivity.this).load(service.getImage()).into(img);
    }

    private void selectImg() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImg() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("imgService/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;
                                    //Do what you want with the url

                                    Toast.makeText(ManageDetailServiceActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                    //Tạo node trên phần database
//                                    HinhAnh hinhAnh = new HinhAnh(edtTenHinh.getText().toString(), String.valueOf(downloadUrl));
                                    String mServiceId = service.getID();

//                                    Service service = new Service(mServiceId, name.getText().toString(),String.valueOf(downloadUrl),Double.parseDouble(price.getText().toString()),description.getText().toString());

                                    Log.d("senddata", edtName.getText().toString()+"-"+String.valueOf(downloadUrl)+"-"+Double.parseDouble(edtPrice.getText().toString()));
                                    serviceModel.editServiceWithID(mServiceId,
                                            edtName.getText().toString(),
                                            String.valueOf(downloadUrl),
                                            Double.parseDouble(edtPrice.getText().toString()),
                                            edtDes.getText().toString(), new ServiceModel.IServiceListennerModel() {
                                                @Override
                                                public void onCompleteAddService(DatabaseError error) {
                                                    if (error == null) {
                                                        Toast.makeText(ManageDetailServiceActivity.this, "Luu du lieu thanh cong", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    } else {
                                                        Toast.makeText(ManageDetailServiceActivity.this, "Loi!!!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ManageDetailServiceActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            String mServiceId = service.getID();
            serviceModel.editServiceWithID(mServiceId,
                    edtName.getText().toString(),
                    service.getImage(),
                    Double.parseDouble(edtPrice.getText().toString()),
                    edtDes.getText().toString(), new ServiceModel.IServiceListennerModel() {
                        @Override
                        public void onCompleteAddService(DatabaseError error) {
                            if (error == null) {
                                Toast.makeText(ManageDetailServiceActivity.this, "Luu du lieu thanh cong", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ManageDetailServiceActivity.this, "Loi!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}