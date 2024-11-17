package com.blackorangejuice.songguojizhang.transaction.home.list.in.account.show;

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
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.account.edit.UpdateEditAccountPageActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.view.TextViewDrawable;

import java.text.SimpleDateFormat;
import java.util.List;

public class AccountItemRecycleViewAdapter extends RecyclerView.Adapter<AccountItemRecycleViewAdapter.AccountItemViewHolder> {
    private List<AccountItem> accountItems;
    ShowAccountListPageFragment showAccountListPageFragment;
    BlockRecycleViewAdapter blockRecycleViewAdapter;
    int blockRecycleViewAdapterPosition;

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

    public AccountItemRecycleViewAdapter(BlockRecycleViewAdapter blockAdapter,int postation, ShowAccountListPageFragment fragment,List<AccountItem> accountItems) {
        this.accountItems = accountItems;
        this.showAccountListPageFragment = fragment;
        this.blockRecycleViewAdapter = blockAdapter;
        this.blockRecycleViewAdapterPosition = postation;
    }

    @NonNull
    @Override
    public AccountItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account_list, parent, false);
        AccountItemViewHolder accountItemViewHolder = new AccountItemViewHolder(view);
        accountItemViewHolder.accountItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = accountItemViewHolder.getAdapterPosition();
                AccountItem accountItem = accountItems.get(adapterPosition);
                // 将该记账项存入全局
                GlobalInfo.lastAddAccount = accountItem;
                UpdateEditAccountPageActivity.startThisActivity(parent.getContext());

            }
        });
        accountItemViewHolder.accountItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int adapterPosition = accountItemViewHolder.getAdapterPosition();
                AccountItem accountItem = accountItems.get(adapterPosition);

                AlertDialog.Builder builder = new AlertDialog.Builder(showAccountListPageFragment.getContext());
                builder.setTitle("你确定要删除此账单吗");
                builder.setMessage("删除后不可恢复");
                builder.setCancelable(true);
                builder.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // 删除该账单
                        AccountItemMapper accountItemMapper = new AccountItemMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(showAccountListPageFragment.getContext()));
                        accountItemMapper.deleteAccountItem(accountItem);
                        // 删除小列表中的账单
                        accountItems.remove(adapterPosition);
                        // 小列表刷新
                        AccountItemRecycleViewAdapter.this.notifyItemRemoved(adapterPosition);
                        // 若当前小列表中已经没有账单,则删除当前block
                        if(accountItems.isEmpty()){
                            blockRecycleViewAdapter.removeFromBlockList(blockRecycleViewAdapterPosition);
                            blockRecycleViewAdapter.notifyItemRemoved(blockRecycleViewAdapterPosition);
                        }

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
