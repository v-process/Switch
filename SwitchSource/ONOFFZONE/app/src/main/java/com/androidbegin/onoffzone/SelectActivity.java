package com.androidbegin.onoffzone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Administrator on 2015-05-06.
 */


public class SelectActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity);

        Button login_btn = (Button) findViewById(R.id.select_login);
        Button guest_btn = (Button) findViewById(R.id.select_guest);
        Button signup_btn = (Button) findViewById(R.id.select_signup);


    }

    public void clickLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
    public void clickSignup(View view){
        Intent intent2 = new Intent(this, SignupActivity.class);
        startActivity(intent2);

    }
    public void clickGuest(View view){

        ParseAnonymousUtils.logIn(new LogInCallback() {//익명 로그인시 데이터 보존이 안돼
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d("MyApp", "Anonymous login failed.");
                } else {
                    Log.d("MyApp", "Anonymous user logged in.");
                    Intent intent3 = new Intent(SelectActivity.this, FirstActivity.class);
                    startActivity(intent3);
                }
            }
        });


    }
}

