package com.makeapp.android.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.WindowManager;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-6-29
 * Time: ÏÂÎç2:41
 */
public class SettingPreferenceActivity
        extends PreferenceActivity {

    private boolean fullscreen = false;
    private int resId;

    public SettingPreferenceActivity(boolean fullscreen, int resId) {
        this.fullscreen = fullscreen;
        this.resId = resId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (fullscreen) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        addPreferencesFromResource(resId);
    }
}
