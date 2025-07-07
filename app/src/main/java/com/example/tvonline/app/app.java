package com.example.tvonline.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

public class app {

    public static final String TAG = "TV_ONLINE";

    public static void toast(String message){
        Toast.makeText(application.getContext(),message,Toast.LENGTH_SHORT);
    }

    public static void log(String message){
        Log.e(TAG,message);
    }

    public static boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
