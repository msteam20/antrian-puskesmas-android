package puskesmas.antrian.com.antrianpuskesmas.etc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

import puskesmas.antrian.com.antrianpuskesmas.BaseApplication;
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

    // Set session login
    public static void isLogin(boolean login){
        BaseApplication.sharedPreferences.edit().putBoolean("login", login).apply();
    }

    // get session login
    public static boolean isLogin(){
        return BaseApplication.sharedPreferences.getBoolean("login", false);
    }

    //Simpan id user
    public static void uid(Integer uid){
        BaseApplication.sharedPreferences.edit().putInt("uid", uid).apply();
    }

    public static Integer uid(){
        return BaseApplication.sharedPreferences.getInt("uid", -1);
    }

    // simpan id masyarakat
    public static void mid(Integer mid){
        BaseApplication.sharedPreferences.edit().putInt("mid", mid).apply();
    }

    public static Integer mid(){
        return BaseApplication.sharedPreferences.getInt("mid", -1);
    }

    public static void nama(String nama){
        BaseApplication.sharedPreferences.edit().putString("nama", nama).apply();
    }

    public static String nama(){
        return BaseApplication.sharedPreferences.getString("nama", null);
    }

    public static void alamat(String alamat){
        BaseApplication.sharedPreferences.edit().putString("alamat", alamat).apply();
    }

    public static String alamat(){
        return BaseApplication.sharedPreferences.getString("alamat", null);
    }

    public static void username(String username){
        BaseApplication.sharedPreferences.edit().putString("username", username).apply();
    }

    public static String username(){
        return BaseApplication.sharedPreferences.getString("username", null);
    }

    public static void noHp(String no_hp){
        BaseApplication.sharedPreferences.edit().putString("no_hp", no_hp).apply();
    }

    public static String noHp(){
        return BaseApplication.sharedPreferences.getString("no_hp", null);
    }

    public static void email(String email){
        BaseApplication.sharedPreferences.edit().putString("email", email).apply();
    }

    public static String email(){
        return BaseApplication.sharedPreferences.getString("email", null);
    }

    public static void doLogin(int uid, int mid, String nama, String alamat, String username, String no_hp, String email){
        isLogin(true);
        uid(uid);
        mid(mid);
        nama(nama);
        alamat(alamat);
        username(username);
        noHp(no_hp);
        email(email);
    }

    public static void doLogout(){
        isLogin(false);
        uid(-1);
        mid(-1);
        nama(null);
        alamat(null);
        username(null);
        noHp(null);
        email(null);
    }
}
