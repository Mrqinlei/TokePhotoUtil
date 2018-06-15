package com.qinlei.tokephoto.delegate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.qinlei.tokephoto.callback.AttachCallBack;
import com.qinlei.tokephoto.callback.TokePhotoCallBack;
import com.qinlei.tokephoto.util.ImageRotateUtil;
import com.qinlei.tokephoto.util.UriUtils;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class DelegateFragment extends Fragment {
    private final int REQUEST_CAPTURE_CAMERA = 0;
    private final int REQUEST_CAPTURE_GALLERY = 1;
    private final int REQUEST_CAPTURE_CAMERA_AND_CROP = 2;
    private final int REQUEST_CAPTURE_GALLERY_AND_CROP = 3;
    private final int REQUEST_CAPTURE_CROP = 4;

    private final int NOT_CORP = -1;

    private File captureCameraFile;
    private File cropFile;

    private int aspectX;
    private int aspectY;

    private Context mContext;

    private AttachCallBack attachCallBack;
    private TokePhotoCallBack tokePhotoCallBack;

    public static DelegateFragment newInstance() {
        Bundle args = new Bundle();
        DelegateFragment fragment = new DelegateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 有可能拍照会在 onAttach 之前所以需要确保在 onAttach 后执行
     *
     * @param attachCallBack
     */
    public void setAttachCallBack(AttachCallBack attachCallBack) {
        tokePhotoCallBack = null;
        this.attachCallBack = attachCallBack;
        if (isAdded()) {
            attachCallBack.onAttach();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (attachCallBack != null) {
            attachCallBack.onAttach();
        }
    }

    public void captureCamera(TokePhotoCallBack callBack) {
        captureCameraForCrop(callBack, false, NOT_CORP, NOT_CORP);
    }

    public void captureCameraForSquare(TokePhotoCallBack callBack) {
        captureCameraForCrop(callBack, true, 1, 1);
    }

    public void captureCameraForCrop(TokePhotoCallBack callBack, boolean isCrop, int aspectX, int aspectY) {
        tokePhotoCallBack = callBack;
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        captureCameraFile = new File(mContext.getExternalCacheDir(), "captureCameraTemp.jpeg");
        Intent captureCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri captureUri = UriUtils.getUriFor24(mContext, captureCameraFile);
        captureCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, captureUri);
        startActivityForResult(captureCameraIntent, isCrop ? REQUEST_CAPTURE_CAMERA_AND_CROP : REQUEST_CAPTURE_CAMERA);
    }

    public void captureGallery(TokePhotoCallBack callBack) {
        captureGalleryForCorp(callBack, false, NOT_CORP, NOT_CORP);
    }

    public void captureGalleryForSquare(TokePhotoCallBack callBack) {
        captureGalleryForCorp(callBack, true, 1, 1);
    }

    public void captureGalleryForCorp(TokePhotoCallBack callBack, boolean isCrop, int aspectX, int aspectY) {
        tokePhotoCallBack = callBack;
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, isCrop ? REQUEST_CAPTURE_GALLERY_AND_CROP : REQUEST_CAPTURE_GALLERY);
    }

    public void cropPicture(Uri uri) {
        cropFile = new File(mContext.getExternalCacheDir(), "cropTemp.jpeg");
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CAPTURE_CAMERA) {
            if (tokePhotoCallBack != null) {
                ImageRotateUtil.of().correctImage(mContext, captureCameraFile.getAbsolutePath());
                tokePhotoCallBack.onSuccess(captureCameraFile);
            }
        } else if (requestCode == REQUEST_CAPTURE_GALLERY) {
            File file = new File(UriUtils.getRealPathFromURI(mContext, data.getData()));
            if (tokePhotoCallBack != null) {
                ImageRotateUtil.of().correctImage(mContext, file.getAbsolutePath());
                tokePhotoCallBack.onSuccess(file);
            }
        } else if (requestCode == REQUEST_CAPTURE_CAMERA_AND_CROP) {
            cropPicture(UriUtils.getUriFor24(mContext, captureCameraFile));
        } else if (requestCode == REQUEST_CAPTURE_GALLERY_AND_CROP) {
            cropPicture(data.getData());
        } else if (requestCode == REQUEST_CAPTURE_CROP) {
            if (tokePhotoCallBack != null) {
                tokePhotoCallBack.onSuccess(cropFile);
            }
        }
    }


}
