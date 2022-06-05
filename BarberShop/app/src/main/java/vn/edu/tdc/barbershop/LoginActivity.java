package vn.edu.tdc.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private MaterialButton buttonLogin;
    private TextInputEditText inputPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        inputPhone = findViewById(R.id.input_login_phone);

        buttonLogin = findViewById(R.id.loginbtn);
        nextActivity();
        buttonLogin.setOnClickListener(view -> {
            String mobile = inputPhone.getText().toString().trim();
            if(mobile.isEmpty() || mobile.length() < 9){
                inputPhone.setError(getString(R.string.alert_number_phone));
                inputPhone.requestFocus();
                return;
            }
            Intent intent = new Intent(this, VerifyPhoneActivity.class);
            intent.putExtra("phone", mobile);
            startActivity(intent);
        });
    }

    private void nextActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Intent intent = new Intent(LoginActivity.this,CustomerScreenActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    }
}