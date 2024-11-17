package com.blackorangejuice.songguojizhang.transaction.home.list.in.event.edit;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.EventItemMapper;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.event.choose.ShowChosenAccountPageActivity;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddEditEventPageActivity extends EditEventActivity {
    public static final String ARG = "arg";
    TextView backTextView;
    TextView timeTextView;
    TextView saveTextView;
    EditText titleEditText;
    EditText contentEditText;
    LinearLayout moreLinearLayout;

    EazyDatabaseHelper songGuoDatabaseHelper;
    SimpleDateFormat simpleDateFormat;
    EventItem eventItem;

    /**
     * 启动此活动
     *
     * @param context
     */

    public static void startThisActivity(Context context,String arg) {
        Intent intent = new Intent(context, AddEditEventPageActivity.class);
        intent.putExtra(ARG,arg);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event_page);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(ARG);
        switch (stringExtra){
            case GlobalConstant.DISABLE_BIND:
                moreLinearLayout.setEnabled(false);
                break;
        }
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(this);
        // 初始化事件项对象,若是从其他界面返回需要保存信息
        eventItem = new EventItem();
        GlobalInfo.lastAddEvent = eventItem;


        // 时间设为当前时间
        Date date = new Date();
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        timeTextView.setText(simpleDateFormat.format(date));
        eventItem.setEventTime(date.getTime());
        eventItem.setEid(-1);

    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_add_edit_event_page_back_textview);
        timeTextView = findViewById(R.id.activity_add_edit_event_page_time);
        saveTextView = findViewById(R.id.activity_add_edit_event_page_save);
        titleEditText = findViewById(R.id.activity_add_edit_event_page_event_title);
        contentEditText = findViewById(R.id.activity_add_edit_event_page_event_content);
        moreLinearLayout = findViewById(R.id.activity_add_edit_event_page_more);
    }

    @Override
    public void setListener() {
        // 返回处理
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditEventPageActivity.this.finish();
            }
        });

        // 时间选择
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cale1 = Calendar.getInstance();

                new DatePickerDialog(AddEditEventPageActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

//                        Toast.makeText(getApplicationContext(), "你选择的是 " + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日", Toast.LENGTH_SHORT).show();
                        String dataS = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        Date theDateAfterParse = new Date();
                        try {
                            theDateAfterParse = simpleDateFormat.parse(dataS);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        timeTextView.setText(simpleDateFormat.format(theDateAfterParse));
                        // 账目设置时间
                        eventItem.setEventTime(theDateAfterParse.getTime());
                    }
                }
                        , cale1.get(Calendar.YEAR)
                        , cale1.get(Calendar.MONTH)
                        , cale1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // 保存按钮
        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存内容
                getEventInfoToAccountItem();
                EventItemMapper eventItemMapper = new EventItemMapper(songGuoDatabaseHelper);
                // 暂存添加账单列表
                List<AccountItem> willAddAccountItemList = GlobalInfo.lastAddEvent.getWillAddAccountItemList();
                // 暂存解绑账单列表
                List<AccountItem> willRemoveAccountItemList = GlobalInfo.lastAddEvent.getWillRemoveAccountItemList();
                // 返回带id的对象
                eventItem = eventItemMapper.insertEventItem(AddEditEventPageActivity.this.eventItem);
                // 绑定所有账单
                if(willAddAccountItemList != null){
                    for(AccountItem a : willAddAccountItemList){
                        AccountItemMapper accountItemMapper = new AccountItemMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(AddEditEventPageActivity.this));
                        a.setEid(eventItem.getEid());
                        accountItemMapper.updateAccountItem(a);
                    }
                }
                // 解绑取消绑定的账单
                if(willRemoveAccountItemList != null){
                    for(AccountItem a : willRemoveAccountItemList){
                        AccountItemMapper accountItemMapper = new AccountItemMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(AddEditEventPageActivity.this));
                        a.setEid(0);
                        accountItemMapper.updateAccountItem(a);
                    }
                }
                EasyUtils.showOneToast("保存成功");
                AddEditEventPageActivity.this.finish();
            }
        });
        // 展示绑定的账单
        moreLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowChosenAccountPageActivity.startThisActivity(AddEditEventPageActivity.this);
            }
        });
    }
    private void getEventInfoToAccountItem() {
        // 标题
        String titleS = titleEditText.getText().toString();
        if (EasyUtils.notEmptyString(titleS)) {
            eventItem.setEventTitle(titleS);
        } else {
            eventItem.setEventTitle(new SimpleDateFormat("yyyy年MM月dd日.记").format(eventItem.getEventTime()));
        }
        // 正文
        String contentS = contentEditText.getText().toString();
        if (EasyUtils.notEmptyString(contentS)) {
            eventItem.setEventContent(contentS);
        } else {
            eventItem.setEventContent("无内容");
        }

        // Bid
        eventItem.setBid(GlobalInfo.currentAccountBook.getBid());
        // 设置账本为当前全局账本
        eventItem.setAccountBook(GlobalInfo.currentAccountBook);
    }
}