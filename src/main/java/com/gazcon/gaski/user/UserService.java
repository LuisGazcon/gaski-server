package com.gazcon.gaski.user;

import java.util.concurrent.ExecutionException;
import java.util.HashMap;
import java.util.Map;

import com.gazcon.gaski.global.utils.UserUtils;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;

@Service()
public class UserService {

    private static final String COLLECTION_NAME = "users";

    private Firestore firestore = FirestoreClient.getFirestore();

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    
    private CollectionReference users;

    public UserService() {
        this.users = firestore.collection(COLLECTION_NAME);
    }

    public User getUserById(String id) throws ExecutionException, InterruptedException {
        DocumentReference documentRef = users.document(id);
        ApiFuture<DocumentSnapshot> future = documentRef.get();
        DocumentSnapshot document = future.get();
        return document.toObject(User.class);
    }

    public Boolean emailExist(String email) throws Exception {
        Query query = users.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot documents = future.get();
        return !documents.isEmpty();
    }

    public Boolean usernameExist(String username) throws Exception {
        Query query = users.whereEqualTo("username", username);
        ApiFuture<QuerySnapshot> future = query.get();
        QuerySnapshot documents = future.get();
        return !documents.isEmpty();
    }

    public User create(Map<String, String> data) throws Exception {
        CreateRequest request = new CreateRequest()
            .setEmail(data.get("email"))
            .setEmailVerified(false)
            .setPassword(data.get("password"))
            .setDisabled(false);
        UserRecord userRecord = auth.createUser(request);
        ApiFuture<WriteResult> future = firestore.document(userRecord.getUid()).set(
            normalizeData(data)
        );
    }

    public Map<String, String> normalizeData(Map<String, String> data) {
        HashMap<String, String> normalized = new HashMap<String, String>();
        normalized.put("email", UserUtils.formatEmail(data.get("email")));
        normalized.put("username", UserUtils.formatUsername(data.get("username")));
        normalized.put("firstName", UserUtils.formatName(data.get("firstName")));
        normalized.put("lastName", UserUtils.formatName(data.get("lastName")));
        return normalized;
    }
}
