package com.blackorangejuice.songguojizhang.bean;

public class ExportItem {
    // 该笔账是收入还是支出?收入值为income,支出值为expenditure
    private String incomeOrExpenditure;

    // 该笔账的金额
    private Double sum;

    // 标签名
    String tagName;

    // 账单备注
    String remark;

    // 时间
    String time;

    // 绑定事件标题
    private String eventItemTitle;

    public String getIncomeOrExpenditure() {
        return incomeOrExpenditure;
    }

    public void setIncomeOrExpenditure(String incomeOrExpenditure) {
        this.incomeOrExpenditure = incomeOrExpenditure;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEventItemTitle() {
        return eventItemTitle;
    }

    public void setEventItemTitle(String eventItemTitle) {
        this.eventItemTitle = eventItemTitle;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
