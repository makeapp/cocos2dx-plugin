/**
 *  Copyright(c) Shanghai QianZhi Network Info Technologies Inc. All right reserved.
 */
package com.makeapp.android.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

/**
 * @author <a href="mailto:yuanyou@shqianzhi.com">yuanyou</a>
 * @version $Date:13-4-23 下午2:06 $
 *          $Id$
 */
public class ExitApplication extends Application
{
    List<Activity> activityList = new ArrayList();

    public void exit()
    {
        for (Activity activity : activityList) {
            try {
                activity.finish();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addActivity(Activity activity)
    {
        Application application = activity.getApplication();
        if (application instanceof ExitApplication) {
            ExitApplication exitApplication = (ExitApplication) application;
            exitApplication.activityList.add(activity);
        }
    }

    public static void exit(Activity activity)
    {
        Application application = activity.getApplication();
        if (application instanceof ExitApplication) {
            ExitApplication exitApplication = (ExitApplication) application;
            exitApplication.exit();
        }
    }
}
