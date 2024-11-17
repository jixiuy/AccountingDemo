package com.blackorangejuice.songguojizhang.transaction.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.utils.other.EazyActivityController;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;

public class GuideUsernamePageActivity extends BasicActivity {
    // 用户名输入框
    private EditText usernameEditText;
    // 退出按钮
    private TextView exitTextView;
    // 继续按钮
    private TextView continueTextView;
    /**
     * 启动此活动
     * @param context
     */
    public static void startThisActivity(Context context){
        Intent intent = new Intent(context, GuideUsernamePageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_username_page);

        findView();
        setListener();
    }

    @Override
    public void init() {

    }

    @Override
    public void findView() {
        usernameEditText = findViewById(R.id.activity_guide_username_page_username);
        exitTextView = findViewById(R.id.activity_guide_username_page_exit);
        continueTextView = findViewById(R.id.activity_guide_username_page_continue);
    }

    @Override
    public void setListener() {
// 退出按钮监听器
        exitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EazyActivityController.finishAll();
            }
        });
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!EasyUtils.notEmptyString(usernameEditText.getText().toString())){
                    EasyUtils.showOneToast("用户名不能为空");
                    return;
                }
                // 保存用户名到全局类
                GlobalInfo.guideInfo.setUsername(usernameEditText.getText().toString());
                // 前往下一个页面
//                GuidePasswordPageActivity.startThisActivity(GuideUsernamePageActivity.this);
                GuideEnablePasswordCheckPageActivity.startThisActivity(GuideUsernamePageActivity.this);
            }
        });
    }
}