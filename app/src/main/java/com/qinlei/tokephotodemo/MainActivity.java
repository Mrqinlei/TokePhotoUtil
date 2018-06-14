package com.qinlei.tokephotodemo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CAPTURE_CAMERA = 0;
    private final int REQUEST_CAPTURE_GALLERY = 1;
    private final int REQUEST_CAPTURE_CAMERA_AND_CROP = 2;
    private final int REQUEST_CAPTURE_GALLERY_AND_CROP = 3;
    private final int REQUEST_CAPTURE_CROP = 4;

    private final int NOT_CORP = -1;

    private ImageView mImageView;
    private File captureCameraFile;
    private File cropFile;
    private int aspectX;
    private int aspectY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.image);

        findViewById(R.id.btn_camera_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureCamera();
            }
        });

        findViewById(R.id.btn_photo_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureGallery();
            }
        });

        findViewById(R.id.btn_camera_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureCameraForSquare();
            }
        });

        findViewById(R.id.btn_photo_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureGalleryForSquare();
            }
        });

        findViewById(R.id.btn_camera_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureCameraForCrop(true, 2, 1);
            }
        });

        findViewById(R.id.btn_photo_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureGalleryForCorp(true, 2, 1);
            }
        });
    }

    public void captureCamera() {
        captureCameraForCrop(false, NOT_CORP, NOT_CORP);
    }

    public void captureCameraForSquare() {
        captureCameraForCrop(true, 1, 1);
    }

    public void captureCameraForCrop(boolean isCrop, int aspectX, int aspectY) {
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        Intent captureCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureCameraFile = new File(getExternalCacheDir(), "captureCameraTemp.jpeg");
        if (Build.VERSION.SDK_INT >= 24) {
            Uri contentUri = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".fileProvider", captureCameraFile);
            captureCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            captureCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(captureCameraFile));
        }
        startActivityForResult(captureCameraIntent, isCrop ? REQUEST_CAPTURE_CAMERA_AND_CROP : REQUEST_CAPTURE_CAMERA);
    }

    public void captureGallery() {
        captureGalleryForCorp(false, NOT_CORP, NOT_CORP);
    }

    public void captureGalleryForSquare() {
        captureGalleryForCorp(true, 1, 1);
    }

    public void captureGalleryForCorp(boolean isCrop, int aspectX, int aspectY) {
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, isCrop ? REQUEST_CAPTURE_GALLERY_AND_CROP : REQUEST_CAPTURE_GALLERY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CAPTURE_CAMERA) {
            mImageView.setImageBitmap(BitmapFactory.decodeFile(captureCameraFile.getAbsolutePath()));
        } else if (requestCode == REQUEST_CAPTURE_GALLERY) {
            Uri imageUri = data.getData();
            File file = new File(getRealPathFromURI(MainActivity.this, imageUri));
            mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        } else if (requestCode == REQUEST_CAPTURE_CAMERA_AND_CROP) {
            if (Build.VERSION.SDK_INT >= 24) {
                Uri contentUri = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".fileProvider", captureCameraFile);
                cropPicture(contentUri);
            } else {
                cropPicture(Uri.fromFile(captureCameraFile));
            }
        } else if (requestCode == REQUEST_CAPTURE_GALLERY_AND_CROP) {
            Uri imageUri = data.getData();
            cropPicture(imageUri);
        } else if (requestCode == REQUEST_CAPTURE_CROP) {
            mImageView.setImageBitmap(BitmapFactory.decodeFile(cropFile.getAbsolutePath()));
        }
    }

    public void cropPicture(Uri uri) {
        cropFile = new File(getExternalCacheDir(), "cropTemp.jpeg");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        if (aspectX > 0 && aspectY > 0) {
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
            aspectX = 0;
            aspectY = 0;
        }
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, REQUEST_CAPTURE_CROP);
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            if (idx == -1) {
                //没有获取到图片地址
            }
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
