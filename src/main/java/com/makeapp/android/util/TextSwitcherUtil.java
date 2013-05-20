package com.makeapp.android.util;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-11-1
 * Time: ÏÂÎç4:36
 */
public class TextSwitcherUtil {

    public static void setText(Activity a, int id, String text) {
        TextSwitcher ts = (TextSwitcher) a.findViewById(id);

        if (ts != null) {
            ts.setText(text);
        }
    }

    public static void setInAnimation(Activity a, int id, int annId) {
        TextSwitcher ts = (TextSwitcher) a.findViewById(id);
        Animation in = AnimationUtils.loadAnimation(a, annId);
        if (ts != null) {
            ts.setInAnimation(in);
        }
    }

    public static void setOutAnimation(Activity a, int id, int annId) {
        TextSwitcher ts = (TextSwitcher) a.findViewById(id);
        Animation out = AnimationUtils.loadAnimation(a, annId);
        if (ts != null) {
            ts.setOutAnimation(out);
        }
    }
}
