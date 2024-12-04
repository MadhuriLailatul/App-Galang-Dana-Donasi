package com.azhar.donasi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.azhar.donasi.helper.FirebaseHelper;
import com.azhar.donasi.model.HistoryModel;
import com.google.android.gms.tasks.Task;

public class DonasiViewModel extends AndroidViewModel {

    private final FirebaseHelper firebaseHelper;
    private final String username;

    public DonasiViewModel(@NonNull Application application, String username) {
        super(application);

        firebaseHelper = FirebaseHelper.getInstance();
        this.username = username;
    }

    public Task<Void> addDonasi(final String note, final int price) {
        HistoryModel historyModel = new HistoryModel();
        historyModel.keterangan = note;
        historyModel.jmlUang = price;

        return firebaseHelper.getHistoryPathDatabase().child(username).push().setValue(historyModel);
    }
}
