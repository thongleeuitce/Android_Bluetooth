package com.example.thongle.bluetooth_hc05;

/**
 * Created by thongle on 25/05/2017.
 */

public class Effect {

    private String title;
    private String code;
    private String content;

    public Effect(String title, String code, String content) {
        this.title = title;
        this.code = code;
        this.content = content;
    }
    public Effect(){
        this.title = "";
        this.code = "";
        this.content = "";
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

}

