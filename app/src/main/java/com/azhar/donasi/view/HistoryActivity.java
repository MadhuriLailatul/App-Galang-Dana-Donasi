package com.azhar.donasi.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.donasi.R;
import com.azhar.donasi.adapter.HistoryAdapter;
import com.azhar.donasi.model.HistoryModel;
import com.azhar.donasi.util.SharedPreferencesHelper;
import com.azhar.donasi.viewmodel.DonasiViewModel;
import com.azhar.donasi.viewmodel.HistoryViewModel;
import com.azhar.donasi.viewmodel.PilihanDonasiViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements HistoryAdapter.RiwayatAdapterCallback {

    List<HistoryModel> historyModelList = new ArrayList<>();
    HistoryAdapter historyAdapter;
    HistoryViewModel historyViewModel;
    Toolbar toolbar;
    RecyclerView rvHistory;
    TextView tvNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setId();
        setInitLayout();
    }

    private void setId() {
        toolbar = findViewById(R.id.toolbar);
        rvHistory = findViewById(R.id.rvHistory);
        tvNotFound = findViewById(R.id.tvNotFound);
    }

    private void setInitLayout() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        tvNotFound.setVisibility(View.GONE);

        historyAdapter = new HistoryAdapter(this, historyModelList, this);
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(historyAdapter);

        historyViewModel = new ViewModelProvider(
                this,
                new HistoryViewModelFactory(getApplication(), this)
        ).get(HistoryViewModel.class);
        historyViewModel.historyModelList.observe(this, modelDatabases -> {
            if (!modelDatabases.isEmpty()) {
                historyAdapter.setDataAdapter(modelDatabases);
            } else {
                tvNotFound.setVisibility(View.VISIBLE);
                rvHistory.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDelete(HistoryModel historyModel) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Hapus riwayat ini?");
        alertDialogBuilder.setPositiveButton("Ya, Hapus", (dialogInterface, i) -> {
            int uid = 0;
            historyViewModel.deleteDataById(uid);
            Toast.makeText(HistoryActivity.this, "Data yang dipilih sudah dihapus",
                    Toast.LENGTH_SHORT).show();
        });

        alertDialogBuilder.setNegativeButton("Batal", (dialogInterface, i) -> dialogInterface.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

class HistoryViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final String username;

    public HistoryViewModelFactory(Application application, Activity activity) {
        this.application = application;

        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(activity);
        this.username = sharedPreferencesHelper.getUsernamePreferences();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HistoryViewModel.class)) {
            return (T) new HistoryViewModel(application, username);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}