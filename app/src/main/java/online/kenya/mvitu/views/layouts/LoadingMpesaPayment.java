package online.kenya.mvitu.views.layouts;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.Toast;



import online.kenya.mvitu.R;
import online.kenya.mvitu.constants.FirebaseInit;
import com.google.firebase.firestore.DocumentChange;

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
