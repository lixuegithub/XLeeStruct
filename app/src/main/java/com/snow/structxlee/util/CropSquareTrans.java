package com.snow.structxlee.util;


import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * 裁剪类
 */

public class CropSquareTrans implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(),source.getHeight());//得到大小，判断宽高哪个大
        int x = (source.getWidth()-size)/2;
        int y = (source.getHeight()-size)/2;
        Bitmap bm= Bitmap.createBitmap(source,x,y,size,size);
        if (bm!=source){
            source.recycle();
        }
        return bm;
    }

    @Override
    public String key() {
        return "square()";
    }
}
