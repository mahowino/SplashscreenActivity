package online.kenya.mvitu.constants;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseInit {
     public static final FirebaseAuth mAuth= FirebaseAuth.getInstance();
     public static final FirebaseFirestore db= FirebaseFirestore.getInstance();
     // Create a Cloud Storage reference from the app
     public static final FirebaseStorage storage=FirebaseStorage.getInstance();
     public static final StorageReference reference=storage.getReference();

}
