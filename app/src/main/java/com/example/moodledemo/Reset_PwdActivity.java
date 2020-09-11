package com.example.moodledemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Reset_PwdActivity extends AppCompatActivity {

    public TextView RP_et1;
    public TextView RP_et2;
    public Button RP_Confirm;
    public String rp_et1;
    public String rp_et2;
    public String temp;

    String User1;
    String Pass1;

    DatabaseReference reff;
    FirebaseDatabase mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__pwd);

        reff = FirebaseDatabase.getInstance().getReference();

        RP_et1 = findViewById(R.id.RP_et1);
        RP_et2 = findViewById(R.id.RP_et2);
        RP_Confirm = findViewById(R.id.RP_Confirm);

        rp_et1 = RP_et1.toString();
        rp_et2 = RP_et2.toString();

        temp = rp_et2;

        RP_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


         reff.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {

                 String password = snapshot.child("password").getValue(String.class);

               //  Pass1 = password;


                 if(rp_et1 == rp_et2){

                     if(password == temp){

                         Toast.makeText(Reset_PwdActivity.this , "Entered Password was similar to Previous Password ",Toast.LENGTH_LONG).show();

                     } else {

                         Toast.makeText(Reset_PwdActivity.this,"Password Changed Successfully",Toast.LENGTH_LONG).show();
                         Intent intent = new Intent(Reset_PwdActivity.this , LoginActivity.class);
                         startActivity(intent);

                     }

                     /* Toast.makeText(Reset_PwdActivity.this,"Password Changed Successfully",Toast.LENGTH_LONG).show();
                     Intent intent = new Intent(Reset_PwdActivity.this , LoginActivity.class);
                     startActivity(intent); */

                 }else if(rp_et1 != rp_et2){

                     Toast.makeText(Reset_PwdActivity.this, "Entered inputs did'nt match" , Toast.LENGTH_LONG);

                 }

                /* if(password != temp){

                     Toast.makeText(Reset_PwdActivity.this , "Entered Password was similar to Previous Password ",Toast.LENGTH_LONG).show();

                 } */

                 //data to be called here in order to change the existing password

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

         });



            }
        });

    }
}