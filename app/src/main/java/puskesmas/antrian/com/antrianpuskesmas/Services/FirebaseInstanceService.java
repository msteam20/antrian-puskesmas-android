package puskesmas.antrian.com.antrianpuskesmas.Services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import puskesmas.antrian.com.antrianpuskesmas.etc.Const;

/**
 * Created by athasamid on 1/10/18.
 */

public class FirebaseInstanceService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("Firebase Token", "Refresh Token : "+refreshToken);
        sendFirebaseTokenToServer();
    }

    public static void sendFirebaseTokenToServer(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        if (!Const.isLogin() || refreshedToken==null)
            return;

        Log.e("FCM ID", refreshedToken);

        //Const.changeFcmId(refreshedToken, null, null);
    }
}
