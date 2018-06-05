package puskesmas.antrian.com.antrianpuskesmas.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.pusher.pushnotifications.PushNotifications;

import puskesmas.antrian.com.antrianpuskesmas.Fragments.AntrianFragment;
import puskesmas.antrian.com.antrianpuskesmas.Fragments.HomeFragment;
import puskesmas.antrian.com.antrianpuskesmas.Fragments.PasienFragment;
import puskesmas.antrian.com.antrianpuskesmas.Fragments.ProfilFragment;
import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.components.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setHasTransientState(false);
        navigation.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        PushNotifications.start(getApplicationContext(), "8fc79784-789e-40e5-8ba5-24e7f0c92a3b");
        PushNotifications.subscribe("masyarakat");

        loadFragment(new HomeFragment());

    }


    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_proses:
                fragment = new AntrianFragment();
                break;
            case R.id.navigation_pasien:
                fragment = new PasienFragment();
                break;
            case R.id.navigation_akun:
                fragment = new ProfilFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
