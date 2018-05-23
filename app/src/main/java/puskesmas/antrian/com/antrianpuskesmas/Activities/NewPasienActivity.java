package puskesmas.antrian.com.antrianpuskesmas.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.OnDateChangedListener;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONObject;

import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.etc.Const;

public class NewPasienActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText nama, alamat, no_telp, no_askes, tgl_lahir, tempat_lahir;
    RadioButton pria, wanita;
    Button button;
    DatePickerDialog datePickerDialog;

    private boolean isDatepickerShowing = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pasien);
        setUpView();

    }

    private void setUpView() {
        nama = findViewById(R.id.input_nama);
        alamat = findViewById(R.id.input_alamat);
        no_telp = findViewById(R.id.input_no_telp);
        no_askes = findViewById(R.id.input_no_askes);
        tgl_lahir = findViewById(R.id.input_tgl_lahir);
        tempat_lahir = findViewById(R.id.input_tempat_lahir);
        pria = findViewById(R.id.radioL);
        wanita = findViewById(R.id.radioP);
        button = findViewById(R.id.btn_add);

        datePickerDialog = new SpinnerDatePickerDialogBuilder()
                .context(NewPasienActivity.this)
                .callback(NewPasienActivity.this)
                .build();

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                isDatepickerShowing = false;
            }
        });


        tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahPasien();
            }
        });
    }

    private void tambahPasien() {
        button.setEnabled(false);
        button.setText("Menyimpan...");
        AndroidNetworking.post(Const.PASIEN)
                .addBodyParameter("nama", nama.getText().toString())
                .addBodyParameter("alamat", alamat.getText().toString())
                .addBodyParameter("kelamin", pria.isChecked() ? "L" : "P")
                .addBodyParameter("tgl_lahir", tgl_lahir.getText().toString())
                .addBodyParameter("tempat_lahir", tempat_lahir.getText().toString())
                .addBodyParameter("no_telp", no_telp.getText().toString())
                .addBodyParameter("no_askes", no_askes.getText().toString())
                .addBodyParameter("id_masyarakat", "1")
                .setTag("Kirim pasien")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.has("success")){
                            Toast.makeText(NewPasienActivity.this, "Pasien Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(NewPasienActivity.this, "Terjadi kesalahan. Silahkan diulangi", Toast.LENGTH_SHORT).show();
                            button.setEnabled(true);
                            button.setText("TAMBAH");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(NewPasienActivity.this, "Terjadi kesalahan. Silahkan diulangi", Toast.LENGTH_SHORT).show();
                        button.setEnabled(true);
                        button.setText("TAMBAH");
                    }
                });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        isDatepickerShowing = false;
        tgl_lahir.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
    }
}
