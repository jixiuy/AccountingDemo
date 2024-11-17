package com.blackorangejuice.songguojizhang.transaction.home.list.in.event.show;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.EventItemMapper;
import com.blackorangejuice.songguojizhang.utils.basic.BasicFragment;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowEventListPageFragment extends BasicFragment {
    private View thisView;
    private Activity activity;
    private List<EventItem> eventItems;
    private Integer currentPage;

    RecyclerView recyclerView;
    EazyDatabaseHelper songGuoDatabaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_show_event_list, container, false);
        return thisView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
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


    public String getFormatDate(Long dateLong) {
        Date date = new Date(dateLong);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public void extendBlocks() {
        // 不是第一次填充,而是扩充
        refreshEventItems();

    }

    @Override
    public void init() {
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(activity);
        currentPage = 1;
        // eventItems初始化
        eventItems = new ArrayList<>();
        // 填充eventItems
        refreshEventItems();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        EventItemRecycleViewAdapter eventItemRecycleViewAdapter = new EventItemRecycleViewAdapter(this,eventItems);
        recyclerView.setAdapter(eventItemRecycleViewAdapter);
    }

    @Override
    public void findView() {
        recyclerView = activity.findViewById(R.id.fragment_show_event_list_recycle_view);
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
    }
}
