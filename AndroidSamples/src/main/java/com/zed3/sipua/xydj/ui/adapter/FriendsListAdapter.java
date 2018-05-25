package com.zed3.sipua.xydj.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lw.android.commonui.customView.DragLayout;
import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.bean.FrindInfo;

import java.util.ArrayList;
import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsViewHolder> implements View.OnClickListener{
    private static final String TAG = FriendsListAdapter.class.getSimpleName();

    private List<FrindInfo> mData = new ArrayList<FrindInfo>();
    private RecyclerViewOnItemClickListener mListener;
    private List<DragLayout> mOpenLayout = new ArrayList<DragLayout>();

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG,"onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_draglayout,parent,false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        FriendsViewHolder vh = new FriendsViewHolder(view);
        vh.mDel.setOnClickListener(this);
        vh.mDragLayout.setSwipeChangeListener(new DragLayout.SwipeStatusChangeListener() {
            @Override
            public void onDraging(DragLayout dragLayout) {
                Log.i(TAG,"onCreateViewHolder  onDraging");
            }

            @Override
            public void onOpen(DragLayout dragLayout) {
                Log.i(TAG,"onCreateViewHolder  onOpen");
                mOpenLayout.add(dragLayout);
            }

            @Override
            public void onClose(DragLayout dragLayout) {
                Log.i(TAG,"onCreateViewHolder  onClose");
                mOpenLayout.remove(dragLayout);
            }

            @Override
            public void onStartOpen(DragLayout dragLayout) {
                Log.i(TAG,"onCreateViewHolder  onStartOpen");
                for (int i =0;i<mOpenLayout.size();i++){
                    mOpenLayout.get(i).close();
                }
                mOpenLayout.clear();
            }

            @Override
            public void onStartClose(DragLayout dragLayout) {
                Log.i(TAG,"onCreateViewHolder  onStartClose");
            }
        });
        return vh;
    }

    public void setListener(RecyclerViewOnItemClickListener listener){
        this.mListener = listener;
    }

    public void addData(List<FrindInfo> data){
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void delete(int position){
        this.mData.remove(position);
        notifyItemRemoved(position);
        //更新删除后，删除位置下方的所有Item的
        if(position<getItemCount()){
            //使用这种方式，更新的时候会闪屏；使用下面的方法，主要是payload不为NULL，就可以避免闪屏
            //也可以修改默认的动画效果
//           notifyItemRangeRemoved(position,getItemCount()-position);
            notifyItemRangeChanged(position,getItemCount()-position,"update");
        }

    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, int position) {
            Log.i(TAG,"onBindViewHolder");
            holder.mContent.setText(mData.get(position).getName());
            holder.mDel.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{
        TextView mContent;
        View mDel;
        DragLayout mDragLayout;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.content);
            mDel = itemView.findViewById(R.id.del);
            mDragLayout = itemView.findViewById(R.id.drag_layout);
        }
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG,"onClick");
        switch (v.getId()){
            case R.id.del:
                if(mListener!=null){
                    mListener.onItemClick(v, (int) v.getTag());
                }
                break;
        }
    }

    public interface RecyclerViewOnItemClickListener{

        void onItemClick(View v,int position);
    }

}
