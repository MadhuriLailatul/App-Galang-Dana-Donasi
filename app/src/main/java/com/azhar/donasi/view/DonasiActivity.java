package com.azhar.donasi.view;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.azhar.donasi.R;
import com.azhar.donasi.util.FunctionHelper;
import com.azhar.donasi.util.SharedPreferencesHelper;
import com.azhar.donasi.viewmodel.DonasiViewModel;
import com.azhar.donasi.viewmodel.PilihanDonasiViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class DonasiActivity extends AppCompatActivity {

    Chip chipOne, chipTwo, chipThree;
    TextInputEditText etJmlUang, etKeterangan;
    BottomSheetDialog bottomSheetDialog;
    AppCompatButton btnDonasi;
    LinearLayout linearVA;
    int strNominal = 0;
    String strKeterangan = "";
    DonasiViewModel donasiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi);

        setId();
        setInitLayout();
    }

    private void setId() {
        chipOne = findViewById(R.id.chipOne);
        chipTwo = findViewById(R.id.chipTwo);
        chipThree = findViewById(R.id.chipThree);
        etJmlUang = findViewById(R.id.etJmlUang);
        etKeterangan = findViewById(R.id.etKeterangan);
        linearVA = findViewById(R.id.linearVA);
        btnDonasi = findViewById(R.id.btnDonasi);
    }

    private void setInitLayout() {
        donasiViewModel = new ViewModelProvider(
                this,
                new DonasiViewModelFactory(getApplication(), this)
        ).get(DonasiViewModel.class);

        chipOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chipOne.isChecked()) {
                    strNominal = 10000;
                    setNominal(strNominal);
                } else {
                    strNominal = 0;
                    setNominal(strNominal);
                }
            }
        });

        chipTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chipTwo.isChecked()) {
                    strNominal = 20000;
                    setNominal(strNominal);
                } else {
                    strNominal = 0;
                    setNominal(strNominal);
                }
            }
        });

        chipThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chipThree.isChecked()) {
                    strNominal = 50000;
                    setNominal(strNominal);
                } else {
                    strNominal = 0;
                    setNominal(strNominal);
                }
            }
        });

        linearVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet, null);

                bottomSheetDialog = new BottomSheetDialog(DonasiActivity.this);
                bottomSheetDialog.setContentView(dialogView);
                bottomSheetDialog.show();
            }
        });

        btnDonasi.setOnClickListener(v -> {
            strKeterangan = Objects.requireNonNull(etKeterangan.getText()).toString();
            strNominal = Integer.parseInt(Objects.requireNonNull(etJmlUang.getText()).toString());
            if (strKeterangan.equals("0") || strKeterangan.isEmpty()) {
                Toast.makeText(DonasiActivity.this, "Data tidak boleh ada yang kosong!",
                        Toast.LENGTH_SHORT).show();
            } else {
                donasiViewModel.addDonasi(strKeterangan, strNominal).addOnSuccessListener(unused -> {
                    Toast.makeText(DonasiActivity.this,
                            "Terima kasih donasinya, cek di menu riwayat ya!", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(DonasiActivity.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void setNominal(int nominal) {
        etJmlUang.setText(String.valueOf(nominal));
    }

}

class DonasiViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final String username;

    public DonasiViewModelFactory(Application application, Activity activity) {
        this.application = application;

        SharedPreferencesHelper sharedPreferencesHelper = SharedPreferencesHelper.getInstance(activity);
        this.username = sharedPreferencesHelper.getUsernamePreferences();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DonasiViewModel.class)) {
            return (T) new DonasiViewModel(application, username);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}