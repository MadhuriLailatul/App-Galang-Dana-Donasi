package com.azhar.donasi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.azhar.donasi.helper.FirebaseHelper;
import com.azhar.donasi.model.DonasiModel;
import com.azhar.donasi.model.HistoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HistoryViewModel extends AndroidViewModel {
    private final MutableLiveData<List<HistoryModel>> _historyModelList = new MutableLiveData<>();
    public LiveData<List<HistoryModel>> historyModelList = _historyModelList;

    private final FirebaseHelper firebaseHelper;
    private final String username;

    public HistoryViewModel(@NonNull Application application, String username) {
        super(application);

        firebaseHelper = FirebaseHelper.getInstance();
        this.username = username;

        getHistoryList();
    }

    private void getHistoryList() {
        firebaseHelper.getHistoryPathDatabase().child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HistoryModel> historyList = new ArrayList<>();
                for (DataSnapshot historySnapshot : snapshot.getChildren()) {
                    HistoryModel history = historySnapshot.getValue(HistoryModel.class);
                    historyList.add(history);
                }
                _historyModelList.postValue(historyList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteDataById(final int uid) {
//        Completable.fromAction(() -> databaseDao.deleteSingleData(uid))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
    }
}