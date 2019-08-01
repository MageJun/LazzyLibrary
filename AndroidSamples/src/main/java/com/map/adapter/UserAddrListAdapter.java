package com.map.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lw.demo.android.samples.R;

import java.util.ArrayList;
import java.util.List;

public class UserAddrListAdapter extends RecyclerView.Adapter {

    private List<Data> mData =new ArrayList<>();
    private int selectPos = -1;

    public void setData(List<Data> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_addr_list_item,parent,false);
        MyVH vh = new MyVH(view);
        vh.itemView.setOnClickListener(mItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  MyVH){
            MyVH vh = (MyVH) holder;
            Data data= mData.get(position);
            vh.itemView.setTag(position);
            vh.mTVAddr.setText(data.getAddr());
            vh.mTVDetail.setText(data.getAddrDetail());
            vh.mTVName.setText(data.getName());
            vh.mTVTel.setText(data.getTel());
            if(position == selectPos){
                vh.mCheckImg.setImageResource(R.drawable.addr_list_icon_selected);
            }else{
                vh.mCheckImg.setImageResource(R.drawable.addr_list_icon_unselected);
            }
        }
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    private View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(),"点击",Toast.LENGTH_SHORT).show();
            int pos = (int) v.getTag();
            if(selectPos!=-1&&selectPos!=pos){
                setItemChecked(selectPos);
            }
            selectPos = pos;
            setItemChecked(pos);
        }
    };

    private void setItemChecked(int pos) {
        Data data = mData.get(pos);
        if(data!=null){
            data.setChecked(!data.isChecked());
            notifyItemChanged(pos);
        }
    }


    public static class MyVH extends RecyclerView.ViewHolder{
        TextView mTVName,mTVTel,mTVAddr,mTVDetail;
        ImageView mCheckImg;

        public MyVH(View itemView) {
            super(itemView);
            mTVName = itemView.findViewById(R.id.tv_name);
            mTVTel = itemView.findViewById(R.id.tv_tel);
            mTVAddr = itemView.findViewById(R.id.tv_addr);
            mTVDetail = itemView.findViewById(R.id.tv_addr_detail);
            mCheckImg = itemView.findViewById(R.id.checkImg);
        }
    }

    public static class Data{
        String name;
        String tel;
        String addr;
        String addrDetail;
        boolean isChecked;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getAddrDetail() {
            return addrDetail;
        }

        public void setAddrDetail(String addrDetail) {
            this.addrDetail = addrDetail;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "name='" + name + '\'' +
                    ", tel='" + tel + '\'' +
                    ", addr='" + addr + '\'' +
                    ", addrDetail='" + addrDetail + '\'' +
                    ", isChecked=" + isChecked +
                    '}';
        }
    }
}
