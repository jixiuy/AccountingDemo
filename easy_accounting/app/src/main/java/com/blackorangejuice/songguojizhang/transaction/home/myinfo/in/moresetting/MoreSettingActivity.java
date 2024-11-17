package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.moresetting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.moresetting.exports.ExportsActivity;
import com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.moresetting.imports.ImportsActivity;
import com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.moresetting.tag.AddTagActivity;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;

public class MoreSettingActivity extends BasicActivity {
    TextView backTextView;
    LinearLayout importLinerLayout;
    LinearLayout exportLinerLayout;
    LinearLayout addTagLinerLayout;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, MoreSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_setting);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {

    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_more_setting_back_textview);
        importLinerLayout = findViewById(R.id.activity_more_setting_import_layout);
        exportLinerLayout = findViewById(R.id.activity_more_setting_export_layout);
        addTagLinerLayout = findViewById(R.id.activity_more_setting_add_tag_layout);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreSettingActivity.this.finish();
            }
        });
        importLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImportsActivity.startThisActivity(MoreSettingActivity.this);
            }
        });
        exportLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExportsActivity.startThisActivity(MoreSettingActivity.this);
            }
        });
        addTagLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTagActivity.startThisActivity(MoreSettingActivity.this);
            }
        });
    }
}

