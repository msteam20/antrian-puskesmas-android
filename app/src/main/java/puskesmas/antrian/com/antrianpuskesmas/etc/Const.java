package puskesmas.antrian.com.antrianpuskesmas.etc;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

import puskesmas.antrian.com.antrianpuskesmas.BuildConfig;
import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.models.Pasien;

/**
 * Semua konstanta disini
 * Termasuk Endpoint api
 */
public class Const {
    public static final String BASE_URL = BuildConfig.BASE_URL;
    public static final String AUTH = BASE_URL+"user/auth";
    public static final String REGISTRASI = BASE_URL+"user/register";
    public static final String POLI = BASE_URL+"poli";
    public static final String ANTRIAN = BASE_URL+"antrian";
    public static final String PASIEN = BASE_URL+"pasien";
}
