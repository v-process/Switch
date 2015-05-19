package com.androidbegin.onoffzone;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends Activity {

	Button logout;
    Button wifi_func;
    AudioManager aManager;
    private ConnectivityManager connectivityManager;


    int wifi_flag = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        TextView txtuser = (TextView) findViewById(R.id.txtuser);

        logout = (Button) findViewById(R.id.logout);

    }

public void functiontrans(View v){

    Toast.makeText(getApplicationContext(), "김승철.", Toast.LENGTH_LONG).show();

    Intent intent = new Intent(
            FirstActivity.this,
            FunctionActivity.class);
    startActivity(intent);
}
}