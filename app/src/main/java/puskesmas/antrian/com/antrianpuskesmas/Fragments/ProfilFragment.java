package puskesmas.antrian.com.antrianpuskesmas.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import puskesmas.antrian.com.antrianpuskesmas.Activities.LoginActivity;
import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.etc.Const;

public class ProfilFragment extends Fragment implements View.OnClickListener{
    private TextView nama, email, noTelp, edit;
    private LinearLayout notif, pass;
    private Button logout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nama = view.findViewById(R.id.nama);
        email = view.findViewById(R.id.email);
        noTelp = view.findViewById(R.id.noTelp);
        edit = view.findViewById(R.id.edit);

        notif = view.findViewById(R.id.layoutNotif);
        pass = view.findViewById(R.id.layoutUbahPassword);

        logout = view.findViewById(R.id.btn_logout);

        logout.setOnClickListener(this);
        notif.setOnClickListener(this);
        pass.setOnClickListener(this);
        edit.setOnClickListener(this);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindData();
    }

    private void bindData() {
        nama.setText(Const.nama());
        email.setText(Const.email());
        noTelp.setText(Const.noHp());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_logout:
                Const.doLogout();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.layoutNotif:
                break;
            case R.id.layoutUbahPassword:
                break;
            case R.id.edit:
                break;
        }
    }
}
