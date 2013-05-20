/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import android.widget.ProgressBar;

/**
 * @author <a href="mailto:shigang@shqianzhi.com">shigang</a>
 * @version $Date:11-4-14 ����10:27 $
 *          $Id$
 */
public class ProgressBarUtil
{
    public static ProgressBar setProgress(Object container, int id, int progess)
    {
        ProgressBar progressBar = (ProgressBar) ViewUtil.findViewById(container, id);
        if (progressBar != null) {
            progressBar.setProgress(progess);
        }
        return progressBar;
    }

    public static int getProgress(Object container, int id, int progess)
    {
        ProgressBar progressBar = (ProgressBar) ViewUtil.findViewById(container, id);
        if (progressBar != null) {
            return progressBar.getProgress();
        }
        return 0;
    }
}
