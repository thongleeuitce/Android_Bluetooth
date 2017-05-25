package com.example.thongle.bluetooth_hc05;

import android.media.Image;

/**
 * Created by thongle on 25/05/2017.
 */

public class Effect {

    private int image;
    private String title;
    private String code;
    private String content;

    public Effect(String title, String code, String content, int image) {
        this.title = title;
        this.code = code;
        this.content = content;
        this.image = image;
    }
    public Effect(){
        this.title = "";
        this.code = "";
        this.content = "";
        this.image = 0;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

