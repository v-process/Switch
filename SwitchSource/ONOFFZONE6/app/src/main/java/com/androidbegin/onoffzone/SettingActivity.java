package com.androidbegin.onoffzone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Administrator on 2015-06-17.
 */
public class SettingActivity extends Activity {

    String objectId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        final ToggleButton tb = (ToggleButton) this.findViewById(R.id.recommend);

        objectId = ParseUser.getCurrentUser().getObjectId();

        tb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tb.isChecked()) {
                    tb.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_on));
                    recommend_flag(1);
                } else {
                    tb.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_off));
                    recommend_flag(0);

                }
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void logoutFunc(View view) {

        if(ParseUser.getCurrentUser().getUsername().length() == 25){
            ParseQuery<ParseUser> query= ParseUser.getQuery();
            query.getInBackground(objectId,new GetCallback<ParseUser>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void done(ParseUser parseUser, ParseException e) {

                    if(e ==null){
                        try {
                            parseUser.delete();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        parseUser.deleteInBackground();



                        ParseUser.logOut();
                    }else{
                        return;
                    }
                }
            });}else{
            ParseUser.logOut();
        }

        Intent intent = new Intent(this, LocationCheck.class);
        PendingIntent sender1 = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender1);

        Intent intent1 = new Intent(this, SelectActivity.class);
        startActivity(intent1);
        finishAffinity(); // 모든 액티비티를 종료시키는 함수. API16, 젤리빈이상부터 지원.
    }

    public void recommend_flag(final int flag){
        ParseQuery<ParseUser> query= ParseUser.getQuery();
        query.getInBackground(objectId,new GetCallback<ParseUser>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void done(ParseUser parseUser, ParseException e) {

                if(e ==null){
                    parseUser.put("recommendFlag",flag);
                    parseUser.saveInBackground();
                }else{
                    return;
                }
            }
        });
    }
}


