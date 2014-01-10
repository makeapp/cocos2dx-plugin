/**
 *  Copyright(c) Shanghai YiJun Network Technologies Inc. All right reserved.
 */
package com.makeapp.android.media;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.util.Log;
import com.makeapp.android.util.AndroidUtil;
import com.makeapp.android.util.HandlerUtil;

/**
 * @author <a href="mailto:yuanyou@makeapp.co">yuanyou</a>
 * @version $Date:13-8-13 上午10:03 $
 *          $Id$
 */
public class AudioRecorder
{
    MediaRecorder mRecorder;
    File file = null;

    private Context context;

    private long startTime;

    public AudioRecorder(Context context)
    {
        this.context = context;
    }

    public File getFile()
    {
        return file;
    }

    private AudioRecorderListener audioRecorderListener;

    public AudioRecorderListener getAudioRecorderListener()
    {
        return audioRecorderListener;
    }

    public void setAudioRecorderListener(AudioRecorderListener audioRecorderListener)
    {
        this.audioRecorderListener = audioRecorderListener;
    }

    public void start()
    {
        file = null;
        HandlerUtil.post(new Runnable()
        {
            public void run()
            {
                try {
                    file = AndroidUtil.getApplicationTempFile(context, "audio", "mp4");
                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    mRecorder.setAudioSamplingRate(8000);
                    //mRecorder.setAudioChannels(2);
                    mRecorder.setOutputFile(file.getAbsolutePath());
                    mRecorder.prepare();
                    mRecorder.start();
                    startTime = System.currentTimeMillis();
                    if (audioRecorderListener != null) {
                        audioRecorderListener.onRecorderStarted(mRecorder);
                    }
                }
                catch (IOException e) {
                    Log.e("", "prepare() failed");
                }
            }
        });
    }

    public void stop()
    {
        HandlerUtil.postDelayed(new Runnable()
        {
            public void run()
            {
                synchronized (AudioRecorder.this) {
                    try {
                        if (mRecorder != null) {
                            long stopTime = System.currentTimeMillis();
                            mRecorder.stop();
                            mRecorder.release();
                            if (audioRecorderListener != null) {
                                audioRecorderListener.onRecorderStopped(mRecorder, file, stopTime - startTime);
                            }
                            mRecorder = null;
                        }
                    }
                    catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 500);
    }

    public void cancel()
    {
        HandlerUtil.post(new Runnable()
        {
            public void run()
            {
                try {
                    if (mRecorder != null) {
                        mRecorder.stop();
                        mRecorder.release();
                        mRecorder = null;
                    }
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface AudioRecorderListener
    {
        public void onRecorderStarted(MediaRecorder mr);

        public void onRecorderStopped(MediaRecorder mr, File file, long time);
    }
}
