package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;

public class AboutPageActivity extends BasicActivity {
    TextView backTextView;
    WebView webView;
    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, AboutPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        findView();
        init();
        setListener();
    }

    public void init() {
        webView.loadUrl("file:///android_asset/html/about.html");
    }

    public void findView() {
        backTextView = findViewById(R.id.activity_about_page_back_textview);
        webView = findViewById(R.id.activity_about_page_web_view);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutPageActivity.this.finish();
            }
        });
    }
}