package com.blackorangejuice.songguojizhang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.blackorangejuice.songguojizhang.transaction.guide.GuideStartPageActivity;
import com.blackorangejuice.songguojizhang.transaction.other.CheckPasswordActivity;
import com.blackorangejuice.songguojizhang.transaction.home.HomePageActivity;
import com.blackorangejuice.songguojizhang.bean.AccountBook;
import com.blackorangejuice.songguojizhang.bean.SettingInfo;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountBookMapper;
import com.blackorangejuice.songguojizhang.db.mapper.SettingInfoMapper;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;

public class MainActivity extends BasicActivity {

    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 判断是否第一次使用该软件
                // preferences 存储 第一次使用的标志
                SharedPreferences songguoPreferences = EasyUtils.getSongGuoSharedPreferences();
                // 如果没有该值,则为第一次启动
                boolean isFirstUse = songguoPreferences.getBoolean(GlobalConstant.IS_FIRST_USE, true);

                if (isFirstUse) {
                    // 如果为第一次启动,则进入引导界面,引导流程结束后再更改标识
                    GuideStartPageActivity.startThisActivity(MainActivity.this);
                } else {
                    // 如果不是第一次启动,取出SID
                    int sid = songguoPreferences.getInt(GlobalConstant.SID, 0);
                    // 初始化全局类
                    EazyDatabaseHelper songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(MainActivity.this);

                    // 设置
                    SettingInfoMapper settingInfoMapper = new SettingInfoMapper(songGuoDatabaseHelper);
                    SettingInfo settingInfo = settingInfoMapper.selectBySid(sid);
                    GlobalInfo.settingInfo = settingInfo;

                    // 账本
                    AccountBookMapper accountBookMapper = new AccountBookMapper(songGuoDatabaseHelper);
                    Integer currentAccountBookBid = settingInfo.getCurrentAccountBookBid();
                    AccountBook accountBook = accountBookMapper.selectByBid(currentAccountBookBid);
                    GlobalInfo.currentAccountBook = accountBook;

                    // 取出是否启用了密码检查
                    String ifEnablePasswordCheck = settingInfo.getIfEnablePasswordCheck();
                    if (String.valueOf(true).equals(ifEnablePasswordCheck)) {
                        CheckPasswordActivity.startThisActivity(MainActivity.this, "");
                    } else {

                        // 进入主页
                        HomePageActivity.startThisActivity(MainActivity.this);
                    }


                }

                MainActivity.this.finish();

            }
        }).start();
    }

    @Override
    public void init() {

    }

    @Override
    public void findView() {

    }

    @Override
    public void setListener() {

    }
}