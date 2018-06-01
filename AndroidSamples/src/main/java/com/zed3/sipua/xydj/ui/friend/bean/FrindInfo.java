package com.zed3.sipua.xydj.ui.friend.bean;

import com.zed3.sipua.xydj.ui.friend.helper.GroupItemDecoration;

public class FrindInfo implements GroupItemDecoration.ItemDecorationData {

    private String name;
    private String status;

    private String number;

    private String headImgUrl;

    private String spellName;//拼音名字

    private String mTag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public void setTag(String mTag) {
        this.mTag = mTag;
    }

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    @Override
    public String toString() {
        return "FrindInfo{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", number='" + number + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", spellName='" + spellName + '\'' +
                ", mTag='" + mTag + '\'' +
                '}';
    }

    @Override
    public String getTag() {
        return mTag;
    }
}
