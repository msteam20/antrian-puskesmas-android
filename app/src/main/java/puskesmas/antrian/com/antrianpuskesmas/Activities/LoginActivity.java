package puskesmas.antrian.com.antrianpuskesmas.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.etc.Const;

public class LoginActivity extends AppCompatActivity{
    TextView registrasi;
    EditText username, password;
    Button submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindView();
    }

    private void bindView() {
        registrasi = findViewById(R.id.register);
        submit = findViewById(R.id.btnSignIn);
        username = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);

        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrasiActivity.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().isEmpty()){
                    username.setError("Username tidak boleh kosong");
                    return;
                }

                if (password.getText().toString().isEmpty()){
                    password.setError("Password tidak boleh kosong");
                    return;
                }

                AndroidNetworking.post(Const.AUTH)
                        .addBodyParameter("username", username.getText().toString())
                        .addBodyParameter("password", password.getText().toString())
                        .setTag("Login")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.has("error")){
                                        Toast.makeText(LoginActivity.this, response.getString("error"), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (!response.getString("jenis_pemilik").equals("masyarakat")){
                                        Toast.makeText(LoginActivity.this, "Username atau password tidak sesuai", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    JSONObject info = response.getJSONObject("info");
                                    Const.doLogin(Integer.parseInt(response.getString("id")), Integer.parseInt(info.getString("id")), info.getString("nama"), info.getString("alamat"), response.getString("username"), info.getString("no_hp"), info.getString("email"));
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(LoginActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.e("error", anError.getErrorBody());
                            }
                        });
            }
        });
    }


}
