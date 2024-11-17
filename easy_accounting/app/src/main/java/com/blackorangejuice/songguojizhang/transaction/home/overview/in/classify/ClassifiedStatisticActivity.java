package com.blackorangejuice.songguojizhang.transaction.home.overview.in.classify;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.blackorangejuice.songguojizhang.R;
import com.blackorangejuice.songguojizhang.db.EazyDatabaseHelper;
import com.blackorangejuice.songguojizhang.db.mapper.AccountItemMapper;
import com.blackorangejuice.songguojizhang.utils.basic.BasicActivity;
import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClassifiedStatisticActivity extends BasicActivity {

    PieChart pieChartIncome;
    PieChart pieChartExpenditure;
    EazyDatabaseHelper songGuoDatabaseHelper;
    AccountItemMapper accountItemMapper;
    TextView backTextView;
    TextView fromTimeTextView;
    TextView toTimeTextView;
    SimpleDateFormat simpleDateFormat;
    Date date0;
    Date date1;

    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, ClassifiedStatisticActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classified_statistic_page);
        findView();
        init();
        setListener();
    }

    @Override
    public void init() {
        songGuoDatabaseHelper = EazyDatabaseHelper.getSongGuoDatabaseHelper(this);
        accountItemMapper = new AccountItemMapper(songGuoDatabaseHelper);
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // 初始化的时候查找全部
        date0 = new Date(0);
        date1 = new Date();
        fromTimeTextView.setText(simpleDateFormat.format(date0));
        toTimeTextView.setText(simpleDateFormat.format(date1));
        drowChart();
    }

    @Override
    public void findView() {
        backTextView = findViewById(R.id.activity_classified_statistic_page_back_textview);
        pieChartIncome = findViewById(R.id.activity_classified_statistic_page_pie_chart_income);
        pieChartExpenditure = findViewById(R.id.activity_classified_statistic_page_pie_chart_expenditure);
        fromTimeTextView = findViewById(R.id.activity_classified_statistic_page_from_time);
        toTimeTextView = findViewById(R.id.activity_classified_statistic_page_to_time);
    }

    /**
     * 绘制图表
     */
    public void drowChart(){
        initPieChartByTime(GlobalConstant.INCOME);
        initPieChartByTime(GlobalConstant.EXPENDITURE);
    }
    @Override
    public void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassifiedStatisticActivity.this.finish();
            }
        });
        // 日期范围选择-从
        fromTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cale1 = Calendar.getInstance();

                new DatePickerDialog(ClassifiedStatisticActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        fromTimeTextView.setText(simpleDateFormat.format(theDateAfterParse));
                        // 账目设置时间
                        date0 = theDateAfterParse;
                        // 重新绘制图表
                        drowChart();
                    }
                }
                        , cale1.get(Calendar.YEAR)
                        , cale1.get(Calendar.MONTH)
                        , cale1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // 日期范围选择-到
        toTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cale1 = Calendar.getInstance();

                new DatePickerDialog(ClassifiedStatisticActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        toTimeTextView.setText(simpleDateFormat.format(theDateAfterParse));
                        // 账目设置时间
                        date1 = new Date(theDateAfterParse.getTime()+ (24 * 60 * 60 * 1000));
                        drowChart();
                    }
                }
                        , cale1.get(Calendar.YEAR)
                        , cale1.get(Calendar.MONTH)
                        , cale1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    /**
     * 按照时间初始化饼图
     * @param incomeOrExpenditure
     */
    public void initPieChartByTime(String incomeOrExpenditure){
        // 获取时间范围内的账单列表
        List<PieEntry> pieEntries = accountItemMapper.selectClassifiedPie(incomeOrExpenditure, date0, date1);
        initPieChart(incomeOrExpenditure,pieEntries);
    }

    /**
     * 初始化饼图
     * @param incomeOrExpenditure
     * @param entries
     */
    public void initPieChart(String incomeOrExpenditure,List<PieEntry> entries){
        // 外观
        // 使用的颜色
        final int[] PIE_COLORS = {
                Color.rgb(181, 194, 202), Color.rgb(129, 216, 200), Color.rgb(241, 214, 145),
                Color.rgb(108, 176, 223), Color.rgb(195, 221, 155), Color.rgb(251, 215, 191),
                Color.rgb(237, 189, 189), Color.rgb(172, 217, 243)
        };
        PieChart pieChart = null;
        switch (incomeOrExpenditure){
            case GlobalConstant.INCOME:
                pieChart = pieChartIncome;
                pieChart.setCenterText("收入");
                break;
            case GlobalConstant.EXPENDITURE:
                pieChart = pieChartExpenditure;
                pieChart.setCenterText("支出");
                break;
        }
        pieChart.setUsePercentValues(true);//使用百分比
        pieChart.setExtraOffsets(25, 10, 25, 25); //设置边距
        pieChart.setDragDecelerationFrictionCoef(0.95f);//设置摩擦系数（值越小摩擦系数越大）
        pieChart.setRotationEnabled(true);//是否可以旋转
        pieChart.setHighlightPerTapEnabled(true);//点击是否放大
        pieChart.setCenterTextSize(20f);//设置环中文字的大小
        pieChart.setDrawCenterText(true);//设置绘制环中文字
        pieChart.setRotationAngle(120f);//设置旋转角度
        pieChart.setTransparentCircleRadius(61f);//设置半透明圆环的半径,看着就有一种立体的感觉
        //这个方法为true就是环形图，为false就是饼图
        pieChart.setDrawHoleEnabled(true);
        //设置环形中间空白颜色是白色
        pieChart.setHoleColor(Color.WHITE);
        //设置半透明圆环的颜色
        pieChart.setTransparentCircleColor(Color.WHITE);
        //设置半透明圆环的透明度
        pieChart.setTransparentCircleAlpha(110);
        // 给饼图设置描述
        Description chartDescription = new Description();
        chartDescription.setText("");
        pieChart.setDescription(chartDescription);
        // 洞的半径
        pieChart.setHoleRadius(70f);
        // 透明圈半径
        pieChart.setTransparentCircleRadius(0f);
        // 是否显示标签
        pieChart.setDrawEntryLabels(false);
        // 数据
        // 饼图展示的数据集 使用数据库中查出的数据

        // 将数据集设置到饼图数据源
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setSliceSpace(3f);//设置饼块之间的间隔
        pieDataSet.setSelectionShift(5f);//设置饼块选中时偏离饼图中心的距离
        pieDataSet.setColors(PIE_COLORS);//设置饼块的颜色
        //设置数据显示方式
        pieDataSet.setValueLinePart1OffsetPercentage(80f);//数据连接线距图形片内部边界的距离，为百分数
        pieDataSet.setValueLinePart1Length(0.3f);
        pieDataSet.setValueLinePart2Length(0.4f);
        pieDataSet.setValueLineColor(Color.BLACK);//设置连接线的颜色
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData pieData = new PieData(pieDataSet);
        // 饼图使用百分比时显示百分号需要使用pieData.setValueFormatter(new PercentFormatter(pieChart));方法
        // 注意要将饼图对象传入,否则无效
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.DKGRAY);
        pieChart.setData(pieData);

        pieChart.invalidate();
    }
}