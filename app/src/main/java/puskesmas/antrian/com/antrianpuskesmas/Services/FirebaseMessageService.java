package puskesmas.antrian.com.antrianpuskesmas.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import puskesmas.antrian.com.antrianpuskesmas.Activities.AntrianActivity;
import puskesmas.antrian.com.antrianpuskesmas.BaseApplication;
import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.etc.Const;

import static android.support.v4.app.NotificationCompat.PRIORITY_HIGH;

/**
 * Created by athasamid on 1/10/18.
 */

public class FirebaseMessageService extends FirebaseMessagingService {
    public static final String PANGGIL = "panggil";

    public static final String REFRESH_ANTRIAN = "REFRESH ANTRIAN";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0 && Const.isLogin()) {
            Log.e("Notif",remoteMessage.getData().toString());
            String event = remoteMessage.getData().get("event");

            if (event == null || event.isEmpty())
                return;

            showNotificationMessage(event, remoteMessage.getData().get("data"));
        }
    }

    private void showNotificationMessage(String event, String data) {
        switch (event){
            case PANGGIL:
                notif(data, event);
                break;
        }
    }

    private void notif(String data, String event) {
//        if (!Const.notif())
//            return;
        try {
            JSONObject object = new JSONObject(data);
            String user = object.getString("nama");
            String message = object.getString("pesan");
            int id = object.getInt("id");

            Intent intentBroadcast = new Intent();
            intentBroadcast.putExtra("id", id);
            sendBroadcast(intentBroadcast);
            String title;
            Intent intent;
            /*switch (event){
                case PANGGIL:
                    default:*/
                    title = message;
                    intent = new Intent(this, AntrianActivity.class);
                    /*break;
            }*/
            intent.putExtra("id", id);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

            notificationBuilder
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setPriority(PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);

            //if (Const.notifSuara())
                notificationBuilder.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + BaseApplication.instance.getApplicationContext().getPackageName() + "/raw/notification"));

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(Integer.parseInt(10+""+id), notificationBuilder.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
