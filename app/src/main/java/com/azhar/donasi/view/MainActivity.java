package com.azhar.donasi.view;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.donasi.R;
import com.azhar.donasi.util.Constants;
import com.azhar.donasi.util.FunctionHelper;
import com.azhar.donasi.util.SharedPreferencesHelper;
import com.azhar.donasi.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    CardView cvDonasiOne, cvDonasiTwo, cvDonasiThree, cvDonasiFour;
    TextView tvDonasi, tvUsername;
    ProgressBar progressCount;
    MainViewModel mainViewModel;

    private SharedPreferencesHelper sharedPreferencesHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(this);

        setId();
        setInitLayout();

        this.getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to Log Out?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sharedPreferencesHelper.clearAll();
                                finishAffinity();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void setId() {
        tvUsername = findViewById(R.id.tv_username);
        cvDonasiOne = findViewById(R.id.cvDonasiOne);
        cvDonasiTwo = findViewById(R.id.cvDonasiTwo);
        cvDonasiThree = findViewById(R.id.cvDonasiThree);
        cvDonasiFour = findViewById(R.id.cvDonasiFour);
        tvDonasi = findViewById(R.id.tvDonasi);
        progressCount = findViewById(R.id.progressCount);
    }

    private void setInitLayout() {
        tvUsername.setText(sharedPreferencesHelper.getUsernamePreferences());

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        mainViewModel.getTotalDonasi().observe(this,
//                new Observer<Integer>() {
//                    @Override
//                    public void onChanged(Integer integer) {
//                        //progress count bisa diubah sesuai keinginan
//                        if (integer == null) {
//                            int totalDonasi = 0;
//                            String strJmlDonasi = FunctionHelper.rupiahFormat(totalDonasi);
//                            tvDonasi.setText(strJmlDonasi + " sudah terkumpul dari Rp10.000.000");
//                            progressCount.setMax(10000000);
//                            progressCount.setProgress(totalDonasi);
//                        } else {
//                            int totalDonasi = integer;
//                            String strJmlDonasi = FunctionHelper.rupiahFormat(totalDonasi);
//                            tvDonasi.setText(strJmlDonasi + " sudah terkumpul dari Rp10.000.000");
//                            progressCount.setMax(10000000);
//                            progressCount.setProgress(totalDonasi);
//                        }
//                    }
//                });

        cvDonasiOne.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PilihanDonasiActivity.class);
            intent.putExtra(PilihanDonasiActivity.EXTRA_DONASI_TYPE, Constants.DONASI_KEMANUSIAAN);
            startActivity(intent);
        });

        cvDonasiTwo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PilihanDonasiActivity.class);
            intent.putExtra(PilihanDonasiActivity.EXTRA_DONASI_TYPE, Constants.DONASI_TANAMAN);
            startActivity(intent);
        });

        cvDonasiThree.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        cvDonasiFour.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AjukanDonasiActivity.class);
            startActivity(intent);
        });
    }

}