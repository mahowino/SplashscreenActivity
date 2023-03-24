package online.kenya.mvitu.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import online.kenya.mvitu.R;
import online.kenya.mvitu.Utils.Sanitizer;
import online.kenya.mvitu.controller.CartHelper;
import online.kenya.mvitu.controller.DarajaApiClient;
import online.kenya.mvitu.models.AccessToken;
import online.kenya.mvitu.models.Cart;
import online.kenya.mvitu.models.STKPush;
import online.kenya.mvitu.views.layouts.LoadingDialog;

import androidx.annotation.NonNull;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import online.kenya.mvitu.constants.MpesaConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SendGoodsActivity extends AppCompatActivity {

    private DarajaApiClient mApiClient;
    private ProgressDialog mProgressDialog;
    Button mPay;
    String number;
    EditText payingPhoneNumber,targetPhoneNummber;
    Cart cart;
    CartHelper cartHelper;
    LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_goods);
        initViews();
        setListeners();
        getAccessToken();
    }

    private void setListeners() {
        mPay.setOnClickListener(view -> sendPrompt());
    }

    private void initViews() {
        ButterKnife.bind(this);
        payingPhoneNumber=findViewById(R.id.editTextSendingPhoneNumber);
        targetPhoneNummber=findViewById(R.id.editTextTargetPhoneNumber);
        mPay=findViewById(R.id.btnSendMpesaPrompt);
        mProgressDialog = new ProgressDialog(this);
        mApiClient = new DarajaApiClient();
        mApiClient.setIsDebug(true); //Set True to enable logging, false to disable.
        cart=getIntent().getParcelableExtra("cart");
        number=getIntent().getStringExtra("number");
        dialog=new LoadingDialog(this);

        cartHelper=new CartHelper(cart);
        payingPhoneNumber.setText(number);
    }


    private void validate() {
        if (TextUtils.isEmpty(payingPhoneNumber.getText().toString())) {payingPhoneNumber.setError("paying number is required"); dialog.dismissDialog();return;}
        if (TextUtils.isEmpty(targetPhoneNummber.getText().toString())) {targetPhoneNummber.setError("target number is required");dialog.dismissDialog();return;}


    }


    private void sendPrompt() {
        String phone_number = payingPhoneNumber.getText().toString();
        String amount =getIntent().getStringExtra("amount");
        performSTKPush(phone_number,amount);
    }

    public void getAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(SendGoodsActivity.this, ""+response.body().accessToken, Toast.LENGTH_SHORT).show();
                    mApiClient.setAuthToken(response.body().accessToken);

                    //Toast.makeText(SendGoodsActivity.this, ""+response.body().accessToken, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {

            }
        });
    }

    public void performSTKPush(String phone_number,String amount) {
        mProgressDialog.setMessage("Processing your request");
        mProgressDialog.setTitle("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        String timestamp = Sanitizer.getTimestamp();
        STKPush stkPush = new STKPush(
                MpesaConstants.BUSINESS_SHORT_CODE,
                Sanitizer.getPassword(MpesaConstants.BUSINESS_SHORT_CODE, MpesaConstants.PASSKEY, timestamp),
                timestamp,
                MpesaConstants.TRANSACTION_TYPE,
                String.valueOf(amount),
                Sanitizer.sanitizePhoneNumber(phone_number),
                MpesaConstants.PARTYB,
                Sanitizer.sanitizePhoneNumber(phone_number),
                MpesaConstants.CALLBACKURL,
                "MPESA Android Test", //Account reference
                "Testing"  //Transaction description
        );

        mApiClient.setGetAccessToken(false);

        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {
                mProgressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        Intent intent=new Intent(getApplicationContext(), payment_success_screen.class);
                        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
                        String date=dateFormat.format(new Date());
                        intent.putExtra("TransactionTime",date);
                        intent.putExtra("phone",Sanitizer.toE164(targetPhoneNummber.getText().toString()));
                        ArrayList cartGoods= (ArrayList) cart.getCartGoods();
                        intent.putParcelableArrayListExtra("goods",cartGoods);

                        startActivity(intent);
                        Timber.d("post submitted to API. %s", response.body());
                    } else {
                        Timber.e("Response %s", response.errorBody().string());
                        Toast.makeText(SendGoodsActivity.this, "error "+response.body(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), MainPage.class);
                        startActivity(intent);
                        finish();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Timber.e(t);
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}