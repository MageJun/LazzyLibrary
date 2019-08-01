package com.zed3.sipua.xydj.ui.friend.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lw.android.commonui.customView.DragLayout;
import com.lw.demo.android.samples.R;
import com.zed3.sipua.xydj.ui.friend.bean.FrindInfo;
import com.zed3.sipua.xydj.ui.groupinviteinfo.helper.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsViewHolder> implements View.OnClickListener{
    private static final String TAG = FriendsListAdapter.class.getSimpleName();

    private List<FrindInfo> mData = new ArrayList<FrindInfo>();
    private RecyclerViewOnItemClickListener mListener;
    private List<DragLayout> mOpenLayout = new ArrayList<DragLayout>();
    private OnItemLongClickListener mOnItemLongClickListener;

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG,"onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.xydj_friend_list_item,parent,false);
        FriendsViewHolder vh = new FriendsViewHolder(view);
        vh.itemView.setOnClickListener(this);
        vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemLongClickListener!=null){
                    int pos = (int) v.getTag();
                    FrindInfo info = mData.get(pos);
                    mOnItemLongClickListener.onItemLongClick(v,info,pos);
                    return true;
                }
                return false;
            }
        });
        return vh;
    }

    public void setListener(RecyclerViewOnItemClickListener listener){
        this.mListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.mOnItemLongClickListener = listener;
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
            holder.mName.setText(mData.get(position).getName());
            holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        ImageView mHeadIcon;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tv_name);
            mHeadIcon = itemView.findViewById(R.id.img_icon);
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
