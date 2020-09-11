package com.example.moodledemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class OtpActivity extends AppCompatActivity {

   public Spinner spinner;
   public EditText Phone;
   public Button SendOtp;

   //TextView temp; // new

    //String num1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));


        Phone = findViewById(R.id.phone);
        SendOtp = findViewById(R.id.SendOtp);

        SendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String numb = Phone.getText().toString().trim();


                    if (numb.isEmpty() || numb.length() < 10) {
                        Phone.setError("Valid Number Is Required");
                        Phone.requestFocus();
                        return;

                    }

                String phone = "+" + code + numb;

                Intent I5 = new Intent(OtpActivity.this, ForgetPassword_Activity.class);
                I5.putExtra("phonenumber", phone);
                startActivity(I5);


            }
        });


    }

     protected void onStart(){
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent I6 = new Intent(OtpActivity.this,MainActivity.class);

            I6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(I6);
        }
    }

}