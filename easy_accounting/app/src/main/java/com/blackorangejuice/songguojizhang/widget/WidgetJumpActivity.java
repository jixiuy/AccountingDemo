package com.blackorangejuice.songguojizhang.widget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blackorangejuice.songguojizhang.MainActivity;
import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountBook;
import com.blackorangejuice.songguojizhang.bean.SettingInfo;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountBookMapper;
import com.blackorangejuice.songguojizhang.db.mapper.SettingInfoMapper;
import com.blackorangejuice.songguojizhang.transaction.other.CheckPasswordActivity;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.account.edit.AddEditAccountPageActivity;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.event.edit.AddEditEventPageActivity;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

/**
 * 由于几记账和记事的活动打开前需要初始化
 * 所以使用当前activity做跳板
 * 专用于跳转到记账或者记事的活动的活动
 * 中间的一个跳板
 */
public class WidgetJumpActivity extends BasicActivity {
    public static final String ACCOUNT_URI = "content://account";
    public static final String EVENT_URI = "content://event";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        findView();
        init();
        setListener();
        this.finish();
    }

    @Override
    public void init() {
        // 判断是否第一次使用该软件
        // preferences 存储 第一次使用的标志
        SharedPreferences songguoPreferences = EasyUtils.getSongGuoSharedPreferences();
        // 如果没有该值,则为第一次启动
        boolean isFirstUse = songguoPreferences.getBoolean(GlobalConstant.IS_FIRST_USE, true);
//        System.out.println("有反应");
        if (isFirstUse) {
            // 如果为第一次启动,则进入引导界面,引导流程结束后再更改标识
            EasyUtils.showOneToast("您还未建立账本");
            MainActivity.startThisActivity(this);
        } else {
            // 如果不是第一次启动,取出SID
            int sid = songguoPreferences.getInt(GlobalConstant.SID, 0);
            // 初始化全局类
            EazyDatabaseHelper songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(WidgetJumpActivity.this);

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
            Intent intent = getIntent();
            Uri data = intent.getData();
            String stringExtra = data.toString();
            if(String.valueOf(true).equals(ifEnablePasswordCheck)){
                // 判断进入的页面
                switch (stringExtra) {
                    case ACCOUNT_URI:
                        CheckPasswordActivity.startThisActivity(WidgetJumpActivity.this,CheckPasswordActivity.JUMP_ACCOUNT);
                        break;
                    case EVENT_URI:
                        CheckPasswordActivity.startThisActivity(WidgetJumpActivity.this,CheckPasswordActivity.JUMP_EVENT);
                        break;

                }
            }else {
                // 判断进入的页面
                switch (stringExtra) {
                    case ACCOUNT_URI:
                        AddEditAccountPageActivity.startThisActivity(this, "");
                        break;
                    case EVENT_URI:
                        AddEditEventPageActivity.startThisActivity(this, "");
                        break;

                }
            }


        }

    }

    @Override
    public void findView() {

    }

    @Override
    public void setListener() {

    }
}
