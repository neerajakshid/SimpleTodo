package com.codepath.simpletodo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotifyService extends Service {

    private NotificationManager mManager;
    ItemDatabase db = new ItemDatabase(this);

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings({ "static-access", "deprecation" })
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String date = df.format(new Date());
        String notify = db.getItemsByDate(date);
            String str = "Today's Action Items: " + notify;
        if (notify != "") {
            mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder notification  = new NotificationCompat.Builder(this)
                    .setContentTitle("Simple Todo")
                    .setContentText(str)
                    .setSmallIcon(R.drawable.ic_notify_icon)
                    .setColor(getResources().getColor(R.color.purple))
                    .setContentIntent(pendingNotificationIntent)
                    .setAutoCancel(true);
            mManager.notify(0, notification.build());
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
