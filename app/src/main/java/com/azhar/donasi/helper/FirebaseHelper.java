package com.azhar.donasi.helper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {
    private static volatile FirebaseHelper INSTANCE;

    public FirebaseDatabase database;

    private FirebaseHelper() {
        database = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getUserPathDatabase() {
        return database.getReference("Users");
    }

    public DatabaseReference getDonasiPathDatabase() {
        return database.getReference("Donasi");
    }

    public DatabaseReference getHistoryPathDatabase() {
        return database.getReference("History");
    }

    public static FirebaseHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (FirebaseHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FirebaseHelper();
                }
            }
        }
        return INSTANCE;
    }
}
