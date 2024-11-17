package com.blackorangejuice.songguojizhang.transaction.home.list.in.event.choose;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.view.TextViewDrawable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowChosenAccountItemRecycleViewAdapter extends RecyclerView.Adapter<ShowChosenAccountItemRecycleViewAdapter.AccountItemViewHolder> {
    private List<AccountItem> accountItems;
    ShowChosenAccountPageActivity showChosenAccountPageActivity;
    List<AccountItem> willRemoveAccountItemList ;

    static class AccountItemViewHolder extends RecyclerView.ViewHolder {
        View accountItemView;
//        ImageView tagImageView;
        TextView tagImageTextView;
        TextView tagNameTextView;
        TextView remarkTextView;
        TextView sumTextView;
        TextView timeTextView;


        public AccountItemViewHolder(@NonNull View itemView) {
            super(itemView);
            accountItemView = itemView;
//            tagImageView = itemView.findViewById(R.id.account_item_in_the_recycle_view_tag_img);
            tagImageTextView = itemView.findViewById(R.id.account_item_in_the_recycle_view_tag_img_text);
            tagNameTextView = itemView.findViewById(R.id.account_item_in_the_recycle_view_tag_name);
            remarkTextView = itemView.findViewById(R.id.account_item_in_the_recycle_view_tag_remark);
            sumTextView = itemView.findViewById(R.id.account_item_in_the_recycle_view_tag_sum);
            timeTextView = itemView.findViewById(R.id.account_item_in_the_recycle_view_tag_time);
        }

    }

    public ShowChosenAccountItemRecycleViewAdapter(ShowChosenAccountPageActivity showChosenAccountPageActivity, List<AccountItem> accountItems) {
        this.accountItems = accountItems;
        this.showChosenAccountPageActivity = showChosenAccountPageActivity;
    }

    @NonNull
    @Override
    public AccountItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account_list, parent, false);
        AccountItemViewHolder accountItemViewHolder = new AccountItemViewHolder(view);
        accountItemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(showChosenAccountPageActivity);
                builder.setTitle("你确定要解除与该账单的绑定吗");
                builder.setCancelable(true);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 暂存到列表
                        AccountItem accountItem = accountItems.get(accountItemViewHolder.getAdapterPosition());
                        if(willRemoveAccountItemList == null){
                            willRemoveAccountItemList = new ArrayList<>();
                            GlobalInfo.lastAddEvent.setWillRemoveAccountItemList(willRemoveAccountItemList);
                        }
                        // 暂存到列表，保存后同意置为0
                        willRemoveAccountItemList.add(accountItem);
                        GlobalInfo.lastAddEvent.getWillAddAccountItemList().remove(accountItem);
//                        showChosenAccountPageActivity.refreshAccountList();
                        showChosenAccountPageActivity.dbrefreshAccountList();


                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                return true;
            }
        });
        return accountItemViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull AccountItemViewHolder holder, int position) {

        AccountItem accountItem = accountItems.get(position);
//        String tagImgName = accountItem.getTag().getTagImgName();
//        String fileName = "tag/" + tagImgName;
//        holder.tagImageView.setImageBitmap(SongGuoUtils.getBitmapByFileName(holder.itemView.getContext(), fileName));

        holder.tagImageTextView.setText(accountItem.getTag().getTagName());
        holder.tagImageTextView.setBackground(TextViewDrawable.getDrawable(accountItem.getTag().getTagImgColor()));

        holder.tagNameTextView.setText(accountItem.getTag().getTagName());
        holder.remarkTextView.setText(accountItem.getRemark());

        // 计算金额
        StringBuilder sum = new StringBuilder("");
        if (AccountItem.INCOME.equals(accountItem.getIncomeOrExpenditure())) {
            sum.append("+");
        } else {
            sum.append("-");
        }

        holder.sumTextView.setText(sum.append(accountItem.getSum()).toString());
        holder.timeTextView.setText(new SimpleDateFormat("yyyy/MM/dd").format(accountItem.getAccountTime()));

    }

    @Override
    public int getItemCount() {
        return accountItems.size();
    }


}
