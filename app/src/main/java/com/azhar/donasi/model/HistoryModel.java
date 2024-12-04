package com.azhar.donasi.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class HistoryModel implements Parcelable {

    public String keterangan;
    public String tipeDonasi;
    public double jmlUang;

    public HistoryModel() {
    }

    protected HistoryModel(Parcel in) {
        tipeDonasi = in.readString();
        keterangan = in.readString();
        jmlUang = in.readDouble();
    }

    public static final Creator<HistoryModel> CREATOR = new Creator<HistoryModel>() {
        @Override
        public HistoryModel createFromParcel(Parcel in) {
            return new HistoryModel(in);
        }

        @Override
        public HistoryModel[] newArray(int size) {
            return new HistoryModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.keterangan);
        dest.writeString(this.tipeDonasi);
        dest.writeDouble(this.jmlUang);
    }

}
