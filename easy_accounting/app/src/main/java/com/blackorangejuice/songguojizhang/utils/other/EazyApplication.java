package com.blackorangejuice.songguojizhang.utils.other;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * 全局获取context，但有的时候不能用
 */
public class EazyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
}
