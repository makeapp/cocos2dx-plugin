/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:13-3-26 下午1:05 $
 *          $Id$
 */
public class ContentUtil
{
    public static String getFilePath(Context context, Uri uri)
    {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor=null;
        try {
            cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            }
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
