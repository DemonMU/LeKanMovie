package com.project.rudy.lekanmovie.model;


/**
 * Created by maoyan on 2018/5/2.
 */

public class Comment  {
    private String content;
    private Movie movie;
    private User user;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
