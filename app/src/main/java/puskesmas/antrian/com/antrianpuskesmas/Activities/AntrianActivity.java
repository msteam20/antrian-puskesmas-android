package puskesmas.antrian.com.antrianpuskesmas.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.components.SquareImageView;
import puskesmas.antrian.com.antrianpuskesmas.etc.Const;
import puskesmas.antrian.com.antrianpuskesmas.models.Antrian;

public class AntrianActivity extends AppCompatActivity {
    int id;
    Antrian antrian;

    SquareImageView icon;
    TextView pasien, poli, waktu, noUrut, dilayani, sisa;
    Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antrian);

        pasien = findViewById(R.id.pasien);
        poli = findViewById(R.id.poli);
        waktu = findViewById(R.id.waktu);
        noUrut = findViewById(R.id.no_urut);
        dilayani = findViewById(R.id.dilayani);
        sisa = findViewById(R.id.sisa);
        btn = findViewById(R.id.btn_refresh);

        icon = findViewById(R.id.icon);

        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra("id")){
            id = intent.getIntExtra("id", -1);
            loadData(id);
        }
    }

    private void loadData(int id) {
        AndroidNetworking.get(Const.ANTRIAN+"/"+id)
                .setTag("Get Antrian")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        antrian = new Gson().fromJson(response, Antrian.class);
                        dataLoaded();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void dataLoaded() {
        if (antrian == null)
            return;

        noUrut.setText(antrian.no_antrian);
        poli.setText(antrian.poli.nama);
        Glide.with(this).load(antrian.poli.icon).into(icon);
    }


}
