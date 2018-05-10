package com.project.rudy.lekanmovie.view.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoyan on 2018/5/2.
 */

public abstract class BaseRecyclerViewAdapter<T, VH extends BaseRecyclerViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context mContext;
    private int mCurrentPosition;
    protected List<T> mData;
    private boolean isHasData;
    private boolean isShowAnim = true; // 是否播放item进入动画
    private int mLastPosition = -1;
    private boolean isLoading = true; // 是否显示loading placeHolder

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        return onCreate(LayoutInflater.from(mContext), parent, viewType);
    }


    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        mCurrentPosition = position;
        position = holder.getAdapterPosition();

        onBind(holder, position);

        Animator[] animators = getAnimators(holder.itemView);
        if (isShowAnim && animators != null && animators.length > 0
                && holder.getAdapterPosition() > mLastPosition) {
            if (animators.length > 1) {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(animators);
                animatorSet.start();
            } else {
                for (Animator animator : animators) {
                    animator.start();
                }
            }

            mLastPosition = holder.getAdapterPosition();
        }

    }

    private Animator[] getAnimators(View itemView) {
        return null;
    }


    @Override
    public int getItemCount() {
        if (mData == null || mData.isEmpty()) {
            isHasData = false;
            return 0;
        }
        return mData.size();
    }

    public void setData(List<T> data) {
        if (data != null) {
            mData = data;
            isHasData = true;
        } else {
            mData = new ArrayList<>();
            isHasData = false;
        }

        notifyItemRangeChanged(0, isHasData ? mData.size() - 1 : 1);

    }

    public void addData(List<T> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        int prePos = mData.size() - 1;
        if (data != null)
            mData.addAll(data);

        if (mData != null && !mData.isEmpty()) {
            isHasData = true;
        } else {
            isHasData = false;
        }

        notifyItemInserted(prePos + 1);
    }

    public void clearData() {
        if (mData != null) {
            mData.clear();
            isHasData = false;

            notifyDataSetChanged();
        }
    }

    public List<T> getData() {
        return mData;
    }

    public boolean isHasData() {
        return isHasData;
    }



    protected abstract VH onCreate(LayoutInflater inflater, ViewGroup parent, int viewType);

    protected abstract void onBind(VH holder, int position);
}
