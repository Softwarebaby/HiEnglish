package com.example.du.hienglish.mvvm.model;

import java.io.Serializable;

public class DataInfo extends BaseModel implements Serializable {
    private int dId;
    private String dTitle;
    private String dExplain;
    private String dPicPath;
    private String dContent;

    public void setdId(int dId) {
        this.dId = dId;
    }

    public int getdId() {
        return dId;
    }

    public void setdTitle(String dTitle) {
        this.dTitle = dTitle;
    }

    public String getdTitle() {
        return dTitle;
    }

    public void setdExplain(String dExplain) {
        this.dExplain = dExplain;
    }

    public String getdExplain() {
        return dExplain;
    }

    public void setdPicPath(String dPicPath) {
        this.dPicPath = dPicPath;
    }

    public String getdPicPath() {
        return dPicPath;
    }

    public void setdContent(String dContent) {
        this.dContent = dContent;
    }

    public String getdContent() {
        return dContent;
    }
}
