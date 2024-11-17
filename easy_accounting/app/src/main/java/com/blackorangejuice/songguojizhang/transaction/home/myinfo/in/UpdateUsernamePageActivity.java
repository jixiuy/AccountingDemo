package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.SettingInfo;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.SettingInfoMapper;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;

public class UpdateUsernamePageActivity extends BasicActivity {
    TextView backTextView;
    TextView saveTextView;
    EditText usernameEditText;


    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, UpdateUsernamePageActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_username_page);
        findView();
        init();
        setListener();
    }

    public void init() {
        usernameEditText.setText(GlobalInfo.settingInfo.getUsername());
    }

    public void findView(){
        backTextView = findViewById(R.id.activity_update_username_page_back_textview);
        saveTextView = findViewById(R.id.activity_update_username_page_save);
        usernameEditText = findViewById(R.id.activity_update_username_page_uername);
    }
    public void setListener(){
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUsernamePageActivity.this.finish();
            }
        });
        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户名
                String usernameS = usernameEditText.getText().toString();
                if(EasyUtils.notEmptyString(usernameS)){
                    SettingInfoMapper settingInfoMapper = new SettingInfoMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(UpdateUsernamePageActivity.this));
                    SettingInfo settingInfo = GlobalInfo.settingInfo;
                    settingInfo.setUsername(usernameS);
                    settingInfoMapper.updateBySid(settingInfo);

                    UpdateUsernamePageActivity.this.finish();
                    EasyUtils.showOneToast("修改成功");
                }else {
                    EasyUtils.showOneToast("用户名不能为空");
                }

            }
        });
    }
}