package com.project.rudy.lekanmovie.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by maoyan on 2018/5/2.
 */

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        //每次new ViewHolder的时候都要new SparseArray？ 会不会覆盖？
        mViews = new SparseArray<>();
    }

    public <T extends View> T findView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            if (view != null) {
                mViews.put(id, view);
            }
        }
        return (T) view;
    }

}
