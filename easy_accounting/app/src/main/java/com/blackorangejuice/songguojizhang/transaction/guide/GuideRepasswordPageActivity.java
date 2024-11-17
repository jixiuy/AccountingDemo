package com.blackorangejuice.songguojizhang.transaction.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.util.Objects;

public class GuideRepasswordPageActivity extends BasicActivity {
    private EditText repasswordEditText;
    private TextView backTextView;
    private TextView continueTextView;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, GuideRepasswordPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_repassword_page);

        findView();

        setListener();
    }

    @Override
    public void init() {

    }

    @Override
    public void findView() {
        repasswordEditText = findViewById(R.id.activity_guide_repassword_page_repassword);
        backTextView = findViewById(R.id.activity_guide_repassword_page_back);
        continueTextView = findViewById(R.id.activity_guide_repassword_page_continue);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuideRepasswordPageActivity.this.finish();
            }
        });
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 验证两次密码是否相同
                String password2 = repasswordEditText.getText().toString();
                String password1 = GlobalInfo.guideInfo.getPassword();
                if (Objects.equals(password1, password2)) {
                    //开启下一个活动:密保
                   PasswordQuestionAndAnswerActivity.startThisActivity(GuideRepasswordPageActivity.this);
                } else {
                    Toast.makeText(GuideRepasswordPageActivity.this, "两次输入的密码不同", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}