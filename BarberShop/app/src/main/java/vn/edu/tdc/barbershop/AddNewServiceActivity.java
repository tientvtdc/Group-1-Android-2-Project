package vn.edu.tdc.barbershop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;
import java.util.UUID;

import vn.edu.tdc.barbershop.entity.Service;
import vn.edu.tdc.barbershop.models.ServiceModel;

public class AddNewServiceActivity extends AppCompatActivity {

    private TextInputEditText name, description, price;
    private ShapeableImageView img;
    private Button btnAdd;
    private int REQ = 1;
    private MaterialToolbar add_new_service_back;

    private Uri filePath;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private ServiceModel serviceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_service);

        init();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImg();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImg();
            }
        });
        add_new_service_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {
        serviceModel = new ServiceModel();
        name = (TextInputEditText) findViewById(R.id.textInputName);
        description = (TextInputEditText) findViewById(R.id.textInputDes);
        price = (TextInputEditText) findViewById(R.id.textInputPrice);
        img = (ShapeableImageView) findViewById(R.id.imgInput);
        btnAdd = (Button) findViewById(R.id.btnAddNewService);
        add_new_service_back = findViewById(R.id.add_new_service_back);
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

                                    Toast.makeText(AddNewServiceActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                    //Tạo node trên phần database
//                                    HinhAnh hinhAnh = new HinhAnh(edtTenHinh.getText().toString(), String.valueOf(downloadUrl));
                                    String mServiceId = mData.push().getKey();

//                                    Service service = new Service(mServiceId, name.getText().toString(),String.valueOf(downloadUrl),Double.parseDouble(price.getText().toString()),description.getText().toString());

                                    serviceModel.addNewSevice(name.getText().toString(),
                                            String.valueOf(downloadUrl),
                                            Double.parseDouble(price.getText().toString()),
                                            description.getText().toString(), new ServiceModel.IServiceListennerModel() {
                                                @Override
                                                public void onCompleteAddService(DatabaseError error) {
                                                    if (error == null) {
                                                        Toast.makeText(AddNewServiceActivity.this, "Luu du lieu thanh cong", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    } else {
                                                        Toast.makeText(AddNewServiceActivity.this, "Loi!!!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddNewServiceActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        }
    }
}