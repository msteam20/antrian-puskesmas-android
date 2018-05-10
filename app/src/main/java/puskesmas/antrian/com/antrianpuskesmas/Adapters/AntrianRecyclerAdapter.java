package puskesmas.antrian.com.antrianpuskesmas.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import okhttp3.internal.http2.Header;
import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.models.Antrian;

public class AntrianRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> list;
    private Context context;

    public AntrianRecyclerAdapter(Context context, List<Object> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch(viewType){
            case 1:
                return new HeaderTitleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_header_title, parent, false));
            case 2:
                return new AntrianViewHolder(LayoutInflater.from(context).inflate(R.layout.item_antrian, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderTitleViewHolder){
            ((HeaderTitleViewHolder) holder).textView.setText((String) list.get(position));
        } else if(holder instanceof AntrianViewHolder) {
            Antrian antrian = (Antrian) list.get(position);
            ((AntrianViewHolder) holder).pasien.setText(antrian.pasien.nama);
            ((AntrianViewHolder) holder).poli.setText(antrian.poli.nama);
            ((AntrianViewHolder) holder).no_antrian.setText(antrian.no_antrian);
            ((AntrianViewHolder) holder).w_antrian.setText(antrian.w_antrian);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) instanceof String ? 1 : 2;
    }

    class HeaderTitleViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public HeaderTitleViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
        }
    }

    class AntrianViewHolder extends RecyclerView.ViewHolder{
        TextView pasien;
        TextView poli;
        TextView no_antrian;
        TextView w_antrian;

        public AntrianViewHolder(View itemView) {
            super(itemView);
            pasien = itemView.findViewById(R.id.pasien);
            poli = itemView.findViewById(R.id.poli);
            no_antrian = itemView.findViewById(R.id.no_urut);
            w_antrian = itemView.findViewById(R.id.w_antrian);
        }
    }
}
