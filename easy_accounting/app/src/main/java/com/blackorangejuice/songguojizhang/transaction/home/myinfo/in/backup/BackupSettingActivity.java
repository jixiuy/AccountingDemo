package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.backup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.SettingInfo;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.SettingInfoMapper;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

public class BackupSettingActivity extends BasicActivity {
    TextView backTextView;
    TextView saveTextView;
    EditText backupUrlEditText;
    EditText restoreUrlEditText;
    LinearLayout restoreLinearLayout;

    EazyDatabaseHelper songGuoDatabaseHelper;
    SettingInfoMapper settingInfoMapper;
    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, BackupSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacup_setting_page);
        findView();
        init();
        setListener();

    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_backup_setting_page_back_textview);
        saveTextView = findViewById(R.id.activity_backup_setting_page_save_textview);
        backupUrlEditText = findViewById(R.id.activity_backup_setting_page_backup_url);
        restoreUrlEditText = findViewById(R.id.activity_backup_setting_page_restore_url);
        restoreLinearLayout = findViewById(R.id.activity_backup_setting_page_restore_linear);
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(BackupSettingActivity.this);
        settingInfoMapper = new SettingInfoMapper(songGuoDatabaseHelper);
        SettingInfo settingInfo = GlobalInfo.settingInfo;
        backupUrlEditText.setText(settingInfo.getBackupUrl());
        restoreUrlEditText.setText(settingInfo.getRestoreUrl());

    }


    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackupSettingActivity.this.finish();
            }
        });
        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingInfo settingInfo = GlobalInfo.settingInfo;
                settingInfo.setBackupUrl(backupUrlEditText.getText().toString());
                settingInfo.setRestoreUrl(restoreUrlEditText.getText().toString());
                settingInfoMapper.updateBySid(settingInfo);
                EasyUtils.showOneToast("设置成功");
            }
        });
        restoreLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NetUtilsForBackup().execute(NetUtilsForBackup.RESTORE);
                GlobalInfo.refresh(BackupSettingActivity.this);
            }
        });
    }
}