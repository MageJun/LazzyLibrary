package com.lw.demo.android.samples.sharetran;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lw.demo.android.samples.R;

import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter {
    List<DataInfo> mData = new ArrayList<>();

    public void addData(List<DataInfo> dataInfos){
        this.mData.clear();
        this.mData.addAll(dataInfos);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType==3){
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_search_result_list_item_line,parent,false);
           MyVH2 myVH2 = new MyVH2(view);
           return myVH2;

       }else{
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_search_result_list_item,parent,false);
           MyVH vh = new MyVH(view);
           return vh;
       }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataInfo info = mData.get(position);

        if(getItemViewType(position)==3){
        }else{
            MyVH vh = (MyVH) holder;
            vh.mTitleTV.setText(info.getTitle());
            vh.mDetailTV.setText(info.getDetail());
            if(getItemViewType(position)==2){
                vh.mImg.setImageResource(R.drawable.map_search_result_addr_icon);
            }else {
                vh.mImg.setImageResource(R.drawable.map_search_result_his_icon);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (mData.get(position).getType()){
            case HISTORY:
                return 1;
            case LOCINFO:
                return 2;
            case LINE:
                return 3;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyVH extends RecyclerView.ViewHolder{

        TextView mTitleTV,mDetailTV;
        ImageView mImg;

        public MyVH(View itemView) {
            super(itemView);
            mTitleTV = itemView.findViewById(R.id.tv_title);
            mDetailTV = itemView.findViewById(R.id.tv_detail);
            mImg = itemView.findViewById(R.id.icon_img);
        }
    }

    public static class MyVH2 extends RecyclerView.ViewHolder{

        public MyVH2(View itemView) {
            super(itemView);
        }
    }

    public static class DataInfo{
        String imgUrl;
        String title;
        String detail;
        Type type;
        public static enum Type{
            HISTORY,
            LINE,
            LOCINFO;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "DataInfo{" +
                    "imgUrl='" + imgUrl + '\'' +
                    ", title='" + title + '\'' +
                    ", detail='" + detail + '\'' +
                    ", type=" + type +
                    '}';
        }
    }
}
