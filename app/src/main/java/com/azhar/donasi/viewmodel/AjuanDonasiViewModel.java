package com.azhar.donasi.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.azhar.donasi.helper.FirebaseHelper;
import com.azhar.donasi.model.DonasiModel;
import com.google.android.gms.tasks.Task;

public class AjuanDonasiViewModel extends AndroidViewModel {
    public MutableLiveData<Uri> imageUri = new MutableLiveData<>(null);

    private final FirebaseHelper firebaseHelper;

    public AjuanDonasiViewModel(@NonNull Application application) {
        super(application);

        firebaseHelper = FirebaseHelper.getInstance();
    }

    public Task<Void> addAjuanDonasi(final String tipeDonasi, final String namaPengajuan, final String alasanDonasi, final int rekening, final String lampiranBukti) {
        DonasiModel donasiModel = new DonasiModel();
        donasiModel.tipeDonasi = tipeDonasi;
        donasiModel.namaPengajuan = namaPengajuan;
        donasiModel.alasanPengajuan = alasanDonasi;
        donasiModel.nomorRekening = rekening;

        return firebaseHelper.getDonasiPathDatabase().push().setValue(donasiModel);
    }

}
