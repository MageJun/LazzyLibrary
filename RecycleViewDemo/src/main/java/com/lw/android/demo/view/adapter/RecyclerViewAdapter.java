package com.lw.android.demo.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lw.android.demo.R;
import com.lw.android.demo.model.PersonalData;
import com.lw.android.demo.view.helper.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zed on 2018/4/25.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements ItemTouchHelperAdapter {


    private final String TAG = RecyclerViewAdapter.class.getSimpleName();
    private List<PersonalData> mData = new ArrayList<PersonalData>();
    private Context mContext;
    private int mWindowWidth;//屏幕宽
    private int mWindowHeight;//屏幕高

    private HashMap<Integer,BitmapInfo> mBitmapInfos = new HashMap<Integer,BitmapInfo>();

    public RecyclerViewAdapter(Context context){
        mContext = context;
        init();
    }

    private void init() {
//        mContext.getResources().getDisplayMetrics().heightPixels;
    }

    public void updateData(List<PersonalData> data){
        updateDataInner(data);
        notifyDataSetChanged();
    }

    private void updateDataInner(List<PersonalData> data) {
        this.mData.clear();
        this.mData.addAll(data);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //inflate 导入的View，类似getView方法。
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.receyclerview_item_text,viewGroup,false);
        //根据新建的View，新建ViewHolder
        ViewHolder vh = new ViewHolder(view);
        Log.i(TAG,"onBindViewHolder width = "+vh.mIM.getLayoutParams().width+", pos = "+i);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
            PersonalData data = mData.get(i);
        viewHolder.mTV.setText(data.getName());
        RequestOptions options = RequestOptions.placeholderOf(R.drawable.ic_launcher_background);
        RequestManager rm = Glide.with(mContext);
        rm.setDefaultRequestOptions(options);
//      rm.load(data.getImgUrl()).into(viewHolder.mIM);  //普通加载网络图片并显示的方式，下面的方式可以自定义修改ImageView的大小，实现瀑布流
        final int position = i;
        Log.i(TAG,"onBindViewHolder width = "+viewHolder.mIM.getLayoutParams().width+", pos = "+position);
        viewHolder.mIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"position = "+position,Toast.LENGTH_SHORT).show();
            }
        });
        rm.load(data.getImgUrl()).into(new ImageViewTarget<Drawable>(viewHolder.mIM) {
            @Override
            protected void setResource(@Nullable Drawable resource) {
                view.setImageDrawable(resource);
            }

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                //当Glid读取完Image信息，准备将图片加载到ImageView上时，会调用这个方法，在这个方法中将图片显示在ImageView上
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if(!mBitmapInfos.containsKey(position)){
                    mBitmapInfos.put(position, new BitmapInfo(resource.getIntrinsicHeight(),resource.getIntrinsicWidth()));
                }
                    //计算 修改ImageView的高,
                    Log.i(TAG,"onResourceReady width = "+params.width+", pos = "+position);
                    BitmapInfo info = mBitmapInfos.get(position);
                    if(info!=null){

                 Log.i(TAG,"onResourceReady bef view.width = "+view.getWidth()+",view.height="+view.getHeight()+" pos = "+position);
                 params.height = view.getWidth()*info.height/info.width;
                 Log.i(TAG,"onResourceReady aft params.width = "+params.width+",params.height="+params.height+" pos = "+position);

                    }
                super.onResourceReady(resource, transition);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onItemMoved(RecyclerView.ViewHolder from, RecyclerView.ViewHolder target) {
            int srcPos = from.getAdapterPosition();
            int desPos = target.getAdapterPosition();
            switchData(srcPos,desPos);


             notifyItemMoved(srcPos,desPos);
    }

    private void switchData(int srcPos,int desPos) {
        PersonalData src = mData.get(srcPos);
        PersonalData des = mData.get(desPos);
        mData.add(srcPos,des);
        mData.add(desPos,src);

        BitmapInfo srcInfo = mBitmapInfos.get(srcPos);
        BitmapInfo desInfo = mBitmapInfos.get(desPos);
        mBitmapInfos.put(srcPos,desInfo);
        mBitmapInfos.put(desPos,srcInfo);

    }


    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder) {
        mData.remove(viewHolder.getAdapterPosition());
        mBitmapInfos.remove(viewHolder.getAdapterPosition());
        notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTV;
        ImageView mIM;

        public ViewHolder(View itemView) {
            super(itemView);
            mTV = itemView.findViewById(R.id.textView);
            mIM = itemView.findViewById(R.id.imageView);
        }
    }

    private static class BitmapInfo{
        int height;
        int width;

        public BitmapInfo(int height, int width) {
            this.height = height;
            this.width = width;
        }
    }

}
