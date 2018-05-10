package com.zed3.sipua.xydj.ui.group;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;

import com.lw.demo.adnroid.samples.R;
import com.zed3.sipua.xydj.ui.BaseActivity;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;
import com.zed3.sipua.xydj.ui.group.bean.PttCustomGrp;

public class GroupInfoModifyActivity extends BaseActivity {
    private PttCustomGrp mGrp;
    private EditText mEdit;
    private int type;//类型 0修改组名  1修改昵称
    @Override
    public boolean isShowTitleCenter() {
        return true;
    }

    @Override
    public boolean isShowTitleLeftCancel() {
        return true;
    }

    @Override
    public boolean isShowTitleRightOk() {
        return true;
    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.xydj_activity_group_info_modify);
        Bundle data = getIntent().getExtras();
        if(data!=null){
            Object obj = data.get("grp");
            if(obj!=null &&obj instanceof PttCustomGrp){
                mGrp = (PttCustomGrp) obj;
            }
            type = data.getInt("type");
        }
        if(mGrp==null){
            return ;
        }

        setRightOkText(R.string.xydj_complete);
        initView();
    }

    private void initView() {
        mEdit = findViewById(R.id.modify_edt);
        if(type == 0){
            setTitleCenterText(R.string.xydj_group_info_modify_groupname);
            mEdit.setHint(R.string.xydj_group_info_modify_hint_groupname);
        }else if(type == 1){
            setTitleCenterText(R.string.xydj_group_info_modify_nickname);
            mEdit.setHint(R.string.xydj_group_info_modify_hint_nickname);
        }
    }

    @Override
    protected void onTitleLeftCancelClick() {
            finish();
    }

    @Override
    protected void onTitleRightOkClick() {
        Editable table = mEdit.getText();
        if (table != null && !TextUtils.isEmpty(table.toString())) {
            if(type == 0){
                //TODO 修改群名称,向服务器发送请求，弹进度条弹窗
                //wait
                mGrp.setGroupName(table.toString());

            }else if(type == 1){
                //TODO 修改群内昵称

            }

        }

        finish();
    }
}
