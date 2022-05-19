package vn.edu.tdc.barbershop;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import vn.edu.tdc.barbershop.entity.User;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = SignupActivity.class.getName();
    private EditText phone, otp;
    private Button btnSendOtp;
    private Button btnVerify;
    private Button btnPrev;
    private ProgressBar bar;
    private TextView btnSendOtpAgain;
    private User userValue;
    private boolean isLoadFirebase = false;

    //firebase
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private String mPhoneNumber;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //find view
        init();

        //event
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(phone.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Nhập số điện thoại của bạn !", Toast.LENGTH_SHORT).show();
                }
                else {
                    mPhoneNumber = phone.getText().toString();
                    bar.setVisibility(View.VISIBLE);
                    sendVerificationCode();
                }
            }
        });
        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(otp.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Nhập mã OTP !", Toast.LENGTH_SHORT).show();
                }
                else {
                    bar.setVisibility(View.VISIBLE);
                    VerifyCode(otp.getText().toString());
                }
            }
        });
        btnSendOtpAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.setVisibility(View.VISIBLE);
                SendOtpAgain();
            }
        });
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        phone = (EditText) findViewById(R.id.phone_number);
        otp = (EditText) findViewById(R.id.otp);
        btnPrev = findViewById(R.id.btn_prev);
        btnSendOtp = findViewById(R.id.send_otp);
        btnVerify = findViewById(R.id.verify);
        btnSendOtpAgain = findViewById(R.id.send_otp_again);
        bar = findViewById(R.id.bar);
    }

    //TODO: send OTP to phone number
    private void sendVerificationCode() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + mPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            Log.d(TAG, "onVerificationCompleted:" + credential);
            Toast.makeText(SignupActivity.this, getString(R.string.toast_verify_success), Toast.LENGTH_SHORT).show();

            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Toast.makeText(SignupActivity.this, getString(R.string.toast_invalid_request), Toast.LENGTH_SHORT).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Toast.makeText(SignupActivity.this, getString(R.string.toast_The_SMS_quota_exceeded), Toast.LENGTH_SHORT).show();
            }

            // Show a message and update the UI
            Toast.makeText(SignupActivity.this, getString(R.string.toast_verify_failed), Toast.LENGTH_SHORT).show();
            bar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId, token);
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:" + verificationId);
            Toast.makeText(SignupActivity.this, getString(R.string.toast_The_SMS_send_to_phone), Toast.LENGTH_SHORT).show();

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            mResendToken = token;
            bar.setVisibility(View.INVISIBLE);
        }
    };
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI

                            //dang ky tai khoan roi -> khong dang ky nua
                            isUserExist(user);
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(SignupActivity.this,
                                        getString(R.string.toast_verification_invalid), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void goToRegisterActivity(String phoneNumber, String idToken) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("idToken", idToken);
        startActivity(intent);
    }

    //TODO: send otp again
    private void SendOtpAgain() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + mPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setForceResendingToken(mResendToken)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    //TODO: verify otp
    private void VerifyCode(String code) {
        if (mVerificationId == null) {
            Toast.makeText(this, getString(R.string.toast_phone_number_has_not_been_verified), Toast.LENGTH_SHORT).show();
        }else {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        }
    }

    //dang nhap
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            bar.setVisibility(View.VISIBLE);
            isUserExist(user);
        }
        bar.setVisibility(View.INVISIBLE);
    }

    //TODO: check user exist in database
    private void isUserExist(FirebaseUser user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("user/" + user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userValue = snapshot.getValue(User.class);
                if (userValue != null) {
                    if (userValue.getPhone().equals(user.getPhoneNumber())) {
                        startActivity(new Intent(SignupActivity.this, CusomerScreenActivity.class));
                        finish();
                    }
                }
                else {
                    Toast.makeText(SignupActivity.this, getString(R.string.toast_please_register_for_an_account), Toast.LENGTH_SHORT).show();
                    goToRegisterActivity(user.getPhoneNumber(), user.getUid());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}