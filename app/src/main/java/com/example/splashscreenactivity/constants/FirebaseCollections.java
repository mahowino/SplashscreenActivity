package com.example.splashscreenactivity.constants;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

public class FirebaseCollections {
    private static final String STORE_PATH="goods/";
    private static final String CUSTOMER_PATH="customers";
    private static final String VOUCHER_PATH="vouchers";
    private static final String CATEGORY_PATH="categories";

    public static final CollectionReference STORE_REFERENCE=FirebaseInit.db.collection(STORE_PATH);
    public static final CollectionReference CUSTOMER_REFERENCE=FirebaseInit.db.collection(CUSTOMER_PATH);
    public static final CollectionReference VOUCHER_REFERENCE=FirebaseInit.db.collection(VOUCHER_PATH);
    public static final CollectionReference CATEGORY_REFERENCE=FirebaseInit.db.collection(CATEGORY_PATH);
}
