package com.blackorangejuice.songguojizhang.bean;

import android.widget.TextView;

public class SearchItem {
    // 类型
    String type = "";
    // id
    Integer id = 0;
    // 标签名
    String tagNameOrEventTitle;
    // 账单金额
    String sum;
    // 账单备注/事件内容
    String remarkOrEventContent;
    // 时间
    Long time;

    public String getTagNameOrEventTitle() {
        return tagNameOrEventTitle;
    }

    public void setTagNameOrEventTitle(String tagNameOrEventTitle) {
        this.tagNameOrEventTitle = tagNameOrEventTitle;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getRemarkOrEventContent() {
        return remarkOrEventContent;
    }

    public void setRemarkOrEventContent(String remarkOrEventContent) {
        this.remarkOrEventContent = remarkOrEventContent;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
