package com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.search;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.bean.SearchItem;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.EventItemMapper;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.account.edit.UpdateEditAccountPageActivity;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.event.edit.UpdateEditEventPageActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 搜索结果展示的适配器
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    List<SearchItem> searchItems;
    Activity activity;
    EazyDatabaseHelper songGuoDatabaseHelper;
    AccountItemMapper accountItemMapper;
    EventItemMapper eventItemMapper;
    SimpleDateFormat simpleDateFormat;
    static class SearchViewHolder extends RecyclerView.ViewHolder {

        // 当前view， 设置点击事件使用
        View thisView;
        // 账单标签/事件标题
        TextView tagNameOrEventTitle;
        // 账单金额
        TextView sum;
        // 账单备注/事件内容
        TextView remarkOrEventContent;
        // 时间
        TextView time;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            thisView = itemView;
            tagNameOrEventTitle = itemView.findViewById(R.id.item_search_result_tag_name_or_event_title);
            sum = itemView.findViewById(R.id.item_search_result_sum);
            remarkOrEventContent = itemView.findViewById(R.id.item_search_result_remark_or_event_content);
            time = itemView.findViewById(R.id.item_search_result_time);
        }
    }

    public SearchAdapter(Activity activity, List<SearchItem> searchItems) {
        this.activity = activity;
        this.searchItems = searchItems;
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(activity);
        accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
        eventItemMapper = new EventItemMapper(songGuoDatabaseHelper);
        simpleDateFormat =  new SimpleDateFormat("yyyy/MM/dd");
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_result, viewGroup, false);
        SearchViewHolder searchViewHolder = new SearchViewHolder(view);
        // 点击后跳转到响应的修改/详情页面
        searchViewHolder.thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前点击的搜索结果
                int adapterPosition = searchViewHolder.getAdapterPosition();
                SearchItem searchItem = searchItems.get(adapterPosition);
                // 获取id(并不知道是账单还是事件的)
                Integer id = searchItem.getId();
                // 获取该搜索结果的类型是账单还是事件
                // 同时查找相应的对象,保存到全局,打开页面
                switch (searchItem.getType()){
                    case GlobalConstant.ACCOUNT:
                        AccountItem accountItem = accountItemMapper.selectByAid(id);
                        GlobalInfo.lastAddAccount = accountItem;
                        UpdateEditAccountPageActivity.startThisActivity(activity);
                        break;
                    case GlobalConstant.EVENT:
                        EventItem eventItem = eventItemMapper.selectByEid(id);
                        GlobalInfo.lastAddEvent = eventItem;
                        UpdateEditEventPageActivity.startThisActivity(activity);
                        break;
                }

            }
        });
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        searchViewHolder.tagNameOrEventTitle.setText(searchItems.get(i).getTagNameOrEventTitle());
        switch (searchItems.get(i).getType()){
            case GlobalConstant.ACCOUNT:
                searchViewHolder.sum.setText(String.valueOf(searchItems.get(i).getSum()));
                break;
            case GlobalConstant.EVENT:
                searchViewHolder.sum.setText("事件");
                break;
        }
        searchViewHolder.remarkOrEventContent.setText(searchItems.get(i).getRemarkOrEventContent());
        searchViewHolder.time.setText(
               simpleDateFormat.format(new Date(searchItems.get(i).getTime()))
        );

    }

    @Override
    public int getItemCount() {
        return searchItems.size();
    }
}
