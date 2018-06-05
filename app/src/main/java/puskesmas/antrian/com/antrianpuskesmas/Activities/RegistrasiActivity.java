package puskesmas.antrian.com.antrianpuskesmas.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.etc.Const;

public class RegistrasiActivity extends AppCompatActivity{
    EditText username, password, confirm_password, nama, alamat, no_hp, email;
    Button button;
    RadioGroup radioKelamin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        bindView();
    }

    private void bindView() {
        radioKelamin = findViewById(R.id.radioGroup);
        button = findViewById(R.id.submit);
        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_password);
        confirm_password = findViewById(R.id.input_confirm_password);
        nama = findViewById(R.id.input_nama);
        alamat = findViewById(R.id.input_alamat);
        email = findViewById(R.id.input_email);
        no_hp = findViewById(R.id.input_no_hp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!password.getText().toString().equals(confirm_password.getText().toString())){
                    confirm_password.setError("Password konfirmasi tidak sesuai");
                    return;
                }

                button.setEnabled(false);
                button.setText("Mengirim data...");
                button.setAllCaps(false);
                regis(username.getText().toString(), password.getText().toString(), nama.getText().toString(), radioKelamin.getCheckedRadioButtonId() == R.id.radioL ? "L" : "P", alamat.getText().toString(), no_hp.getText().toString(), email.getText().toString());
            }
        });
    }

    private void regis(String username, String password, String nama, String kelamin, String alamat, String no_hp, String email) {
        AndroidNetworking.post(Const.REGISTRASI)
                .addBodyParameter("username", username)
                .addBodyParameter("password", password)
                .addBodyParameter("nama", nama)
                .addBodyParameter("kelamin", kelamin)
                .addBodyParameter("alamat", alamat)
                .addBodyParameter("no_hp", no_hp)
                .addBodyParameter("email", email)
                .setTag("registrasi")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("success")){
                                JSONObject info = response.getJSONObject("info");
                                Const.doLogin(Integer.parseInt(response.getString("id")), Integer.parseInt(info.getString("id")), info.getString("nama"), info.getString("alamat"), response.getString("username"), info.getString("no_hp"), info.getString("email"));
                                startActivity(new Intent(RegistrasiActivity.this, MainActivity.class));
                                finish();
                            } else if(response.has("error")){
                                Toast.makeText(RegistrasiActivity.this, response.getJSONArray("error").join("\n"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegistrasiActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        button.setEnabled(true);
                        button.setText("Registrasi");
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", anError.getErrorBody());
                        button.setEnabled(true);
                        button.setText("Registrasi");
                    }
                });
    }
}
