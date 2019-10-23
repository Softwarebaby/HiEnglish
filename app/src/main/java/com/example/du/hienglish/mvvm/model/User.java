package com.example.du.hienglish.mvvm.model;

public class User {
    private int uId;
    private String uTel;
    private String uPsd;
    private String uName;
    private int uIde;
    private String uImg;
    private int uState;
    private int uLive;

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getuId() {
        return uId;
    }

    public void setuTel(String uTel) {
        this.uTel = uTel;
    }

    public String getuTel() {
        return uTel;
    }

    public void setuPsd(String uPsd) {
        this.uPsd = uPsd;
    }

    public String getuPsd() {
        return uPsd;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuName() {
        return uName;
    }

    public void setuIde(int uIde) {
        this.uIde = uIde;
    }

    public int getuIde() {
        return uIde;
    }

    public void setuImg(String uImg) {
        this.uImg = uImg;
    }

    public String getuImg() {
        return uImg;
    }

    public void setuState(int uState) {
        this.uState = uState;
    }

    public int getuState() {
        return uState;
    }

    public void setuLive(int uLive) {
        this.uLive = uLive;
    }

    public int getuLive() {
        return uLive;
    }
}
