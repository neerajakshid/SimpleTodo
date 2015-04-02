package com.codepath.simpletodo;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reciever extends BroadcastReceiver {
    ItemDatabase db;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent service = new Intent(context, NotifyService.class);
        context.startService(service);

    }
}
