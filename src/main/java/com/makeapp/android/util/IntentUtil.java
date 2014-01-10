/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import com.makeapp.android.task.AsyncTask;
import com.makeapp.javase.file.FileUtil;
import com.makeapp.javase.http.URLUtil;
import com.makeapp.javase.lang.StringUtil;
import com.makeapp.javase.util.crypto.Base64;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-11-5 ����4:51 $
 *          $Id$
 */
public class IntentUtil
{
    /**
     * �ļ�·��.
     *
     * @param filePath
     */
    public static Intent createViewFile(String filePath)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(filePath));
        String extName = FileUtil.getExtFromName(filePath);
        if(StringUtil.isValid(extName)){
            extName = extName.toLowerCase();
        }
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extName);
        intent.setDataAndType(uri, mimeType);
        return intent;
    }

    public static void startViewFile(Context context, String filePath)
    {
        try {
            context.startActivity(createViewFile(filePath));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startViewUrl(final Context context, final String fileUrl, boolean neeDialog)
    {
        final ProgressDialog progressDialog = neeDialog ? DialogUtil.createProgressDialog(context) : null;

        new AsyncTask<Object, Intent, String>()
        {
            protected void onPreExecute()
            {
                super.onPreExecute();
                if (progressDialog != null) {
                    progressDialog.setTitle("Loading...");
                    progressDialog.show();
                }
            }

            protected String doInBackground(Object... params)
            {
                String extName = FileUtil.getExtFromName(fileUrl);

                File tmpFile = AndroidUtil.getApplicationImageDir(context);
                tmpFile = new File(tmpFile, Base64.encodeToString(fileUrl) + "." + extName);
                if (!tmpFile.exists()) {
                    try {
                        URLUtil.saveContent(fileUrl, tmpFile);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (tmpFile != null && tmpFile.length() > 0) {
                    return tmpFile.getAbsolutePath();
                }
                return null;
            }

            protected void onPostExecute(String o)
            {
                DialogUtil.dismiss(progressDialog);

                if (o != null) {
                    startViewFile(context, o);
                }
            }
        }.execute();
    }

    public static Intent createSearchMatket(String pkgName)
    {
        Uri uri = Uri.parse("market://search?q=pname:" + pkgName);
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static void startSearchMatket(Context context, String pkgName)
    {
        context.startActivity(createSearchMatket(pkgName));
    }

    public static Intent createViewMatket(String pkgName)
    {
        Uri uri = Uri.parse("market://details?id=" + pkgName);
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static void startViewMatket(Context context, String pkgName)
    {
        context.startActivity(createViewMatket(pkgName));
    }

    public static Intent createInstallAPK(String filePath)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
        return intent;
    }

    public static void startInstallAPK(Context context, String filePath)
    {
        context.startActivity(createInstallAPK(filePath));
    }

    public static Intent createUninstallAPK(String packageName)
    {
        Uri uninstallUri = Uri.fromParts("package", packageName, null);
        return new Intent(Intent.ACTION_DELETE, uninstallUri);
    }

    public static void startUninstallAPK(Context context, String packageName)
    {
        context.startActivity(createUninstallAPK(packageName));
    }

    /**
     * @param to
     * @param cc
     * @param subject
     * @param content
     * @param contentType message/rfc822 text/plain
     * @param attachment
     */
    public static Intent createSendEmail(String[] to, String[] cc, String subject, String content, String contentType, String attachment)
    {
        Intent it = new Intent(Intent.ACTION_SEND);
        if (to != null) {
            it.putExtra(Intent.EXTRA_EMAIL, to);
        }
        if (cc != null) {
            it.putExtra(Intent.EXTRA_CC, cc);
        }
        it.putExtra(Intent.EXTRA_TEXT, content);
        it.putExtra(Intent.EXTRA_SUBJECT, subject);
        it.setType(contentType);
        if (StringUtil.isValid(attachment)) {
            it.putExtra(Intent.EXTRA_STREAM, attachment);
        }
        return it;
    }

    public static void startSendEmail(Context context, String to[], String[] cc, String subject, String content, String contentType, String attachment)
    {
        context.startActivity(createSendEmail(to, cc, subject, content, contentType, attachment));
    }

    public static Intent createSendEmail(String[] to, String[] cc, String subject, String content, String contentType)
    {
        return createSendEmail(to, cc, subject, content, contentType, null);
    }

    public static void startSendEmail(Context context, String to[], String[] cc, String subject, String content, String contentType)
    {
        context.startActivity(createSendEmail(to, cc, subject, content, contentType));
    }

    public static Intent createSendEmail(String[] to, String subject, String content, String contentType)
    {
        return createSendEmail(to, null, subject, content, contentType, null);
    }

    public static void startSendEmail(Context context, String[] to, String subject, String content, String contentType)
    {
        context.startActivity(createSendEmail(to, subject, content, contentType));
    }

    public static Intent createSendEmail(String to, String subject, String content, String contentType)
    {
        return createSendEmail(new String[]{to}, null, subject, content, contentType, null);
    }

    public static void startSendEmail(Context context, String to, String subject, String content, String contentType)
    {
        context.startActivity(createSendEmail(to, subject, content, contentType));
    }

    public static Intent createSendSms(String to, String content)
    {
        Uri uri = Uri.parse("smsto:" + to);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", content);
        return it;
    }

    public static void startSendSms(Context context, String to, String content)
    {
        context.startActivity(createSendSms(to, content));
    }

    public static Intent createSendMMS(String content, String attachment, String attachmentType)
    {
        Uri uri = Uri.parse(attachment);
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra("sms_body", content);
        it.putExtra(Intent.EXTRA_STREAM, uri);
        it.setType(attachmentType);
        return it;
    }

    public static void startCall(Context context, String content, String attachment, String attachmentType)
    {
        context.startActivity(createSendMMS(content, attachment, attachmentType));
    }

    /**
     * <uses-permission id="android .permission.CALL_PHONE" />
     *
     * @param number
     */
    public static Intent createCall(String number)
    {
        Uri uri = Uri.parse("tel:" + number);
        return new Intent(Intent.ACTION_DIAL, uri);
    }

    public static void startCall(Context context, String number)
    {
        context.startActivity(createCall(number));
    }

    public static Intent createNavMap(String startLat, String endLat)
    {
        Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr=startLat%20startLng&daddr=endLat%20endLng&hl=en");
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static void startNavMap(Context context, String startLat, String endLat)
    {
        context.startActivity(createNavMap(startLat, endLat));
    }

    public static Intent createMap(long lat, long lng)
    {
        Uri uri = Uri.parse("geo:" + lat + "," + lng);
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static void startMap(Context context, long lat, long lng)
    {
        context.startActivity(createMap(lat, lng));
    }

    public static Intent createBrowser(String url)
    {
        Uri uri = Uri.parse(url);
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent getPictureFile(Activity context, int reqCode)
    {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            intent.putExtra("return-path", true);
            context.startActivityForResult(intent, reqCode);
            return intent;
        }
        catch (ActivityNotFoundException e) {
        }
        return null;
    }

    public static Intent takeCameraPicture(Activity context, int reqCode)
    {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra("return-path", true);
            context.startActivityForResult(intent, reqCode);
            return intent;
        }
        catch (ActivityNotFoundException e) {
        }
        return null;
    }

    public static Intent cropCameraPicture(Activity context, int reqCode, int witdh, int height)
    {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra("return-path", true);
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", witdh);
            intent.putExtra("outputY", height);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", false); // no face detection
            context.startActivityForResult(intent, reqCode);
            return intent;
        }
        catch (ActivityNotFoundException e) {
        }
        return null;
    }

    public static Intent takeCameraVideo(Activity context, int reqCode)
    {
        try {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra("return-path", true);
            context.startActivityForResult(intent, reqCode);
            return intent;
        }
        catch (ActivityNotFoundException e) {
        }
        return null;
    }

    public static Intent cropPictureFile(Activity context, int reqCode, int witdh, int height)
    {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", witdh);
            intent.putExtra("outputY", height);
            intent.putExtra("return-data", true);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", false);
//            intent.putExtra("return-path", true);
            context.startActivityForResult(intent, reqCode);
            return intent;
        }
        catch (ActivityNotFoundException e) {
        }
        return null;
    }

    public static Intent cropPicture(Activity context, Uri uri, int outputX, int outputY, int requestCode, boolean faceDetection)
    {
//        Intent intent = new Intent("com.android.msg_share_camera.action.CROP");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 2);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", !faceDetection); // no face detection
        try {
            context.startActivityForResult(intent, requestCode);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return intent;
    }


    public static void startBrowser(Context context, String url)
    {
        context.startActivity(createBrowser(url));
    }
}
