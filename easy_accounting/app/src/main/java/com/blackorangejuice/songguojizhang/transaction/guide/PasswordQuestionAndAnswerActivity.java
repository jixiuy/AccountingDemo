package com.blackorangejuice.songguojizhang.transaction.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

public class PasswordQuestionAndAnswerActivity extends BasicActivity {
    private EditText questionEditText;
    private EditText answerEditText;
    private TextView backTextView;
    private TextView continueTextView;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, PasswordQuestionAndAnswerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_question_and_answer);

        findView();

        setListener();
    }

    @Override
    public void init() {

    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_guide_password_question_and_answer_page_back);
        continueTextView = findViewById(R.id.activity_guide_password_question_and_answer_page_continue);
        questionEditText = findViewById(R.id.activity_guide_password_question_and_answer_page_question);
        answerEditText = findViewById(R.id.activity_guide_password_question_and_answer_page_answer);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordQuestionAndAnswerActivity.this.finish();
            }
        });
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = questionEditText.getText().toString();
                String answer = answerEditText.getText().toString();
                if (!(question.isEmpty() || answer.isEmpty())) {
                    // 将信息保存到引导全局变量
                    GlobalInfo.guideInfo.setPasswordQuestion(question);
                    GlobalInfo.guideInfo.setPasswordAnswer(answer);
                    // 开启下一个活动:设置账本名
                    GuideAccountBookNamePageActivity.startThisActivity(PasswordQuestionAndAnswerActivity.this);
                } else {
                    EasyUtils.showOneToast("密保问题和答案不能为空");
                }
            }
        });
    }
}