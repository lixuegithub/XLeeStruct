package com.snow.structxlee.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 *Picasso工具类
 */

public class PicassoUtils {
    //裁剪图片大小
    public static void loadImageViewSize(Context context, String path, int width, int height, ImageView imageView){
        Picasso.with(context).load(path).resize(width,height).centerCrop().into(imageView);
    }

    public static void loadImageViewModler(Context context, String path, int resID, ImageView imageView){
        Picasso.with(context).load(path).fit().placeholder(resID).into(imageView);
    }

    public static void loadImageViewCrop(Context context, String path, ImageView imageView){
        Picasso.with(context).load(path).transform(new CropSquareTransformation()).into(imageView);
    }

    public  static  class CropSquareTransformation implements Transformation {

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
}
