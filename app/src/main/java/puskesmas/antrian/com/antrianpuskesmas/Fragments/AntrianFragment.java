package puskesmas.antrian.com.antrianpuskesmas.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import puskesmas.antrian.com.antrianpuskesmas.Adapters.AntrianRecyclerAdapter;
import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.etc.Const;
import puskesmas.antrian.com.antrianpuskesmas.models.Antrian;

public class AntrianFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Object> list;
    AntrianRecyclerAdapter adapter;

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
        setUpRecyclerView();
        loadData();
    }

    private void loadData() {
        AndroidNetworking.get(Const.ANTRIAN)
                .addQueryParameter("id_user", "1") // Todo ganti dengan shared preferences
                .setPriority(Priority.MEDIUM)
                .setTag("GET ANTRIAN")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        List<Antrian> antrian = new Gson().fromJson(response, new TypeToken<List<Antrian>>() {
                        }.getType());

                        List<Antrian> inProgress = new ArrayList<>();
                        List<Antrian> histori = new ArrayList<>();

                        for (Antrian antri : antrian) {
                            if(antri.in_progress)
                                inProgress.add(antri);
                            else
                                histori.add(antri);
                        }

                        if (inProgress.size() > 0){
                            list.add("Dalam Antrian");
                            list.addAll(inProgress);
                        }

                        list.add("Histori Antrian");
                        list.addAll(histori);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", anError.getErrorBody());
                    }
                });
    }

    private void setUpRecyclerView() {
        list = new ArrayList<>();
        adapter = new AntrianRecyclerAdapter(getContext(), list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }


}
