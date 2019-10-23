package com.example.du.hienglish.mvvm.model;

import java.io.Serializable;

public class Video extends BaseModel implements Serializable {
    private String vId;
    private String vName;
    private String vImage;

    public void setvId(String vId) {
        this.vId = vId;
    }

    public String getvId() {
        return vId;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getvName() {
        return vName;
    }

    public void setvImage(String vImage) {
        this.vImage = vImage;
    }

    public String getvImage() {
        return vImage;
    }
}
