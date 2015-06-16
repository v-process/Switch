package com.androidbegin.onoffzone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jang on 2015-05-14.
 */
public class ItemAdapter extends BaseAdapter {
    Context context;
    int layoutId;
    ArrayList<ItemData> ItemDataArr;
    LayoutInflater Inflater;
    ItemAdapter(Context context,int layoutId,ArrayList<ItemData> ItemDataArr){
        this.context=context;
        this.layoutId=layoutId;
        this.ItemDataArr=ItemDataArr;
        Inflater= (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ItemDataArr.size();
    }

    @Override
    public Object getItem(int position) {
        return ItemDataArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int pos = position;
        if(convertView==null){
            convertView = Inflater.inflate(layoutId,parent,false);
        }
        ImageView listItemKindIv = (ImageView)convertView.findViewById(R.id.listItemKindIv);
        listItemKindIv.setImageBitmap(ItemDataArr.get(position).kindImg);

//        TextView listItemKindTv = (TextView)convertView.findViewById(R.id.listItemKindTv);
//        listItemKindTv.setText(ItemDataArr.get(position).kindTxt);

        TextView listItemTitle = (TextView)convertView.findViewById(R.id.listItemTitle);
        listItemTitle.setText(ItemDataArr.get(position).title);

        return convertView;
    }
}
