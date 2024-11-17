package com.blackorangejuice.songguojizhang.bean;

public class Tag {
    private Integer tid;
    // 标签名
    private String tagName;
    // （已弃用）标签对应的图片名
    private String tagImgName;
    // 标签背景色
    private Integer tagImgColor;

    public Integer getTagImgColor() {
        return tagImgColor;
    }

    public void setTagImgColor(Integer tagImgColor) {
        this.tagImgColor = tagImgColor;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagImgName() {
        return tagImgName;
    }

    public void setTagImgName(String tagImgName) {
        this.tagImgName = tagImgName;
    }
}
