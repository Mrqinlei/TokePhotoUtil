package com.qinlei.tokephotodemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.qinlei.tokephoto.TokePhotoUtils;
import com.qinlei.tokephoto.callback.TokePhotoCallBack;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    private int REQUEST_PERMISSIONS = 0;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.image);

        /**
         * 只是简单的处理了权限问题
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_PERMISSIONS);
        }

        findViewById(R.id.btn_camera_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokePhotoUtils.getInstance().captureCamera(MainActivity.this, new TokePhotoCallBack() {
                    @Override
                    public void onSuccess(File file) {
                        mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    }
                });
            }
        });

        findViewById(R.id.btn_camera_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokePhotoUtils.getInstance().captureCameraForSquare(MainActivity.this, new TokePhotoCallBack() {
                    @Override
                    public void onSuccess(File file) {
                        mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    }
                });
            }
        });

        findViewById(R.id.btn_camera_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokePhotoUtils.getInstance().captureCameraForCrop(MainActivity.this, new TokePhotoCallBack() {
                    @Override
                    public void onSuccess(File file) {
                        mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    }
                }, 2, 1);
            }
        });

        findViewById(R.id.btn_photo_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokePhotoUtils.getInstance().captureGallery(MainActivity.this, new TokePhotoCallBack() {
                    @Override
                    public void onSuccess(File file) {
                        mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    }
                });
            }
        });

        findViewById(R.id.btn_photo_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokePhotoUtils.getInstance().captureGalleryForSquare(MainActivity.this, new TokePhotoCallBack() {
                    @Override
                    public void onSuccess(File file) {
                        mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    }
                });
            }
        });

        findViewById(R.id.btn_photo_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokePhotoUtils.getInstance().captureGalleryForCorp(MainActivity.this, new TokePhotoCallBack() {
                    @Override
                    public void onSuccess(File file) {
                        mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    }
                }, 2, 1);
            }
        });
    }
}
