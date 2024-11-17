package com.blackorangejuice.songguojizhang.transaction.home.list.in.account.show;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.Block;

import java.text.SimpleDateFormat;
import java.util.List;

public class BlockRecycleViewAdapter extends RecyclerView.Adapter<BlockRecycleViewAdapter.BlockViewHolder> {

    // 存放时间列表的列表
    private List<Block> blockList;


    ShowAccountListPageFragment showAccountListPageFragment;

    static class BlockViewHolder extends RecyclerView.ViewHolder {
        View blockItem;
        TextView textView;
        RecyclerView recyclerView;

        public BlockViewHolder(@NonNull View itemView) {
            super(itemView);
            blockItem = itemView;
            textView = itemView.findViewById(R.id.big_block_time);
            recyclerView = itemView.findViewById(R.id.account_item_recycle_view);
        }


    }

    public BlockRecycleViewAdapter(ShowAccountListPageFragment fragment, List<Block> blockList) {
        showAccountListPageFragment = fragment;
        // 传入时间列表列表
        this.blockList = blockList;

    }

    @NonNull
    @Override
    public BlockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.block_with_time_and_account_list_recycle_view_item, parent, false);
        return new BlockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockViewHolder holder, int position) {
        Block block = this.blockList.get(position);
        // 绑定一个时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        holder.textView.setText(simpleDateFormat.format(block.getDate()));

        View blockItem = holder.blockItem;
        // 绑定一个RV
        RecyclerView recyclerView = blockItem.findViewById(R.id.account_item_recycle_view);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(SelfApplication.getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(blockItem.getContext());

        recyclerView.setLayoutManager(layoutManager);
        // 绑定adapter
        AccountItemRecycleViewAdapter adapter = new AccountItemRecycleViewAdapter(this, position, showAccountListPageFragment, block.getThatDayAccountItems());

        recyclerView.setAdapter(adapter);
        holder.recyclerView = recyclerView;
        Log.i("BlockRecycleViewAdapter", String.valueOf(position));
        // 如果已经展示到结尾
        // 更新全局大块类


    }

    @Override
    public int getItemCount() {
        return blockList.size();
    }

    public void removeFromBlockList(int position){
        blockList.remove(position);
    }
}
