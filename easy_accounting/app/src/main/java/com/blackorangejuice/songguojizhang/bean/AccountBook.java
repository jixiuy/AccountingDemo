package com.blackorangejuice.songguojizhang.bean;

public class AccountBook {

    public static final String SHOW_NONE = "showNone";
    public static final String SHOW_ALL = "showAll";
    public static final String SHOW_YEAR = "showYear";
    public static final String SHOW_MONTH = "showMonth";
    public static final String SHOW_WEEK = "showWeek";


    private Integer bid;

    // 账本名
    private String accountBookName;

    // 账本设置：结余
    private Double surplusAll;
    private Double surplusYear;
    private Double surplusMonth;
    private Double surplusWeek;

    // 账本设置：预算
    private Double budgetAll;
    private Double budgetYear;
    private Double budgetMonth;
    private Double budgetWeek;

    // 总花费
    private Double expenditureAll;
    private Double expenditureYear;
    private Double expenditureMonth;
    private Double expenditureWeek;

    // 总收入
    private Double incomeAll;
    private Double incomeYear;
    private Double incomeMonth;
    private Double incomeWeek;

    private String overviewBudget;
    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getAccountBookName() {
        return accountBookName;
    }

    public void setAccountBookName(String accountBookName) {
        this.accountBookName = accountBookName;
    }

    public Double getSurplusAll() {
        return surplusAll;
    }

    public void setSurplusAll(Double surplusAll) {
        this.surplusAll = surplusAll;
    }

    public Double getSurplusYear() {
        return surplusYear;
    }

    public void setSurplusYear(Double surplusYear) {
        this.surplusYear = surplusYear;
    }

    public Double getSurplusMonth() {
        return surplusMonth;
    }

    public void setSurplusMonth(Double surplusMonth) {
        this.surplusMonth = surplusMonth;
    }

    public Double getSurplusWeek() {
        return surplusWeek;
    }

    public void setSurplusWeek(Double surplusWeek) {
        this.surplusWeek = surplusWeek;
    }



    public Double getBudgetAll() {
        return budgetAll;
    }

    public void setBudgetAll(Double budgetAll) {
        this.budgetAll = budgetAll;
    }

    public Double getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(Double budgetYear) {
        this.budgetYear = budgetYear;
    }

    public Double getBudgetMonth() {
        return budgetMonth;
    }

    public void setBudgetMonth(Double budgetMonth) {
        this.budgetMonth = budgetMonth;
    }

    public Double getBudgetWeek() {
        return budgetWeek;
    }

    public void setBudgetWeek(Double budgetWeek) {
        this.budgetWeek = budgetWeek;
    }

    public String getOverviewBudget() {
        return overviewBudget;
    }

    public void setOverviewBudget(String overviewBudget) {
        this.overviewBudget = overviewBudget;
    }

    public Double getExpenditureAll() {
        return expenditureAll;
    }

    public void setExpenditureAll(Double expenditureAll) {
        this.expenditureAll = expenditureAll;
    }

    public Double getExpenditureYear() {
        return expenditureYear;
    }

    public void setExpenditureYear(Double expenditureYear) {
        this.expenditureYear = expenditureYear;
    }

    public Double getExpenditureMonth() {
        return expenditureMonth;
    }

    public void setExpenditureMonth(Double expenditureMonth) {
        this.expenditureMonth = expenditureMonth;
    }

    public Double getExpenditureWeek() {
        return expenditureWeek;
    }

    public void setExpenditureWeek(Double expenditureWeek) {
        this.expenditureWeek = expenditureWeek;
    }

    public Double getIncomeAll() {
        return incomeAll;
    }

    public void setIncomeAll(Double incomeAll) {
        this.incomeAll = incomeAll;
    }

    public Double getIncomeYear() {
        return incomeYear;
    }

    public void setIncomeYear(Double incomeYear) {
        this.incomeYear = incomeYear;
    }

    public Double getIncomeMonth() {
        return incomeMonth;
    }

    public void setIncomeMonth(Double incomeMonth) {
        this.incomeMonth = incomeMonth;
    }

    public Double getIncomeWeek() {
        return incomeWeek;
    }

    public void setIncomeWeek(Double incomeWeek) {
        this.incomeWeek = incomeWeek;
    }
}
