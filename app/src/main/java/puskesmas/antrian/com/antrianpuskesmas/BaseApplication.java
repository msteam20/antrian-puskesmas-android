package puskesmas.antrian.com.antrianpuskesmas;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        // inisialisasi Okttp untuk networking
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

        // Set okhttp pada networking
        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);

        // Set enable logging jika mode debug
        if (BuildConfig.DEBUG)
            AndroidNetworking.enableLogging();
    }
}
