package com.project.rudy.lekanmovie.view.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.project.rudy.lekanmovie.R;
import com.project.rudy.lekanmovie.model.Movie;
import com.project.rudy.lekanmovie.view.activity.BaseActivity;
import com.project.rudy.lekanmovie.view.activity.MovieDetailActivity;
import com.project.rudy.lekanmovie.view.widget.AutoFitSizeTextView;
import com.project.rudy.lekanmovie.view.widget.TagGroup;

import java.util.List;


public class MovieListAdapter extends BaseRecyclerViewAdapter<Movie, MovieListAdapter.MovieVH> {

    public MovieListAdapter(List<Movie> movies) {
        setData(movies);
    }

    @Override
    protected MovieVH onCreate(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new MovieVH(inflater.inflate(R.layout.item_movie, parent, false));
    }

    @Override
    protected void onBind(final MovieVH holder, int position) {
        if (mData == null)
            return;
        final Movie model = mData.get(position);
        if (model == null)
            return;

        holder.mNameText.setText(model.getName());
       // holder.mNameText.setAlpha(position == 0 ? 1 : 0);
        Glide.with(mContext)
                .load(model.getPoster())
                .placeholder(R.drawable.pic_place_holder)
                .error(R.drawable.pic_place_holder)
                .into(holder.mPosterImg);
        holder.tagContainer.setTagData(model.getMovieTypeList(),
                R.color.colorAccent);
        if (model.getGrade() != null) {
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.gradeText.setVisibility(View.VISIBLE);
            holder.releaseDateText.setVisibility(View.GONE);
            holder.ratingBar.setRating((Float.valueOf(model.getGrade())) / 2.0f);
            holder.ratingBar.setFillColor(ContextCompat.getColor(
                    mContext, R.color.colorAccent));
            holder.gradeText.setText(model.getGrade());
        } else {
            holder.ratingBar.setVisibility(View.GONE);
            holder.gradeText.setVisibility(View.INVISIBLE);
            holder.releaseDateText.setVisibility(View.VISIBLE);
            holder.releaseDateText.setText(model.getPlayDate().getData());
            holder.releaseDateText.append(" 上映");
        }
        holder.castText.setText(model.getMovieStars());

        holder.mOpenInfoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mOpenInfoImg.setVisibility(View.GONE);
                holder.mInfoLayout.setVisibility(View.VISIBLE);
            }
        });
        holder.mPosterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 BaseActivity.navigate(mContext, MovieDetailActivity.class, model);
            }
        });
    }

    public static class MovieVH extends BaseRecyclerViewHolder {

        public AutoFitSizeTextView mNameText;
        public ImageView mOpenInfoImg;
        public View mInfoLayout;
        ImageView mPosterImg;
        TagGroup tagContainer;
        SimpleRatingBar ratingBar;
        TextView gradeText;
        TextView releaseDateText;
        TextView castText;

        MovieVH(View itemView) {
            super(itemView);

            mPosterImg = findView(R.id.movie_poster_img);
            mNameText = findView(R.id.movie_name_text);
            mOpenInfoImg = findView(R.id.movie_open_info_img);
            mInfoLayout = findView(R.id.movie_info_layout);
            tagContainer = findView(R.id.movie_type_container);
            ratingBar = findView(R.id.movie_rating_bar);
            gradeText = findView(R.id.movie_grade_text);
            releaseDateText = findView(R.id.movie_release_date_text);
            castText = findView(R.id.movie_cast_text);
        }
    }
}
