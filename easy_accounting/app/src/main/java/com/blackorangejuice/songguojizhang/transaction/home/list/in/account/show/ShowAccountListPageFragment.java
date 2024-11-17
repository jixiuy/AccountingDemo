package com.blackorangejuice.songguojizhang.transaction.home.list.in.account.show;

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
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.bean.Block;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.TagMapper;
import com.blackorangejuice.songguojizhang.utils.basic.BasicFragment;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ShowAccountListPageFragment extends BasicFragment {
    private View thisView;
    private Activity activity;
    private List<Block> blocks;
    private Integer currentPage;
    private RecyclerView recyclerView;
    BlockRecycleViewAdapter blockRecycleViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_show_account_list, container, false);
        return thisView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        // testInit();

    }

    private void testInit() {
        // test
        AccountItemMapper accountItemMapper = new AccountItemMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(ShowAccountListPageFragment.this.getContext()));
        AccountItem accountItem = new AccountItem();
        accountItem.setIncomeOrExpenditure(AccountItem.INCOME);
        accountItem.setTid(2);
        accountItem.setSum(50.00);
        accountItem.setRemark("我是一条备注");
        accountItem.setIfBorrowOrLend(AccountItem.IF_FALSE);
        accountItem.setBid(1);
        accountItem.setEid(0);
        for (int j = 0; j < 100; j++) {
            Long min = 1639207308234L; // 定义随机数的最小值
            Random r = new Random();
            long unixtime = (long) (min + r.nextDouble() * 1000 * 60 * 60 * 24 * 31);
            accountItem.setAccountTime(unixtime);
            accountItemMapper.insertAccountItem(accountItem);
        }
    }

    // 在fragment可见的时候，进行adapter初始化
    @Override
    public void onResume() {
        super.onResume();
        findView();
        init();
        setListener();

    }

    /**
     * 刷新全局块列表
     *
     * @param isFirst
     */
    public void refreshGlobleBlocks(boolean isFirst) {

        // 当前块
        Block currentBlock = new Block();
        // 当前遍历时间
        String currentBlockDateString = "";

        // 如果是第一次加载而不是往里面添加
        if (isFirst) {
            // 查询记账列表
            AccountItemMapper accountItemMapper = new AccountItemMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(getContext()));
            List<AccountItem> accountItems = accountItemMapper.selectDescPage(currentPage++, GlobalConstant.PAGE_SIZE);
//            List<AccountItem> accountItems = accountItemMapper.selectDesc();

            if (accountItems.isEmpty()) {
                return;
            }

            // 将第一条数据的时间作为当前块的时间
            AccountItem firstAccountItem = accountItems.get(0);
            Long accountTime = firstAccountItem.getAccountTime();
            currentBlockDateString = getFormatDate(accountTime);
            currentBlock.setDate(new Date(firstAccountItem.getAccountTime()));
            // 组合Tag
            TagMapper tagMapper = new TagMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(getContext()));
            firstAccountItem.setTag(tagMapper.selectByTid(firstAccountItem.getTid()));
            // 将第一项加入当前块
            currentBlock.getThatDayAccountItems().add(firstAccountItem);
            // 如果列表中只有一项
            // 则将这一项加入
            if (accountItems.size() == 1) {
                blocks.add(currentBlock);
            }
            // 从第二条开始
            for (int i = 1; i < accountItems.size(); i++) {
                AccountItem accountItem = accountItems.get(i);
                // 组合Tag
                accountItem.setTag(tagMapper.selectByTid(accountItem.getTid()));
                // 获取当前遍历到的记账项的时间
                String dateString = getFormatDate(accountItem.getAccountTime());
                // 当前遍历时间是否与当前块的时间相同
                if (Objects.equals(dateString, currentBlockDateString)) {
                    // 相同则添加
                    currentBlock.getThatDayAccountItems().add(accountItem);
                } else {
                    // 若不同,则当前块存入列表
                    blocks.add(currentBlock);
                    // 当前遍历日期等于该项日期
                    currentBlockDateString = dateString;
                    // 当前块等于新建块,并传入当前遍历日期
                    currentBlock = new Block();
                    currentBlock.setDate(new Date(accountItem.getAccountTime()));
                    // 将该项存入当前块
                    currentBlock.getThatDayAccountItems().add(accountItem);
                }
                if (i == accountItems.size() - 1) {
                    // 若遍历完成,将当前块存入全局块列表中(因为至少最后一项没有存入列表中)
                    blocks.add(currentBlock);
                }
            }

        } else {
            // 查询记账列表
            AccountItemMapper accountItemMapper = new AccountItemMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(getContext()));
            List<AccountItem> accountItems = accountItemMapper.selectDescPage(currentPage++, GlobalConstant.PAGE_SIZE);
//            List<AccountItem> accountItems = accountItemMapper.selectDesc();
            // 如果列表为空，直接返回
            if (accountItems.isEmpty()) {
                return;
            }
            // 如果不是第一次而是扩展blocks
            // 将blocks中最后一个块取出作为当前块
            // 获取最后一个块
            currentBlock = blocks.get(blocks.size() - 1);
            // 避免全局中的最后一个块重复添加
            blocks.remove(blocks.size() - 1);
            // 当前块的时间也设为取出块的时间
            currentBlockDateString = getFormatDate(currentBlock.getDate().getTime());


            // 遍历该列表
            for (int i = 0; i < accountItems.size(); i++) {
                AccountItem accountItem = accountItems.get(i);

                // 组合Tag
                TagMapper tagMapper = new TagMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(getContext()));
                accountItem.setTag(tagMapper.selectByTid(accountItem.getTid()));

                String dateString = getFormatDate(accountItem.getAccountTime());
                if (Objects.equals(dateString, currentBlockDateString)) {
                    currentBlock.getThatDayAccountItems().add(accountItem);
                } else {
                    // 将当前块添加到大块类列表中
                    blocks.add(currentBlock);

                    // 当前遍历日期等于该项日期
                    currentBlockDateString = dateString;
                    // 当前块等于新建块,并传入当前遍历日期
                    currentBlock = new Block();
                    currentBlock.setDate(new Date(accountItem.getAccountTime()));
                    // 将该项存入当前块
                    currentBlock.getThatDayAccountItems().add(accountItem);
                }
                if (i == accountItems.size() - 1) {
                    // 若遍历完成,将当前块存入全局块列表中(因为至少最后一项没有存入列表中)
                    blocks.add(currentBlock);
                }
            }

        }

    }

    public String getFormatDate(Long dateLong) {
        Date date = new Date(dateLong);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public void extendBlocks() {
        // 不是第一次填充,而是扩充
        refreshGlobleBlocks(false);
        blockRecycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void init() {
        currentPage = 1;
        // blocks初始化
        blocks = new ArrayList<>();
        // 第一次填充blocks
        refreshGlobleBlocks(true);

        blockRecycleViewAdapter = new BlockRecycleViewAdapter(this, blocks);
        recyclerView.setAdapter(blockRecycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void findView() {
        recyclerView = activity.findViewById(R.id.fragment_show_account_list_recycle_view);
    }

    @Override
    public void setListener() {
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) { //当前状态为停止滑动
                    if (!recyclerView.canScrollVertically(1)) { // 到达底部
                        System.out.println("到底了");
                        extendBlocks();
                    } else if (!recyclerView.canScrollVertically(-1)) { // 到达顶部
//                            System.out.println("到顶了");
                    }
                }
            }
        };
        recyclerView.addOnScrollListener(onScrollListener);
    }

}
