package com.blackorangejuice.songguojizhang.bean;

import com.blackorangejuice.songguojizhang.utils.globle.GlobalConstant;

public class SettingInfo {

    private Integer sid;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 密保问题
    private String passwordQuestion;
    // 密保答案
    private String passwordAnswer;
    // 是否启用登陆密码检查,存true或false
    private String ifEnablePasswordCheck;
    // 备份地址
    private String backupUrl;
    // 恢复地址
    private String restoreUrl;
    // 默认启动页面
    private String defultLaunchPage;
    // 默认新增页面
    private String defultAddPage;
    // 默认当前使用账本bid
    private Integer currentAccountBookBid;

    public static SettingInfo getDefultSettingInfo() {
        SettingInfo settingInfo = new SettingInfo();
        settingInfo.setUsername("");
        settingInfo.setPassword("");
        settingInfo.setPasswordQuestion("");
        settingInfo.setPasswordAnswer("");
        settingInfo.setIfEnablePasswordCheck("false");
        settingInfo.setBackupUrl("");
        settingInfo.setRestoreUrl("");
        settingInfo.setDefultLaunchPage(GlobalConstant.LAUNCH_PAGE_OVERVIEW_PAGE);
        settingInfo.setDefultAddPage(GlobalConstant.ADD_PAGE_ACCOUNT);
        settingInfo.setCurrentAccountBookBid(0);
        return settingInfo;
    }

    public String getBackupUrl() {
        return backupUrl;
    }

    public void setBackupUrl(String backupUrl) {
        this.backupUrl = backupUrl;
    }

    public String getRestoreUrl() {
        return restoreUrl;
    }

    public void setRestoreUrl(String restoreUrl) {
        this.restoreUrl = restoreUrl;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIfEnablePasswordCheck() {
        return ifEnablePasswordCheck;
    }

    public void setIfEnablePasswordCheck(String ifEnablePasswordCheck) {
        this.ifEnablePasswordCheck = ifEnablePasswordCheck;
    }

    public String getDefultLaunchPage() {
        return defultLaunchPage;
    }

    public void setDefultLaunchPage(String defultLaunchPage) {
        this.defultLaunchPage = defultLaunchPage;
    }

    public String getDefultAddPage() {
        return defultAddPage;
    }

    public void setDefultAddPage(String defultAddPage) {
        this.defultAddPage = defultAddPage;
    }

    public Integer getCurrentAccountBookBid() {
        return currentAccountBookBid;
    }

    public void setCurrentAccountBookBid(Integer currentAccountBookBid) {
        this.currentAccountBookBid = currentAccountBookBid;
    }

    public String getPasswordQuestion() {
        return passwordQuestion;
    }

    public void setPasswordQuestion(String passwordQuestion) {
        this.passwordQuestion = passwordQuestion;
    }

    public String getPasswordAnswer() {
        return passwordAnswer;
    }

    public void setPasswordAnswer(String passwordAnswer) {
        this.passwordAnswer = passwordAnswer;
    }
}
