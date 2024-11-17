package com.blackorangejuice.songguojizhang.transaction.home.list.in.account.choose;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.EventItemMapper;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.event.edit.AddEditEventPageActivity;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;

import java.util.ArrayList;
import java.util.List;

public class ChooseEventPageActivity extends BasicActivity {
    RecyclerView recyclerView;
    private TextView backTextView;
    private TextView addEventTextView;


    private List<EventItem> eventItems;
    private Integer currentPage;
    EazyDatabaseHelper songGuoDatabaseHelper;

    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, ChooseEventPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_event_page);
    }

    /**
     * 在fragment可见的时候，进行adapter初始化
     */
    @Override
    public void onResume() {
        super.onResume();

        findView();
        init();
        setListener();
    }


    /**
     * 刷新eventItem列表
     */
    private void refreshEventItems() {
        // 分页查找事件列表
        EventItemMapper eventItemMapper = new EventItemMapper(songGuoDatabaseHelper);
        List<EventItem> eventItemlist = eventItemMapper.selectDescPage(currentPage++, GlobalConstant.PAGE_SIZE);
        eventItems.addAll(eventItemlist);

    }


    public void extendBlocks() {
        // 不是第一次填充,而是扩充
        refreshEventItems();

    }

    @Override
    public void init() {
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(this);
        currentPage = 1;
        // eventItems初始化
        eventItems = new ArrayList<>();
        // 填充eventItems
        refreshEventItems();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChooseEventItemRecycleViewAdapter chooseEventItemRecycleViewAdapter = new ChooseEventItemRecycleViewAdapter(this, eventItems);
        recyclerView.setAdapter(chooseEventItemRecycleViewAdapter);
    }

    @Override
    public void findView() {
        recyclerView = findViewById(R.id.activity_choose_event_page_show_event_list_recycle_view);
        backTextView = findViewById(R.id.activity_choose_event_page_back_textview);
        addEventTextView = findViewById(R.id.activity_choose_event_page_add_event);
    }

    @Override
    public void setListener() {
        // 滚动检测
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) { //当前状态为停止滑动
                    if (!recyclerView.canScrollVertically(1)) { // 到达底部
                        System.out.println("到底了");
                        extendBlocks();
                    } else if (!recyclerView.canScrollVertically(-1)) { // 到达顶部
                        System.out.println("到顶了");
                    }
                }
            }
        };
        recyclerView.addOnScrollListener(onScrollListener);
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseEventPageActivity.this.finish();
            }
        });
        addEventTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditEventPageActivity.startThisActivity(ChooseEventPageActivity.this,GlobalConstant.DISABLE_BIND);
            }
        });
    }
}
