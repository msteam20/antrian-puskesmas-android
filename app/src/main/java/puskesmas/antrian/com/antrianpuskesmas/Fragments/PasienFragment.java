package puskesmas.antrian.com.antrianpuskesmas.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import puskesmas.antrian.com.antrianpuskesmas.Activities.NewPasienActivity;
import puskesmas.antrian.com.antrianpuskesmas.Adapters.PasienRecyclerAdapter;
import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.etc.Const;
import puskesmas.antrian.com.antrianpuskesmas.models.Pasien;

import static android.app.Activity.RESULT_OK;

public class PasienFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    List<Pasien> pasiens = new ArrayList<>();
    PasienRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        fab = view.findViewById(R.id.fab);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getContext(), NewPasienActivity.class), 302);
            }
        });
    }

    private void setUpRecyclerView() {
        adapter = new PasienRecyclerAdapter(getContext(), pasiens);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);
        recyclerView.setAdapter(adapter);

        loadData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 302 && resultCode == RESULT_OK){
            loadData();
        }
    }

    private void loadData() {
        pasiens.clear();
        AndroidNetworking.get(Const.PASIEN)
                .addQueryParameter("id_masyarakat", Const.mid()+"")
                .setTag("Get pasiens")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        List<Pasien> newPasien = new Gson().fromJson(response, new TypeToken<List<Pasien>>() {
                        }.getType());

                        pasiens.addAll(newPasien);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", anError.getErrorBody());
                    }
                });
    }


}
