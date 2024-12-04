package com.azhar.donasi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.azhar.donasi.helper.FirebaseHelper;
import com.azhar.donasi.model.DonasiModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PilihanDonasiViewModel extends AndroidViewModel {
    private final MutableLiveData<List<DonasiModel>> _donasiModelList = new MutableLiveData<>();
    public LiveData<List<DonasiModel>> donasiModelList = _donasiModelList;

    private final FirebaseHelper firebaseHelper;

    public PilihanDonasiViewModel(@NonNull Application application, String donasiType) {
        super(application);

        firebaseHelper = FirebaseHelper.getInstance();
        getDonasiList(donasiType);
    }

    private void getDonasiList(String donasiType) {
        firebaseHelper.getDonasiPathDatabase().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<DonasiModel> donasiList = new ArrayList<>();
                for (DataSnapshot donasiSnapshot : snapshot.getChildren()) {
                    DonasiModel donasi = donasiSnapshot.getValue(DonasiModel.class);
                    if (donasi != null && donasi.getTipeDonasi().equals(donasiType)) {
                        donasiList.add(donasi);
                    }
                }
                _donasiModelList.postValue(donasiList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}