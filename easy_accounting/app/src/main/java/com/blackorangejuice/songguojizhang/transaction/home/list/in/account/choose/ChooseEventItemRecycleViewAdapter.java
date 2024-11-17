package com.blackorangejuice.songguojizhang.transaction.home.list.in.account.choose;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.text.SimpleDateFormat;
import java.util.List;

public class ChooseEventItemRecycleViewAdapter extends RecyclerView.Adapter<ChooseEventItemRecycleViewAdapter.EventItemViewHolder> {

    private List<EventItem> eventItems;
    ChooseEventPageActivity chooseEventPageActivity;

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

    public ChooseEventItemRecycleViewAdapter(ChooseEventPageActivity activity, List<EventItem> eventItems) {
        this.eventItems = eventItems;
        this.chooseEventPageActivity = activity;
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
                GlobalInfo.lastAddAccount.setEid(eventItem.getEid());
                chooseEventPageActivity.finish();
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
