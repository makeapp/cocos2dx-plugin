/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import java.io.File;
import java.lang.reflect.Field;

import android.app.Activity;
import android.app.ActivityManagerNative;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.RemoteException;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.makeapp.javase.file.FileUtil;
import com.makeapp.javase.http.HttpException;
import com.makeapp.javase.http.HttpUtil;
import com.makeapp.javase.lang.ArrayUtil;
import com.makeapp.javase.lang.StringUtil;
import com.makeapp.javase.util.DataUtil;
import com.makeapp.javase.util.StreamInterceptor;
import com.makeapp.javase.util.URLUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:12-12-11 ����6:07 $
 *          $Id$
 */
public class AndroidUtil
{
    /**
     * 1212 http://58.215.189.90/release/yiyang-crm-1.2.12.apk
     * Changes :
     * s
     * s
     * s
     *
     * @param context
     * @param url
     */
    public static VersionInfo checkVersion(Context context, String url)
    {
        VersionInfo versionInfo = new VersionInfo();
        try {
            String content = HttpUtil.getHttpClient(url).getText(url);
            if (StringUtil.isValid(content)) {
                int pos1 = 0;
                if ((pos1 = url.indexOf("/restapi/application")) > 0) {
                    JSONObject jsonObject = new JSONObject(content);
                    int thisVersionCode = PackageUtil.getPackageVersionCode(context);
                    int versionCode = jsonObject.optInt("versionCode");
                    versionInfo.changes = jsonObject.optString("changes");
                    if (versionCode > thisVersionCode) {
                        versionInfo.newVersion = true;
                        versionInfo.apkURL = url.substring(0, pos1) + jsonObject.optString("path");
                    }
                }
                else {
                    int pos = content.indexOf("\n");
                    if (pos > 0) {
                        String apkInfo = content.substring(0, pos);
                        if (pos + 1 < content.length()) {
                            versionInfo.changes = content.substring(pos + 1);
                        }
                        String[] params = StringUtil.toStringArray(apkInfo, ' ');
                        if (ArrayUtil.isValid(params)) {
                            int thisVersionCode = PackageUtil.getPackageVersionCode(context);
                            int versionCode = DataUtil.getInt(params[0]);
                            if (versionCode > thisVersionCode) {
                                versionInfo.newVersion = true;
                                versionInfo.apkURL = params[1];
                            }
                        }
                    }
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (HttpException e) {
            e.printStackTrace();
        }
        return versionInfo;
    }

    public static void showVersionUpgrade(final Context context, final String url)
    {
        final AsyncTask<String, Long, File> upgradeTask = new AsyncTask<String, Long, File>()
        {
            long total = 0;
            ProgressDialog progressDialog = null;
            boolean canceled = false;

            @Override
            protected void onPreExecute()
            {
                String title = ResourcesUtil.getString(context, "cv_progress_dialog_title");
                progressDialog = new ProgressDialog(context);
                progressDialog.setMax(100);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle(title);
                progressDialog.setProgress(0);
                progressDialog.show();
                progressDialog.onStart();
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    public void onCancel(DialogInterface dialog)
                    {
                        canceled = true;
                        String cancel = ResourcesUtil.getString(context, "cv_task_canceled");
                        ToastUtil.show(context, cancel);
                    }
                });
            }

            @Override
            protected void onProgressUpdate(Long... values)
            {
                int progress = DataUtil.getInt(values[0], 0);
                progressDialog.setProgress((int) (progress * 100 / total));
                super.onProgressUpdate(values);
            }

            @Override
            protected File doInBackground(String... params)
            {
                try {
                    File tmp = getApplicationExternalStorage(context, "tmp");
                    File file = new File(tmp, String.valueOf(System.currentTimeMillis()) + ".apk");

                    URLUtil.saveContent(params[0], file, new StreamInterceptor()
                    {
                        public boolean begin(long t)
                        {
                            total = t;
                            return true;
                        }

                        public boolean accept(long size)
                        {
                            publishProgress(size);
                            if (canceled) {
                                return false;
                            }
                            return true;
                        }
                    });
                    return file;
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(File apkFile)
            {
                DialogUtil.dismiss(progressDialog);
                if (!canceled) {
                    if (FileUtil.isValid(apkFile)) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                        context.startActivity(intent);
                        if (context instanceof Activity) {
                            Activity activity = (Activity) context;
//                            activity.finish();
                        }
                    }
                    else {
                        String title = ResourcesUtil.getString(context, "cv_download_fault");
                        ToastUtil.show(context, title);
                    }
                }
            }
        };

        AsyncTask<String, Integer, VersionInfo> checkVersion = new AsyncTask<String, Integer, VersionInfo>()
        {
            protected VersionInfo doInBackground(String... params)
            {
                return checkVersion(context, params[0]);
            }

            protected void onPostExecute(final VersionInfo versionInfo)
            {
                super.onPostExecute(versionInfo);

                if (versionInfo.newVersion) {
                    String title = ResourcesUtil.getString(context, "cv_upgrade_title");
                    String cancel = ResourcesUtil.getString(context, "cv_cancel");
                    String ok = ResourcesUtil.getString(context, "cv_ok");
                    DialogUtil.showAlert(context, title, versionInfo.changes, cancel, ok, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            upgradeTask.execute(versionInfo.apkURL);
                        }
                    });
                }
//                else {
//                    ToastUtil.show(context, ResourcesUtil.getString(context, "cv_new_title"));
//                }
            }
        };

        checkVersion.execute(url);
    }

    public static class VersionInfo
    {
        boolean newVersion = false;
        String changes = "";
        String apkURL;
    }

    public static File getExternalStorage()
    {
        if (android.os.Build.MODEL.equals("SCH-I939") || android.os.Build.MODEL.equals("GT-I9300")) {
            return new File("/mnt/extSdCard");
        }

        if (android.os.Build.MODEL.equals("XT882")) {
            return new File("/sdcard-ext/");
        }

        if (android.os.Build.MODEL.equals("XT3X")) {
            return new File("/sdcard/external_sd/");
        }

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return Environment.getExternalStorageDirectory();
        }
        else {
            return null;
        }
    }

    public static File getApplicationExternalStorage(Context context, String type)
    {
        if (context == null) {
            return null;
        }
        File file = getExternalStorage(context.getPackageName(), type);
        if (file == null) {
            file = context.getExternalFilesDir(type);
            if (file == null) {
                file = new File(context.getFilesDir(), type);
            }
        }

        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    public static File getApplicationThumbnailDir(Context context)
    {
        return getApplicationExternalStorage(context, ".thumbnail");
    }

    public static File getApplicationImageDir(Context context)
    {
        return getApplicationExternalStorage(context, ".image");
    }

    public static File getApplicationVideoDir(Context context)
    {
        return getApplicationExternalStorage(context, ".video");
    }

    public static File getApplicationAudioDir(Context context)
    {
        return getApplicationExternalStorage(context, ".audio");
    }

    public static File getApplicationCacheDir(Context context)
    {
        return getApplicationExternalStorage(context, ".cache");
    }

    public static File getApplicationTempFile(Context context, String type, String extName)
    {
        if (context == null) {
            return null;
        }
        File dir = getApplicationExternalStorage(context, type);

        return FileUtil.createTemp(dir, extName);
    }

    public static File getExternalStorage(String... types)
    {
        File file = getExternalStorage();
        if (file != null && ArrayUtil.isValid(types)) {
            for (String type : types) {
                file = new File(file, type);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
        }
        return file;
    }

    public static String getSystemInfo(Context context)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("package=").append(context.getPackageName()).append("\n");
        buffer.append("versionCode=").append(PackageUtil.getPackageVersionCode(context)).append("\n");
        buffer.append("versionName=").append(PackageUtil.getPackageVersionName(context)).append("\n");
        buffer.append("").append("\n");
        // ʹ�÷������ռ��豸��Ϣ.��Build���а�����豸��Ϣ,
        // ����: ϵͳ�汾��,�豸����� �Ȱ�����Գ����������Ϣ
        // ������Ϣ��ο�����Ľ�ͼ
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                buffer.append(field.getName()).append("=");
                buffer.append(field.get(null) + "").append("\n");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }

    public static Configuration getConfiguration()
    {
        try {
            return ActivityManagerNative.getDefault().getConfiguration();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getExternalStorageFreePercent()
    {
        StatFs fs = getExternalStorageStatFs();
        if (fs == null) {
            return -1;
        }
        float totalBlocks = fs.getBlockCount();
        float availaBlock = fs.getAvailableBlocks();
        float s = availaBlock / totalBlocks;
        s *= 100;
        return (int) s;
    }

    public static int getExternalStorageUsedPercent()
    {
        return 100 - getExternalStorageFreePercent();
    }

    public static StatFs getExternalStorageStatFs()
    {
        int result = 0;
        try {
            // 取得sdcard文件路径
            StatFs statFs = new StatFs(getExternalStorage().getPath());
            return statFs;
//               // 获取BLOCK数量
//               float totalBlocks = statFs.getBlockCount();
//               // 可使用的Block的数量
//               float availaBlock = statFs.getAvailableBlocks();
//               float s = availaBlock / totalBlocks;
//               s *= 100;
//               result = (int) s;
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public static String getVersion()
    {
        return android.os.Build.MODEL + ","
            + android.os.Build.VERSION.SDK + ","
            + android.os.Build.VERSION.RELEASE;
    }


    /**
     * 1 (0x00000001)           Android 1.0             BASE
     * 2 (0x00000002)           Android 1.1             BASE_1_1
     * 3 (0x00000003)           Android 1.5             CUPCAKE
     * 4 (0x00000004)           Android 1.6             DONUT
     * 5 (0x00000005)           Android 2.0             ECLAIR
     * 6 (0x00000006)           Android 2.0.1          ECLAIR_0_1
     * 7 (0x00000007)           Android 2.1             ECLAIR_MR1
     * 8 (0x00000008)           Android 2.2             FROYO
     * 9 (0x00000009)           Android 2.3             GINGERBREAD
     * 10 (0x0000000a)         Android 2.3.3          GINGERBREAD_MR1
     * 11 (0x0000000b)         Android 3.0             HONEYCOMB
     * 12 (0x0000000c)         Android 3.1             HONEYCOMB_MR1
     * 13 (0x0000000d)         Android 3.2             HONEYCOMB_MR2
     */
    public static int getSDKVersion()
    {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static DisplayMetrics getDisplayMetrics(Activity context)
    {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static float applyDimension(Activity context, int unit, float value)
    {
        return applyDimension(unit, value, getDisplayMetrics(context));
    }

    public static float applyDimension(int unit, float value, DisplayMetrics metrics)
    {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;

            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;

            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;

            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);

            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;

            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }

        return 0;

    }
}
