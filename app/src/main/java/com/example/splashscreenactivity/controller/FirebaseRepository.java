package com.example.splashscreenactivity.controller;

import com.example.splashscreenactivity.constants.FirebaseCollections;
import com.example.splashscreenactivity.constants.FirebaseFields;
import com.example.splashscreenactivity.constants.FirebaseInit;
import com.example.splashscreenactivity.interfaces.Callback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class FirebaseRepository {
    public static CollectionReference createCollectionReference(String path) {
        return FirebaseInit.db.collection(path);
    }
    public static void setDocument(Map map, DocumentReference reference, final Callback callback) {
        reference.set(map).addOnCompleteListener(task -> runTaskValidation(task, callback));
    }
    private static void runTaskValidation(Task task, Callback callback) {
        if (task.isSuccessful()) callback.onSuccess(task);
        else callback.onError("fail");
    }
    public static Query createQuery(CollectionReference reference, String key, String value) {
        return reference.whereEqualTo(key, value);
    }
    public static void getDocumentsFromQueryInCollection(Query query, final Callback callback) {
        query.get().addOnCompleteListener(task -> runTaskValidation(task, callback));
    }

    public static void searchVoucherByCode(String voucherCode,Callback callback){

        //QUERY
        Query query=createQuery(FirebaseCollections.VOUCHER_REFERENCE, FirebaseFields.VOUCHER_CODE,voucherCode);

        getDocumentsFromQueryInCollection(query, new Callback() {
            @Override
            public void onSuccess(Object object) {
                Task<QuerySnapshot> task=(Task<QuerySnapshot>)object;
                for (DocumentSnapshot snapshot:task.getResult()){

                    if (snapshot.exists()){
                        //network Exists
                        callback.onSuccess(snapshot);
                    }
                    else callback.onError(null);
                }
                callback.onError(null);

            }

            @Override
            public void onError(Object object) {
                callback.onError(object);
            }
        });
    }
    public static void checkIfCodeExists(String code, Callback callback) {
        FirebaseRepository.searchVoucherByCode(code, new Callback() {
            @Override
            public void onSuccess(Object object) {
                //document exists hence a failure
                callback.onError(object);
            }

            @Override
            public void onError(Object object) {
                //document does not exist unless a db failure.
                String message = (String) object;
                if (message == null) callback.onSuccess(object);
                else callback.onError(object);
            }
        });

    }

}
