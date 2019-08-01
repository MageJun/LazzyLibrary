package com.android.kotlindemo.adapter;

import android.animation.ValueAnimator;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.ViewUtils;

import android.os.Build;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.android.kotlindemo.R;
import com.android.kotlindemo.model.bean.net.StoryBean;
import com.android.kotlindemo.model.bean.net.ThemeNewsBean;
import com.lazzy.common.lib.utils.L;
import com.lazzy.common.lib.utils.ResourceHelper;
import com.lazzy.common.lib.utils.ViewHelper;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseItemView;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseRecycleViewAdapter;
import com.lazzy.common.lib.widget.recyclerview.adapter.BaseViewHolder;
import com.lazzy.common.lib.widget.recyclerview.adapter.ItemViewManager;
import com.lazzy.common.lib.widget.recyclerview.decoration.ItemDividerDecoration;

import java.util.ArrayList;

public class TopStoryListItem2 extends BaseItemView<StoryBean> {

    private LinearLayoutManager layoutManager ;
    private SparseArray<BitmapFactory.Options> mOptionsArray = new SparseArray<>();
    private final ValueAnimator valueAnimator;

    public TopStoryListItem2(){
        valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(1f,1.15f);
        valueAnimator.setDuration(8*1000);
        valueAnimator.setRepeatCount(Integer.MAX_VALUE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setStartDelay(1*1000);
    }

    private BaseRecycleViewAdapter<ThemeNewsBean.EditorsBean> mAdapter = new BaseRecycleViewAdapter<ThemeNewsBean.EditorsBean>() {
        @Override
        public void onCreateMulitTypeItemView() {
            addItemView(new EditorItemView());
        }
    };

    private RecyclerView mListView;
    private Matrix matrix =null;
    private int repeatCount = 0;
    private float preValue = 0f;

    @Override
    public View getItemView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_top_story2,parent,false);
        mListView = view.findViewById(R.id.editorListView);
        layoutManager = new LinearLayoutManager(parent.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mListView.setLayoutManager(layoutManager);
        ItemDividerDecoration decoration = new ItemDividerDecoration(parent.getContext(),LinearLayoutManager.HORIZONTAL);
        decoration.setOffset(ResourceHelper.getDimen(6));
        mListView.addItemDecoration(decoration);
        mListView.setAdapter(mAdapter);
        matrix = new Matrix();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindVH(@NonNull BaseViewHolder holder, int position, StoryBean data) {
        L.i("TopStoryListItem2","TopStoryListItem2 onBindVH");
        valueAnimator.pause();
        valueAnimator.removeAllUpdateListeners();
        repeatCount = 0;
        preValue = 0f;
        final ImageView img = holder.getView(R.id.imgview);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Matrix matrix = new Matrix();
                if(value<preValue){
                    repeatCount++;
                }
                preValue = value;
                int type = repeatCount%4;
                L.i("TopStoryListItem2","onAnimationUpdate value = "+value +",repeatCount= "+repeatCount);

                switch (type){
                    case 0://第一遍，左上角
                        matrix.postScale(value,value);
                        break;
                    case 1://第四遍，右下角
                        matrix.postScale(value,value,img.getWidth(),img.getHeight());
                        break;
                    case 2://第三遍，左下角
                        matrix.postScale(value,value,0,img.getHeight());
                        break;
                    case 3://第二遍，右上角
                        matrix.postScale(value,value,img.getWidth(),0);
                        break;
                }
                img.setImageMatrix(matrix);

            }
        });
        valueAnimator.start();


        holder.setText(R.id.description,data.getTitle());

        ViewHelper.setImgview(img,data.getImage());
        if(data.getExtra()!=null){
            ArrayList<ThemeNewsBean.EditorsBean> editorsBeans =data.getExtra().getParcelableArrayList("editors");
            if(editorsBeans!=null){
                mAdapter.setData(editorsBeans);
            }
        }
    }

    @Override
    public int getItemViewType() {
        return ItemViewManager.obtainItemType();
    }

    @Override
    public boolean isForViewType(StoryBean storyBean, int position) {
        return storyBean.getMode()== StoryBean.Mode.IMG;
    }
}
