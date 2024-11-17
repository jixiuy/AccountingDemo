package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.search;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.SearchItem;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.EventItemMapper;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SearchByKeywordActivity extends BasicActivity {
    TextView backTextView;
    Button searchButton;
    EditText searchEditText;
    RecyclerView searchRecyclerView;
    EazyDatabaseHelper songGuoDatabaseHelper;
    AccountItemMapper accountItemMapper;
    EventItemMapper eventItemMapper;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, SearchByKeywordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_keyword_page);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(this);
        accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
        eventItemMapper = new EventItemMapper(songGuoDatabaseHelper);

    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_search_by_keyword_page_back_textview);
        searchEditText = findViewById(R.id.activity_search_by_keyword_page_edittext);
        searchButton = findViewById(R.id.activity_search_by_keyword_page_Search_button);
        searchRecyclerView = findViewById(R.id.activity_search_by_keyword_page_show_result_recycle_view);
    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchByKeywordActivity.this.finish();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取关键词
                String keyword = searchEditText.getText().toString();
                if(!EasyUtils.notEmptyString(keyword)){
                    keyword = "";
                }
                // 查找账单
                List<SearchItem> searchItemsAccount = accountItemMapper.selectByKeyWord(keyword);
                // 查找事件
                List<SearchItem> searchItemsEvent = eventItemMapper.selectByKeyWord(keyword);
                // 组合list
                List<SearchItem> searchItemsAll  = new ArrayList<>();
                searchItemsAll.addAll(searchItemsAccount);
                searchItemsAll.addAll(searchItemsEvent);
                // 按时间排序
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    searchItemsAll.sort(new Comparator<SearchItem>() {
                        @Override
                        public int compare(SearchItem o1, SearchItem o2) {
                            return (int) (o1.getTime() - o2.getTime());
                        }
                    });
                }
                // 提交给recyclerView
                searchRecyclerView.setLayoutManager(new LinearLayoutManager(SearchByKeywordActivity.this));
                searchRecyclerView.setAdapter(new SearchAdapter(SearchByKeywordActivity.this,searchItemsAll));

            }
        });
    }
}