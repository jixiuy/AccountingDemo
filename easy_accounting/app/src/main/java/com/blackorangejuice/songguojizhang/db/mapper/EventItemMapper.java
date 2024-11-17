package com.blackorangejuice.songguojizhang.db.mapper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.bean.SearchItem;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventItemMapper {
    public static final String INSERT_EVENT_ITEM = "insert into t_event_item\n" +
            " (event_title,event_content,event_time,bid)\n" +
            " values\n" +
            " (?,?,?,?)";
    public static final String SELECT_THE_NEW = "select * from t_event_item order by eid desc limit 1";
    public static final String DELETE_EVENT_ITEM = "delete from t_event_item where eid = ?";
    public static final String UPDATE_EVENT_ITEM = "update t_event_item \n" +
            "set \n" +
            "event_title = ?,\n" +
            "event_content = ?,\n" +
            "event_time = ?,\n" +
            "bid = ?\n" +
            "where eid = ?";
    public static final String SELECT_DESC_PAGE = "select * from t_event_item where bid = ? order by event_time desc limit ?,?";
    public static final String SELECT_BY_EID = "select * from t_event_item where eid = ?";
    public static final String DELETE_BY_BOOK = "delete from t_event_item where bid = ?";
    public static final String RESET_ACCOUNT_ITEM_EID = "update t_account_item\n" +
            "set eid = 0\n" +
            "where eid = ?";
    public static final String SELECT_BY_KEY_WORD = "select * from t_event_item where event_title like ? or event_content like ?";
    public static final String SELECT_BY_TIME = "select * from t_event_item where bid = ? and event_time between ? and ?";
    EazyDatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public EventItemMapper(EazyDatabaseHelper songGuoDatabaseHelper) {
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();

    }

    /**
     * 插入一条事件
     *
     * @param eventItem
     * @return
     */
    public EventItem insertEventItem(EventItem eventItem) {
        sqLiteDatabase.execSQL(INSERT_EVENT_ITEM, new String[]{
                eventItem.getEventTitle(), eventItem.getEventContent(),
                String.valueOf(eventItem.getEventTime()), String.valueOf(eventItem.getBid())});
        return selectTheNew();
    }

    /**
     * 寻找刚插入的事件
     *
     * @return
     */
    private EventItem selectTheNew() {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_THE_NEW, null);
        EventItem eventItem = new EventItem();
        if (cursor.moveToFirst()) {
            eventItem.setEid(cursor.getInt(cursor.getColumnIndex("eid")));
            eventItem.setEventTitle(cursor.getString(cursor.getColumnIndex("event_title")));
            eventItem.setEventContent(cursor.getString(cursor.getColumnIndex("event_content")));
            eventItem.setEventTime(cursor.getLong(cursor.getColumnIndex("event_time")));
            eventItem.setBid(cursor.getInt(cursor.getColumnIndex("bid")));
        }
        cursor.close();
        return eventItem;
    }

    /**
     * 删除事件
     *
     * @param eventItem
     */
    public void deleteEventItem(EventItem eventItem) {
        sqLiteDatabase.execSQL(DELETE_EVENT_ITEM, new String[]{String.valueOf(eventItem.getEid())});
        // 将所有事件id为本事件的id的账单的事件id设为0
        sqLiteDatabase.execSQL(RESET_ACCOUNT_ITEM_EID, new String[]{String.valueOf(eventItem.getEid())});
    }

    /**
     * 更新事件
     *
     * @param eventItem
     * @return
     */
    public EventItem updateEventItem(EventItem eventItem) {
        sqLiteDatabase.execSQL(UPDATE_EVENT_ITEM, new String[]{
                eventItem.getEventTitle(), eventItem.getEventContent(),
                String.valueOf(eventItem.getEventTime()), String.valueOf(eventItem.getBid()),
                String.valueOf(eventItem.getEid())});
        return selectByEid(eventItem.getEid());
    }

    /**
     * 分页查找事件
     *
     * @param page
     * @param size
     * @return
     */
    public List<EventItem> selectDescPage(Integer page, Integer size) {
        // 页面转指针
        int index = (page - 1) * size;
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_DESC_PAGE, new String[]{String.valueOf(GlobalInfo.currentAccountBook.getBid()), String.valueOf(index), String.valueOf(size)});
        List<EventItem> eventItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                EventItem eventItem = new EventItem();
                eventItem.setEid(cursor.getInt(cursor.getColumnIndex("eid")));
                eventItem.setEventTitle(cursor.getString(cursor.getColumnIndex("event_title")));
                eventItem.setEventContent(cursor.getString(cursor.getColumnIndex("event_content")));
                eventItem.setEventTime(cursor.getLong(cursor.getColumnIndex("event_time")));
                eventItem.setBid(cursor.getInt(cursor.getColumnIndex("bid")));
                eventItems.add(eventItem);
            } while (cursor.moveToNext());
        }


        cursor.close();
        return eventItems;
    }

    /**
     * 通过eid查找事件
     *
     * @param eid
     * @return
     */
    public EventItem selectByEid(Integer eid) {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_EID, new String[]{String.valueOf(eid)});
        EventItem eventItem = null;
        if (cursor.moveToFirst()) {
            eventItem = new EventItem();
            eventItem.setEid(cursor.getInt(cursor.getColumnIndex("eid")));
            eventItem.setEventTitle(cursor.getString(cursor.getColumnIndex("event_title")));
            eventItem.setEventContent(cursor.getString(cursor.getColumnIndex("event_content")));
            eventItem.setEventTime(cursor.getLong(cursor.getColumnIndex("event_time")));
            eventItem.setBid(cursor.getInt(cursor.getColumnIndex("bid")));
        }
        cursor.close();
        return eventItem;
    }

    /**
     * 删除某账本下的所有事件
     *
     * @param bid
     */
    public void deleteEventItemByBook(Integer bid) {
        sqLiteDatabase.execSQL(DELETE_BY_BOOK, new String[]{String.valueOf(bid)});
    }

    /**
     * 关键词查找
     *
     * @param keyWord
     * @return
     */
    public List<SearchItem> selectByKeyWord(String keyWord) {

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_KEY_WORD, new String[]{"%" + keyWord + "%","%" + keyWord + "%"});
        List<SearchItem> searchItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                SearchItem searchItem = new SearchItem();
                searchItem.setId(cursor.getInt(cursor.getColumnIndex("eid")));
                searchItem.setSum("");
                searchItem.setRemarkOrEventContent(cursor.getString(cursor.getColumnIndex("event_content")));
                searchItem.setTime(cursor.getLong(cursor.getColumnIndex("event_time")));
                searchItem.setType(GlobalConstant.EVENT);
                TagMapper tagMapper = new TagMapper(songGuoDatabaseHelper);
                searchItem.setTagNameOrEventTitle(cursor.getString(cursor.getColumnIndex("event_title")));
                searchItems.add(searchItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return searchItems;
    }

    /**
     * 按照时间查找
     * @param d0
     * @param d1
     * @return
     */
    public List<SearchItem> selectByTime(Date d0, Date d1) {

        Long date0 = d0.getTime();
        // 这里为啥要加一来着，忘了
        Long date1 = d1.getTime() + 1;
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_TIME, new String[]{
                String.valueOf(GlobalInfo.currentAccountBook.getBid()),
                String.valueOf(date0), String.valueOf(date1)
        });

        List<SearchItem> searchItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                SearchItem searchItem = new SearchItem();
                searchItem.setId(cursor.getInt(cursor.getColumnIndex("eid")));
                searchItem.setSum("");
                searchItem.setRemarkOrEventContent(cursor.getString(cursor.getColumnIndex("event_content")));
                searchItem.setTime(cursor.getLong(cursor.getColumnIndex("event_time")));
                searchItem.setType(GlobalConstant.EVENT);
                TagMapper tagMapper = new TagMapper(songGuoDatabaseHelper);
                searchItem.setTagNameOrEventTitle(cursor.getString(cursor.getColumnIndex("event_title")));
                searchItems.add(searchItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return searchItems;
    }

}
