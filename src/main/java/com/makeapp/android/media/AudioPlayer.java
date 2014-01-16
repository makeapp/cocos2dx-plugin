/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.media;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import com.makeapp.android.task.RemoteFileTask;
import com.makeapp.android.util.AndroidUtil;
import com.makeapp.javase.file.FileAdapter;
import com.makeapp.javase.http.HttpClient;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-8-13 上午9:58 $
 *          $Id$
 */
public class AudioPlayer
{
    MediaPlayer mp = new MediaPlayer();

    private Context context;

    private String lastFile;

    public AudioPlayer(Context context)
    {
        this.context = context;
    }

    private HttpClient httpClient;

    public HttpClient getHttpClient()
    {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient)
    {
        this.httpClient = httpClient;
    }

    private AudioPlayerListener audioPlayerListener;

    public AudioPlayerListener getAudioPlayerListener()
    {
        return audioPlayerListener;
    }

    public void setAudioPlayerListener(AudioPlayerListener audioPlayerListener)
    {
        this.audioPlayerListener = audioPlayerListener;
    }

    public void play(final String url)
    {
        play(url, null);
    }

    public void play(final String url, String localFile)
    {
        if (localFile == null && url.startsWith("http://")) {
            File tmpDir = AndroidUtil.getApplicationAudioDir(context);
            RemoteFileTask task = new RemoteFileTask();
            task.setHttpClient(httpClient);
            task.setSavePath(tmpDir);
            task.setFileListener(new FileAdapter()
            {
                public void onFileWrited(File newFile)
                {
                    super.onFileWrited(newFile);
                    play(url, newFile.getAbsolutePath());
                }
            });
            task.download(url);
            return;
        }
        else {
            localFile = url;
        }

        if (lastFile != null) {
            if (audioPlayerListener != null) {
                audioPlayerListener.onPlayerStopped(lastFile);
            }
        }
        try {
            mp.reset();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        try {
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
            {
                public void onPrepared(MediaPlayer mp)
                {
                    if (audioPlayerListener != null) {
                        audioPlayerListener.onPlayerStarted(url);
                    }
                    mp.start();
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                public void onCompletion(MediaPlayer mp)
                {
                    mp.stop();
                    lastFile = null;
                    if (audioPlayerListener != null) {
                        audioPlayerListener.onPlayerStopped(url);
                    }
                }
            });

            mp.setDataSource(localFile);
            mp.prepare();
            lastFile = url;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        if (lastFile != null) {
            if (audioPlayerListener != null) {
                audioPlayerListener.onPlayerStopped(lastFile);
            }
            try {
                mp.reset();
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
            lastFile = null;
        }
    }

    public interface AudioPlayerListener
    {
        public void onPlayerStarted(String file);

        public void onPlayerStopped(String file);
    }
}
