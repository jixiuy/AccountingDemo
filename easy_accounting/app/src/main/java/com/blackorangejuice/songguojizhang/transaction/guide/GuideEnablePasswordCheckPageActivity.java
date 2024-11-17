package com.blackorangejuice.songguojizhang.transaction.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

public class GuideEnablePasswordCheckPageActivity extends BasicActivity {
    private Switch enableSwitch;
    private TextView backTextView;
    private TextView continueTextView;
    /**
     * 启动此活动
     * @param context
     */
    public static void startThisActivity(Context context){
        Intent intent = new Intent(context,GuideEnablePasswordCheckPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_enable_password_check_page);

        findView();

        setListener();
    }

    @Override
    public void init() {

    }

    @Override
    public void findView() {
        enableSwitch = findViewById(R.id.activity_guide_enable_password_check_page_switch);
        backTextView = findViewById(R.id.activity_guide_enable_password_check_page_back);
        continueTextView = findViewById(R.id.activity_guide_enable_password_check_page_continue);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuideEnablePasswordCheckPageActivity.this.finish();
            }
        });
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存设置状态
                GlobalInfo.guideInfo.setIfEnablePasswordCheck(String.valueOf(enableSwitch.isChecked()));
                switch (GlobalInfo.guideInfo.getIfEnablePasswordCheck()){
                    // 启用密码检查则设置密码
                    case GlobalConstant.TRUE:
                        GuidePasswordPageActivity.startThisActivity(GuideEnablePasswordCheckPageActivity.this);
                        break;
                    // 否则密码设置为空，直接进入账本名
                    case GlobalConstant.FALSE:
                        GuideAccountBookNamePageActivity.startThisActivity(GuideEnablePasswordCheckPageActivity.this);
                        break;
                }
            }
        });
    }
}