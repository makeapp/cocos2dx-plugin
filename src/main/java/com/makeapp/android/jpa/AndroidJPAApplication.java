package com.makeapp.android.jpa;

import android.app.Application;

/**
 * Created by IntelliJ IDEA.
 * User: yuanyou
 * Date: 11-7-1
 * Time: ����11:50
 */
public class AndroidJPAApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        EntityContext.setEntityManagerFactory("default",new AndroidPersistenceProvider().createEntityManagerFactory("default"));
    }
}
