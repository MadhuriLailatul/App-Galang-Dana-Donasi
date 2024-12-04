package com.azhar.donasi.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.azhar.donasi.databinding.ActivityRegisterBinding;
import com.azhar.donasi.viewmodel.LoginViewModel;
import com.azhar.donasi.viewmodel.RegisterViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private RegisterViewModel registerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        setListeners();
    }

    private void setListeners() {
        binding.btnSignUp.setOnClickListener(v -> {
            if (isValid()) {
                String username = binding.edUsername.getText().toString();
                String password = binding.edPassword.getText().toString();

                registerViewModel.regsiterUser(username, password).addOnSuccessListener(unused -> {
                    Toast.makeText(RegisterActivity.this, "User telah dibuat! Silahkan Login.", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, "Error : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        binding.btnSignIn.setOnClickListener(v -> {
            finish();
        });
    }

    private boolean isValid() {
        if (binding.edUsername.getText().toString().isEmpty()) {
            Toast.makeText(this, "Isi Username dengan benar!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.edPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Isi Password dengan benar!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}