package com.blackorangejuice.songguojizhang.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block {
    public Block(){
        thatDayAccountItems = new ArrayList<>();
    }
    private Date date;
    private List<AccountItem> thatDayAccountItems;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<AccountItem> getThatDayAccountItems() {
        return thatDayAccountItems;
    }

    public void setThatDayAccountItems(List<AccountItem> thatDayAccountItems) {
        this.thatDayAccountItems = thatDayAccountItems;
    }
}
