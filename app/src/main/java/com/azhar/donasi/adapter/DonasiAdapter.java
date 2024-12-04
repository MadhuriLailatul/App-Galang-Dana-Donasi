package com.azhar.donasi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.azhar.donasi.R;
import com.azhar.donasi.model.DonasiModel;

import java.util.List;

public class DonasiAdapter extends RecyclerView.Adapter<DonasiAdapter.ViewHolder> {

    List<DonasiModel> modelDatabase;
    Context mContext;
    DonasiAdapterCallback mAdapterCallback;

    public DonasiAdapter(Context context, List<DonasiModel> modelInputList,
                         DonasiAdapterCallback adapterCallback) {
        this.mContext = context;
        this.modelDatabase = modelInputList;
        this.mAdapterCallback = adapterCallback;
    }

    public void setDataAdapter(List<DonasiModel> items) {
        modelDatabase.clear();
        modelDatabase.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public DonasiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ajuan_donasi,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DonasiAdapter.ViewHolder holder, int position) {
        final DonasiModel data = modelDatabase.get(position);

        holder.tvNamaPengajuan.setText(data.namaPengajuan);
        holder.tvAlasanPengajuan.setText(data.alasanPengajuan);
    }

    @Override
    public int getItemCount() {
        return modelDatabase.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNamaPengajuan, tvAlasanPengajuan;
        public LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNamaPengajuan = itemView.findViewById(R.id.tv_nama_pengajuan);
            tvAlasanPengajuan = itemView.findViewById(R.id.tv_alasan_pengajuan);
            layout = itemView.findViewById(R.id.root_layout);

            layout.setOnClickListener(view -> {
                mAdapterCallback.onClick(new DonasiModel());
            });
        }
    }

    public interface DonasiAdapterCallback {
        void onClick(DonasiModel modelDatabase);
    }

}
