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
import puskesmas.antrian.com.antrianpuskesmas.models.Poli;

public class AntrianFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Object> list = new ArrayList<>();
    AntrianRecyclerAdapter adapter;

    private Bundle saveInstance;

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
    }

    private Bundle saveState() { /* called either from onDestroyView() or onSaveInstanceState() */
        Bundle state = new Bundle();
        state.putString("data-antrian", new Gson().toJson(list));
        return state;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveInstance = saveState();
    }

    private void loadData() {
        AndroidNetworking.get(Const.ANTRIAN)
                .addQueryParameter("id_user", Const.uid()+"")
                .setPriority(Priority.MEDIUM)
                .setTag("GET ANTRIAN")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        list.clear();
                        List<Antrian> antrian = new Gson().fromJson(response, new TypeToken<List<Antrian>>() {
                        }.getType());
                        separateAntrian(antrian);
                        Const.storeStateAntrian(antrian);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", anError.getErrorBody());
                    }
                });
    }

    private void setUpRecyclerView() {
        separateAntrian(Const.loadStateAntrian());
        adapter = new AntrianRecyclerAdapter(getContext(), list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadData();
    }

    void separateAntrian(List<Antrian> antrian){
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
    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putBundle("antrian-fragment", (saveInstance != null) ? saveInstance : saveState());
    }
}
