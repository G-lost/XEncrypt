package com.xencrypt.headfile.xencrypt;

import android.app.Application;
import android.content.Context;

/**
 * Created by Lost on 2016/11/19.
 */

public class myApplication extends Application
{
    private static Context context;

    @Override
    public void onCreate()
    {
        context = getApplicationContext();
    }

    public static Context getContext()
    {
        return context;
    }
}
