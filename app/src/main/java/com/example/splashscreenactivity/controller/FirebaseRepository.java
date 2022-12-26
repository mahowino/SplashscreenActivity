package com.example.splashscreenactivity.controller;

import com.example.splashscreenactivity.constants.FirebaseInit;
import com.example.splashscreenactivity.interfaces.Callback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

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

}
