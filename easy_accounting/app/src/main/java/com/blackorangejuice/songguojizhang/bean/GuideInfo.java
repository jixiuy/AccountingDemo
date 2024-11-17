package com.blackorangejuice.songguojizhang.bean;

public class GuideInfo {
    private String username;
    private String password;
    private String passwordQuestion;
    private String passwordAnswer;

    private String ifEnablePasswordCheck;
    private String accountBookName;

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

    public String getIfEnablePasswordCheck() {
        return ifEnablePasswordCheck;
    }

    public void setIfEnablePasswordCheck(String ifEnablePasswordCheck) {
        this.ifEnablePasswordCheck = ifEnablePasswordCheck;
    }


    public String getAccountBookName() {
        return accountBookName;
    }

    public void setAccountBookName(String accountBookName) {
        this.accountBookName = accountBookName;
    }


}
