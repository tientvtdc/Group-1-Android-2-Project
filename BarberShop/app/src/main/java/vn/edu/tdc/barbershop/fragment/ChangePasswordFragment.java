package vn.edu.tdc.barbershop.fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import vn.edu.tdc.barbershop.R;

public class ChangePasswordFragment extends Fragment{

    private View mView;
    private EditText edtNewPassword;
    private Button brnChangePassword;
    //private ProgressDialog.progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_change_password, container, false);
        initUi();

        brnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChangePassword();
            }
        });

        return mView;
    }

    private void initUi(){
        //progressDialog = new ProgressDialog(getActivity());
        edtNewPassword = mView.findViewById(R.id.edt_new_password);
        brnChangePassword = mView.findViewById(R.id.btn_change_password);
    }

    private void onClickChangePassword (){
        String strNewPassword = edtNewPassword.getText().toString().trim();
        //ProgressDialog.show();
//        past11-14p

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrenUser();
//
//        user.updatePassword(strNewPassword).addOnCompleteListener(new OnCompleteListener<Void>(){
//            @Override
//            public void onComplete(@NonNull Task<Void> task){
//                if (task.isSuccessful()){
//                    Toast.makeText(getActivity(), "Mật khẩu người dùng đã được cập nhật", Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//                }
//            }
//        });

    }

}
