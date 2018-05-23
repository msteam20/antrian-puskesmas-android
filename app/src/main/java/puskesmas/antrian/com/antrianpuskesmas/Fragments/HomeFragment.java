package puskesmas.antrian.com.antrianpuskesmas.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import puskesmas.antrian.com.antrianpuskesmas.Activities.AntrianActivity;
import puskesmas.antrian.com.antrianpuskesmas.Adapters.PoliRecyclerAdapter;
import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.etc.Const;
import puskesmas.antrian.com.antrianpuskesmas.models.Pasien;
import puskesmas.antrian.com.antrianpuskesmas.models.Poli;

public class HomeFragment extends Fragment implements PoliRecyclerAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private List<Pasien> pasien = new ArrayList<>();
    private PoliRecyclerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPasien();
    }

    private void setRecyclerView (List<Poli> poli){
        adapter = new PoliRecyclerAdapter(getContext(), poli, this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void loadData(){
        // Load Data Poli
        AndroidNetworking.get(Const.POLI)
            .setTag("Mendapatkan List Poli")
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    List<Poli> poli = new Gson().fromJson(response, new TypeToken<List<Poli>>() {
                    }.getType());

                    setRecyclerView(poli);
                }

                @Override
                public void onError(ANError anError) {
                    Log.e("error", anError.getErrorBody());
                }
            });
    }

    void loadPasien(){
        // Load data pasien
        AndroidNetworking.get(Const.PASIEN)
                .setTag("Mendapatkan List Pasien")
                .addQueryParameter("id_masyarakat", "1")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        List<Pasien> pasiens = new Gson().fromJson(response, new TypeToken<List<Pasien>>() {
                        }.getType());

                        pasien = pasiens;
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", anError.getErrorBody());
                    }
                });
    }

    @Override
    public void onClick(Poli poli) {
        showDialogPilihPasien(poli);
    }


    private void requestAntrian(Poli poli, int id_pasien){
        AndroidNetworking.post(Const.ANTRIAN)
                .addBodyParameter("id_poli", String.valueOf(poli.id))
                .addBodyParameter("id_pasien", String.valueOf(id_pasien))
                .setTag("Request Antrian")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = response.getInt("id");
                            Intent intent = new Intent(getContext(), AntrianActivity.class);
                            intent.putExtra("id", id);
                            getContext().startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    void showDialogPilihPasien (final Poli poli) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_pilih_pasien);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView textView = dialog.findViewById(R.id.poli);
        final MaterialSpinner spinner = dialog.findViewById(R.id.spinner);
        List<String> pasiens = new ArrayList<>();
        for (Pasien pas : pasien) {
            pasiens.add(pas.nama+" "+ (pas.no_askes == null ? "" : pas.no_askes));
        }

        spinner.setItems(pasiens);
        Button btn_ok = dialog.findViewById(R.id.btn_ok);
        Button btn_batal = dialog.findViewById(R.id.btn_batal);

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestAntrian(poli, pasien.get(spinner.getSelectedIndex()).id);
                dialog.dismiss();
            }
        });

        textView.setText(poli.nama);

        dialog.show();

        dialog.getWindow().setAttributes(lp);

    }
}
