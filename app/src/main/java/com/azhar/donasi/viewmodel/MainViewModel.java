package com.azhar.donasi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    LiveData<Integer> integerLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<Integer> getTotalDonasi() {
        return integerLiveData;
    }

}
