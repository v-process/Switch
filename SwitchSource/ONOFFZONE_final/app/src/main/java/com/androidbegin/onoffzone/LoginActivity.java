package com.androidbegin.onoffzone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {
	// 변수 선언.
	Button loginbutton;
	Button signup;
	String usernametxt;
	String passwordtxt;
	EditText password;
	EditText username;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);

		loginbutton = (Button) findViewById(R.id.login);

		loginbutton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// 입력된 텍스트 받기.
				usernametxt = username.getText().toString();
				passwordtxt = password.getText().toString();

				// 유저 확인.
				ParseUser.logInInBackground(usernametxt, passwordtxt,
						new LogInCallback() {
							public void done(ParseUser user, ParseException e) {
								if (user != null) {
									// 가입되어있으면 로그인.
									Intent intent = new Intent(
											LoginActivity.this,
											FirstActivity.class);
									startActivity(intent);
									Toast.makeText(getApplicationContext(),
											"로그인되었습니다.",
											Toast.LENGTH_LONG).show();
									finish();
								} else {
									Toast.makeText(
											getApplicationContext(),
											"아이디와 비밀번호를 확인하고 회원가입해주세요.",
											Toast.LENGTH_LONG).show();
								}
							}
						});
			}
		});
	}
}
