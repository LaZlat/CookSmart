package com.example.smartcook;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.content.Context;

final class UriChanger {

    public String getRealPathFromURI(Uri contentUri, Context context) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};

            cursor = context.getContentResolver().query(contentUri, proj, null, null,
                    null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}