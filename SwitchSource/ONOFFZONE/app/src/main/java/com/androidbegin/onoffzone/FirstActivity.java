package com.androidbegin.onoffzone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FirstActivity extends Activity {

    Button logout;
    ListView list;
    ArrayList<ItemData> itemDataArr;
    ItemAdapter mAdapter;
    int itemPosition;
    FirstActivityDialog mFirstActivityDialog;
    Bitmap bitmap;

    Button wifi_func;
    AudioManager aManager;
    private ConnectivityManager connectivityManager;

    String objectId;
    int currentLength =0;
    int intentLength =0;
    String intentPlace;
    int intentFlag=0;

    int wifi_flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        objectId = ParseUser.getCurrentUser().getObjectId();

        list = (ListView)findViewById(R.id.itemListView);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon);
        itemDataArr = new ArrayList<ItemData>();

        mAdapter = new ItemAdapter(this,R.layout.first_activity_list_item,itemDataArr);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mFirstActivityDialog = new FirstActivityDialog();
                        mFirstActivityDialog.show(getFragmentManager(),"MYTAG");
                        itemPosition = i;
//                        Toast.makeText(FirstActivity.this,itemDataArr.get(i).title,Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
        });
        Intent intent = getIntent();
        intentLength = intent.getIntExtra("intentLength",-2);
        intentPlace = intent.getStringExtra("intentPlace");
        intentFlag = intent.getIntExtra("intentFlag", -2);

        listRefresh();
    }

    public void listRefresh(){

        final String[] listPlace = new String[1];
        final String[] listFlag = new String[1];
        final JSONArray[] listFlagArray = {new JSONArray()};
        final JSONArray[] listPlaceArray = {new JSONArray()};
        final int[] ArrayLength = {0};
        final int[] listFlagtmp = {0};


        ParseQuery<ParseUser> query= ParseUser.getQuery();
        query.getInBackground(objectId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {

                if(e ==null){
                    try {
                        ArrayLength[0] = parseUser.getJSONArray("listFlagArray").length();
                        currentLength = ArrayLength[0];
                    }catch (NullPointerException e1){
                        ArrayLength[0] = 0;
                        currentLength = ArrayLength[0];
                    }

                    listFlagArray[0] = parseUser.getJSONArray("listFlagArray");
                    listPlaceArray[0] = parseUser.getJSONArray("listPlaceArray");

                    for(int i=0; i<ArrayLength[0];i++) {
                        try {
                            listFlag[0] = listFlagArray[0].getString(i);
                            listPlace[0] = listPlaceArray[0].getString(i);
                            listFlag[0] = listFlag[0].substring(1, listFlag[0].length() - 1);
                            listPlace[0] = listPlace[0].substring(2,listPlace[0].length()-2);
                            listFlagtmp[0] = Integer.parseInt(listFlag[0]);
                            getIntentItem(listFlagtmp[0],listPlace[0]);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if(intentLength == currentLength){
                        getIntentItem(intentFlag,intentPlace);
                        currentLength++;
                    }
                }else{
                }
            }
        });
    }
    public void functiontrans(View v){

        Toast.makeText(getApplicationContext(), "김승철.", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(
                FirstActivity.this,
                FunctionActivity.class);
        startActivity(intent);
    }

    public void addItem(View view) {
        Intent intent = new Intent(this, FunctionActivity.class);
        intent.putExtra("currentLength",currentLength);
        startActivity(intent);
        finish();
    }
    public void getIntentItem(int flag,String place){
        String flagToString="";
        switch (flag){
            case 0:
                flagToString = "기능 설정 안됨";
                break;
            case 1:
                flagToString = "와이파이";
                break;
            case 2:
                flagToString = "와이파이/진동";
                break;
            case 3:
                flagToString = "와이파이/푸시";
                break;
            case 4:
                flagToString = "진동";
                break;
            case 5:
                flagToString = "진동/푸시";
                break;
            case 6:
                flagToString = "푸시";
                break;
            case 7:
                flagToString = "와이파이/진동/푸시";
                break;
            default: flagToString = "에러";
        }

        itemDataArr.add(new ItemData(bitmap,place));
        mAdapter.notifyDataSetChanged();
    }

    public class FirstActivityDialog extends DialogFragment {
        TextView dialogYes;

        public Dialog onCreateDialog(Bundle saveInstanceState){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
            mBuilder.setView(mLayoutInflater.inflate(R.layout.first_dialog, null));
            return  mBuilder.create();
        }

        public void closeDialog(){
            FirstActivityDialog.this.dismiss();
        }
        public void onStop(){
            super.onStop();
        }
    }
    public void dialogYes(View view) {
        itemDataArr.remove(itemPosition);
        mAdapter.notifyDataSetChanged();
        mFirstActivityDialog.closeDialog();
    }

    public void dialogNo(View view) {
        mFirstActivityDialog.closeDialog();
    }



}

