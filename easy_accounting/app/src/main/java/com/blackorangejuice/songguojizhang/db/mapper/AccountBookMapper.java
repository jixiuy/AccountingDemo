package com.blackorangejuice.songguojizhang.db.mapper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blackorangejuice.songguojizhang.bean.AccountBook;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AccountBookMapper {
    public static final String INSERT_ACCOUNT_BOOK = "insert into t_account_book \n" +
            "(account_book_name,budget_all, budget_year, budget_month, budget_week, overview_budget)\n" +
            "values\n" +
            "(?,?,?,?,?,?)";

    public static final String UPDATE_ACCOUNT_BOOK_NAME = "update t_account_book\n" +
            "set account_book_name = ?\n" +
            "where bid = ?";

    public static final String DELETE_ACCOUNT_BOOK = "delete from t_account_book\n" +
            "    where bid = ?";

    public static final String SELETE_BY_BID = "select * from t_account_book where bid = ?";

    public static final String SELETE_BY_ACCOUNT_BOOK_NAME = "select * from t_account_book where account_book_name = ?";
    public static final String SELETE_ALL = "select * from t_account_book";
    public static final String UPDATE_ACCOUNT_BOOK_SETTING = "update t_account_book\n" +
            "set account_book_name = ?,\n" +
            "    budget_all = ?,\n" +
            "    budget_year = ?, \n" +
            "    budget_month = ?,\n" +
            "    budget_week = ?, \n" +
            "    overview_budget = ?\n" +
            "where bid = ?;";

    EazyDatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    /**
     * 构造方法传入helper
     * @param songGuoDatabaseHelper
     */
    public AccountBookMapper(EazyDatabaseHelper songGuoDatabaseHelper){
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();
    }

    /**
     * 插入账本
     * @param accountBook
     * @return
     */
    public AccountBook insertAccountBook(AccountBook accountBook){
        sqLiteDatabase.execSQL(INSERT_ACCOUNT_BOOK,new String[]{
                accountBook.getAccountBookName(),
                String.valueOf(0.0),
                String.valueOf(0.0),
                String.valueOf(0.0),
                String.valueOf(0.0),
                ""
        });
        return selectByAccountBookName(accountBook.getAccountBookName());
    }

    /**
     * 更新账本名
     * @param accountBook
     * @return
     */
    public AccountBook updateAccountBookName(AccountBook accountBook){
        sqLiteDatabase.execSQL(UPDATE_ACCOUNT_BOOK_NAME,new String[]{accountBook.getAccountBookName(), String.valueOf(accountBook.getBid())});
        return selectByBid(accountBook.getBid());
    }

    /**
     * 删除帐本
     * @param accountBook
     */
    public void deleteAccountBook(AccountBook accountBook){

        sqLiteDatabase.execSQL(DELETE_ACCOUNT_BOOK,new String[]{String.valueOf(accountBook.getBid())});
    }


    /**
     * 通过uid和AccountBookName获取账本
     * @param accountBookName
     * @return
     */
    public AccountBook selectByAccountBookName(String accountBookName){
        Cursor cursor = sqLiteDatabase.rawQuery(SELETE_BY_ACCOUNT_BOOK_NAME,new String[]{accountBookName});
        AccountBook accountBook = new AccountBook();
        accountBook.setAccountBookName(accountBookName);
        if(cursor.moveToFirst()){
            accountBook.setBid(cursor.getInt(cursor.getColumnIndex("bid")));
            accountBook.setBudgetAll(Double.valueOf(cursor.getString(cursor.getColumnIndex("budget_all"))));
            accountBook.setBudgetYear(Double.valueOf(cursor.getString(cursor.getColumnIndex("budget_year"))));
            accountBook.setBudgetMonth(Double.valueOf(cursor.getString(cursor.getColumnIndex("budget_month"))));
            accountBook.setBudgetWeek(Double.valueOf(cursor.getString(cursor.getColumnIndex("budget_week"))));
            accountBook.setOverviewBudget(cursor.getString(cursor.getColumnIndex("overview_budget")));

        }
        cursor.close();
        return accountBook;
    }

    /**
     * 通过bid查账本
     * @param bid
     * @return
     */
    public AccountBook selectByBid(Integer bid){
        Cursor cursor = sqLiteDatabase.rawQuery(SELETE_BY_BID,new String[]{String.valueOf(bid)});
        AccountBook accountBook = new AccountBook();
        accountBook.setBid(bid);

        if(cursor.moveToFirst()){
            accountBook.setAccountBookName(cursor.getString(cursor.getColumnIndex("account_book_name")));
            accountBook.setBudgetAll(Double.valueOf(cursor.getString(cursor.getColumnIndex("budget_all"))));
            accountBook.setBudgetYear(Double.valueOf(cursor.getString(cursor.getColumnIndex("budget_year"))));
            accountBook.setBudgetMonth(Double.valueOf(cursor.getString(cursor.getColumnIndex("budget_month"))));
            accountBook.setBudgetWeek(Double.valueOf(cursor.getString(cursor.getColumnIndex("budget_week"))));
            accountBook.setOverviewBudget(cursor.getString(cursor.getColumnIndex("overview_budget")));

        }
        cursor.close();
        return accountBook;
    }

    /**
     * 查询所有账本
     * @return
     */
    public List<AccountBook> selectAll(){
        List<AccountBook> accountBooks = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(SELETE_ALL,null);

        if(cursor.moveToFirst()){
            do{

                AccountBook accountBook = new AccountBook();
                accountBook.setBid(cursor.getInt(cursor.getColumnIndex("bid")));
                accountBook.setAccountBookName(cursor.getString(cursor.getColumnIndex("account_book_name")));
                accountBook.setBudgetAll(Double.valueOf(cursor.getString(cursor.getColumnIndex("budget_all"))));
                accountBook.setBudgetYear(Double.valueOf(cursor.getString(cursor.getColumnIndex("budget_year"))));
                accountBook.setBudgetMonth(Double.valueOf(cursor.getString(cursor.getColumnIndex("budget_month"))));
                accountBook.setBudgetWeek(Double.valueOf(cursor.getString(cursor.getColumnIndex("budget_week"))));
                accountBook.setOverviewBudget(cursor.getString(cursor.getColumnIndex("overview_budget")));

                accountBooks.add(accountBook);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return accountBooks;
    }

    /**
     * 更新账本设置
     * @param accountBook
     * @return
     */
    public AccountBook updateAccountBookSetting(AccountBook accountBook){
        sqLiteDatabase.execSQL(UPDATE_ACCOUNT_BOOK_SETTING,new String[]{
                accountBook.getAccountBookName(),
                String.valueOf(accountBook.getBudgetAll()),
                String.valueOf(accountBook.getBudgetYear()),
                String.valueOf(accountBook.getBudgetMonth()),
                String.valueOf(accountBook.getBudgetWeek()),
                accountBook.getOverviewBudget(),
                String.valueOf(accountBook.getBid())
        });
        return selectByBid(accountBook.getBid());
    }
}
