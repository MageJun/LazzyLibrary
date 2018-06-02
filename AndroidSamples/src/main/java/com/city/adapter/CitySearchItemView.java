package com.city.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.city.bean.CityData;
import com.common.widget.recyclerview.adapter.BaseItemView;
import com.common.widget.recyclerview.adapter.BaseViewHolder;
import com.common.widget.recyclerview.adapter.ItemViewManager;
import com.lw.demo.android.samples.R;

public class CitySearchItemView extends BaseItemView<CityData> {
    @Override
    public View getItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_search_item,parent,false);
        return view;
    }

    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, CityData data) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"搜索。。。", Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    public int getItemViewType() {
        return ItemViewManager.obtainItemType();
    }

    @Override
    public boolean isForViewType(CityData cityData, int position) {
        if(cityData.getmType() == CityData.CityDataType.SEARCH){
            return true;
        }
        return false;
    }

    private void calcLeftDrawableSize(EditText edt) {
        Drawable leftDrawable = edt.getCompoundDrawables()[0];
        if(leftDrawable!=null){
            int ldW = edt.getContext().getResources().getDimensionPixelSize(R.dimen.xydj_group_member_list_search_icon_wh);
            int ldH =  edt.getContext().getResources().getDimensionPixelSize(R.dimen.xydj_group_member_list_search_icon_wh);
            leftDrawable.setBounds(0,0,ldW,ldH);
            edt.setCompoundDrawables(leftDrawable,edt.getCompoundDrawables()[1],edt.getCompoundDrawables()[2],edt.getCompoundDrawables()[3]);
        }
    }
}
