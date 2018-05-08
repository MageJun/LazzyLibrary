package com.zed3.sipua.xydj.ui.group.bean;

/**
 * 群组成员信息
 */
public class GroupMemeberInfo {

    private String name;//成员姓名
    private String number;//成员号码
    private Status status = Status.OFFLINE;//在线状态
    private String headIconUrl;//头像地址

    public enum Status{
        ONLINE,
        OFFLINE;
    }
}

