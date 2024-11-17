package com.blackorangejuice.songguojizhang.transaction.home.list.in.event.show;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.EventItemMapper;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.event.edit.UpdateEditEventPageActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.text.SimpleDateFormat;
import java.util.List;

public class EventItemRecycleViewAdapter extends RecyclerView.Adapter<EventItemRecycleViewAdapter.EventItemViewHolder> {

    private List<EventItem> eventItems;
    private ShowEventListPageFragment showEventListPageFragment;

    static class EventItemViewHolder extends RecyclerView.ViewHolder {
        View eventItemView;
        TextView titleTextView;
        TextView contentTextView;
        TextView timeTextView;


        public EventItemViewHolder(@NonNull View itemView) {
            super(itemView);
            eventItemView = itemView;
            titleTextView = itemView.findViewById(R.id.item_event_list_title);
            contentTextView = itemView.findViewById(R.id.item_event_list_content);
            timeTextView = itemView.findViewById(R.id.item_event_list_time);
        }

    }

    public EventItemRecycleViewAdapter(ShowEventListPageFragment fragment,List<EventItem> eventItems) {
        this.eventItems = eventItems;
        this.showEventListPageFragment = fragment;
    }

    @NonNull
    @Override
    public EventItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_list, parent, false);
        EventItemViewHolder eventItemViewHolder = new EventItemViewHolder(view);

        eventItemViewHolder.eventItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = eventItemViewHolder.getAdapterPosition();
                EventItem eventItem = eventItems.get(adapterPosition);
                // 将该事件项存入全局
                GlobalInfo.lastAddEvent = eventItem;
                UpdateEditEventPageActivity.startThisActivity(parent.getContext());

            }
        });
        eventItemViewHolder.eventItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int adapterPosition = eventItemViewHolder.getAdapterPosition();
                EventItem eventItem = eventItems.get(adapterPosition);

                AlertDialog.Builder builder = new AlertDialog.Builder(showEventListPageFragment.getContext());
                builder.setTitle("你确定要删除此事件吗");
                builder.setMessage("删除后不可恢复");
                builder.setCancelable(true);
                builder.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 删除该记事
                        EventItemMapper eventItemMapper = new EventItemMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(showEventListPageFragment.getContext()));
                        eventItemMapper.deleteEventItem(eventItem);
                        eventItems.remove(adapterPosition);
                        EventItemRecycleViewAdapter.this.notifyItemRemoved(adapterPosition);
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();

                return true;
            }
        });
        return eventItemViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull EventItemViewHolder holder, int position) {

        EventItem eventItem = eventItems.get(position);

        holder.titleTextView.setText(eventItem.getEventTitle());
        holder.contentTextView.setText(eventItem.getEventContent());
        holder.timeTextView.setText(new SimpleDateFormat("yyyy/MM/dd").format(eventItem.getEventTime()));

    }

    @Override
    public int getItemCount() {
        return eventItems.size();
    }


}
