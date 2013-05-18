package com.shivgadhia.android.SecretCodeLauncher.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.shivgadhia.android.SecretCodeLauncher.activities.DebugSecretActivtiy;

public class SecretCodeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent activityToLaunch = new Intent(context, DebugSecretActivtiy.class);
        activityToLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activityToLaunch);
    }
}
