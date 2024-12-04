package com.azhar.donasi.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.azhar.donasi.databinding.ActivityLoginBinding;
import com.azhar.donasi.util.SharedPreferencesHelper;
import com.azhar.donasi.viewmodel.LoginViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this);

        // Cek jika USERNAME sudah ada di SharedPreferences, langsung ke MainActivity
        if (!Objects.equals(sharedPreferencesHelper.getUsernamePreferences(), SharedPreferencesHelper.USERNAME_PREFERENCES)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        setListeners();
    }

    private void setListeners() {
        binding.btnSignIn.setOnClickListener(v -> {
            if (isValid()) {
                String username = binding.edUsername.getText().toString();
                String password = binding.edPassword.getText().toString();

                loginViewModel.loginUser().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isLoginSuccessful = false;

                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            String storedUsername = userSnapshot.getKey();
                            String storedPassword = userSnapshot.getValue(String.class);

                            if (storedUsername != null && storedUsername.equals(username)) {
                                if (storedPassword != null && storedPassword.equals(password)) {
                                    isLoginSuccessful = true;

                                    // Simpan USERNAME ke SharedPreferences
                                    sharedPreferencesHelper.writeUsernamePreferences(storedUsername);

                                    // Pindah ke MainActivity
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                    break;
                                } else {
                                    Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }

                        if (isLoginSuccessful) {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
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