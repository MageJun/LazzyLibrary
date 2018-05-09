package com.zed3.sipua.xydj.ui.group.bean;

import java.io.Serializable;

/**
 * 自建组成员信息模型类
 */
public class CustomGroupMemberInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String memberName;// 成员名称
	private String memberNum;// 成员号码
	private String Mgrade;// 成员级别
	private String memberStatus;// 成员状态 0为不在线，1为听讲，2为讲话，3为在线

	public CustomGroupMemberInfo() {
		memberName = "";
		memberNum = "";
		memberStatus = "0";
		Mgrade = "255";
	}
	public String getMgrade() {
		return Mgrade;
	}

	public void setMgrade(String mgrade) {
		Mgrade = mgrade;
	}
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	@Override
	public String toString() {
		return "CustomGroupMemberInfo [memberName=" + memberName
				+ ", memberNum=" + memberNum + ", memberStatus=" + memberStatus
				+", Mgrade=" + Mgrade +"]";
	}

}
