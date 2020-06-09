package com.example.workmanagerandfirebasetesting;

import android.content.Context;
import android.widget.Toast;

public class Config {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static int a = 1;

    public static String user_id = null;

}
