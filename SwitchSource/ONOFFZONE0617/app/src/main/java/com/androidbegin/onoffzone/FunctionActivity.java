package com.androidbegin.onoffzone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Administrator on 2015-05-14.
 */
public class FunctionActivity extends Activity {
    Button bell_btn;// 진동 소리 전환 버튼
    Button wifi_btn; // 와이파이 전환 버튼

    AudioManager aManager;
    private ConnectivityManager connectivityManager;

    String objectId;
    int currentLength = 0;

    int wifi_flag = 0;
    int bell_flag = 0;
    int alarm_flag = 0;
    int total_flag = 0;

    int alarmType_flag = 0; //음성 1++//진동 3++// 푸시 7++
    String alarmMemo = "";

    EditText et_alarmName;
    EditText et_alarmMemo;
    String alarmName = "";

    LinearLayout ll_alarmType;
    LinearLayout ll_alarmMemo;

    SeekBar volumeSeekBar;
    int volume = 0;

    double latitude;
    double longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity);

        bell_btn = (Button) findViewById(R.id.bell_btn);
        wifi_btn = (Button) findViewById(R.id.wifi_btn);

        volumeSeekBar = (SeekBar) findViewById(R.id.volumeSeekBar);

        et_alarmMemo = (EditText) findViewById(R.id.et_alarmMemo);
        et_alarmName = (EditText) findViewById(R.id.alarmName);
        ll_alarmMemo = (LinearLayout) findViewById(R.id.alarmMemo);
        ll_alarmType = (LinearLayout) findViewById(R.id.alarmType);
        if (alarm_flag == 0) {
            ll_alarmType.setVisibility(View.INVISIBLE);
            ll_alarmMemo.setVisibility(View.INVISIBLE);
        }

        aManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final ToggleButton tb = (ToggleButton) this.findViewById(R.id.toggleButton01);
        final ToggleButton tb2 = (ToggleButton) this.findViewById(R.id.toggleButton02);
        final ToggleButton tb3 = (ToggleButton) this.findViewById(R.id.toggleButton03);
        final ToggleButton tb4 = (ToggleButton) this.findViewById(R.id.toggleButton04);
        final ToggleButton tb5 = (ToggleButton) this.findViewById(R.id.toggleButton05);
        final ToggleButton tb6 = (ToggleButton) this.findViewById(R.id.toggleButton06);

        objectId = ParseUser.getCurrentUser().getObjectId();
        Intent intent = getIntent();
        currentLength = intent.getIntExtra("currentLength", -1);

        tb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tb.isChecked()) {
                    tb.setBackgroundDrawable(getResources().getDrawable(R.drawable.on_wifi));
                    wifi_flag = 0;
                    total_flag += 1;
                } else {
                    tb.setBackgroundDrawable(getResources().getDrawable(R.drawable.off_wifi));
                    wifi_flag = 1;
                    total_flag -= 1;
                }
            }
        });
        tb2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tb2.isChecked()) {
                    tb2.setBackgroundDrawable(getResources().getDrawable(R.drawable.on_manner));
                    bell_flag = 0;
                    total_flag += 3;
                } else {
                    tb2.setBackgroundDrawable(getResources().getDrawable(R.drawable.off_manner));
                    bell_flag = 1;
                    total_flag -= 3;
                }
            }
        });
        tb3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tb3.isChecked()) {
                    tb3.setBackgroundDrawable(getResources().getDrawable(R.drawable.on_push));
                    alarm_flag = 0;
                    total_flag += 7;
                    ll_alarmType.setVisibility(View.VISIBLE);
                } else {
                    tb3.setBackgroundDrawable(getResources().getDrawable(R.drawable.off_push));
                    alarm_flag = 1;
                    total_flag -= 7;
                    ll_alarmType.setVisibility(View.INVISIBLE);
                }
            }
        });


        // 사운드
        tb4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tb4.isChecked()) {
                    tb4.setBackgroundDrawable(getResources().getDrawable(R.drawable.on_sound));
                    alarmType_flag += 1;
                } else {
                    tb4.setBackgroundDrawable(getResources().getDrawable(R.drawable.off_sound));
                    alarmType_flag -= 1;
                }
            }
        });
        // 진동
        tb5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tb5.isChecked()) {
                    tb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.on_vib));
                    alarmType_flag += 3;
                } else {
                    tb5.setBackgroundDrawable(getResources().getDrawable(R.drawable.off_vib));
                    alarmType_flag -= 3;
                }
            }
        });
        // 푸시
        tb6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tb6.isChecked()) {
                    tb6.setBackgroundDrawable(getResources().getDrawable(R.drawable.on_etc));
                    alarmType_flag += 7;
                    ll_alarmMemo.setVisibility(View.VISIBLE);
                } else {
                    tb6.setBackgroundDrawable(getResources().getDrawable(R.drawable.off_etc));
                    alarmType_flag -= 7;
                    ll_alarmMemo.setVisibility(View.INVISIBLE);
                }
            }
        });


        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volume = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void flag_check() {
        switch (total_flag) {
            case 0:
                total_flag = 0;
                break;
            case 1:
                total_flag = 1;
                break;
            case 4:
                total_flag = 2;
                break;
            case 8:
                total_flag = 3;
                break;
            case 3:
                total_flag = 4;
                break;
            case 10:
                total_flag = 5;
                break;
            case 7:
                total_flag = 6;
                break;
            case 11:
                total_flag = 7;
                break;
        }
    }

    public void belltrans(View v) {
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

    public void wifitrans(View v) {
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

    public boolean locationcheck() {
        if (latitude != 0) {
            return true;
        }
        return false;
    }

    public void saveBtn(View view) {
        if (!locationcheck()) {//로케이션 체크
            Toast.makeText(this, "장소를 설정하세요", Toast.LENGTH_SHORT).show();
            return;
        }
        flag_check(); //플레그 체크

        alarmName = et_alarmName.getText().toString();

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.getInBackground(objectId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {

                if (e == null) {
                    //쿼리성공
                    JSONArray listFlag = new JSONArray();
                    JSONArray listPlace = new JSONArray();
                    JSONArray listAlarmType = new JSONArray();
                    JSONArray listAlarmVolume = new JSONArray();
                    JSONArray listAlarmMemo = new JSONArray();
                    JSONArray listlatitude = new JSONArray();
                    JSONArray listlongitude = new JSONArray();

                    listFlag.put(total_flag); // Flag를 JSONarray에 삽입한다.(1 = 와이파이 / 2 = 와이파이,진동...)
                    listPlace.put(alarmName);
                    try {
                        listlatitude.put(latitude);
                        listlongitude.put(longitude);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                    // 밑에것들은 알람타입에 관한것들.
                    if (alarm_flag == 0) {
                        listAlarmType.put(alarmType_flag);
                        parseUser.add("listAlarmType", listAlarmType);
                        //볼륨
                        if (alarmType_flag == 1 || alarmType_flag == 4 || alarmType_flag == 8 || alarmType_flag == 11) {
                            listAlarmVolume.put(volume);
                            parseUser.add("listVolumeArray", listAlarmVolume);
                        } else {
                            listAlarmVolume.put(-1);
                            parseUser.add("listVolumeArray", listAlarmVolume);
                        }
                        //푸시 메모
                        if (alarmType_flag > 6) {
                            alarmMemo = et_alarmMemo.getText().toString();
                            listAlarmMemo.put(alarmMemo);
                            parseUser.add("listAlarmMemoArray", listAlarmMemo);
                        } else {
                            listAlarmMemo.put("-1");
                            parseUser.add("listAlarmMemoArray", listAlarmMemo);
                        }
                    } else {
                        listAlarmType.put(-1);
                        parseUser.add("listAlarmType", listAlarmType);
                        listAlarmMemo.put("-1");
                        parseUser.add("listAlarmMemoArray", listAlarmMemo);
                        listAlarmVolume.put(-1);
                        parseUser.add("listVolumeArray", listAlarmVolume);
                    }
                    parseUser.add("latitude", listlatitude);
                    parseUser.add("longitude", listlongitude);
                    parseUser.add("listFlagArray", listFlag);
                    parseUser.add("listPlaceArray", listPlace);
                    parseUser.saveInBackground();
                } else {
                    return;
                }
            }
        });
        Intent intent = new Intent(this, FirstActivity.class);
        intent.putExtra("intentLength", currentLength);
        intent.putExtra("intentFlag", total_flag);
        intent.putExtra("intentPlace", alarmName);
        startActivity(intent);
        finish();
    }


    public void mapMove(View view) {

        Intent intent = new Intent(this, NewMapsActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) { // 구분자 값을 받음, 구분자 값이 0 이라면 처리
            if (data != null) { // 보낸 데이터가 있을 시 처리
                latitude = data.getDoubleExtra("latitude", 0);
                longitude = data.getDoubleExtra("longitude", 0); // 받아온 testSum 값을 담음
                Toast.makeText(this, "값 받아오기 성공", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!locationcheck()) {//로케이션 체크
            Toast.makeText(this, "장소를 설정하세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        flag_check(); //플레그 체크

        alarmName = et_alarmName.getText().toString();
        if(alarmName == ""){
            Toast.makeText(this, "알람이름을 설정하세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.getInBackground(objectId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {

                if (e == null) {
                    //쿼리성공
                    JSONArray listFlag = new JSONArray();
                    JSONArray listPlace = new JSONArray();
                    JSONArray listAlarmType = new JSONArray();
                    JSONArray listAlarmVolume = new JSONArray();
                    JSONArray listAlarmMemo = new JSONArray();
                    JSONArray listlatitude = new JSONArray();
                    JSONArray listlongitude = new JSONArray();

                    listFlag.put(total_flag); // Flag를 JSONarray에 삽입한다.(1 = 와이파이 / 2 = 와이파이,진동...)
                    listPlace.put(alarmName);
                    try {
                        listlatitude.put(latitude);
                        listlongitude.put(longitude);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                    // 밑에것들은 알람타입에 관한것들.
                    if (alarm_flag == 0) {
                        listAlarmType.put(alarmType_flag);
                        parseUser.add("listAlarmType", listAlarmType);
                        //볼륨
                        if (alarmType_flag == 1 || alarmType_flag == 4 || alarmType_flag == 8 || alarmType_flag == 11) {
                            listAlarmVolume.put(volume);
                            parseUser.add("listVolumeArray", listAlarmVolume);
                        } else {
                            listAlarmVolume.put(-1);
                            parseUser.add("listVolumeArray", listAlarmVolume);
                        }
                        //푸시 메모
                        if (alarmType_flag > 6) {
                            alarmMemo = et_alarmMemo.getText().toString();
                            listAlarmMemo.put(alarmMemo);
                            parseUser.add("listAlarmMemoArray", listAlarmMemo);
                        } else {
                            listAlarmMemo.put("-1");
                            parseUser.add("listAlarmMemoArray", listAlarmMemo);
                        }
                    } else {
                        listAlarmType.put(-1);
                        parseUser.add("listAlarmType", listAlarmType);
                        listAlarmMemo.put("-1");
                        parseUser.add("listAlarmMemoArray", listAlarmMemo);
                        listAlarmVolume.put(-1);
                        parseUser.add("listVolumeArray", listAlarmVolume);
                    }
                    parseUser.add("latitude", listlatitude);
                    parseUser.add("longitude", listlongitude);
                    parseUser.add("listFlagArray", listFlag);
                    parseUser.add("listPlaceArray", listPlace);
                    parseUser.saveInBackground();
                } else {
                    return;
                }
            }
        });
        Intent intent = new Intent(this, FirstActivity.class);
        intent.putExtra("intentLength", currentLength);
        intent.putExtra("intentFlag", total_flag);
        intent.putExtra("intentPlace", alarmName);
        startActivity(intent);
        finish();
        return true;
    }
}