package com.common.widget.recyclerview.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 通用ItemView接口，
 * 主要三个方法：
 * onCreateViewHolder
 * onBindViewHolder
 * getItemViewType
 */
public interface IItemView<T> {

    /**
     * 作用同{@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     * @param parent
     * @param viewType
     * @return
     */
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    /**
     * 作用同{@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}
     * @param holder
     * @param position
     * @param data
     */
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, T data);


    /**
     * 返回ItemView的类型
     * @return
     */
    public int getItemViewType();


    /**
     * 检查当前数据是否是当前ItemView的类型
     * BaseItemView中默认返回true，如果用户可以根据需要进行判断
     * 例如：
     * Bean是用来展示的数据，不同的Bean有不同的状态
     * 假设状态值为0，需要显示文字，状态值为1需要显示图片
     * 那么需要分别在文字的IItemView#isForViewType和图片的IItemView#isForViewType中判断状态，然后决定返回true  or false
     * @param t 用来展示的数据类型
     * @param position
     * @return
     */
    public boolean isForViewType(T t,int position);
}
