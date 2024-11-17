package com.blackorangejuice.songguojizhang.transaction.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;

public class GuidePasswordPageActivity extends BasicActivity {
    private EditText passwordEditText;
    private TextView backTextView;
    private TextView continueTextView;

    /**
     * 启动此活动
     * @param context
     */
    public static void startThisActivity(Context context){
        Intent intent = new Intent(context, GuidePasswordPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_password_page);

       findView();
        setListener();

    }

    @Override
    public void init() {

    }

    @Override
    public void findView() {
        passwordEditText = findViewById(R.id.activity_guide_password_page_password);
        backTextView = findViewById(R.id.activity_guide_password_page_back);
        continueTextView = findViewById(R.id.activity_guide_password_page_continue);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuidePasswordPageActivity.this.finish();
            }
        });
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!EasyUtils.notEmptyString(passwordEditText.getText().toString())){
                    EasyUtils.showOneToast("密码不能为空");
                    return;
                }
                GlobalInfo.guideInfo.setPassword(passwordEditText.getText().toString());
                GuideRepasswordPageActivity.startThisActivity(GuidePasswordPageActivity.this);
            }
        });
    }
}