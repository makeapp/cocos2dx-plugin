package com.makeapp.android.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-5-17
 * Time: ÏÂÎç2:43
 */
public class CameraUtil
{
    public static Bitmap getPicture(Intent data)
    {
        try {
            Bundle extras = data.getExtras();
            if (extras != null)
                return (Bitmap) extras.get("data");
        } catch (Exception e) {
        }
        return null;
    }

    public static void takePicture(Activity context)
    {
        Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
        context.startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
    }

    public static void takePicture(Activity context, String path)
    {
        Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
        context.startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
    }

}
