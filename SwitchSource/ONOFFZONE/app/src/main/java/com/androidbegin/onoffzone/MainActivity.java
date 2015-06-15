package com.androidbegin.onoffzone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

public class MainActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


        Parse.initialize(this, "ntOZOUf2S4MVuGQ4D7SHdJ4qxlCJa7Em8sp00hOn", "DcFDqWrpJlRPUjCVZmRaMLDw5oHQPQUprqhCN4oy");

		// 사용자가 익명인지 아닌지 판단.
		if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
			// 익명인 경우 로그인 엑티비티로
			Intent intent = new Intent(MainActivity.this,
					SelectActivity.class);
			startActivity(intent);
			finish();
		} else {
			// 익명사용자가 아닌경우
			// 현재유저가져오기.
			ParseUser currentUser = ParseUser.getCurrentUser();
			if (currentUser != null) {
				// 기존 로그인이 되어있다면 FirstActivity로.
				Intent intent = new Intent(MainActivity.this, FirstActivity.class);
				startActivity(intent);
				finish();
			} else {
				// 로그인엑티비티로.
				Intent intent = new Intent(MainActivity.this,
						SelectActivity.class);
				startActivity(intent);
				finish();
			}
		}

	}
}
