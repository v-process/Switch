package com.androidbegin.onoffzone;

import android.annotation.SuppressLint;
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


    int wifi_flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        list = (ListView)findViewById(R.id.itemListView);




        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon);
        itemDataArr = new ArrayList<ItemData>();
        itemDataArr.add(new ItemData(bitmap,"와이파이","나의집"));


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

    }
    public void functiontrans(View v){

        Toast.makeText(getApplicationContext(), "김승철.", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(
                FirstActivity.this,
                FunctionActivity.class);
        startActivity(intent);
    }

    public void addItem(View view) {
        itemDataArr.add(new ItemData(bitmap,"추가","추가추가"));
        mAdapter.notifyDataSetChanged();
    }
    @SuppressLint("ValidFragment")
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

