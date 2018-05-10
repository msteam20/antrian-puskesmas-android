package puskesmas.antrian.com.antrianpuskesmas.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.models.Poli;

public class PoliRecyclerAdapter extends RecyclerView.Adapter<PoliRecyclerAdapter.PoliViewHolder> {
    private List<Poli> poli;
    private Context context;
    private OnItemClickListener listener;

    public PoliRecyclerAdapter(Context context, List<Poli> poli, OnItemClickListener listener) {
        this.poli = poli;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PoliViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_poli, parent, false);
        return new PoliViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoliViewHolder holder, int position) {
        final Poli currentPoli = poli.get(position);
        Glide.with(context).load(currentPoli.icon).into(holder.icon);
        holder.title.setText(currentPoli.nama);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Passing data ke Fragment
                listener.onClick(currentPoli);
            }
        });
    }

    @Override
    public int getItemCount() {
        return poli.size();
    }

    class PoliViewHolder extends RecyclerView.ViewHolder{
        public ImageView icon;
        public TextView title;
        public View view;
        public PoliViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            icon = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textView);
        }
    }

    public interface OnItemClickListener{
        void onClick(Poli poli);
    }
}
