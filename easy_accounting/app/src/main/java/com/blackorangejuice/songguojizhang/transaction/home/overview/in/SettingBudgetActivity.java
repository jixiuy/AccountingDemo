package com.blackorangejuice.songguojizhang.transaction.home.overview.in;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.bean.AccountBook;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountBookMapper;
import com.blackorangejuice.songguojizhang.utils.EasyUtils;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalInfo;
import com.blackorangejuice.songguojizhang.utils.inputfilter.CashierInputFilter;

/**
 * 设置预算的活动
 */
public class SettingBudgetActivity extends BasicActivity {
    public static final String All_BUDGET = "all_buget";
    public static final String YEAR_BUDGET = "year_buget";
    public static final String MONTH_BUDGET = "month_buget";
    public static final String WEEK_BUDGET = "week_buget";

    TextView backTextView;
    TextView informationTextView;
    TextView allBudgetTextView;
    TextView allSurplusTextView;
    TextView yearBudgetTextView;
    TextView yearSurplusTextView;
    TextView monthBudgetTextView;
    TextView monthSurplusTextView;
    TextView weekBudgetTextView;
    TextView weekSurplusTextView;
    Spinner overviewDisplaySpinner;
    LinearLayout overviewBudgetLinearLayout;

    AccountBookMapper accountBookMapper;
    EazyDatabaseHelper songGuoDatabaseHelper;


    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, SettingBudgetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_budget);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(SettingBudgetActivity.this);
        accountBookMapper = new AccountBookMapper(songGuoDatabaseHelper);
        // 刷新结余
        refreshSurplusData();

        allBudgetTextView.setText(String.valueOf(GlobalInfo.currentAccountBook.getBudgetAll()));
        allSurplusTextView.setText(String.valueOf(GlobalInfo.currentAccountBook.getSurplusAll()));
        yearBudgetTextView.setText(String.valueOf(GlobalInfo.currentAccountBook.getBudgetYear()));
        yearSurplusTextView.setText(String.valueOf(GlobalInfo.currentAccountBook.getSurplusYear()));
        monthBudgetTextView.setText(String.valueOf(GlobalInfo.currentAccountBook.getBudgetMonth()));
        monthSurplusTextView.setText(String.valueOf(GlobalInfo.currentAccountBook.getSurplusMonth()));
        weekBudgetTextView.setText(String.valueOf(GlobalInfo.currentAccountBook.getBudgetWeek()));
        weekSurplusTextView.setText(String.valueOf(GlobalInfo.currentAccountBook.getSurplusWeek()));
        // 设置被选择的展示项
        setSellictionSpinner();

    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_setting_budget_back_textview);
        informationTextView = findViewById(R.id.activity_setting_budget_information_textview);
        allBudgetTextView = findViewById(R.id.activity_setting_budget_all_budget_textview);
        allSurplusTextView = findViewById(R.id.activity_setting_budget_all_surplus_textview);
        yearBudgetTextView = findViewById(R.id.activity_setting_budget_year_budget_textview);
        yearSurplusTextView = findViewById(R.id.activity_setting_budget_year_surplus_textview);
        monthBudgetTextView = findViewById(R.id.activity_setting_budget_month_budget_textview);
        monthSurplusTextView = findViewById(R.id.activity_setting_budget_month_surplus_textview);
        weekBudgetTextView = findViewById(R.id.activity_setting_budget_week_budget_textview);
        weekSurplusTextView = findViewById(R.id.activity_setting_budget_week_surplus_textview);
        overviewDisplaySpinner = findViewById(R.id.activity_setting_budget_overview_display_setting_spinner);
        overviewBudgetLinearLayout = findViewById(R.id.activity_setting_budget_overview_display_setting_linear_layout);

    }

    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingBudgetActivity.this.finish();
            }
        });
        allBudgetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBudget(All_BUDGET);
            }
        });
        yearBudgetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBudget(YEAR_BUDGET);
            }
        });
        monthBudgetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBudget(MONTH_BUDGET);

            }
        });
        weekBudgetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBudget(WEEK_BUDGET);

            }
        });

        // 下拉列表设置监听器
        overviewDisplaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AccountBook accountBook = GlobalInfo.currentAccountBook;
                switch (position) {
                    case 0:
                        accountBook.setOverviewBudget(AccountBook.SHOW_NONE);
                        break;
                    case 1:
                        accountBook.setOverviewBudget(AccountBook.SHOW_ALL);
                        break;
                    case 2:
                        accountBook.setOverviewBudget(AccountBook.SHOW_YEAR);
                        break;
                    case 3:
                        accountBook.setOverviewBudget(AccountBook.SHOW_MONTH);
                        break;
                    case 4:
                        accountBook.setOverviewBudget(AccountBook.SHOW_WEEK);
                        break;
                }
                accountBookMapper.updateAccountBookSetting(accountBook);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setSellictionSpinner();
            }
        });

    }

    /**
     * 设置预算
     */
    private void setBudget(String setType) {
        // 弹窗输入框输入预算
        EditText editText = new EditText(SettingBudgetActivity.this);
        editText.setFilters(new InputFilter[]{new CashierInputFilter()});
        new AlertDialog.Builder(SettingBudgetActivity.this).setTitle("请输入预算")
                .setView(editText)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (EasyUtils.notEmptyString(editText.getText().toString())) {
                                    // 获取输入值
                                    Double budgetSum = Double.valueOf(editText.getText().toString());
                                    // 将全局账本中的预算设置为输入值
                                    AccountBook accountBook = GlobalInfo.currentAccountBook;
                                    AccountBookMapper accountBookMapper = new AccountBookMapper(EazyDatabaseHelper.getSongGuoDatabaseHelper(SettingBudgetActivity.this));
                                    // 判断当前实在设置哪个范围的预算
                                    switch (setType) {
                                        case All_BUDGET:
                                            accountBook.setBudgetAll(budgetSum);
                                            break;
                                        case YEAR_BUDGET:
                                            accountBook.setBudgetYear(budgetSum);
                                            break;
                                        case MONTH_BUDGET:
                                            accountBook.setBudgetMonth(budgetSum);
                                            break;
                                        case WEEK_BUDGET:
                                            accountBook.setBudgetWeek(budgetSum);
                                            break;

                                    }
                                    // 使用mapper更新
                                    accountBookMapper.updateAccountBookSetting(accountBook);
                                    // 刷新当前页面
                                    refreshCurrentPage();
                                } else {
                                    EasyUtils.showOneToast( "error");
                                }

                            }
                        }
                ).setNegativeButton("取消", null).show();

    }

    /**
     * 更新当前页面数据
     */
    public void refreshCurrentPage() {
        init();
    }

    /**
     * 刷新结余
     */
    public static void refreshSurplusData() {
        AccountBook accountBook = GlobalInfo.currentAccountBook;
        // 获取账本设置中的全部预算
        Double budgetAll = accountBook.getBudgetAll();
        // 获取已经消费的数目
        Double allExpenditure = accountBook.getExpenditureAll();
        accountBook.setSurplusAll(budgetAll - allExpenditure);
        // 获取账本设置中的年预算
        Double budgetYear = accountBook.getBudgetYear();
        // 获取已经消费的数目
        Double yearExpenditure = accountBook.getExpenditureYear();
        accountBook.setSurplusYear(budgetYear - yearExpenditure);
        // 获取账本设置中的月预算
        Double budgetMonth = accountBook.getBudgetMonth();
        // 获取已经消费的数目
        Double monthExpenditure = accountBook.getExpenditureMonth();
        accountBook.setSurplusMonth(budgetMonth - monthExpenditure);
        // 获取账本设置中的周预算
        Double budgetWeek = accountBook.getBudgetWeek();
        // 获取已经消费的数目
        Double weekExpenditure = accountBook.getExpenditureWeek();
        accountBook.setSurplusWeek(budgetWeek - weekExpenditure);
    }

    /**
     * 设置overview界面展示哪一个范围
     */
    private void setSellictionSpinner() {
        String overviewBudget = GlobalInfo.currentAccountBook.getOverviewBudget();
        switch (overviewBudget) {
            case AccountBook.SHOW_ALL:
                overviewDisplaySpinner.setSelection(1);
                break;
            case AccountBook.SHOW_YEAR:
                overviewDisplaySpinner.setSelection(2);
                break;
            case AccountBook.SHOW_MONTH:
                overviewDisplaySpinner.setSelection(3);
                break;
            case AccountBook.SHOW_WEEK:
                overviewDisplaySpinner.setSelection(4);
                break;
            case AccountBook.SHOW_NONE:
                overviewDisplaySpinner.setSelection(0);
                break;
        }
    }


}