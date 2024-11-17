package com.blackorangejuice.songguojizhang.transaction.home.list.in.account.edit;

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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountItem;
import com.blackorangejuice.songguojizhang.bean.EventItem;
import com.blackorangejuice.songguojizhang.bean.Tag;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.EventItemMapper;
import com.blackorangejuice.songguojizhang.db.mapper.TagMapper;
import com.blackorangejuice.songguojizhang.transaction.home.list.in.account.choose.ShowChosenEventPageActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.inputfilter.CashierInputFilter;
import com.blackorangejuice.songguojizhang.utils.view.TextViewDrawable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddEditAccountPageActivity extends EditAccountActivity {
    public static final String ARG = "arg";
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

    TagGridAdapter tagGridAdapter;
    TagMapper tagMapper;
    AccountItem accountItem;
    EazyDatabaseHelper songGuoDatabaseHelper;
    SimpleDateFormat simpleDateFormat;



    /**
     * 带参数的启动
     * @param context
     * @param arg
     */
    public static void startThisActivity(Context context,String arg){
        Intent intent = new Intent(context, AddEditAccountPageActivity.class);
        intent.putExtra(ARG,arg);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_account_page);

        findView();
        init();
        setListener();


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


        // 设置是否借出借入
        // 暂时设为false
        accountItem.setIfBorrowOrLend(AccountItem.IF_FALSE);

        // Bid
        accountItem.setBid(GlobalInfo.currentAccountBook.getBid());

        // 设置账本为当前全局账本
        accountItem.setAccountBook(GlobalInfo.currentAccountBook);


    }


    @Override
    public void init() {
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(ARG);
        // 递归事件开启的页面不能绑定事件,避免不必要的问题
        switch (stringExtra){
            // 如果是递归调用，禁用绑定按钮
            case GlobalConstant.DISABLE_BIND:
                bindEventLinearLayout.setEnabled(false);
                break;
        }
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(this);
        tagMapper = new TagMapper(songGuoDatabaseHelper);
        // 初始化记账项对象,若是从其他界面返回需要保存信息
        accountItem = new AccountItem();
        GlobalInfo.lastAddAccount = accountItem;


        // 时间设为当前时间
        Date date = new Date();
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        timeTextView.setText(simpleDateFormat.format(date));
        accountItem.setAccountTime(date.getTime());

        // 金额输入框过滤器
        CashierInputFilter.setCashierInputFilter(sumEditText);

        // tag网格
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);//第二个参数为网格的列数
        recyclerView.setLayoutManager(layoutManager);
        List<Tag> tags = tagMapper.selectAll();
        tagGridAdapter = new TagGridAdapter(tags, this);
        recyclerView.setAdapter(tagGridAdapter);
        // 设置默认tag
        accountItem.setTag(tags.get(0));
        setTagNameAndImg(tags.get(0));
        accountItem.setTid(tags.get(0).getTid());

        // 支出还是收入text View处理
        // 默认为支出记账
        accountItem.setIncomeOrExpenditure(AccountItem.EXPENDITURE);
        expenditureTextView.setTextColor(0xff323232);

        // 若通过eid能查找出event，则进行事件名显示
        EventItemMapper eventItemMapper = new EventItemMapper(songGuoDatabaseHelper);
        EventItem eventItem = eventItemMapper.selectByEid(accountItem.getEid());
        if (eventItem != null) {
            bindEventTitleTextView.setText(eventItem.getEventTitle());
        }

    }

    @Override
    public void findView() {
        // 返回标签
        backTextView = findViewById(R.id.activity_add_edit_account_page_back_textview);
        // 标签名
        tagNameTextView = findViewById(R.id.activity_add_edit_account_page_tag_name);
        // 标签图片
//        tagImageView = findViewById(R.id.activity_add_edit_account_page_tag_img);
        // 标签图片二代
        tagImageTextView = findViewById(R.id.activity_add_edit_account_page_tag_text);
        // 金额输入框
        sumEditText = findViewById(R.id.activity_add_edit_account_page_sum_edit);
        // 标签选择网格
        recyclerView = findViewById(R.id.activity_add_edit_account_page_tag_grid);
        // 收入与支出标签
        incomeTextView = findViewById(R.id.activity_add_edit_account_page_income_textview);
        expenditureTextView = findViewById(R.id.activity_add_edit_account_page_expenditure_textview);
        // 备注输入框
        remarkEditText = findViewById(R.id.activity_add_edit_account_page_remark_edit);
        // 时间
        timeTextView = findViewById(R.id.activity_add_edit_account_page_time);
        // 保存
        saveTextView = findViewById(R.id.activity_add_edit_account_page_save);
        // 绑定事件
        bindEventLinearLayout = findViewById(R.id.activity_add_edit_account_page_bind_event);
        // 绑定事件标题
        bindEventTitleTextView = findViewById(R.id.activity_add_edit_account_page_bind_event_title);

    }

    @Override
    public void setListener() {
        // 返回处理
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditAccountPageActivity.this.finish();
            }
        });

        // 时间选择
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cale1 = Calendar.getInstance();

                new DatePickerDialog(AddEditAccountPageActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

//                        Toast.makeText(getApplicationContext(), "你选择的是 " + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日", Toast.LENGTH_SHORT).show();
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

        incomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountItem.setIncomeOrExpenditure(AccountItem.INCOME);
                incomeTextView.setTextColor(0xff323232);
                expenditureTextView.setTextColor(0xff808080);
            }
        });
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
                accountItem = accountItemMapper.insertAccountItem(AddEditAccountPageActivity.this.accountItem);
                EasyUtils.showOneToast("保存成功");
                AddEditAccountPageActivity.this.finish();
            }
        });
        // 绑定按钮
        bindEventLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalInfo.lastAddAccount = AddEditAccountPageActivity.this.accountItem;
                ShowChosenEventPageActivity.startThisActivity(AddEditAccountPageActivity.this);
            }
        });
        bindEventLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEditAccountPageActivity.this);
                builder.setTitle("你确定要解除绑定吗");
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
    /**
     *
     */
    public void setTagNameAndImg(Tag tag) {
        accountItem.setTag(tag);
//        String tagImgName = tag.getTagImgName();
        tagNameTextView.setText(tag.getTagName());
        tagImageTextView.setText(tag.getTagName());
        tagImageTextView.setBackground(TextViewDrawable.getDrawable(tag.getTagImgColor()));
//        Bitmap bitmap = SongGuoUtils.getBitmapByFileName(this, "tag/" + tagImgName);
//        tagImageView.setImageBitmap(bitmap);


    }

}