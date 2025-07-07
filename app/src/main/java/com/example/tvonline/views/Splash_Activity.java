package com.example.tvonline.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tvonline.R;
import com.example.tvonline.app.app;

public class Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        init();
    }

    private void init(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(app.isConnected()){
                    startActivity(new Intent(Splash_Activity.this,Intro_Activity.class));
                    finish();
                }
                else {
                    showAlert();
                }

            }
        },2500);

    }

    private void showAlert(){

        AlertDialog alertDialog ;
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.RoundedDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_alert_splash,null);

        builder.setView(view);

        alertDialog = builder.create();

        alertDialog.setCancelable(false);

        TextView textview_retry = view.findViewById(R.id.textview_retry);

        TextView textview_exit = view.findViewById(R.id.textview_exit);

        textview_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                init();
            }
        });

        textview_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                finish();
            }
        });


        alertDialog.show();
    }

}
