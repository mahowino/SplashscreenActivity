package com.example.splashscreenactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.splashscreenactivity.controller.FirebaseAuth;
import com.example.splashscreenactivity.models.Customer;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class PhoneNumberActivity extends AppCompatActivity{
    EditText phone;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        initViews();
        setListeners();
    }

    private void setListeners() {
        submit.setOnClickListener(view -> login());
    }

    private void login() {
        String phoneNumber=phone.getText().toString().trim();
        if (phoneIsValid(phoneNumber)){
            Customer customer=new Customer();
            customer.setPhoneNumber(phoneNumber);

            FirebaseAuth.logInWithPhoneNumber(
                    customer,
                    PhoneNumberActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    //main activity

                    Toast.makeText(PhoneNumberActivity.this, "Successfully detected code", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(PhoneNumberActivity.this, "error in verification: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else Toast.makeText(this, "write a valid number", Toast.LENGTH_SHORT).show();

    }

    private boolean phoneIsValid(String phoneNumber) {
    if (phoneNumber.isEmpty())
        return false;
    if (phoneNumber.length()>10)
        return false;
    return true;
    }

    private void initViews() {
        phone=findViewById(R.id.editTextPhone);
        submit=findViewById(R.id.btnSignUp);
    }
}