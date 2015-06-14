package com.androidbegin.onoffzone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by Administrator on 2015-05-14.
 */
public class FunctionActivity extends Activity{
    Button bell_btn;// 진동 소리 전환 버튼
    Button wifi_btn; // 와이파이 전환 버튼

    AudioManager aManager;
    private ConnectivityManager connectivityManager;

    int wifi_flag = 0;
    int bell_flag = 0;
    int alram_flag = 0;

    int total_flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity);

        TextView txtuser = (TextView) findViewById(R.id.txtuser);

        bell_btn = (Button) findViewById(R.id.bell_btn);
        wifi_btn = (Button) findViewById(R.id.wifi_btn);
        aManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);



        final ToggleButton tb = (ToggleButton)this.findViewById(R.id.toggleButton01);
        final ToggleButton tb2 = (ToggleButton)this.findViewById(R.id.toggleButton02);
        final ToggleButton tb3 = (ToggleButton)this.findViewById(R.id.toggleButton03);

        tb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tb.isChecked()) {
                    tb.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonon));
                    wifi_flag = 1;
                } else {
                    tb.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonoff));
                    wifi_flag = 0;
                }
            }
        });


        tb2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tb2.isChecked()) {
                    tb2.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonon));
                    bell_flag = 1;

                } else {
                    tb2.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonoff));
                    bell_flag = 0;

                }
            }
        });
        tb3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tb3.isChecked()) {
                    tb3.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonon));
                    alram_flag = 0;

                } else {
                    tb3.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonoff));
                    alram_flag = 1;
                }
            }
        });


    }

    public void flag_check(){
        if(wifi_flag == 0){
            if(bell_flag == 0 && alram_flag==0 ) {
                total_flag = 0;
            }
            else if(bell_flag == 0 && alram_flag == 1){
                total_flag = 6;
            }
            else if(bell_flag == 1 && alram_flag ==0){
                total_flag = 4;
            }
            else if(bell_flag == 1 && alram_flag ==1){
                total_flag = 5;
            }
        }
        else if(wifi_flag ==1){
            if(bell_flag == 0 && alram_flag==0 ) {
                total_flag = 1;
            }
            else if(bell_flag == 0 && alram_flag == 1){
                total_flag = 3;
            }
            else if(bell_flag == 1 && alram_flag ==0){
                total_flag = 2;
            }
            else if(bell_flag == 1 && alram_flag ==1){
                total_flag = 7;
            }
        }
    }

    public void belltrans(View v){
        if (bell_flag == 1) {
            Toast.makeText(getApplicationContext(), "벨소리", Toast.LENGTH_LONG).show();
            aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            bell_flag = 0;
        } else {
            Toast.makeText(getApplicationContext(), "진동", Toast.LENGTH_LONG).show();
            aManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            bell_flag = 1;

        }
    }
    public void wifitrans(View v){
        if (wifi_flag == 0) {
            toggleWiFi(true);
            Toast.makeText(getApplicationContext(), "Wi-Fi 켜짐", Toast.LENGTH_LONG).show();
            wifi_flag = 1;
        } else {
            toggleWiFi(false);
            Toast.makeText(getApplicationContext(), "Wi-Fi 꺼짐", Toast.LENGTH_LONG).show();
            wifi_flag = 0;

        }
    }
    public void mapFunc(View v) {
        Intent intent1 = new Intent(this, MapActivity.class);
        startActivity(intent1);
    }

    //와이파이 조절
    public void toggleWiFi(boolean status) {
        WifiManager wifiManager = (WifiManager) this
                .getSystemService(Context.WIFI_SERVICE);
        if (status == true && !wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        } else if (status == false && wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }
}