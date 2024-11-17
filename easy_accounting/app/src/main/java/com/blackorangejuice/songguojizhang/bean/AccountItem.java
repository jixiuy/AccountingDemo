package com.blackorangejuice.songguojizhang.bean;

public class AccountItem {
    public static final String INCOME = "income";
    public static final String EXPENDITURE = "expenditure";
    public static final String IF_TRUE = "true";
    public static final String IF_FALSE = "false";


    private Integer aid;
    // 该笔账是收入还是支出?收入值为income,支出值为expenditure
    private String incomeOrExpenditure;
    // 该笔账的标签id
    private Integer tid;
    // 保存该笔账的标签
    private Tag tag;
    // 该笔账的金额
    private Double sum;
    // 备注
    private String remark;
    // 记账时间,存时间戳
    private Long accountTime;
    // 是否属于借入或者借出,存true或false
    private String ifBorrowOrLend;
    // 对应账本id
    private Integer bid;
    // 对应账本
    private AccountBook accountBook;
    // 绑定事件id
    private Integer eid;
    // 绑定事件
    private EventItem eventItem;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getIncomeOrExpenditure() {
        return incomeOrExpenditure;
    }

    public void setIncomeOrExpenditure(String incomeOrExpenditure) {
        this.incomeOrExpenditure = incomeOrExpenditure;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(Long accountTime) {
        this.accountTime = accountTime;
    }

    public String getIfBorrowOrLend() {
        return ifBorrowOrLend;
    }

    public void setIfBorrowOrLend(String ifBorrowOrLend) {
        this.ifBorrowOrLend = ifBorrowOrLend;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public AccountBook getAccountBook() {
        return accountBook;
    }

    public void setAccountBook(AccountBook accountBook) {
        this.accountBook = accountBook;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public EventItem getEventItem() {
        return eventItem;
    }

    public void setEventItem(EventItem eventItem) {
        this.eventItem = eventItem;
    }
}
