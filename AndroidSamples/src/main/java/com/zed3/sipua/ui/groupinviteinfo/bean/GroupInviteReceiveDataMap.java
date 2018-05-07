package com.zed3.sipua.ui.groupinviteinfo.bean;

import java.io.Serializable;
import java.util.List;

public class GroupInviteReceiveDataMap {


    private String mMapName;

    private int id;

    private List<GroupInviteReceiveData> mDatas;

    public String getmMapName() {
        return mMapName;
    }

    public void setmMapName(String mMapName) {
        this.mMapName = mMapName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<GroupInviteReceiveData> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<GroupInviteReceiveData> mDatas) {
        this.mDatas = mDatas;
    }


    @Override
    public String toString() {
        return "GroupInviteReceiveDataMap{" +
                "mMapName='" + mMapName + '\'' +
                ", id=" + id +
                ", mDatas=" + mDatas +
                '}';
    }

    public static class GroupInviteReceiveData implements Serializable {

        private String groupName;//群组名

        private String inviteInfo;//邀请信息

        private String inviteUser;//邀请人

        private long time;//邀请时间

        public enum Status{
            ACCEPTED,
            WAIT;
        }
        private Status status=Status.ACCEPTED;//邀请状态 0 未同意  1 已同意

        private boolean isMap;//

        private String mapName;

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

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public boolean isMap() {
            return isMap;
        }

        public void setMap(boolean map) {
            isMap = map;
        }


        public String getMapName() {
            return mapName;
        }

        public void setMapName(String mapName) {
            this.mapName = mapName;
        }

        @Override
        public String toString() {
            return "GroupInviteReceiveData{" +
                    "groupName='" + groupName + '\'' +
                    ", inviteInfo='" + inviteInfo + '\'' +
                    ", inviteUser='" + inviteUser + '\'' +
                    ", time=" + time +
                    ", status=" + status +
                    ", isMap=" + isMap +
                    ", mapName='" + mapName + '\'' +
                    '}';
        }
    }
}
