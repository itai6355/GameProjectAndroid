package com.example.gameproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;


public class Battery extends BroadcastReceiver {
    //

    private final int battery;

    public Battery() {
        this(99);
    }

    public Battery(int battery) {
        this.battery = battery;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra("level", 0);

        if (level < battery) {
            timerDelayRemoveDialog(5000, new AlertDialog.Builder(context).
                    setTitle("Low Battery").
                    setIcon(android.R.drawable.ic_lock_idle_low_battery).
                    show());
        }
    }

    public void timerDelayRemoveDialog(long time, final Dialog d) {
        new Handler().postDelayed(d::dismiss, time);
    }

}
