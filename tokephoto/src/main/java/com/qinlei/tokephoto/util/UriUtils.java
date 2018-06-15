package com.qinlei.tokephoto.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

public class UriUtils {

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null,
                null, null);
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

    public static Uri getUriFor24(Context context, File file) {
        Uri captureUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            captureUri = convertUriFor24(context, file);
        } else {
            captureUri = Uri.fromFile(file);
        }
        return captureUri;
    }

    private static Uri convertUriFor24(Context context, File file) {
        return FileProvider.getUriForFile(context,
                context.getPackageName() + ".fileProvider", file);
    }
}
