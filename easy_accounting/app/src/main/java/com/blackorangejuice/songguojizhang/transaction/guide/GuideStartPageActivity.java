package com.blackorangejuice.songguojizhang.transaction.guide;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.GuideInfo;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import pub.devrel.easypermissions.EasyPermissions;

public class GuideStartPageActivity extends BasicActivity {
    TextView continueTextView;
    /**
     * 启动此活动
     * @param context
     */
    public static void startThisActivity(Context context){
        Intent intent = new Intent(context, GuideStartPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_start_page);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {
        useEasyPermission();
    }

    @Override
    public void findView() {
        continueTextView = findViewById(R.id.guide_start_page_continue_text_view);
    }

    @Override
    public void setListener() {
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalInfo.guideInfo = new GuideInfo();
                GuideUsernamePageActivity.startThisActivity(GuideStartPageActivity.this);
                GuideStartPageActivity.this.finish();
            }
        });
    }
    /**
     * 申请权限
     */
    public void useEasyPermission() {
        String[] perms = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET
        };

        if (EasyPermissions.hasPermissions(this, perms)) {
            // 不做处理
        } else {
            // 申请权限
            EasyPermissions.requestPermissions(this, "请允许本应用的读写文件权限与网络权限",1, perms);
        }
    }
}