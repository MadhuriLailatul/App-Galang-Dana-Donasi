package com.azhar.donasi.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class DonasiModel implements Parcelable {

    public String namaPengajuan;
    public String alasanPengajuan;
    public String tipeDonasi;
    public int nomorRekening;

    public DonasiModel() {
    }

    protected DonasiModel(Parcel in) {
        tipeDonasi = in.readString();
        namaPengajuan = in.readString();
        alasanPengajuan = in.readString();
        nomorRekening = in.readInt();
    }

    public String getNamaPengajuan() {
        return namaPengajuan;
    }

    public String getAlasanPengajuan() {
        return alasanPengajuan;
    }

    public String getTipeDonasi() {
        return tipeDonasi;
    }

    public int getNomorRekening() {
        return nomorRekening;
    }

    public static final Creator<DonasiModel> CREATOR = new Creator<DonasiModel>() {
        @Override
        public DonasiModel createFromParcel(Parcel in) {
            return new DonasiModel(in);
        }

        @Override
        public DonasiModel[] newArray(int size) {
            return new DonasiModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.namaPengajuan);
        dest.writeString(this.alasanPengajuan);
        dest.writeString(this.tipeDonasi);
        dest.writeDouble(this.nomorRekening);
    }
}
