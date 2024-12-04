package com.azhar.donasi.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.azhar.donasi.R;
import com.azhar.donasi.databinding.ActivityAjukanDonasiBinding;
import com.azhar.donasi.util.Constants;
import com.azhar.donasi.viewmodel.AjuanDonasiViewModel;
import com.azhar.donasi.util.SharedPreferencesHelper;

import java.util.Objects;

public class AjukanDonasiActivity extends AppCompatActivity {
    private ActivityAjukanDonasiBinding binding;
    private AjuanDonasiViewModel ajuanDonasiViewModel;
    private SharedPreferencesHelper sharedPreferencesHelper;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    ajuanDonasiViewModel.imageUri.postValue(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAjukanDonasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ajuanDonasiViewModel = new ViewModelProvider(this).get(AjuanDonasiViewModel.class);
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this);

        // Ambil USER_NAME dari SharedPreferences dan tampilkan di EditText
        String userName = sharedPreferencesHelper.getUsernamePreferences();
        if (!userName.equals(SharedPreferencesHelper.USERNAME_PREFERENCES)) {
            binding.edNamaPengajuan.setText(userName); // Set nama pengguna otomatis
            binding.edNamaPengajuan.setEnabled(false); // Nonaktifkan input
            binding.edNamaPengajuan.setFocusable(false); // Hilangkan fokus
            binding.edNamaPengajuan.setTextColor(ContextCompat.getColor(this, R.color.black)); // Pastikan warna teks terlihat
        } else {
            Toast.makeText(this, "Gagal mengambil nama pengguna", Toast.LENGTH_SHORT).show();
        }

        viewModelObserver();
        setSpinner();
        setListeners();
    }

    private void viewModelObserver() {
        ajuanDonasiViewModel.imageUri.observe(this, uri -> {
            binding.ivLampiran.setImageURI(uri);
        });
    }

    private void setSpinner() {
        String[] type = new String[]{Constants.DONASI_KEMANUSIAAN, Constants.DONASI_TANAMAN};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        type);

        binding.jenisSpinner.setAdapter(adapter);
    }

    private void setListeners() {
        binding.btnImagePicker.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        binding.btnSimpan.setOnClickListener(v -> {
            if (isValid()) {
                String jenisDonasi = binding.jenisSpinner.getSelectedItem().toString();
                String namaPengajuan = Objects.requireNonNull(binding.edNamaPengajuan.getText()).toString();
                String alasanPengajuan = Objects.requireNonNull(binding.edAlasanPengajuan.getText()).toString();
                int rekening = Integer.parseInt(Objects.requireNonNull(binding.edRekening.getText()).toString());
                String lampiranBukti = Objects.requireNonNull(ajuanDonasiViewModel.imageUri.getValue()).toString();

                ajuanDonasiViewModel.addAjuanDonasi(jenisDonasi, namaPengajuan, alasanPengajuan, rekening, lampiranBukti).addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Ajuan Donasi telah dibuat!", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(AjukanDonasiActivity.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    private boolean isValid() {
        if (binding.jenisSpinner.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(this, "Isi Jenis dengan benar!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Objects.requireNonNull(binding.edNamaPengajuan.getText()).toString().isEmpty()) {
            Toast.makeText(this, "Isi Nama dengan benar!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Objects.requireNonNull(binding.edAlasanPengajuan.getText()).toString().isEmpty()) {
            Toast.makeText(this, "Isi Alasan dengan benar!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Objects.requireNonNull(binding.edRekening.getText()).toString().isEmpty()) {
            Toast.makeText(this, "Isi Rekening dengan benar!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ajuanDonasiViewModel.imageUri.getValue() == null) {
            Toast.makeText(this, "Isi Foto Lampiran dengan benar!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}