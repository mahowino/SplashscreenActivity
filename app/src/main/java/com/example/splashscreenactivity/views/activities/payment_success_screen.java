package com.example.splashscreenactivity.views.activities;

import static com.example.splashscreenactivity.constants.FirebaseCollections.CUSTOMER_REFERENCE;
import static com.example.splashscreenactivity.constants.FirebaseCollections.VOUCHER_REFERENCE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.splashscreenactivity.FinalActivitySender;
import com.example.splashscreenactivity.R;
import com.example.splashscreenactivity.constants.FirebaseFields;
import com.example.splashscreenactivity.constants.FirebaseInit;
import com.example.splashscreenactivity.controller.FirebaseRepository;
import com.example.splashscreenactivity.interfaces.Callback;
import com.example.splashscreenactivity.models.GoodType;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class payment_success_screen extends AppCompatActivity {
    double wallet_amount;
    CollectionReference collectionReference ;
    Date currentDate;
    boolean isTripBooking;
    private Long TransactionTime;
    List<GoodType> goodTypes;
    String phoneNumber;

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference= CUSTOMER_REFERENCE.document(FirebaseInit.mAuth.getUid()).collection("transactions");
        collectionReference.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.d("error", "Listen failed.", error);
                return;
            }

            //get Time

            for (DocumentSnapshot snapshot:value){
                //check how you store transaction time in your database

                Long currentTime=Long.parseLong(String.valueOf(snapshot.get(FirebaseFields.TRANSACTION_DATE)));

                int difference= currentTime.compareTo(TransactionTime);
                if(difference>0){
                    WalletUpdated();
                }

            }


        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success_screen);
        TransactionTime= Long.valueOf(getIntent().getExtras().getString("TransactionTime"));
        currentDate=new Date();

        goodTypes=getIntent().getParcelableArrayListExtra("goods");
        phoneNumber=getIntent().getStringExtra("phone");

    }

    private void WalletUpdated(){

        //generate voucher
        DocumentReference reference=VOUCHER_REFERENCE.document();
        FirebaseRepository.setDocument(createVoucher(), reference, new Callback() {
            @Override
            public void onSuccess(Object object) {
                Intent Confirmation=new Intent(getApplicationContext(), FinalActivitySender.class);
                Confirmation.putExtra("VoucherID",reference.getId());
                Toast.makeText(getApplicationContext(), "Wallet successfully updated", Toast.LENGTH_SHORT).show();
                startActivity(Confirmation);
                finish();

            }

            @Override
            public void onError(Object object) {

                Toast.makeText(payment_success_screen.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Map createVoucher() {
        Map<String,Object> map=new HashMap<>();
        map.put("goods",goodTypes);
        map.put("sender",FirebaseInit.mAuth.getCurrentUser().getPhoneNumber());
        map.put("receiver",phoneNumber);
        return map;
    }


}