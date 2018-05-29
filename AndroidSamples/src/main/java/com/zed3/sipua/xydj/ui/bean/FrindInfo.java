package com.zed3.sipua.xydj.ui.bean;

import com.zed3.sipua.xydj.ui.ItemDividerDecoration;
import com.zed3.sipua.xydj.ui.friend.helper.GroupItemDecoration;

public class FrindInfo implements GroupItemDecoration.ItemDecorationData {

    private String name;
    private String staus;

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
                ", staus='" + staus + '\'' +
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
