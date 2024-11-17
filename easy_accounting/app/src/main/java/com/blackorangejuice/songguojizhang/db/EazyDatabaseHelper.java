package com.blackorangejuice.songguojizhang.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;

public class EazyDatabaseHelper extends SQLiteOpenHelper {

    private Context myContext;
    public static EazyDatabaseHelper songGuoDatabaseHelper = null;

    /**
     * 私有构造方法
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    private EazyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myContext = context;
    }

    /**
     * 单例模式
     * @param context
     * @return
     */
    public static EazyDatabaseHelper getSongGuoDatabaseHelper(Context context){
        if(songGuoDatabaseHelper == null){
            songGuoDatabaseHelper = new EazyDatabaseHelper(context, GlobalConstant.DATABASE_NAME, null, 1);
        }
        return songGuoDatabaseHelper;
    }

    private static final String CREAT_TABLE_TAG = "create table t_tag(\n" +
            "    tid integer primary key autoincrement,\n" +
            "    tag_name text not null unique,\n" +
            "    tag_img_name text ,\n" +
            "    tag_img_color integer not null\n" +
            ")";
    private static final String CREATE_TABLE_ACCOUNT_BOOK = "create table t_account_book(\n" +
            "    bid integer primary key autoincrement,\n" +
            "    account_book_name text not null unique,\n" +
            "    budget_all real,\n" +
            "    budget_year real,\n" +
            "    budget_month real,\n" +
            "    budget_week real,\n" +
            "    overview_budget text\n" +
            ")";
    private static final String CREAT_TABLE_EVENT_ITEM= "create table t_event_item(\n" +
            "    eid integer primary key autoincrement,\n" +
            "    event_title text not null,\n" +
            "    event_content text,\n" +
            "    event_time integer not null,\n" +
            "    bid integer not null\n" +
            ")";
    private static final String CREAT_TABLE_ACCOUNT_ITEM = "create table t_account_item(\n" +
            "    aid integer primary key autoincrement,\n" +
            "    income_or_expenditure text not null,\n" +
            "    tid integer not null,\n" +
            "    sum real not null,\n" +
            "    remark text not null,\n" +
            "    account_time integer not null,\n" +
            "    if_borrow_or_lend text not null,\n" +
            "    bid integer not null,\n" +
            "    eid integer not null\n" +
            ")";
    private static final String CREAT_TABLE_SETTING_INFO = "create table t_setting_info(\n" +
            "    sid integer primary key autoincrement,\n" +
            "    username text not null unique,\n" +
            "    password text,\n" +
            "    password_question text,\n" +
            "    password_answer text,\n"+
            "    if_enable_password_check text not null,\n" +
            "    backup_url text,\n"+
            "    restore_url text,\n"+
            "    defult_launch_page text not null,\n" +
            "    defult_add_page text not null,\n" +
            "    current_account_book_bid integer\n" +
            "\n" +
            ")";
    public static final String CREAT_TABLE_BORROW_LEND = "create table t_borrow_lend(\n" +
            "    men text not null,\n" +
            "    sum real not null,\n" +
            "    borrow_time integer not null,\n" +
            "    return_time integer,\n" +
            "    remark text not null,\n" +
            "    bid integer not null\n" +
            ")";
    public static final String GUIDE_CREATE_ACCOUNT_BOOK = "insert into t_account_book \n" +
            "(bid, account_book_name,budget_all, budget_year, budget_month, budget_week, overview_budget)\n" +
            "values\n" +
            "(1,\"长按可删除该账本\",1000,1000,1000,1000,\"showMonth\")";
    public static final String GUIDE_CREATE_EVENT= "insert into t_event_item\n" +
            " (eid, event_title,event_content,event_time,bid)\n" +
            " values\n" +
            " (1,\"长按删除，点击编辑\",\"如果你在事件编辑界面，点击上方时间区域可以设置记事时间。点击下方灰色区域绑定账单，长按下方灰色区域可以删除当前事件。编辑结束点击右上角对勾保存\",0,2)";
    public static final String GUIDE_CREATE_ACCOUNT_ONE= " insert into t_account_item \n" +
            "(income_or_expenditure, tid, sum, remark, account_time, if_borrow_or_lend, bid, eid)\n" +
            "values\n" +
            "(\"income\",\"1\",\"10.24\",\"长按该条账单可删除\",0,\"false\",2,1)";
    public static final String GUIDE_CREATE_ACCOUNT_TWO= "insert into t_account_item \n" +
            "(income_or_expenditure, tid, sum, remark, account_time, if_borrow_or_lend, bid, eid)\n" +
            "values\n" +
            "(\"expenditure\",\"3\",\"84.21\",\"点击备注可进行编辑\",0,\"false\",2,1)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_TABLE_TAG);
        db.execSQL(CREATE_TABLE_ACCOUNT_BOOK);
        db.execSQL(CREAT_TABLE_EVENT_ITEM);
        db.execSQL(CREAT_TABLE_ACCOUNT_ITEM);
        db.execSQL(CREAT_TABLE_SETTING_INFO);
        db.execSQL(CREAT_TABLE_BORROW_LEND);
        // 引导数据
        db.execSQL(GUIDE_CREATE_ACCOUNT_BOOK);
        db.execSQL(GUIDE_CREATE_EVENT);
        db.execSQL(GUIDE_CREATE_ACCOUNT_ONE);
        db.execSQL(GUIDE_CREATE_ACCOUNT_TWO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
