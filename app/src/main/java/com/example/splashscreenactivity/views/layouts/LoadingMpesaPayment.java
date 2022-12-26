package com.example.splashscreenactivity.views.layouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.splashscreenactivity.R;
import com.example.splashscreenactivity.constants.FirebaseInit;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class LoadingMpesaPayment {
    Activity activity;
    AlertDialog dialog;

    public LoadingMpesaPayment(Activity activity) {
        this.activity = activity;
    }
    public void startLoadingAlertDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog,null));
        builder.setCancelable(true);
        dialog=builder.create();
        dialog.show();

        //set listener
        FirebaseInit.db.collection("cities")
                .whereEqualTo("state", "CA")
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                       e.printStackTrace();
                        return;
                    }

                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            Toast.makeText(activity, "doc added", Toast.LENGTH_SHORT).show();
                            dismissDialog();
                        }
                    }
                });
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
