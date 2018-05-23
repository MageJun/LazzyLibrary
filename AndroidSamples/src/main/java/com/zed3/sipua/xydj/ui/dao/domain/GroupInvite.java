package com.zed3.sipua.xydj.ui.dao.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tab_groupInvite")
public class GroupInvite {
    @DatabaseField (generatedId = true,columnName = "_ID")
    public int id = 0;
    @DatabaseField(columnName = "groupName")
    private String groupName;//群组名
    @DatabaseField(columnName = "inviteInfo")
    private String inviteInfo;//邀请信息
    @DatabaseField(columnName = "inviteUser")
    private String inviteUser;//邀请人
    @DatabaseField(columnName = "invitedUser")
    private String invitedUser;//被邀请人
    @DatabaseField(columnName = "time")
    private long time;//邀请时间
    @DatabaseField(columnName = "type")
    private INVITE_TYPE type = INVITE_TYPE.UNKNOW;//数据类型 0 收到的邀请 1 发出去的邀请

    public enum INVITE_TYPE{
        UNKNOW,
        RECEIVE,
        SEND;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getInviteInfo() {
        return inviteInfo;
    }

    public void setInviteInfo(String inviteInfo) {
        this.inviteInfo = inviteInfo;
    }

    public String getInviteUser() {
        return inviteUser;
    }

    public void setInviteUser(String inviteUser) {
        this.inviteUser = inviteUser;
    }

    public String getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(String invitedUser) {
        this.invitedUser = invitedUser;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public INVITE_TYPE getType() {
        return type;
    }

    public void setType(INVITE_TYPE type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GroupInvite{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", inviteInfo='" + inviteInfo + '\'' +
                ", inviteUser='" + inviteUser + '\'' +
                ", invitedUser='" + invitedUser + '\'' +
                ", time=" + time +
                ", type=" + type +
                '}';
    }
}
