package com.zed3.sipua.ui.model;

public final class GlobalService {

    private GlobalService(){}

    public static IGroupInviteService getGroupInviteService(){
            return  GroupInviteServiceFactory.getsInstance().newGroupInviteServiceInstance();
    }
}
