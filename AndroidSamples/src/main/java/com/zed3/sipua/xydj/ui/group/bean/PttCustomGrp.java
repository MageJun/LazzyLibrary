package com.zed3.sipua.xydj.ui.group.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义群组信息模型类
 */
public class PttCustomGrp implements Serializable {
	private static final long serialVersionUID = 1L;
	private String groupCreatorNum;// 创建者号码
	private String groupCreatorName;// 创建者名称
	private String groupNum;// 自建组号码
	private String groupName;// 自建组名称
	private String speakerIdle;// 对讲时话权方空闲超时时间，默认值20，单位：秒
	private String speakerTotal;// 对讲时话权方总讲话时长，默认值120，单位:秒
	private String groupIdleTotal;// 对讲时对讲组空闲时长，默认值900，单位:秒
	private String recordmode;// 对讲组的录音方式，0：全程录音，1：根据话权方录音标记，2：不录音，默认值为2
	private int level;// 对讲组的等级，等级的值为1至7，数字越低，表示级别越高
	private String report_heartbeat;// 终端状态上报周期，默认值1800,单位:秒，取值范围-1到3600值为-1时，完全不上报
	private List<CustomGroupMemberInfo> member_list;// 成员信息
	private int count;// 成员数量

	private int maxMember;//群成员上限
	private String expiryDate;//群组有效期

	public PttCustomGrp() {
		speakerIdle = "20";
		speakerTotal = "120";
		groupIdleTotal = "900";
		recordmode = "2";
		level = 7;
		report_heartbeat = "1800";
		member_list = new ArrayList<CustomGroupMemberInfo>();
		count = 0;
	}

	public String getGroupCreatorNum() {
		return groupCreatorNum;
	}

	public void setGroupCreatorNum(String groupCreatorNum) {
		this.groupCreatorNum = groupCreatorNum;
	}

	public String getGroupCreatorName() {
		return groupCreatorName;
	}

	public void setGroupCreatorName(String groupCreatorName) {
		this.groupCreatorName = groupCreatorName;
	}

	public String getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSpeakerIdle() {
		return speakerIdle;
	}

	public void setSpeakerIdle(String speakerIdle) {
		this.speakerIdle = speakerIdle;
	}

	public String getSpeakerTotal() {
		return speakerTotal;
	}

	public void setSpeakerTotal(String speakerTotal) {
		this.speakerTotal = speakerTotal;
	}

	public String getGroupIdleTotal() {
		return groupIdleTotal;
	}

	public void setGroupIdleTotal(String groupIdleTotal) {
		this.groupIdleTotal = groupIdleTotal;
	}

	public String getRecordmode() {
		return recordmode;
	}

	public void setRecordmode(String recordmode) {
		this.recordmode = recordmode;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	public String getReport_heartbeat() {
		return report_heartbeat;
	}

	public void setReport_heartbeat(String report_heartbeat) {
		this.report_heartbeat = report_heartbeat;
	}

	public List<CustomGroupMemberInfo> getMember_list() {
		return member_list;
	}

	public void setMember_list(List<CustomGroupMemberInfo> member_list) {
		this.member_list = new ArrayList<CustomGroupMemberInfo>(member_list);
	}

	public int getMaxMember() {
		return maxMember;
	}

	public void setMaxMember(int maxMember) {
		this.maxMember = maxMember;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "PttCustomGrp [groupCreatorNum=" + groupCreatorNum
				+ ", groupCreatorName=" + groupCreatorName + ", groupNum="
				+ groupNum + ", groupName=" + groupName + ", speakerIdle="
				+ speakerIdle + ", speakerTotal=" + speakerTotal
				+ ", groupIdleTotal=" + groupIdleTotal + ", recordmode="
				+ recordmode + ", level=" + level + ", report_heartbeat="
				+ report_heartbeat + ", member_list=" + member_list + "]";
	}

}