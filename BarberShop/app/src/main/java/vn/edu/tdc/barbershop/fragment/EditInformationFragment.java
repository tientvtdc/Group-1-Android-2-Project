package vn.edu.tdc.barbershop.fragment;
import static vn.edu.tdc.barbershop.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Magnifier;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import vn.edu.tdc.barbershop.MainActivity;
import vn.edu.tdc.barbershop.R;

public class EditInformationFragment extends Fragment{

    private View mView;
    private ImageView imgUser;
    private EditText edtName;
    private EditText edtPhone;
    private Button brnEditInformation;
    private Uri mUri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_edit_information, container, false);
        initUi();
        initListener();

        brnEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEditInformation();
            }
        });

        return mView;
    }

    private void initUi(){

        imgUser = mView.findViewById(R.id.imgUser);
        edtName = mView.findViewById(R.id.edtName);
        brnEditInformation = mView.findViewById(R.id.btn_edit_information);

        setUserInformation();
    }

    private void onClickEditInformation (){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        String strEditInformation = edtName.getText().toString().trim();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strEditInformation)
                .setPhotoUri(mUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task){
                if (task.isSuccessful()){
                    Toast.makeText(getActivity(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void setUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        edtName.setText(user.getDisplayName());
        //Gdule.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_profile).into(imgUser);

    }

    private void initListener(){
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermisstion();
            }
        });

        brnEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEditInformation();
            }
        });
    }

    private void onClickRequestPermisstion(){
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity == null){
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mainActivity.openGallery();
            return;
        }
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            mainActivity.openGallery();
        }else {
            String[] permisstions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permisstions, MY_REQUEST_CODE);
        }
    }

    public void setBitMapImageView (Bitmap bitMapImageView){
        imgUser.setImageBitmap(bitMapImageView);
    }


    public void setmUri(Uri mUri) {
        this.mUri = mUri;
    }
}
