package com.zed3.sipua.ui.bean;

public class FrindInfo {

    private String name;
    private String staus;

    private String number;

    private String headImgUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    @Override
    public String toString() {
        return "FrindInfo{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", staus='" + staus + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                '}';
    }
}
