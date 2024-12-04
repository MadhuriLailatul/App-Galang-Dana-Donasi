package com.azhar.donasi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.azhar.donasi.helper.FirebaseHelper;
import com.google.android.gms.tasks.Task;

public class RegisterViewModel extends AndroidViewModel {

    private final FirebaseHelper firebaseHelper;

    public RegisterViewModel(@NonNull Application application) {
        super(application);

        firebaseHelper = FirebaseHelper.getInstance();
    }

    public Task<Void> regsiterUser(String username, String password) {
        return firebaseHelper.getUserPathDatabase().child(username).setValue(password);
    }
}