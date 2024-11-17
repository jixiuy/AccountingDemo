package com.blackorangejuice.songguojizhang.transaction.home.list.in.account.edit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.Tag;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.TagMapper;
import com.blackorangejuice.songguojizhang.transaction.home.myinfo.in.moresetting.tag.AddTagActivity;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.view.TextViewDrawable;

import java.util.List;

/**
 * 账单编辑页面的tag网格适配器
 */
public class TagGridAdapter extends RecyclerView.Adapter<TagGridAdapter.TagGridViewHolder> {
    // 该网格当前所在页面
    EditAccountActivity editAccountPageActivity;

    private List<Tag> tagGridItems;

    private Context mContext ;

    private Tag currentShowTag;

    static class TagGridViewHolder extends RecyclerView.ViewHolder {

        private View tagItem;
//        ImageView tagImageView;
        TextView tagImageTextView;
        TextView tagTextView;

        public TagGridViewHolder(@NonNull View itemView) {
            super(itemView);
            tagItem = itemView;
//            tagImageView = itemView.findViewById(R.id.tag_grid_item_image_view);
            tagImageTextView = itemView.findViewById(R.id.tag_grid_item_image_text_view);
            tagTextView = itemView.findViewById(R.id.tag_grid_item_text_view);
        }
    }

    public TagGridAdapter(List<Tag> tags, EditAccountActivity activity) {
        tagGridItems = tags;
        // 末尾添加标签按钮
        Tag addButtonTag = new Tag();
        addButtonTag.setTagName("添加");
        addButtonTag.setTagImgColor(0xFFF0F0F0);
        tagGridItems.add(addButtonTag);
        editAccountPageActivity = activity;
    }


    public void refreshTags(List<Tag> tags){
        tagGridItems = tags;
        // 末尾添加标签按钮
        Tag addButtonTag = new Tag();
        addButtonTag.setTagName("添加");
        addButtonTag.setTagImgColor(0xFFF0F0F0);
        tagGridItems.add(addButtonTag);
        // 提醒grid刷新
        TagGridAdapter.this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TagGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tag_grid, parent, false);
        TagGridViewHolder tagGridViewHolder = new TagGridViewHolder(view);

        tagGridViewHolder.tagItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = tagGridViewHolder.getAdapterPosition();
                Tag theTagWhichWantToSetOnThisAccountItem = tagGridItems.get(adapterPosition);
                // 如果是添加按钮则要跳到添加界面
                if(theTagWhichWantToSetOnThisAccountItem.getTagName().equals("添加")){
                    AddTagActivity.startThisActivity(editAccountPageActivity);
                    return ;
                }
                currentShowTag = theTagWhichWantToSetOnThisAccountItem;
                editAccountPageActivity.setTagNameAndImg(theTagWhichWantToSetOnThisAccountItem);
            }

        });
        tagGridViewHolder.tagItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int adapterPosition = tagGridViewHolder.getAdapterPosition();
                Tag theTagWhichWantToDelete = tagGridItems.get(adapterPosition);
                // 如果是添加按钮则不能删除
                if(theTagWhichWantToDelete.getTagName().equals("添加")){
                    return true;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(editAccountPageActivity);
                builder.setTitle("你确定要删除此标签吗");
                builder.setMessage("删除后不可恢复");
                builder.setCancelable(true);
                builder.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // 删除该账单
                        TagMapper tagMapper = new TagMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(editAccountPageActivity));
                        // 判断当前账单是否使用了该标签
                        if(currentShowTag == theTagWhichWantToDelete) {
                            EasyUtils.showOneToast("当前编辑账单使用了该标签，故该标签无法删除，若必须删除，请先修改");
                        }else if(tagMapper.deleteByTid(theTagWhichWantToDelete)){
                            // 删除列表中的标签
                            tagGridItems.remove(adapterPosition);
                            // 网格刷新
                            TagGridAdapter.this.notifyItemRemoved(adapterPosition);
                        }else{
                            EasyUtils.showOneToast("您还有账单使用了该标签，故该标签无法删除，若必须删除，请先删除对应账单");
                        }




                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();

                return true;
            }
        });
        return tagGridViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull TagGridViewHolder holder, int position) {
        Tag bindTag = tagGridItems.get(position);
        // 绑定标签名
        holder.tagTextView.setText(bindTag.getTagName());
        // 绑定图片

//        String tagImgName = bindTag.getTagImgName();
//        Bitmap bitmap = SongGuoUtils.getBitmapByFileName(mContext,"bindTag/"+tagImgName);
//        holder.tagImageView.setImageBitmap(bitmap);
        holder.tagImageTextView.setText(bindTag.getTagName());
        holder.tagImageTextView.setBackground(TextViewDrawable.getDrawable(bindTag.getTagImgColor()));
        if(bindTag.getTagName().equals("添加")){
            holder.tagImageTextView.setText("+");
            holder.tagImageTextView.setTextColor(0xFF000000);
        }
    }

    @Override
    public int getItemCount() {
        return tagGridItems.size();
    }


}
