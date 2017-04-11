package com.snow.structxlee.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.snow.structxlee.R;
import com.snow.structxlee.base.BaseCommWithTopBar;
import com.snow.structxlee.popwindow.SelectPicPopupWindow;
import com.snow.structxlee.util.ImageUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by XLee 图片选择
 */

public class SelectPhotoActivity extends BaseCommWithTopBar {
    /**
     * 选择图片的返回码
     */
    public final static int SELECT_IMAGE_PICK_PHOTO = 200;
    public final static int SELECT_IMAGE_TAKE_PHOTO = 201;
    /**
     * 当前选择的图片的路径
     */
    public String mImagePath;
    private SelectPicPopupWindow menuWindow;
    private ImageView ivContent;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_select_photo);
        initView();
    }

    private void initView() {
        ivContent = (ImageView) findViewById(R.id.iv_content);
        ((Button) findViewById(R.id.btn_selectpic)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicturePopupWindow();
            }
        });
    }

    /**
     * 拍照或从图库选择图片(PopupWindow形式)
     */
    public void showPicturePopupWindow() {
        menuWindow = new SelectPicPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏弹出窗口
                menuWindow.dismiss();
                switch (v.getId()) {
                    case R.id.takePhotoBtn:// 拍照
                        takePhoto();
                        break;
                    case R.id.pickPhotoBtn:// 相册选择图片
                        pickPhoto();
                        break;
                    case R.id.cancelBtn:// 取消
                        break;
                    default:
                        break;
                }
            }
        });
        menuWindow.showAtLocation(findViewById(R.id.choose_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            /**
             * 通过指定图片存储路径，解决部分机型onActivityResult回调 data返回为null的情况
             */
            //获取与应用相关联的路径
            String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            //根据当前时间生成图片的名称
            String timestamp = "/" + formatter.format(new Date()) + ".jpg";
            File imageFile = new File(imageFilePath, timestamp);// 通过路径创建保存文件
            mImagePath = imageFile.getAbsolutePath();
            Uri imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
            startActivityForResult(intent, SELECT_IMAGE_TAKE_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在!", Toast.LENGTH_LONG).show();
        }
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_IMAGE_PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Log.e("xlee", "imageSrc:" + data.getData().toString());
            String imagePath = "";
            switch (requestCode) {
                case SELECT_IMAGE_PICK_PHOTO:
                    if (data != null && data.getData() != null) {//有数据返回直接使用返回的图片地址
//                        imagePath = ImageUtils.getFilePathByFileUri(this, data.getData());
                        Bitmap bm = data.getParcelableExtra("data");
                        ivContent.setImageBitmap(bm);
                    } else {//无数据使用指定的图片路径
                        imagePath = mImagePath;
                        Log.e("xlee","image is null");
                    }
                    break;
                case SELECT_IMAGE_TAKE_PHOTO:
                    Bitmap bm = data.getParcelableExtra("data");
                    ivContent.setImageBitmap(bm);
                    break;
                default:
                    break;
            }
        }

    }
}
