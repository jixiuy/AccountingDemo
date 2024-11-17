package com.blackorangejuice.songguojizhang.transaction.home.list.in.account.choose;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.EventItemMapper;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowChosenEventPageActivity extends BasicActivity {
    TextView backTextView;
    TextView timeTextView;
    TextView addTextView;
    EditText titleEditText;
    EditText contentEditText;
    LinearLayout moreLinearLayout;

    EazyDatabaseHelper songGuoDatabaseHelper;
    SimpleDateFormat simpleDateFormat;
    EventItem eventItem;

    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, ShowChosenEventPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chosen_event_page);
        findView();
        init();
        setListener();
    }


    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(this);
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // 初始化事件项对象
        AccountItem accountItem = GlobalInfo.lastAddAccount;
        EventItemMapper eventItemMapper = new EventItemMapper(songGuoDatabaseHelper);
        eventItem = eventItemMapper.selectByEid(accountItem.getEid());
        if(eventItem == null){
            // 没有事件则直接跳到选择界面
            ChooseEventPageActivity.startThisActivity(ShowChosenEventPageActivity.this);
            ShowChosenEventPageActivity.this.finish();
            return;
        }


        // 从eventItem中获取信息
        // 时间
        timeTextView.setText(simpleDateFormat.format(new Date(this.eventItem.getEventTime())));
        // 标题
        titleEditText.setText(this.eventItem.getEventTitle());
        titleEditText.setEnabled(false);
        // 内容
        contentEditText.setText(this.eventItem.getEventContent());
        contentEditText.setEnabled(false);


    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_show_chosen_event_page_back_textview);
        timeTextView = findViewById(R.id.activity_show_chosen_event_page_time);
        addTextView = findViewById(R.id.activity_show_chosen_event_page_add);
        titleEditText = findViewById(R.id.activity_show_chosen_event_page_event_title);
        contentEditText = findViewById(R.id.activity_show_chosen_event_page_event_content);
        moreLinearLayout = findViewById(R.id.activity_show_chosen_event_page_more);
    }

    @Override
    public void setListener() {
        // 返回处理
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowChosenEventPageActivity.this.finish();
            }
        });
        addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseEventPageActivity.startThisActivity(ShowChosenEventPageActivity.this);
            }
        });
        moreLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyUtils.showOneToast("当前页面不可编辑");
            }
        });


    }

    private void getEventInfoToAccountItem() {
        // 标题
        eventItem.setEventTitle(titleEditText.getText().toString());
        // 正文
        eventItem.setEventContent(contentEditText.getText().toString());
        // Bid
        eventItem.setBid(GlobalInfo.currentAccountBook.getBid());
        // 设置账本为当前全局账本
        eventItem.setAccountBook(GlobalInfo.currentAccountBook);
    }
}