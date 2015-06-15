package com.androidbegin.onoffzone;

import android.graphics.Bitmap;

/**
 * Created by jang on 2015-05-14.
 */
public class ItemData {
    Bitmap kindImg;
    String title;
    ItemData(Bitmap kindImg,String title){
        this.kindImg=kindImg;
        this.title=title;
    }
}
