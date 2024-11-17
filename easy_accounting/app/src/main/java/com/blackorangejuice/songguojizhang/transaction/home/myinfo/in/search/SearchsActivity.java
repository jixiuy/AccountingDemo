package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;

public class SearchsActivity extends BasicActivity {
    TextView backTextView;
    LinearLayout byTimeLinearLayout;
    LinearLayout byKeywordLinearLayout;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, SearchsActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchs);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {

    }

    @Override
    public void findView() {
         backTextView = findViewById(R.id.activity_searchs_back_textview);
         byTimeLinearLayout = findViewById(R.id.activity_searchs_by_time);
         byKeywordLinearLayout = findViewById(R.id.activity_searchs_by_keyword);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchsActivity.this.finish();
            }
        });
        byTimeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchByTimeActivity.startThisActivity(SearchsActivity.this);
            }
        });
        byKeywordLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchByKeywordActivity.startThisActivity(SearchsActivity.this);
            }
        });
    }
}