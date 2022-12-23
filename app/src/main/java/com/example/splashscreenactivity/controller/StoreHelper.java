package com.example.splashscreenactivity.controller;

import android.hardware.lights.LightsManager;

import androidx.annotation.NonNull;

import com.example.splashscreenactivity.constants.FirebaseCollections;
import com.example.splashscreenactivity.constants.FirebaseInit;
import com.example.splashscreenactivity.interfaces.getItemsCallback;
import com.example.splashscreenactivity.models.Goods;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StoreHelper {

    public static void getAllGoods(getItemsCallback callback) {
        FirebaseCollections.STORE_REFERENCE.get().addOnSuccessListener(snapshot -> {
            List<Goods> goods=new ArrayList<>();
            for (DocumentSnapshot snapshotDocs:snapshot){
                goods.add(snapshotDocs.toObject(Goods.class));
            }
            callback.onSuccess(goods);
        }).addOnFailureListener(e -> {
                    callback.onError();
                });
    }
}
