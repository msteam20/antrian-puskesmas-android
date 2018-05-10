package puskesmas.antrian.com.antrianpuskesmas.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.models.Pasien;

public class PasienRecyclerAdapter extends RecyclerView.Adapter<PasienRecyclerAdapter.PasienViewHolder> {
    private Context context;
    private List<Pasien> pasiens;

    public PasienRecyclerAdapter(Context context, List<Pasien> pasiens) {
        this.context = context;
        this.pasiens = pasiens;
    }

    @NonNull
    @Override
    public PasienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PasienViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pasien, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PasienViewHolder holder, int position) {
        Pasien pasien = pasiens.get(position);
        holder.nama.setText(pasien.nama);
        holder.akses.setText(pasien.no_askes);
        holder.ttl.setText(pasien.tempat_lahir+", "+pasien.tgl_lahir);
    }

    @Override
    public int getItemCount() {
        return pasiens.size();
    }

    class PasienViewHolder extends RecyclerView.ViewHolder{
        TextView nama, akses, ttl;
        public PasienViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            akses = itemView.findViewById(R.id.no_askes);
            ttl = itemView.findViewById(R.id.ttl);
        }
    }
}
