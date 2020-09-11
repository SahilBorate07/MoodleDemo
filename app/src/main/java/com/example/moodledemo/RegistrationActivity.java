package com.example.moodledemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    public TextView rUSERNAME;
    public TextView rNAME;
    public TextView rEMAIL;
    public TextView rPASSWORD;
    public TextView rPHONE;
    public Button rSUBMIT;

    DatabaseReference reff;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        rUSERNAME = findViewById(R.id.rUSERNAME);
        rNAME = findViewById(R.id.rNAME);
        rEMAIL = findViewById(R.id.rEMAIL);
        rPASSWORD = findViewById(R.id.rPASSWORD);
        rPHONE = findViewById(R.id.rPHONE);
        rSUBMIT = findViewById(R.id.rSUBMIT);

        member = new Member();

        reff = FirebaseDatabase.getInstance().getReference().child("Member");

        rSUBMIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reff = FirebaseDatabase.getInstance().getReference().child(rUSERNAME.getText().toString());

                Long Phone = Long.parseLong(rPHONE.getText().toString().trim());

                member.setUserName(rUSERNAME.getText().toString().trim());
                member.setName(rNAME.getText().toString().trim());
                member.setEmail(rEMAIL.getText().toString().trim());
                member.setPassword(rPASSWORD.getText().toString().trim());
                member.setPhone(rPHONE.getText().toString());
               // reff.push();
                reff.setValue(member);
                //reff.child(String.valueOf(member)).setValue(member);


                Toast.makeText(RegistrationActivity.this,"Data Inserted Successfully",Toast.LENGTH_LONG).show();
                Intent I4 = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(I4);

            }
        });


    }
}