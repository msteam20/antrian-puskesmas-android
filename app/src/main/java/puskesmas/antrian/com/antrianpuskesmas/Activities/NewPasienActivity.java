package puskesmas.antrian.com.antrianpuskesmas.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.OnDateChangedListener;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import puskesmas.antrian.com.antrianpuskesmas.R;

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



        tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDatepickerShowing)
                    return;

                isDatepickerShowing = true;

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

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        isDatepickerShowing = false;
        tgl_lahir.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
    }
}
