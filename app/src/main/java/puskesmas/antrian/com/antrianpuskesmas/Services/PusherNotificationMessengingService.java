package puskesmas.antrian.com.antrianpuskesmas.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;
import com.pusher.pushnotifications.fcm.MessagingService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import puskesmas.antrian.com.antrianpuskesmas.Activities.AntrianActivity;
import puskesmas.antrian.com.antrianpuskesmas.BaseApplication;
import puskesmas.antrian.com.antrianpuskesmas.R;
import puskesmas.antrian.com.antrianpuskesmas.etc.Const;

import static android.support.v4.app.NotificationCompat.PRIORITY_HIGH;

public class PusherNotificationMessengingService extends MessagingService{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() == null)
            return;

        Log.e("new notif", remoteMessage.getData().toString());

        prosessNotif(remoteMessage.getData().toString());
    }

    private void prosessNotif(String data) {
        try{
            JSONObject jsonObject = new JSONObject(data);
            JSONArray info = jsonObject.getJSONArray("info");
            String event = jsonObject.getString("event");

            if (event.equals("panggil")){
                if (info.length() > 0){
                    for (int i = 0; i < info.length(); i++) {
                        JSONObject detInfo = info.getJSONObject(i);
                        String idUser = detInfo.getString("id_user");
                        String idAntrian = detInfo.getString("id");
                        String idMasyarakat = detInfo.getString("id_masyarakat");
                        String namaPasien = detInfo.getString("nama");
                        String noSekarang = detInfo.getString("no_sekarang");
                        String noUrut = detInfo.getString("no_antrian");
                        String title = "Info antrian";
                        String pesan = noUrut.equals(noSekarang) ? "Sekarang giliran "+namaPasien : (Integer.parseInt(noUrut) - Integer.parseInt(noSekarang)) +" antrian tersisa";
                        if (Const.uid() == Integer.parseInt(idUser)){
                            Intent intentBroadcast = new Intent();
                            intentBroadcast.putExtra("id", idAntrian);
                            sendBroadcast(intentBroadcast);
                            Intent intent = new Intent(this, AntrianActivity.class);
                            intent.putExtra("id", idAntrian);

                            PendingIntent pendingIntent = PendingIntent.getActivity(this, Integer.parseInt(idAntrian), intent, PendingIntent.FLAG_UPDATE_CURRENT);

                            showNotif(idAntrian, title, pesan, pendingIntent);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showNotif(String id, String title, String pesan, PendingIntent pendingIntent){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription(pesan);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Hearty365")
                .setContentIntent(pendingIntent)
                //     .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(title)
                .setContentText(pesan);

        notificationManager.notify(/*notification id*/100, notificationBuilder.build());
    }
}
