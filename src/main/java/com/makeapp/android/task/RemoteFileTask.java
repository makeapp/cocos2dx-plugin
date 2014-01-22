/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.task;

import java.io.File;
import java.io.IOException;

import android.util.Log;
import com.makeapp.javase.file.FileListener;
import com.makeapp.javase.file.FileUtil;
import com.makeapp.javase.http.HttpClient;
import com.makeapp.javase.http.HttpException;
import com.makeapp.javase.lang.StringUtil;
import com.makeapp.javase.util.URLUtil;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-8-13 上午9:22 $
 *          $Id$
 */
public class RemoteFileTask
    extends AsyncTask<String, Integer, String>
{
    private File savePath;

    public File getSavePath()
    {
        return savePath;
    }

    public void setSavePath(File savePath)
    {
        this.savePath = savePath;
    }

    private String saveName;

    public String getSaveName()
    {
        return saveName;
    }

    public void setSaveName(String saveName)
    {
        this.saveName = saveName;
    }

    private boolean overwrite = false;

    public boolean isOverwrite()
    {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite)
    {
        this.overwrite = overwrite;
    }

    HttpClient httpClient;

    public HttpClient getHttpClient()
    {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient)
    {
        this.httpClient = httpClient;
    }

    protected FileListener fileListener;

    public FileListener getFileListener()
    {
        return fileListener;
    }

    public void setFileListener(FileListener fileListener)
    {
        this.fileListener = fileListener;
    }

    private File localFile;

    public void download(String fileURL)
    {
        if (savePath == null) {
            Log.i("FileDownload", "Invalid save path");
            return;
        }

        localFile = null;
        if (StringUtil.isInvalid(saveName)) {
            localFile = new File(savePath, FileUtil.getCacheName(fileURL));
        }
        else {
            saveName = StringUtil.replace(saveName, "/", "_");
            localFile = new File(savePath, saveName);
        }

        if (!savePath.exists()) {
            savePath.mkdirs();
        }

        if (doDownload(localFile, 0)) {
            execute(fileURL);
        }
        else {
            onPostExecute(localFile.getAbsolutePath());
            Log.i("FileDownload", "exists " + localFile.getAbsolutePath());
        }
    }


    public boolean doDownload(File localFile, int type)
    {
        return localFile != null && (!localFile.exists() || overwrite || localFile.length() == 0);
    }

    protected String doInBackground(String... params)
    {
        if (localFile != null) {
            if (doDownload(localFile, 1)) {
                File tmpFile = new File(localFile.getAbsolutePath() + ".tmp");
                Log.i("FileDownload", "download " + params[0] + " to " + localFile);
                if (httpClient != null) {
                    try {
                        httpClient.saveStream(params[0], tmpFile);
                        FileUtil.renameTo(tmpFile, localFile);
                    }
                    catch (HttpException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        URLUtil.saveContent(params[0], tmpFile);
                        FileUtil.renameTo(tmpFile, localFile);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                Log.i("FileDownload", "exists " + localFile.getAbsolutePath());
            }
            return localFile.getAbsolutePath();
        }

        return null;
    }

    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        if (fileListener != null && StringUtil.isValid(s)) {
            File file = new File(s);
            if (file.exists()) {
                fileListener.onFileWrited(file);
            }
        }
    }
}
