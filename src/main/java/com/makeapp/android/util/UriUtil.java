package com.makeapp.android.util;

import android.net.Uri;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-9-28
 * Time: обнГ2:49
 */
public class UriUtil {

    public static Uri getUri(String packageName, int resId) {
        return Uri.parse("android.resource://" + packageName + "/" + resId);
    }
}
