package com.androidbegin.onoffzone;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2015-05-14.
 */
public class FunctionActivity extends Activity{
    Button bell_btn;// 진동 소리 전환 버튼
    Button wifi_btn; // 와이파이 전환 버튼

    AudioManager aManager;
    private ConnectivityManager connectivityManager;

    int bell_flag = 0;
    int wifi_flag = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity);

        TextView txtuser = (TextView) findViewById(R.id.txtuser);

        bell_btn = (Button) findViewById(R.id.bell_btn);
        wifi_btn = (Button) findViewById(R.id.wifi_btn);
        aManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

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