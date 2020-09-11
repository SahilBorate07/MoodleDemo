package com.example.moodledemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgetPassword_Activity extends AppCompatActivity {

    public EditText Txtcode;

    public ProgressBar progressBar;
    public String VerificationId;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_);

        mAuth = FirebaseAuth.getInstance();

        Txtcode = findViewById(R.id.CODE);

        progressBar = findViewById(R.id.Progress);

        String phonenumber = getIntent().getStringExtra("phonenumber");
        Log.d("Phone",""+phonenumber);
        sendVerificationCode(phonenumber);

        findViewById(R.id.CREATE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = Txtcode.getText().toString().trim();
                if(code.isEmpty() || code.length()<6){
                    Txtcode.setError("Enter Code");
                    Txtcode.requestFocus();
                    return;
                }

               // progressBar.setVisibility(View.VISIBLE);

                verifyCode(code);

            }
        });

    }

    private void verifyCode(String code){
     PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationId,code);
     signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){

                         Intent intent = new Intent(ForgetPassword_Activity.this, Reset_PwdActivity.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                         startActivity(intent);

                     }else {

                      Toast.makeText(ForgetPassword_Activity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                     }

                    }
                });

    }

    private void sendVerificationCode(String number){
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            VerificationId = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();

            if(code != null){
               // progressBar.setVisibility(View.VISIBLE);
             verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(ForgetPassword_Activity.this,e.getMessage(),Toast.LENGTH_LONG).show();

        }
    };

}