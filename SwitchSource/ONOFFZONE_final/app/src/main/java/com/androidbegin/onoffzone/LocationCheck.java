package com.androidbegin.onoffzone;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

public class LocationCheck extends BroadcastReceiver {

    private GpsInfo gps;
    String objectId;

    double current_latitude;
    double current_longitude;

    Context mContext;

    NotificationManager nm;
    @Override
    public void onReceive(Context context, Intent intent) //10초마다 이리루 들어옵니다
    {

        this.mContext = context;
        objectId = ParseUser.getCurrentUser().getObjectId();

        gps = new GpsInfo(context);

        if (gps.isGetLocation()) {

            current_latitude = gps.getLatitude();
            current_longitude = gps.getLongitude();
            CompareLocate();
//            Toast.makeText(context,
//                    "당신의 위치 - \n위도: " + current_latitude + "\n경도: " + current_longitude,
//                    Toast.LENGTH_LONG).show();
        }


    }
    public void CompareLocate(){
        ParseQuery<ParseUser> query= ParseUser.getQuery();
        query.getInBackground(objectId,new GetCallback<ParseUser>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void done(ParseUser parseUser, ParseException e) {

                if(e ==null){
                    int arrayLength =0;

                    JSONArray listlatitude = new JSONArray();
                    JSONArray listlongitude = new JSONArray();
                    double latitude = 0;
                    double longitude = 0;
                    listlatitude = parseUser.getJSONArray("latitude");
                    listlongitude = parseUser.getJSONArray("longitude");
                    try {
                        arrayLength = listlatitude.length();
                    }catch (NullPointerException e1){
                        arrayLength = 0;
                    }
                    for(int i=0;i<arrayLength;i++){
                        //if(현재위도경도값이 서버에 저장되있는 위도경도값과 같다면)
                        // 현재 라티튜트값-0.001 <서버에 저장되있는 라티튜트값 < 현재 라티튜트값 +0.001  &&
                        // ㅇㅇ >현재 -0.001  & ㅇㅇ<현재 +0.001 && \
                        try {
                            latitude = Double.parseDouble(listlatitude.getString(i).substring(1, listlatitude.getString(i).length() - 1));
                            longitude = Double.parseDouble(listlongitude.getString(i).substring(1, listlongitude.getString(i).length() - 1));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        double a = 0.0009;
                        if(latitude>current_latitude-a & latitude<current_latitude+a & longitude>current_longitude-a & longitude <current_longitude+a){


                            JSONArray listFlag = new JSONArray();
                            JSONArray listAlarmType = new JSONArray();
                            JSONArray listAlarmVolume = new JSONArray();
                            JSONArray listAlarmMemo = new JSONArray();
                            JSONArray listPlace = new JSONArray();
                            listFlag = parseUser.getJSONArray("listFlagArray");
                            listAlarmType = parseUser.getJSONArray("listAlarmType");
                            listAlarmVolume = parseUser.getJSONArray("listVolumeArray");
                            listAlarmMemo = parseUser.getJSONArray("listAlarmMemoArray");
                            listPlace = parseUser.getJSONArray("listPlaceArray");
                            try {
                                //플레그 체크, 알람타입체크
                                latitude = Double.parseDouble(listlatitude.getString(i).substring(1, listlatitude.getString(i).length() - 1));

                                flagChk(Integer.parseInt(listFlag.getString(i).substring(1, listFlag.getString(i).length() - 1)));
                                //알람타입체크는 푸시메시지 관련된기능인데.. 아직 잘모르겠군.
                                typeChk(Integer.parseInt(listAlarmType.getString(i).substring(1, listAlarmType.getString(i).length() - 1)),listPlace.getString(i).substring(2,listPlace.getString(i).length()-2),listAlarmMemo.getString(i).substring(2,listAlarmMemo.getString(i).length()-2));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }}else{
                    return;
                }
            }
        });
    }

    public void flagChk(int flag){
        bellTrans(flag);
        wifiTrans(flag);
    }
    public void typeChk(int type,String a, String b){
        if(type>6){
            noti(a,b);
        }
    }

    public void wifiTrans(int flag){
        WifiManager wManager = (WifiManager)this.mContext.getSystemService(Context.WIFI_SERVICE);
        if(flag==1 || flag ==2 || flag==3 || flag ==7){
            wManager.setWifiEnabled(true);
        }else {
            wManager.setWifiEnabled(false);
        }
    }
    public void bellTrans(int flag){
        AudioManager aManager = (AudioManager)this.mContext.getSystemService(Context.AUDIO_SERVICE);
        if (flag==2 || flag==4|| flag==5 || flag==7){
            aManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }else{
            aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }
    void noti(String Title, String content){
        nm = (NotificationManager) this.mContext.getSystemService(this.mContext.NOTIFICATION_SERVICE);//노티 등록
        // nm.cancel(1);//누르고 없애기
        CharSequence from =Title;//제목
        CharSequence message = content;
        //PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);

        @SuppressWarnings("deprecation")
        Notification notif = new Notification(R.drawable.status3,"ONOFFZONE 알람입니다." , System.currentTimeMillis());

        Intent notificationIntent = new Intent(this.mContext, SplashActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this.mContext, 0, notificationIntent, 0);
        notif.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.defaults |= Notification.DEFAULT_VIBRATE;
        notif.defaults |= Notification.DEFAULT_SOUND;

        notif.setLatestEventInfo(mContext, from, message, pendingIntent);
        nm.notify(1, notif);
    }
}





