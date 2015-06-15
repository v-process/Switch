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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

/**
 * Created by Administrator on 2015-05-14.
 */
public class FunctionActivity extends Activity{
    Button bell_btn;// 진동 소리 전환 버튼
    Button wifi_btn; // 와이파이 전환 버튼

    AudioManager aManager;
    private ConnectivityManager connectivityManager;

    String objectId;
    int currentLength =0;

    int wifi_flag = 0;
    int bell_flag = 0;
    int alram_flag = 0;

    int total_flag = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity);

        bell_btn = (Button) findViewById(R.id.bell_btn);
        wifi_btn = (Button) findViewById(R.id.wifi_btn);
        aManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        objectId = ParseUser.getCurrentUser().getObjectId();
        Intent intent = getIntent();
        currentLength = intent.getIntExtra("currentLength",-1);

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
                    alram_flag = 1;

                } else {
                    tb3.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonoff));
                    alram_flag = 0;
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

    public void saveBtn(View view) {

        flag_check(); //플레그 체크


        ParseQuery<ParseUser> query= ParseUser.getQuery();

        query.getInBackground(objectId,new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {

                if(e ==null){
                    //쿼리성공
                    JSONArray listFlag = new JSONArray();
                    JSONArray listPlace = new JSONArray();
                    listFlag.put(total_flag); // Flag를 JSONarray에 삽입한다.(1 = 와이파이 / 2 = 와이파이,진동...)
                    listPlace.put("제목");

                    parseUser.add("listFlagArray", listFlag);
                    parseUser.add("listPlaceArray",listPlace);
                    parseUser.saveInBackground();
                }else{
                    return;
                }
            }
        });
        Intent intent = new Intent(this,FirstActivity.class);
        intent.putExtra("intentLength",currentLength);
        intent.putExtra("intentFlag",total_flag);
        intent.putExtra("intentPlace","몰라");
        startActivity(intent);
        finish();
    }

}