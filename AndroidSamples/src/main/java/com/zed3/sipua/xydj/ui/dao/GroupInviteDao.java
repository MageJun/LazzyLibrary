package com.zed3.sipua.xydj.ui.dao;

import android.content.Context;

import com.zed3.sipua.xydj.ui.dao.domain.GroupInvite;

public class GroupInviteDao extends BaseDao{
    public GroupInviteDao(Context context) {
        super(context);
    }

    @Override
    protected void loadDao() {
        mDao = DatabaseHelper.getInstance(mContext).getDao(GroupInvite.class);
    }
}
