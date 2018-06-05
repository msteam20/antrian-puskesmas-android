package puskesmas.antrian.com.antrianpuskesmas;

import android.app.Application;
import android.content.SharedPreferences;

import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.firebase.FirebaseApp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BaseApplication extends Application {
    public static SharedPreferences sharedPreferences;
    public static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        instance = this;


        // inisialisasi Okttp untuk networking request Api ke server
        OkHttpClient okHttpClient = null;
        if (BuildConfig.DEBUG) {
            okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .followRedirects(true)
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder().addHeader("Connection", "close").build();
                            return chain.proceed(request);
                        }
                    })
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();
        } else {
            okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .followRedirects(true)
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder().addHeader("Connection", "close").build();
                            return chain.proceed(request);
                        }
                    })
                    .build();
        }

        // Set okhttp pada networking request Api ke server
        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);

        // Set enable logging jika mode debug
        if (BuildConfig.DEBUG)
            AndroidNetworking.enableLogging();

        FirebaseApp.initializeApp(this);
    }
}
