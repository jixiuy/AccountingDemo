package com.blackorangejuice.songguojizhang.transaction.home.list.in.event.choose;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.TagMapper;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.util.ArrayList;
import java.util.List;

public class ShowChosenAccountPageActivity extends BasicActivity {
    RecyclerView chosenAccountRecyclerView;
    TextView backTextView;
    TextView addTextView;

    EazyDatabaseHelper songGuoDatabaseHelper;
    ShowChosenAccountItemRecycleViewAdapter showChosenAccountItemRecycleViewAdapter;
    List<AccountItem> accountItems;
    AccountItemMapper accountItemMapper;


    public static void startThisActivity(Context context){
        Intent intent = new Intent(context,ShowChosenAccountPageActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chosen_account_page);

        findView();
        init();
        setListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        reinit();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(ShowChosenAccountPageActivity.this);
        accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
        accountItems = new ArrayList<>();
        accountItems.addAll(accountItemMapper.selectByEvent(GlobalInfo.lastAddEvent));

        GlobalInfo.lastAddEvent.getWillAddAccountItemList().addAll(accountItems);

        // 组合tag
        TagMapper tagMapper = new TagMapper(songGuoDatabaseHelper);
        for(AccountItem accountItem:accountItems){
            accountItem.setTag(tagMapper.selectByTid(accountItem.getTid()));
        }
        showChosenAccountItemRecycleViewAdapter = new ShowChosenAccountItemRecycleViewAdapter(ShowChosenAccountPageActivity.this,accountItems);
        chosenAccountRecyclerView.setAdapter(showChosenAccountItemRecycleViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShowChosenAccountPageActivity.this);
        chosenAccountRecyclerView.setLayoutManager(layoutManager);

    }
    public void reinit() {
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(ShowChosenAccountPageActivity.this);
        accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
        accountItems = new ArrayList<>();
        accountItems.addAll(accountItemMapper.selectByEvent(GlobalInfo.lastAddEvent));

        // 如果用户新增了绑定，则添加
        if(GlobalInfo.lastAddEvent.getWillAddAccountItemList()!=null){
            accountItems.clear();
            accountItems.addAll(GlobalInfo.lastAddEvent.getWillAddAccountItemList());
        }
        // 组合tag
        TagMapper tagMapper = new TagMapper(songGuoDatabaseHelper);
        for(AccountItem accountItem:accountItems){
            accountItem.setTag(tagMapper.selectByTid(accountItem.getTid()));
        }
        showChosenAccountItemRecycleViewAdapter = new ShowChosenAccountItemRecycleViewAdapter(ShowChosenAccountPageActivity.this,accountItems);
        chosenAccountRecyclerView.setAdapter(showChosenAccountItemRecycleViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShowChosenAccountPageActivity.this);
        chosenAccountRecyclerView.setLayoutManager(layoutManager);

    }
    @Override
    public void findView() {
        chosenAccountRecyclerView = findViewById(R.id.activity_show_chosen_account_page_show_event_list_recycle_view);
        backTextView = findViewById(R.id.activity_show_chosen_account_page_back_textview);
        addTextView = findViewById(R.id.activity_show_chosen_account_page_add);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowChosenAccountPageActivity.this.finish();
            }
        });
        addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseShowAccountListPageActivity.startThisActivity(ShowChosenAccountPageActivity.this);
            }
        });
    }
    
    public void refreshAccountList() {
        accountItems.clear();
        accountItems.addAll(accountItemMapper.selectByEvent(GlobalInfo.lastAddEvent));
        // 组合tag
        TagMapper tagMapper = new TagMapper(songGuoDatabaseHelper);
        for(AccountItem accountItem:accountItems){
            accountItem.setTag(tagMapper.selectByTid(accountItem.getTid()));
        }
        showChosenAccountItemRecycleViewAdapter.notifyDataSetChanged();
    }

    public void dbrefreshAccountList() {
        accountItems.clear();
        accountItems.addAll(GlobalInfo.lastAddEvent.getWillAddAccountItemList());
        showChosenAccountItemRecycleViewAdapter.notifyDataSetChanged();
    }
}