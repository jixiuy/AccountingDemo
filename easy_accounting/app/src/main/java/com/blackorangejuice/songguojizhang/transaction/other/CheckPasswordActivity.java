package com.blackorangejuice.songguojizhang.transaction.other;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.transaction.home.HomePageActivity;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.account.edit.AddEditAccountPageActivity;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.event.edit.AddEditEventPageActivity;
import com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.UpdatePasswordPageActivity;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.other.EazyActivityController;

import java.util.Objects;

public class CheckPasswordActivity extends BasicActivity {
    public static final String JUMP_TO = "jump_to";
    public static final String JUMP_ACCOUNT="jump_account";
    public static final String JUMP_EVENT="jump_event";

    EditText usernameEditText;
    EditText passwordEditText;
    Button checkButton;
    Button forgetButton;

    public static void startThisActivity(Context context, String jumpTo) {
        Intent intent = new Intent(context, CheckPasswordActivity.class);
        intent.putExtra(JUMP_TO,jumpTo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 先清空所有之前打开未销毁的活动
        EazyActivityController.finishAll();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_password);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {
    }

    @Override
    public void findView() {
        usernameEditText = findViewById(R.id.activity_check_password_username);
        passwordEditText = findViewById(R.id.activity_check_password_password);
        checkButton = findViewById(R.id.activity_check_password_button);
        forgetButton = findViewById(R.id.activity_forget_password_button);
    }



    @Override
    public void setListener() {
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // 判断空
                if (EasyUtils.notEmptyString(username) && EasyUtils.notEmptyString(password)) {
                    String username1 = GlobalInfo.settingInfo.getUsername();
                    String password1 = GlobalInfo.settingInfo.getPassword();
                    // 验证账号密码
                    if(Objects.equals(username,username1)&&Objects.equals(password,password1)){
                        switch (getIntent().getStringExtra(JUMP_TO)){
                            // 如果intent中存放了标识,则按标识跳转
                            case JUMP_ACCOUNT:
                                AddEditAccountPageActivity.startThisActivity(CheckPasswordActivity.this, "");
                                break;
                            case JUMP_EVENT:
                                AddEditEventPageActivity.startThisActivity(CheckPasswordActivity.this, "");
                                break;
                            default:
                                // 如果不符合,则跳转到首页
                                HomePageActivity.startThisActivity(CheckPasswordActivity.this);
                                break;
                        }
                        CheckPasswordActivity.this.finish();
                    }else {
                        EasyUtils.showOneToast("用户名或者密码错误");
                    }
                }else {
                    EasyUtils.showOneToast("用户名或者密码不能为空");
                }
            }
        });
        // 忘记密码
        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = new EditText(CheckPasswordActivity.this);
                editText.setSingleLine(true);
                new AlertDialog.Builder(CheckPasswordActivity.this).setTitle(GlobalInfo.settingInfo.getPasswordQuestion())
                        .setView(editText)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String userAnswer = editText.getText().toString();
                                        if(EasyUtils.notEmptyString(userAnswer)){
                                            if(userAnswer.equals(GlobalInfo.settingInfo.getPasswordAnswer())){
                                                UpdatePasswordPageActivity.startThisActivity(CheckPasswordActivity.this);
                                            }else {
                                                EasyUtils.showOneToast("答案错误");
                                            }
                                        }else {
                                            EasyUtils.showOneToast("密保答案不能为空");
                                        }

                                    }
                                }
                        ).setNegativeButton("取消", null).show();
            }
        });
    }
}