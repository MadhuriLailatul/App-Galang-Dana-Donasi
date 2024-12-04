package com.azhar.donasi.view;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.azhar.donasi.adapter.DonasiAdapter;
import com.azhar.donasi.databinding.ActivityPilihanDonasiBinding;
import com.azhar.donasi.model.DonasiModel;
import com.azhar.donasi.viewmodel.PilihanDonasiViewModel;

import java.util.ArrayList;
import java.util.List;

public class PilihanDonasiActivity extends AppCompatActivity implements DonasiAdapter.DonasiAdapterCallback {
    public static String EXTRA_DONASI_TYPE = "extra_donasi_type";
    private String donasiType;
    List<DonasiModel> modelDatabaseList = new ArrayList<>();


    private ActivityPilihanDonasiBinding binding;
    private PilihanDonasiViewModel pilihanDonasiViewModel;

    private DonasiAdapter donasiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPilihanDonasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        donasiType = getIntent().getStringExtra(EXTRA_DONASI_TYPE);
        pilihanDonasiViewModel = new ViewModelProvider(
                this,
                new PilihanDonasiViewModelFactory(getApplication(), donasiType)
        ).get(PilihanDonasiViewModel.class);

        pilihanDonasiViewModel.donasiModelList.observe(this, listDonasi -> {
            if (listDonasi.isEmpty()) {
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.rvDonasi.setVisibility(View.GONE);
            } else {
                binding.tvEmpty.setVisibility(View.GONE);
                binding.rvDonasi.setVisibility(View.VISIBLE);

                donasiAdapter = new DonasiAdapter(this, listDonasi, this);

                binding.rvDonasi.setHasFixedSize(true);
                binding.rvDonasi.setLayoutManager(new LinearLayoutManager(this));
                binding.rvDonasi.setAdapter(donasiAdapter);
            }
        });

        setInitLayout();
    }

    private void setInitLayout() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.tvToolbarTitle.setText("Pilihan Donasi " + donasiType);

    }

    @Override
    public void onClick(DonasiModel modelDatabase) {
        Intent intent = new Intent(this, DonasiActivity.class);
        startActivity(intent);
    }
}

class PilihanDonasiViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final String donasiType;

    public PilihanDonasiViewModelFactory(Application application, String donasiType) {
        this.application = application;
        this.donasiType = donasiType;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PilihanDonasiViewModel.class)) {
            return (T) new PilihanDonasiViewModel(application, donasiType);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}