package com.azhar.donasi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.azhar.donasi.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

public class LoginViewModel extends AndroidViewModel {

    private final FirebaseHelper firebaseHelper;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        firebaseHelper = FirebaseHelper.getInstance();
    }

    public DatabaseReference loginUser() {
        return firebaseHelper.getUserPathDatabase();
    }

}
