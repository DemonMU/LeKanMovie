package com.project.rudy.lekanmovie.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.project.rudy.lekanmovie.R;
import com.project.rudy.lekanmovie.model.Comment;
import com.project.rudy.lekanmovie.model.Movie;
import com.project.rudy.lekanmovie.view.widget.TagGroup;

import java.util.List;

/**
 * Created by maoyan on 2018/5/2.
 */

public class MainMovieCommentListAdapter extends BaseRecyclerViewAdapter<Comment, MainMovieCommentListAdapter.MainMovieVH> {

    public MainMovieCommentListAdapter(List<Comment> data) {
        setData(data);
    }

    @Override
    protected MainMovieVH onCreate(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new MainMovieVH(inflater.inflate(R.layout.item_main_movie, null));
    }

    @Override
    protected void onBind(MainMovieVH holder, int position) {
        if (mData == null)
            return;
        final Comment currentMovieComment = mData.get(position);
        holder.userNameTv.setText(currentMovieComment.getUser().getName());
//        holder.movieNameTv.setText(currentMovieComment.getMovie().getName());


    }

    public static class MainMovieVH extends BaseRecyclerViewHolder {
        TextView userNameTv;
        ImageView moviePosterIv;
        TextView movieNameTv;
        TagGroup movieTypeTg;
        SimpleRatingBar movieRateSrb;
        TextView movieReleaseDateTv;
        TextView movieGradeTv;
        TextView movieCastTv;
        TextView userCommentTv;

        public MainMovieVH(View itemView) {
            super(itemView);
            userNameTv = itemView.findViewById(R.id.user_name_text);
            moviePosterIv = itemView.findViewById(R.id.movie_poster_img);
            movieNameTv = itemView.findViewById(R.id.movie_name_text);
            movieTypeTg = itemView.findViewById(R.id.movie_type_container);
            movieRateSrb = itemView.findViewById(R.id.movie_rating_bar);
            movieReleaseDateTv = itemView.findViewById(R.id.movie_release_date_text);
            movieGradeTv = itemView.findViewById(R.id.movie_grade_text);
            movieCastTv = itemView.findViewById(R.id.movie_cast_text);
            userCommentTv = itemView.findViewById(R.id.user_comment_text);
        }
    }
}
