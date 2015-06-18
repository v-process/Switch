package com.androidbegin.onoffzone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Administrator on 2015-05-04.
 */
public class SignupActivity extends Activity{

    EditText password;
    EditText username;
    Button signup;

    String usernametxt;
    String passwordtxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        signup = (Button) findViewById(R.id.signup);
    }


        public void signupbtn(View arg0) {
            // Retrieve the text entered from the EditText
            usernametxt = username.getText().toString();
            passwordtxt = password.getText().toString();

            // Force user to fill up the form
            if (usernametxt.equals("") && passwordtxt.equals("")) {
                Toast.makeText(getApplicationContext(),
                        "아이디와 비밀번호를 입력해주세요",
                        Toast.LENGTH_LONG).show();

            } else {
                // Save new user data into Parse.com Data Storage
                ParseUser user = new ParseUser();
                user.setUsername(usernametxt);
                user.setPassword(passwordtxt);
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // Show a simple Toast message upon successful registration
                            Toast.makeText(getApplicationContext(),
                                    "회원가입이 완료 되었습니다.",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignupActivity.this, FirstActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "회원가입 에러", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
            }

        }

}
