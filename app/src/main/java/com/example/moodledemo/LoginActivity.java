package com.example.moodledemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    public TextView Username;
    public TextView Password;
    public TextView Forget;
    public Button SignIn;
    public Button Register;


    String User;
    String Pass;

    FirebaseAuth mAuth;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.R_EMAIL);
        SignIn = findViewById(R.id.SignIn);
        Register = findViewById(R.id.Register);
        Forget = findViewById(R.id.Forget);

        reff  = FirebaseDatabase.getInstance().getReference();

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth = FirebaseAuth.getInstance();
                User = Username.getText().toString();
                Pass = Password.getText().toString();

                reff = reff.child(Username.getText().toString());

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // Data to be called here for login process

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                if(User.isEmpty()){
                    Username.setError("Please Enter UserName");
                    Username.requestFocus();
                }
                else if(Pass.isEmpty()){
                    Password.setError("Please Enter Password");
                    Password.requestFocus();
                }
                else if (!(User.isEmpty() || Pass.isEmpty())) {

                    Toast.makeText(LoginActivity.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
                    Intent I1 = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(I1);

                } else {

                    Toast.makeText(LoginActivity.this,"SignIN Failed",Toast.LENGTH_LONG).show();

                }
            }

        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent I2 = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(I2);

            }
        });

        Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reff =  reff.child(Username.getText().toString());  //to access account with the username required and then change password

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // Data to be called for changing password

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent I3 = new Intent(LoginActivity.this,OtpActivity.class);
                startActivity(I3);

            }
        });



    }
}