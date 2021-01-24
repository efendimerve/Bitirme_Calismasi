package com.example.deneme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ResultReceiver extends BroadcastReceiver {

    public static String ACTION_CLICK = "ACTION_CLICK";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equalsIgnoreCase(ACTION_CLICK)){
            //Toast.makeText(context, "GO TO APP", Toast.LENGTH_SHORT).show();
        }
    }
}

