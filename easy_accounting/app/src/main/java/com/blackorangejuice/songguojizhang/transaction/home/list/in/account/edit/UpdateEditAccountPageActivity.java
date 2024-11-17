package com.blackorangejuice.songguojizhang.transaction.home.list.in.account.edit;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.bean.Tag;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.EventItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.TagMapper;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.account.choose.ShowChosenEventPageActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.inputfilter.CashierInputFilter;
import com.blackorangejuice.songguojizhang.utils.view.TextViewDrawable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateEditAccountPageActivity extends EditAccountActivity {
    EditText sumEditText;
    RecyclerView recyclerView;
    TextView backTextView;
    TextView incomeTextView;
    TextView expenditureTextView;
//    ImageView tagImageView;
    TextView tagImageTextView;
    TextView tagNameTextView;
    EditText remarkEditText;
    TextView timeTextView;
    TextView deleteTextView;
    TextView saveTextView;
    LinearLayout bindEventLinearLayout;
    TextView bindEventTitleTextView;


    AccountItem accountItem;
    TagMapper tagMapper;
    TagGridAdapter tagGridAdapter;
    SimpleDateFormat simpleDateFormat;
    EazyDatabaseHelper songGuoDatabaseHelper;


    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, UpdateEditAccountPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_edit_account_page);

        findView();
        init();
        setListener();


    }

    public void getAccountInfoToAccountItem() {
        // 获取金额

        String sum = "";
        if (!EasyUtils.notEmptyString(sum = sumEditText.getText().toString())) {
            sum = "0.0";
        }

        accountItem.setSum(Double.valueOf(sum));

        // 设置tagId
        accountItem.setTid(accountItem.getTag().getTid());
        // 获取备注

        String remark = "";
        if (!EasyUtils.notEmptyString(remark = remarkEditText.getText().toString())) {
            remark = "无备注";
        }
        accountItem.setRemark(remark);


        // 设置账本为当前全局账本
        accountItem.setAccountBook(GlobalInfo.currentAccountBook);
        // Bid
        accountItem.setBid(GlobalInfo.currentAccountBook.getBid());


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 若通过eid能查找出event，则进行事件名显示
        EventItemMapper eventItemMapper = new EventItemMapper(songGuoDatabaseHelper);
        EventItem eventItem = eventItemMapper.selectByEid(accountItem.getEid());
        if (eventItem != null) {
            bindEventTitleTextView.setText(eventItem.getEventTitle());
        }

        tagGridAdapter.refreshTags(tagMapper.selectAll());
    }

    @Override
    public void init() {
        // 初始化记账项对象
        if (GlobalInfo.lastAddAccount == null) {
            EasyUtils.showOneToast("发生错误：空的记账项");
            UpdateEditAccountPageActivity.this.finish();
        } else {
            accountItem = GlobalInfo.lastAddAccount;
        }
        // 数据库操作对象
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(UpdateEditAccountPageActivity.this);
        tagMapper = new TagMapper(songGuoDatabaseHelper);
        // 取出金额显示
        sumEditText.setText(String.valueOf(accountItem.getSum()));
        // 取出备注显示
        remarkEditText.setText(accountItem.getRemark());

        // 时间设为accountitem中的时间
        Date date = new Date(accountItem.getAccountTime());
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        timeTextView.setText(simpleDateFormat.format(date));

        // 金额输入框过滤器
        CashierInputFilter.setCashierInputFilter(sumEditText);

        // tag网格
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);//第二个参数为网格的列数
        recyclerView.setLayoutManager(layoutManager);

        List<Tag> tags = tagMapper.selectAll();
        tagGridAdapter = new TagGridAdapter(tags, this);
        recyclerView.setAdapter(tagGridAdapter);
        // 设置tag为accountitem中的tag

        setTagNameAndImg(tagMapper.selectByTid(accountItem.getTid()));

        // 支出还是收入text View处理
        // 查看accountitem中的记账是啥
        String incomeOrExpenditure = accountItem.getIncomeOrExpenditure();
        switch (incomeOrExpenditure) {
            case AccountItem.INCOME:
                incomeTextView.setTextColor(0xff323232);
                expenditureTextView.setTextColor(0xff808080);
                break;
            case AccountItem.EXPENDITURE:
                expenditureTextView.setTextColor(0xff323232);
                incomeTextView.setTextColor(0xff808080);
                break;
        }
        EventItemMapper eventItemMapper = new EventItemMapper(songGuoDatabaseHelper);
        EventItem eventItem = eventItemMapper.selectByEid(accountItem.getEid());
        if (eventItem != null) {
            bindEventTitleTextView.setText(eventItem.getEventTitle());
        }

    }

    @Override
    public void findView() {
        // 返回标签
        backTextView = findViewById(R.id.activity_update_edit_account_page_back_textview);
        // 标签名
        tagNameTextView = findViewById(R.id.activity_update_edit_account_page_tag_name);
        // 标签图片
//        tagImageView = findViewById(R.id.activity_update_edit_account_page_tag_img);
        // 标签图片
        tagImageTextView = findViewById(R.id.activity_update_edit_account_page_tag_img_text);
        // 金额输入框
        sumEditText = findViewById(R.id.activity_update_edit_account_page_sum_edit);
        // 标签选择网格
        recyclerView = findViewById(R.id.activity_update_edit_account_page_tag_grid);
        // 收入与支出标签
        incomeTextView = findViewById(R.id.activity_update_edit_account_page_income_textview);
        expenditureTextView = findViewById(R.id.activity_update_edit_account_page_expenditure_textview);
        // 备注输入框
        remarkEditText = findViewById(R.id.activity_update_edit_account_page_remark_edit);
        // 时间
        timeTextView = findViewById(R.id.activity_update_edit_account_page_time);
        // 保存
        saveTextView = findViewById(R.id.activity_update_edit_account_page_save);
        // 删除
        deleteTextView = findViewById(R.id.activity_update_edit_account_page_delete);

        bindEventLinearLayout = findViewById(R.id.activity_update_edit_account_page_bind_event);
        bindEventTitleTextView = findViewById(R.id.activity_update_edit_account_page_bind_event_title);

    }

    @Override
    public void setListener() {
        // 返回处理
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateEditAccountPageActivity.this.finish();
            }
        });

        // 删除处理
        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateEditAccountPageActivity.this);
                builder.setTitle("确认删除该条吗?");
                builder.setMessage("删除后不可恢复");
                builder.setCancelable(true);
                builder.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EasyUtils.showOneToast("删除成功");
                        AccountItemMapper accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
                        accountItemMapper.deleteAccountItem(accountItem);
                        UpdateEditAccountPageActivity.this.finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        SongGuoUtils.showOneToast("取消成功");
                    }
                });
                builder.show();
            }
        });
        // 时间选择
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cale1 = Calendar.getInstance();

                new DatePickerDialog(UpdateEditAccountPageActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        String dataS = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        Date theDateAfterParse = new Date();
                        try {
                            theDateAfterParse = simpleDateFormat.parse(dataS);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        timeTextView.setText(simpleDateFormat.format(theDateAfterParse));
                        // 账目设置时间
                        accountItem.setAccountTime(theDateAfterParse.getTime());
                    }
                }
                        , cale1.get(Calendar.YEAR)
                        , cale1.get(Calendar.MONTH)
                        , cale1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // 收入
        incomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountItem.setIncomeOrExpenditure(AccountItem.INCOME);
                incomeTextView.setTextColor(0xff323232);
                expenditureTextView.setTextColor(0xff808080);
            }
        });
        // 支出
        expenditureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountItem.setIncomeOrExpenditure(AccountItem.EXPENDITURE);
                expenditureTextView.setTextColor(0xff323232);
                incomeTextView.setTextColor(0xff808080);
            }
        });

        // 保存按钮
        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccountInfoToAccountItem();
                AccountItemMapper accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
                // 返回带id的对象
                accountItem = accountItemMapper.updateAccountItem(UpdateEditAccountPageActivity.this.accountItem);
                EasyUtils.showOneToast("修改成功");
                UpdateEditAccountPageActivity.this.finish();
            }
        });
        // 绑定按钮
        bindEventLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalInfo.lastAddAccount = UpdateEditAccountPageActivity.this.accountItem;
                ShowChosenEventPageActivity.startThisActivity(UpdateEditAccountPageActivity.this);
            }
        });
        bindEventLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateEditAccountPageActivity.this);
                builder.setTitle("你确定要解除绑定吗");
//                builder.setMessage("删除后不可恢复");
                builder.setCancelable(true);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        accountItem.setEid(0);
                        bindEventTitleTextView.setText("");
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                return true;

            }
        });
    }

    @Override
    public void setTagNameAndImg(Tag tag) {
        accountItem.setTag(tag);
        tagNameTextView.setText(tag.getTagName());
//        String tagImgName = tag.getTagImgName();
//        Bitmap bitmap = SongGuoUtils.getBitmapByFileName(this, "tag/" + tagImgName);
//        tagImageView.setImageBitmap(bitmap);
        tagImageTextView.setText(tag.getTagName());
        tagImageTextView.setBackground(TextViewDrawable.getDrawable(tag.getTagImgColor()));
    }


}