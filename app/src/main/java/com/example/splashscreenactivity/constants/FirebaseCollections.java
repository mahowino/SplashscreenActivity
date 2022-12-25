package com.example.splashscreenactivity.constants;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

public class FirebaseCollections {
    private static final String STORE_PATH="goods/";
    public static final CollectionReference STORE_REFERENCE=FirebaseInit.db.collection(STORE_PATH);

}
