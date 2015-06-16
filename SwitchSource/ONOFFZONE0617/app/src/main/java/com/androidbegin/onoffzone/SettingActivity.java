package com.androidbegin.onoffzone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.parse.ParseUser;

/**
 * Created by Administrator on 2015-06-17.
 */
public class SettingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        final ToggleButton tb = (ToggleButton) this.findViewById(R.id.recommend);

        tb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tb.isChecked()) {
                    tb.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_off));

                } else {
                    tb.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_on));

                }
            }
        });

    }

    public void logoutFunc(View view) {
        ParseUser.logOut();
        Intent intent1 = new Intent(this, SelectActivity.class);
        startActivity(intent1);
        finish();
    }
}
