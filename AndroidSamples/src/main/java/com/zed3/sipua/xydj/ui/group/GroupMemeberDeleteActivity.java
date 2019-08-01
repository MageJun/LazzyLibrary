package com.zed3.sipua.xydj.ui.group;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.BaseActivity;
import com.zed3.sipua.xydj.ui.GridDividerDecoration2;
import com.common.widget.recyclerview.decoration.ItemDividerDecoration;
import com.zed3.sipua.xydj.ui.group.adapter.GroupDeleteMemeberListAdapter;
import com.zed3.sipua.xydj.ui.group.adapter.GroupDeleteSelectMemeberListAdapter;
import com.zed3.sipua.xydj.ui.group.adapter.OnItemCheckedListener;
import com.zed3.sipua.xydj.ui.group.bean.CustomGroupMemberInfo;
import com.zed3.sipua.xydj.ui.group.bean.PttCustomGrp;

import java.util.ArrayList;
import java.util.List;

public class GroupMemeberDeleteActivity extends BaseActivity {

    private static final String TAG = GroupMemeberDeleteActivity.class.getSimpleName();
    private RecyclerView mMemeberListRecyclerView,mSelectMemeberListView;
    private EditText mEditText;
    private View mSelectMoreIcon;
    private View mSelectLine;
    private GroupDeleteMemeberListAdapter mAllMemeberListAdapter;
    private GroupDeleteSelectMemeberListAdapter mDelelteMemeberListAdapter;
    private PttCustomGrp mGrp;
    private List<CustomGroupMemberInfo> mSelectList = new ArrayList<CustomGroupMemberInfo>();
    private int mSelectVisibleCount=0;
    private LinearLayoutManager mSelectListViewLM;

    @Override
    public boolean isShowTitleCenter() {
        return true;
    }

    @Override
    public boolean isShowLeftBtn() {
            return true;
    }

    @Override
    public boolean isShowRightBtn() {
        return true;
    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.xydj_activity_group_member_delete);

        Bundle data = getIntent().getExtras();
        if(data!=null){
            Object obj = data.get("grp");
            if(obj!=null && obj instanceof PttCustomGrp){
                mGrp = (PttCustomGrp) obj;
            }
        }

        initView();

        initData();

    }


    private void initView() {
        mMemeberListRecyclerView = findViewById(R.id.member_list_view);
        mSelectMemeberListView = findViewById(R.id.select_list_view);
        mEditText = findViewById(R.id.group_memeber_list_edit);
        mSelectLine = findViewById(R.id.select_line);
        mSelectMoreIcon = findViewById(R.id.select_more_icon);
        setTitleCenterText("移除群成员");
        setLeftBtnText("取消");
        setRightBtnText("移除");
        calcLeftDrawableSize();
    }

    private void initData() {

        int count =calcElementCount();
        boolean includeEdg = false;
        GridLayoutManager layoutManager = new GridLayoutManager(this,count);
        int itemMarginHeight = getResources().getDimensionPixelOffset(R.dimen.xydj_group_member_info_padding_rl);
        GridDividerDecoration2 decoration = new GridDividerDecoration2(this, LinearLayoutManager.HORIZONTAL,includeEdg);
        decoration.setRanksSameOffset(false);
        decoration.setRowOffset(itemMarginHeight);
        decoration.setSpanCount(count);
        mAllMemeberListAdapter = new GroupDeleteMemeberListAdapter(this);
        mAllMemeberListAdapter.setOnItemCheckedListener(mCheckedListener);
        mMemeberListRecyclerView.setLayoutManager(layoutManager);
        mMemeberListRecyclerView.setAdapter(mAllMemeberListAdapter);
        mMemeberListRecyclerView.addItemDecoration(decoration);
        if(mGrp!=null && mGrp.getMember_list()!=null){
            mAllMemeberListAdapter.setData(mGrp.getMember_list());
        }

        mDelelteMemeberListAdapter = new GroupDeleteSelectMemeberListAdapter(this);
        ItemDividerDecoration ide = new ItemDividerDecoration(this,LinearLayout.HORIZONTAL);
        ide.setOffset(itemMarginHeight);
        mSelectMemeberListView.addItemDecoration(ide);
        mSelectMemeberListView.setAdapter(mDelelteMemeberListAdapter);
        mSelectListViewLM=new LinearLayoutManager(this);
        mSelectListViewLM.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSelectMemeberListView.setLayoutManager(mSelectListViewLM);
        mDelelteMemeberListAdapter.setData(mSelectList);

        mSelectVisibleCount = calcSelectListVisableCount();
        updateSelectListLine();

    }

    private void updateSelectListLine() {
        if(mSelectList.size()==0){
            mSelectLine.setVisibility(View.GONE);
        }else{
            mSelectLine.setVisibility(View.VISIBLE);
            if(mSelectList.size()<=mSelectVisibleCount){
                mSelectMoreIcon.setVisibility(View.INVISIBLE);
            }else{
                mSelectMoreIcon.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置搜索图标的大小
     */
    private void calcLeftDrawableSize() {
        Drawable leftDrawable = mEditText.getCompoundDrawables()[0];
        if(leftDrawable!=null){
            int ldW = getResources().getDimensionPixelSize(R.dimen.xydj_group_member_list_search_icon_wh);
            int ldH = getResources().getDimensionPixelSize(R.dimen.xydj_group_member_list_search_icon_wh);
            leftDrawable.setBounds(0,0,ldW,ldH);
            mEditText.setCompoundDrawables(leftDrawable,mEditText.getCompoundDrawables()[1],mEditText.getCompoundDrawables()[2],mEditText.getCompoundDrawables()[3]);
        }
    }

    /**
     * 计算所有成员列表显示的列数
     * @return
     */
    private int calcElementCount(){
        int itemWidth = getResources().getDimensionPixelOffset(R.dimen.xydj_group_member_item_icon_wh);
        int itemMarginWidth = getResources().getDimensionPixelOffset(R.dimen.xydj_group_member_delete_item_div);
        int itemPaddingWidth =getResources().getDimensionPixelOffset(R.dimen.xydj_group_member_info_padding_rl);
        itemMarginWidth+=itemPaddingWidth;
        int paddingWidth =0;
        int width = getResources().getDisplayMetrics().widthPixels-paddingWidth*2;

        /**
         * 计算Item的个数，offsetCount=itemCount和-1;
         * 屏幕宽度width
         * item和offset的总量不能大于width
         * offsetCount*offset+itemCount*item<=width
         * offset*itemCount-offset+itemCount*item
         * itemCount = (width+offsett)/(item+offset)
         */
        int itemCount = (width+itemMarginWidth)/(itemWidth+itemMarginWidth);
        int count = itemCount;
        Log.i(TAG,"width = "+width+",itemWidth = "+itemWidth+", itemMarginWidth="+itemMarginWidth+" ,initData count = "+count);
        return count;
    }

    /**
     * 计算选中列表一面可以显示的Ite数
     */
    private int  calcSelectListVisableCount(){
        int width = getResources().getDisplayMetrics().widthPixels;
        //屏幕宽度-父布局左右padding值-右侧icon宽度-右侧margin/item宽度+item左右padding+间隔

        return 5;
    }


    private OnItemCheckedListener<CustomGroupMemberInfo> mCheckedListener = new OnItemCheckedListener<CustomGroupMemberInfo>() {
        @Override
        public void onItemCheckedChange(View view, int pos, boolean checked, CustomGroupMemberInfo data) {
                if(checked){
                    mSelectList.add(data);
                    mDelelteMemeberListAdapter.insertData(data);
                }else{
                    mSelectList.remove(data);
                    mDelelteMemeberListAdapter.removeData(data);
                }
                updateSelectListLine();
        }
    };

    public void onClick(View view){
        switch (view.getId()){
            case R.id.select_more_icon:
                int pos = mSelectListViewLM.findLastVisibleItemPosition();
                if(pos<mSelectList.size()-1)
                    mSelectMemeberListView.smoothScrollToPosition(pos+1);
                else{
                    mSelectMemeberListView.smoothScrollToPosition(pos);
                }
                break;
        }
    }

    @Override
    protected void onTitleLeftBtnClick() {
        finish();
    }

    @Override
    protected void onTitleRightBtnClick() {
        //TODO 删除自己组操作

        mGrp.getMember_list().removeAll(mSelectList);
        Intent result = new Intent();
        result.putExtra("grp",mGrp);
        setResult(10086,result);
        finish();
    }
}
