package com.blackorangejuice.songguojizhang.transaction.home.list.in.event.choose;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.view.TextViewDrawable;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 事件绑定账单时选择账单页面的item适配器
 */
public class ChooseAccountItemRecycleViewAdapter extends RecyclerView.Adapter<ChooseAccountItemRecycleViewAdapter.AccountItemViewHolder> {
    private List<AccountItem> accountItems;
    List<AccountItem> willAddAccountItemList;
    List<AccountItem> willRemoveAccountItemList ;

    static class AccountItemViewHolder extends RecyclerView.ViewHolder {
        View accountItemView;
//        ImageView tagImageView;
        TextView tagImageTextView;
        TextView tagNameTextView;
        TextView remarkTextView;
        TextView sumTextView;
        TextView timeTextView;
        CheckBox checkBox;


        public AccountItemViewHolder(@NonNull View itemView) {
            super(itemView);
            accountItemView = itemView;
//            tagImageView = itemView.findViewById(R.id.item_choose_account_list_img);
            tagImageTextView = itemView.findViewById(R.id.item_choose_account_list_img_text);
            tagNameTextView = itemView.findViewById(R.id.item_choose_account_list_name);
            remarkTextView = itemView.findViewById(R.id.item_choose_account_list_remark);
            sumTextView = itemView.findViewById(R.id.item_choose_account_list_sum);
            timeTextView = itemView.findViewById(R.id.item_choose_account_list_time);
            checkBox = itemView.findViewById(R.id.item_choose_account_list_checkbox);
        }

    }

    public ChooseAccountItemRecycleViewAdapter(ChooseBlockRecycleViewAdapter blockAdapter, int postation, ChooseShowAccountListPageActivity fragment, List<AccountItem> accountItems) {
        this.accountItems = accountItems;
        //获取全局列表
        willAddAccountItemList = GlobalInfo.lastAddEvent.getWillAddAccountItemList();


        willRemoveAccountItemList = GlobalInfo.lastAddEvent.getWillRemoveAccountItemList();
        willRemoveAccountItemList.clear();
    }

    @NonNull
    @Override
    public AccountItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_choose_account_list, parent, false);
        AccountItemViewHolder accountItemViewHolder = new AccountItemViewHolder(view);
        accountItemViewHolder.accountItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checkbox 反选
                accountItemViewHolder.checkBox.setChecked(!accountItemViewHolder.checkBox.isChecked());

            }
        });
        /**
         * 这里的重点就在于: 要在保存后才能进行账单与事件的绑定或者取消绑定,
         * 否则不管有没有进行保存,都会对账单事件绑定做修改
         * 保存事件后会获取保存后的事件, 此时获取事件id,遍历绑定列表中的账单进行绑定
         * 取消绑定列表中的事件id置为0
         */
        accountItemViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AccountItemMapper accountItemMapper = new AccountItemMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(accountItemViewHolder.itemView.getContext()));
                int adapterPosition = accountItemViewHolder.getAdapterPosition();
                AccountItem accountItem = accountItems.get(adapterPosition);
                if (isChecked) {
                    // 将当前账单添加到当前事件的list中
                    // 这里使用list主要是因为在新建的时候事件还没有id，无法绑定，所以暂存
                    ChooseAccountItemRecycleViewAdapter.this.willAddAccountItemList.add(accountItem);
                } else {
                    // 移除
                    ChooseAccountItemRecycleViewAdapter.this.willAddAccountItemList.remove(accountItem);
                    // 暂存到移除列表，保存后统一置为0
                    willRemoveAccountItemList.add(accountItem);
                }
            }
        });


        return accountItemViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull AccountItemViewHolder holder, int position) {

        AccountItem accountItem = accountItems.get(position);
        // 设置图片
//        String tagImgName = accountItem.getTag().getTagImgName();
//        String fileName = "tag/" + tagImgName;
//        holder.tagImageView.setImageBitmap(SongGuoUtils.getBitmapByFileName(holder.itemView.getContext(), fileName));

        holder.tagImageTextView.setText(accountItem.getTag().getTagName());
        holder.tagImageTextView.setBackground(TextViewDrawable.getDrawable(accountItem.getTag().getTagImgColor()));

        // 设置文字
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
        // 获取account的eid
        Integer eid = accountItem.getEid();
        // 若eid不为0
        if (eid != 0) {
            // 如果eid为当前事件的id,则设为选中状态
            if (eid == GlobalInfo.lastAddEvent.getEid()) {
                holder.checkBox.setChecked(true);
            } else {
                // 否则隐藏
                holder.checkBox.setEnabled(false);
                holder.itemView.setClickable(false);
                holder.checkBox.setVisibility(View.INVISIBLE);
            }

        }


    }

    @Override
    public int getItemCount() {
        return accountItems.size();
    }


}
